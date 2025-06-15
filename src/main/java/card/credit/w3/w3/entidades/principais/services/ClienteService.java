package card.credit.w3.w3.entidades.principais.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	 private final ClienteRepository clienteRepository;

	    public ClienteService(ClienteRepository clienteRepository) {
	        this.clienteRepository = clienteRepository;
	    }

	    @Transactional
	    public Cliente criarCliente(Cliente cliente) {
	        validarCliente(cliente);
	        clienteRepository.findByCpf(cliente.getCpf())
	        .ifPresent(c -> {
	            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF");
	        });
	        
	        Cliente cliente1 = new Cliente();
	        cliente1.setName(cliente.getName());
	        cliente1.setCpf(cliente.getCpf());
	        cliente1.setDataNascimento(cliente.getDataNascimento());
	        cliente1.setRendaMensal(cliente.getRendaMensal());
	        
	        return clienteRepository.save(cliente1);
	    }

	    private void validarCliente(Cliente cliente) {
	        validarCPF(cliente.getCpf());
	        validarIdade(cliente.getDataNascimento());
	        validarNome(cliente.getName());
	        validarRenda(cliente.getRendaMensal());
	    }

	    private void validarCPF(String cpf) {
	        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
	        
	
	        if (cpfNumerico.length() != 11) {
	            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
	        }
	        

	        if (cpfNumerico.matches("(\\d)\\1{10}")) {
	            throw new IllegalArgumentException("CPF inválido");
	        }
	    }

	    private void validarIdade(LocalDate dataNascimento) {
	        if (dataNascimento == null) {
	            throw new IllegalArgumentException("Data de nascimento é obrigatória");
	        }
	        
	        LocalDate hoje = LocalDate.now();
	        int idade = Period.between(dataNascimento, hoje).getYears();
	        
	        if (idade < 18) {
	            throw new IllegalArgumentException("Cliente deve ter pelo menos 18 anos de idade");
	        }
	    }
	    
	    private void validarNome(String nome) {
	        if (nome == null || nome.trim().isEmpty()) {
	            throw new IllegalArgumentException("Nome é obrigatório");
	        }
	        
	        if (nome.trim().length() < 2) {
	            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
	        }
	        
	     
	        if (!nome.matches("^[\\p{L} ]+$")) {
	            throw new IllegalArgumentException("Nome deve conter apenas letras e espaços");
	        }
	    }
	    private void validarRenda(BigDecimal rendaMensal) {
	        if (rendaMensal == null) {
	            throw new IllegalArgumentException("Renda mensal é obrigatória");
	        }
	        
	        if (rendaMensal.compareTo(BigDecimal.ZERO) < 0) {
	            throw new IllegalArgumentException("Renda mensal não pode ser negativa");
	        }
	        }

	 
	    @Transactional(readOnly = true)
	    public List<Cliente> listarTodos() {
	        return clienteRepository.findAll();
	    }
	    
	    @Transactional(readOnly = true)
	    public Cliente buscarPorId(Long id) {
	    	return clienteRepository.findById(id)
	    	        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
	    }

	    @Transactional
	    public Cliente atualizarCliente(Long id, Cliente cliente) {
	    	clienteRepository.findByCpf(cliente.getCpf())
	        .ifPresent(c -> {
	            if (!c.getId().equals(id)) {
	                throw new IllegalArgumentException("CPF já cadastrado para outro cliente");
	            }
	        });
	        
	        Cliente cliente1 = buscarPorId(id);
	        cliente1.setName(cliente.getName());
	        cliente1.setCpf(cliente.getCpf());
	        cliente1.setDataNascimento(cliente.getDataNascimento());
	        cliente1.setRendaMensal(cliente.getRendaMensal());
	        
	        return clienteRepository.save(cliente1);
	    }

	    @Transactional
	    public void deletarCliente(Long id) {
	        Cliente cliente = buscarPorId(id);
	        clienteRepository.delete(cliente);
	    }
}
