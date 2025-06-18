package card.credit.w3.w3.entidades.secundarias.services;

import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.secundarias.CancelamentoCartaoDefinitivo;
import card.credit.w3.w3.entidades.secundarias.repository.CancelamentoCartaoDefinitivoRepository;
import card.credit.w3.w3.enums.StatusCartao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CancelamentoCartaoDefinitivoService {
    private final CartaoRepository cartaoRepository;
    private final CancelamentoCartaoDefinitivoRepository cancelamentoCartaoDefinitivoRepository;


    @Transactional
    public void cancelarCartaoDefinitivo(String numeroCartao, String cpf, String motivoCancelamento) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if (cartao == null) {
            throw new IllegalArgumentException("Cartão não encontrado com o número: " + numeroCartao);
        }

        if (!cartao.getCliente().getCpf().equals(cpf)) {
            throw new IllegalArgumentException("CPF não corresponde ao titular do cartão");
        }

        //Adicionar validacao de Fatura

        cartao.setStatus(StatusCartao.CANCELADO);
        cartaoRepository.save(cartao);

        CancelamentoCartaoDefinitivo cancelamento = new CancelamentoCartaoDefinitivo();
        cancelamento.setNumeroCartao(numeroCartao);
        cancelamento.setCpfCliente(cpf);
        cancelamento.setMotivoCancelamento(motivoCancelamento);
        cancelamento.setDataCancelamento(java.time.LocalDate.now());
        cancelamento.setCartao(cartao);
        
        cancelamentoCartaoDefinitivoRepository.save(cancelamento);
    }
}
