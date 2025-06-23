package infra.entidades.secundarias.services;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.Cliente;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.infra.entidades.secundarias.repository.CadastroRedefinicaoSenhaRepository;
import card.credit.w3.w3.infra.entidades.secundarias.services.CadastroRedefinicaoSenhaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastroRedefinicaoSenhaServiceTest {

    @InjectMocks
    private CadastroRedefinicaoSenhaService service;

    @Mock
    private ClienteRepository clienteRepo;

    @Mock
    private CartaoRepository cartaoRepo;

    @Mock
    private CadastroRedefinicaoSenhaRepository cadastroSenhaRepository;

    private Cliente cliente;
    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("12345678901");

        cartao = new Cartao();
        cartao.setId(1L);
        cartao.setCliente(cliente);
        cartao.setStatus(StatusCartao.SOLICITADO);
    }

    @Test
    void deveCadastrarSenhaComSucesso() {
        when(cartaoRepo.findById(1L)).thenReturn(Optional.of(cartao));

        service.cadastrarOuRedefinirSenha(1L, "12345678901", "741258", true);

        verify(cartaoRepo).save(any(Cartao.class));
        verify(cadastroSenhaRepository).save(any());
        assertEquals(StatusCartao.APROVADO, cartao.getStatus());
        assertNotNull(cartao.getSenhaHash());
    }

    @Test
    void deveLancarExcecaoSeSenhaNaoTem6Digitos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "123", true);
        });
        assertEquals("A senha deve conter exatamente 6 dígitos numéricos", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeSenhaSequencial() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "123456", true);
        });
        assertEquals("Senha não pode ser sequencial (ex: 123456)", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeSenhaRepetitiva() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "111111", true);
        });
        assertEquals("Senha não pode conter dígitos repetidos (ex: 111111)", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeCartaoNaoEncontrado() {
        when(cartaoRepo.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "839204", true); 
        });
        assertEquals("Cartão não encontrado", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeStatusNaoSolicitadoEmCadastro() {
        cartao.setStatus(StatusCartao.APROVADO);
        when(cartaoRepo.findById(1L)).thenReturn(Optional.of(cartao));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "839204", true);
        });
        assertEquals("Só é possível cadastrar senha para cartões com status SOLICITADO", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeCpfNaoBateComClienteDoCartao() {
        cliente.setCpf("99999999999");
        when(cartaoRepo.findById(1L)).thenReturn(Optional.of(cartao));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarOuRedefinirSenha(1L, "12345678901", "741258", true);
        });
        assertEquals("CPF não corresponde ao titular do cartão", ex.getMessage());
    }
}