package card.credit.w3.w3.entidades.secundarias.services;

import org.springframework.stereotype.Service;
import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.entidades.secundarias.CadastroRedefinicaoSenha;
import card.credit.w3.w3.entidades.secundarias.repository.CadastroRedefinicaoSenhaRepository;
import card.credit.w3.w3.enums.StatusCartao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CadastroRedefinicaoSenhaService {
    
    private final ClienteRepository clienteRepo;
    private final CartaoRepository cartaoRepo;
    private final CadastroRedefinicaoSenhaRepository cadastroSenhaRepository;

    @Transactional
    public void cadastrarOuRedefinirSenha(Long cartaoId, String cpf, String senha, boolean isCadastro) {
        if (senha == null || !senha.matches("\\d{6}")) {
            throw new IllegalArgumentException("A senha deve conter exatamente 6 dígitos numéricos");
        }
    
        
        if (isSenhaSequencial(senha)) {
            throw new IllegalArgumentException("Senha não pode ser sequencial (ex: 123456)");
        }
        
        if (isSenhaRepetitiva(senha)) {
            throw new IllegalArgumentException("Senha não pode conter dígitos repetidos (ex: 111111)");
        }

      
        Cartao cartao = cartaoRepo.findById(cartaoId)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado"));
        
        if (!cartao.getStatus().equals(StatusCartao.SOLICITADO) && isCadastro) {
            throw new IllegalArgumentException("Só é possível cadastrar senha para cartões com status SOLICITADO");
        }

        
        Cliente cliente = cartao.getCliente();
        if (!cliente.getCpf().equals(cpf)) {
            throw new IllegalArgumentException("CPF não corresponde ao titular do cartão");
        }

        cartao.setSenhaHash(senha);
       
        if (isCadastro) {
            cartao.setStatus(StatusCartao.APROVADO);
        }
        cartaoRepo.save(cartao);
        
    

       
        CadastroRedefinicaoSenha cadastroSenha = new CadastroRedefinicaoSenha();
        cadastroSenha.setSenhaCartao((senha));
        cadastroSenha.setDataCriacao(LocalDateTime.now());
        cadastroSenha.setCartao(cartao);
        cadastroSenha.setCpfCliente(cpf);
        
        
        cadastroSenhaRepository.save(cadastroSenha);
    }
    
    private boolean isSenhaSequencial(String senha) {
        int sequenciaAsc = 0;
        int sequenciaDesc = 0;
        
        for (int i = 1; i < senha.length(); i++) {
            int current = Character.getNumericValue(senha.charAt(i));
            int previous = Character.getNumericValue(senha.charAt(i-1));
            
            if (current == previous + 1) sequenciaAsc++;
            if (current == previous - 1) sequenciaDesc++;
        }
        
        return sequenciaAsc >= 4 || sequenciaDesc >= 4; 
    }

    private boolean isSenhaRepetitiva(String senha) {
        char primeiroDigito = senha.charAt(0);
        return senha.chars().allMatch(c -> c == primeiroDigito);
    }
}
