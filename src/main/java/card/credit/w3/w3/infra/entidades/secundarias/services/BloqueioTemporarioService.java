package card.credit.w3.w3.infra.entidades.secundarias.services;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.enums.MotivoBloqueioTemporario;
import card.credit.w3.w3.domain.models.enums.StatusCartao;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.secundarias.BloqueioTemporario;
import card.credit.w3.w3.infra.entidades.secundarias.repository.BloqueioTemporarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BloqueioTemporarioService {

    private final CartaoRepository cartaoRepository;
    private final BloqueioTemporarioRepository bloqueioTemporarioRepository;

    @Transactional
    public void bloquearTemporariamente(Long cartaoId, String cpf, MotivoBloqueioTemporario motivo) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado"));

        if (!cartao.getCliente().getCpf().equals(cpf)) {
            throw new IllegalArgumentException("CPF não corresponde ao titular do cartão");
        }

        if (cartao.getStatus() != StatusCartao.ATIVO) {
            throw new IllegalArgumentException("Cartão precisa estar ATIVO para bloqueio temporário");
        }

        
        cartao.setStatus(StatusCartao.BLOQUEADO_TEMPORARIO);
        cartaoRepository.save(cartao);

       
        BloqueioTemporario bloqueio = new BloqueioTemporario();
        bloqueio.setMotivo(motivo);
        bloqueio.setNumeroCartao(cartao.getNumeroCartao());
        bloqueio.setCpfCliente(cpf);
        bloqueio.setDataCriacao(LocalDateTime.now());
        bloqueio.setCartao(cartao);
        
        bloqueioTemporarioRepository.save(bloqueio);
    }
}