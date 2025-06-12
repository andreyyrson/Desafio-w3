package card.credit.w3.w3.entidades.secundarias.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import card.credit.w3.w3.entidades.principais.Cartao;
import card.credit.w3.w3.entidades.principais.Cliente;
import card.credit.w3.w3.entidades.principais.dto.CartaoDTO;
import card.credit.w3.w3.entidades.principais.repositorios.CartaoRepositorio;
import card.credit.w3.w3.entidades.principais.repositorios.ClienteRepositorio;
import card.credit.w3.w3.entidades.secundarias.SolicitacaoCartao;
import card.credit.w3.w3.entidades.secundarias.dto.SolicitacaoCartaoDTO;
import card.credit.w3.w3.entidades.secundarias.repositorios.SolicitacaoCartaoRepositorio;
import card.credit.w3.w3.enums.BandeiraCartao;
import card.credit.w3.w3.enums.StatusCartao;
import card.credit.w3.w3.enums.TipoCartao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCartaoService {
    private final  SolicitacaoCartaoRepositorio soliticitacaoRepo;
    private final ClienteRepositorio clienteRepo;
    private final CartaoRepositorio cartaoRepo;
    
    
    @Transactional
    public String solicitarCartao(String cpf, String nome, LocalDate nascimento, 
            double renda, String tipo, String bandeira) {
        
    
        if (!validarCPF(cpf)) throw new RuntimeException("CPF inválido");
        if (!maiorDeIdade(nascimento)) throw new RuntimeException("Deve ter 18 anos ou mais");
        if (tipo.equals("CREDITO") && renda < 1000) throw new RuntimeException("Renda insuficiente para crédito");

       
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
        cartao.setNumero(numeroCartao);
        cartao.setTipo(TipoCartao.valueOf(tipo.toUpperCase()));
        cartao.setBandeira(BandeiraCartao.valueOf(bandeira.toUpperCase()));
        cartao.setDataCriacaoCartao(LocalDate.now());
        cartao.setStatus(StatusCartao.SOLICITADO);
        cartao.setCliente(cliente); 

        cartaoRepo.save(cartao); 
        SolicitacaoCartao solicitacao = new SolicitacaoCartao();
        solicitacao.setCliente(cliente);
        solicitacao.setDataSolicitacao(LocalDate.now());
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
