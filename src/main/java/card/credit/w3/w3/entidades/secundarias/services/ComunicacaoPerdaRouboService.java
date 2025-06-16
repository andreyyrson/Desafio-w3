package card.credit.w3.w3.entidades.secundarias.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.entidades.secundarias.ComunicacaoPerdaRoubo;
import card.credit.w3.w3.entidades.secundarias.repository.ComunicacaoPerdaRouboRepository;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoOcorrencia;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComunicacaoPerdaRouboService {

	private final CartaoRepository cartaoRepository;
    private final ComunicacaoPerdaRouboRepository comunicacaoRepository;
    private final ClienteRepository clienteRepository;

  
    @Transactional
    public void comunicarPerdaRoubo(String numeroCartao, String cpfCliente,
                                   TipoOcorrencia tipoOcorrencia, String numeroBoletimOcorrencia) {
        
       
        Cliente cliente = validarCliente(cpfCliente);
        
    
        Cartao cartao = validarCartao(numeroCartao, cliente);
        
      
        validarStatusCartao(cartao);
        
      
        processarBloqueio(cartao, numeroCartao, cpfCliente, tipoOcorrencia, numeroBoletimOcorrencia);
    }

    private Cliente validarCliente(String cpfCliente) {
        return clienteRepository.findByCpf(cpfCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o CPF informado"));
    }

    private Cartao validarCartao(String numeroCartao, Cliente cliente) {
        List<Cartao> cartoes = cartaoRepository.findByCliente(cliente);
        
        if (cartoes.isEmpty()) {
            throw new IllegalArgumentException("Nenhum cartão encontrado para este cliente");
        }
        
       
        Cartao cartao = cartoes.stream()
                .filter(c -> c.getNumeroCartao().equals(numeroCartao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cartão com número " + numeroCartao + " não encontrado para este cliente"));
        
        return cartao;
    }

    private void validarStatusCartao(Cartao cartao) {
        if (cartao.getStatus() != StatusCartao.ATIVO) {
            throw new IllegalStateException(
                String.format("Só é possível bloquear cartões com status ATIVO. Status atual: %s", 
                cartao.getStatus())
            );
        }
    }

    private void processarBloqueio(Cartao cartao, String numeroCartao, String cpfCliente,
                                TipoOcorrencia tipoOcorrencia, String numeroBoletimOcorrencia) {
        
    	
        cartao.setStatus(StatusCartao.BLOQUEADO_PERDA_ROUBO);
        cartaoRepository.save(cartao);

 
        ComunicacaoPerdaRoubo comunicacao = new ComunicacaoPerdaRoubo();
        comunicacao.setNumeroCartao(numeroCartao);
        comunicacao.setCpfCliente(cpfCliente);
        comunicacao.setTipoOcorrencia(tipoOcorrencia);
        comunicacao.setNumeroBoletimOcorrencia(numeroBoletimOcorrencia);
        comunicacao.setCartao(cartao);
        comunicacao.setDataRegistro(LocalDateTime.now());
        
        comunicacaoRepository.save(comunicacao);
    }
}
