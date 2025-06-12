package entidades.secundarias.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.principais.Cliente;
import entidades.principais.dto.CartaoDTO;
import entidades.principais.repositorios.CartaoRepositorio;
import entidades.principais.repositorios.ClienteRepositorio;
import entidades.secundarias.SolicitacaoCartao;
import entidades.secundarias.dto.SolicitacaoCartaoDTO;
import entidades.secundarias.repositorios.SolicitacaoCartaoRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoCartaoService {
    private final  SolicitacaoCartaoRepositorio soliticitacaoRepo;
    private final ClienteRepositorio clienteRepo;
    private final CartaoRepositorio cartaoRepo;
    
    public String solicitarCartao(String cpf, String nome, LocalDate nascimento, 
            double renda, String tipo, String bandeira) {
    	
    	if (!validarCPF(cpf)) {
    		throw new RuntimeException("CPF inválido");
    		}

    	if (!maiorDeIdade(nascimento)) {
    		throw new RuntimeException("Deve ter 18 anos ou mais");
    		}

    	if (tipo.equals("CREDITO") && renda < 1000) {
    		throw new RuntimeException("Renda insuficiente para crédito");
    		}

    		
    	String numeroCartao = gerarNumeroCartao(bandeira);
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
