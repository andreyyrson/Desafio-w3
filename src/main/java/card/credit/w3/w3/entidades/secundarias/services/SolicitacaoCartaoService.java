package card.credit.w3.w3.entidades.secundarias.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Random;
import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoCartao;
import card.credit.w3.w3.entidades.secundarias.repository.SolicitacaoCartaoRepository;
import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCartaoService {
    
    private final SolicitacaoCartaoRepository solicitacaoRepo;
    private final ClienteRepository clienteRepo;
    private final CartaoRepository cartaoRepo;

    private static final BigDecimal RENDA_MINIMA_CREDITO = new BigDecimal("1000.00");
    private static final BigDecimal MULTIPLICADOR_LIMITE = new BigDecimal("2.5");
    private static final int ANOS_VALIDADE = 8;

    @Transactional
    public Cartao solicitarCartao(Long clienteId, TipoCartao tipo, BandeiraCartao bandeira) {
        Cliente cliente = buscarClienteValido(clienteId);
        validarRequisitosCartao(cliente, tipo);
        Cartao cartao = criarCartao(cliente, tipo, bandeira);
        registrarSolicitacao(cliente, cartao);
        
        return cartao;
    }

    private Cliente buscarClienteValido(Long clienteId) {
        return clienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    private void validarRequisitosCartao(Cliente cliente, TipoCartao tipo) {
        if (!maiorDeIdade(cliente.getDataNascimento())) {
            throw new IllegalArgumentException("Cliente deve ter 18 anos ou mais");
        }
        
        if (tipo == TipoCartao.CREDITO && 
            BigDecimal.valueOf(cliente.getRendaMensal()).compareTo(RENDA_MINIMA_CREDITO) < 0) {
            throw new IllegalArgumentException("Renda insuficiente para cartão de crédito");
        }
    }

    private Cartao criarCartao(Cliente cliente, TipoCartao tipo, BandeiraCartao bandeira) {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(gerarNumeroCartao(bandeira));
        cartao.setTipo(tipo);
        cartao.setBandeira(bandeira);
        cartao.setDataCriacaoCartao(LocalDateTime.now());
        cartao.setStatus(StatusCartao.SOLICITADO);
        cartao.setCliente(cliente);
        BigDecimal limite = BigDecimal.valueOf(cliente.getRendaMensal()).multiply(MULTIPLICADOR_LIMITE);
        cartao.setLimite(limite.setScale(2, RoundingMode.HALF_UP));
        cartao.setVencimento(LocalDate.now().plusYears(ANOS_VALIDADE));
        
        return cartaoRepo.save(cartao);
    }

    private void registrarSolicitacao(Cliente cliente, Cartao cartao) {
        SolicitacaoCartao solicitacao = new SolicitacaoCartao();
        solicitacao.setCliente(cliente);
        solicitacao.setCartaoGerado(cartao);
        solicitacao.setDataSolicitacao(LocalDate.now());
        solicitacao.setBandeira(cartao.getBandeira());
        solicitacao.setRendaInformada(cliente.getRendaMensal());
        solicitacao.setTipo(cartao.getTipo());
        solicitacao.setStatus(StatusCartao.SOLICITADO);
        
        solicitacaoRepo.save(solicitacao);
    }

    private String gerarNumeroCartao(BandeiraCartao bandeira) {
        Random rand = new Random();
        String prefixo = switch (bandeira) {
            case VISA -> "4";
            case MASTERCARD -> "5";
            case ELO -> "6";
            default -> "1";
        };

        StringBuilder numero = new StringBuilder(prefixo);
        for (int i = 0; i < 15; i++) {
            numero.append(rand.nextInt(10));
        }
        return numero.toString();
    }

    private boolean maiorDeIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears() >= 18;
    }
}