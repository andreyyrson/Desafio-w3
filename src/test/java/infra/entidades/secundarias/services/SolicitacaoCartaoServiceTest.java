package infra.entidades.secundarias.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import card.credit.w3.w3.domain.models.enums.BandeiraCartao;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.domain.models.enums.TipoCartao;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.infra.entidades.secundarias.SolicitacaoCartao;
import card.credit.w3.w3.infra.entidades.secundarias.repository.SolicitacaoCartaoRepository;
import card.credit.w3.w3.infra.entidades.secundarias.services.SolicitacaoCartaoService;
import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class SolicitacaoCartaoServiceTest {

    @InjectMocks
    private SolicitacaoCartaoService service;

    @Mock
    private ClienteRepository clienteRepo;

    @Mock
    private CartaoRepository cartaoRepo;

    @Mock
    private SolicitacaoCartaoRepository solicitacaoRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCartaoQuandoClienteValidoESolicitacaoValida() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setCpf("12345678901");
        cliente.setRendaMensal(2000.00);
        cliente.setDataNascimento(LocalDate.now().minusYears(25));

        Cartao cartaoSalvo = new Cartao();
        cartaoSalvo.setId(100L);
        cartaoSalvo.setCliente(cliente);
        cartaoSalvo.setStatus(StatusCartao.SOLICITADO);

        when(clienteRepo.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(cartaoRepo.save(any(Cartao.class))).thenReturn(cartaoSalvo);

        // Act
        Cartao resultado = service.solicitarCartao(clienteId, TipoCartao.CREDITO, BandeiraCartao.VISA);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusCartao.SOLICITADO, resultado.getStatus());
        verify(solicitacaoRepo).save(any(SolicitacaoCartao.class));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        Long clienteId = 99L;
        when(clienteRepo.findById(clienteId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.solicitarCartao(clienteId, TipoCartao.CREDITO, BandeiraCartao.VISA);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteMenorDeIdade() {
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setDataNascimento(LocalDate.now().minusYears(17));
        cliente.setRendaMensal(1500.00);

        when(clienteRepo.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.solicitarCartao(cliente.getId(), TipoCartao.CREDITO, BandeiraCartao.MASTERCARD);
        });

        assertEquals("Cliente deve ter 18 anos ou mais", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoRendaInsuficienteParaCredito() {
        Cliente cliente = new Cliente();
        cliente.setId(3L);
        cliente.setDataNascimento(LocalDate.now().minusYears(30));
        cliente.setRendaMensal(800.00);

        when(clienteRepo.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.solicitarCartao(cliente.getId(), TipoCartao.CREDITO, BandeiraCartao.ELO);
        });

        assertEquals("Renda insuficiente para cartão de crédito", ex.getMessage());
    }
}