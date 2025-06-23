package infra.entidades.principais.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.Cliente;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.infra.entidades.principais.services.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CartaoRepository cartaoRepo;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente();
        clienteValido.setName("João Silva");
        clienteValido.setCpf("12345678909");
        clienteValido.setDataNascimento(LocalDate.now().minusYears(20));
        clienteValido.setRendaMensal(3000.0);
    }
    
    @Test
    void criarCliente_deveSalvarClienteQuandoDadosValidos() {
   
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteValido);

  
        Cliente resultado = clienteService.criarCliente(clienteValido);


        assertNotNull(resultado);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void criarCliente_deveLancarExcecaoQuandoCpfJaExistente() {
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.of(clienteValido));
        assertThrows(IllegalArgumentException.class, () -> clienteService.criarCliente(clienteValido));
    }

    @Test
    void criarCliente_deveLancarExcecaoQuandoNomeInvalido() {
        clienteValido.setName("J");
        assertThrows(IllegalArgumentException.class, () -> clienteService.criarCliente(clienteValido));
    }

    @Test
    void criarCliente_deveLancarExcecaoQuandoIdadeInvalida() {
        clienteValido.setDataNascimento(LocalDate.now().minusYears(17));
        assertThrows(IllegalArgumentException.class, () -> clienteService.criarCliente(clienteValido));
    }
}
