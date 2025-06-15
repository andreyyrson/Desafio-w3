package card.credit.w3.w3.entidades.secundarias.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.entidades.principais.repository.ClienteRepository;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoCartao;
import card.credit.w3.w3.entidades.secundarias.dto.SolicitacaoCartaoDTO;
import card.credit.w3.w3.entidades.secundarias.repository.SolicitacaoCartaoRepositorio;
import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCartaoService {
    private final  SolicitacaoCartaoRepositorio soliticitacaoRepo;
    private final ClienteRepository clienteRepo;
    private final CartaoRepository cartaoRepo;
    
    
    @Transactional
    public String solicitarCartao(String cpf, String nome, LocalDate nascimento, 
            BigDecimal renda, String tipo, String bandeira) {
        
    
        if (!validarCPF(cpf)) throw new RuntimeException("CPF inválido");
        if (!maiorDeIdade(nascimento)) throw new RuntimeException("Deve ter 18 anos ou mais");
        if ("CREDITO".equals(tipo) && renda.compareTo(new BigDecimal("1000.0")) < 0) {
            throw new RuntimeException("Renda insuficiente para crédito");
        }

       
        Cliente cliente = clienteRepo.findByCpf(cpf)
            .orElseGet(() -> {
                Cliente novoCliente = new Cliente();
                novoCliente.setCpf(cpf);
                novoCliente.setName(nome);
                novoCliente.setDataNascimento(nascimento);
                novoCliente.setRendaMensal(renda);
                return clienteRepo.save(novoCliente);
            });

 
        String numeroCartao = gerarNumeroCartao(bandeira);
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setTipo(TipoCartao.valueOf(tipo.toUpperCase()));
        cartao.setBandeira(BandeiraCartao.valueOf(bandeira.toUpperCase()));
        cartao.setDataCriacaoCartao(LocalDate.now());
        cartao.setStatus(StatusCartao.SOLICITADO);
        cartao.setCliente(cliente); 

        cartaoRepo.save(cartao); 
        SolicitacaoCartao solicitacao = new SolicitacaoCartao();
        solicitacao.setCliente(cliente);
        solicitacao.setCartaoGerado(cartao);
        solicitacao.setDataSolicitacao(LocalDate.now());
        solicitacao.setBandeira(BandeiraCartao.valueOf(bandeira.toUpperCase()));
        solicitacao.setRendaInformada(renda);
        solicitacao.setTipo(TipoCartao.valueOf(tipo.toUpperCase()));
        solicitacao.setStatus(StatusCartao.SOLICITADO);
        soliticitacaoRepo.save(solicitacao);

        return "Cartão " + numeroCartao + " criado com status: SOLICITADO";
    }
    

    private boolean validarCPF(String cpf) {
        return cpf != null && cpf.length() == 11; 
    }
    
    private boolean maiorDeIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears() >= 18;
    }
    
    private String gerarNumeroCartao(String bandeira) {
        Random rand = new Random();
        String prefixo = "";
        
        if (bandeira.equalsIgnoreCase("VISA")) prefixo = "4";
        else if (bandeira.equalsIgnoreCase("MASTERCARD")) prefixo = "5";
        else if (bandeira.equalsIgnoreCase("ELO")) prefixo = "6";
        
        StringBuilder numero = new StringBuilder(prefixo);
        for (int i = 0; i < 15; i++) {
            numero.append(rand.nextInt(10));
        }
        return numero.toString();
    }
    }
