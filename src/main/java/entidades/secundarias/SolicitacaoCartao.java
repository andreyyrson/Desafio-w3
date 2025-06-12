package entidades.secundarias;

import java.time.LocalDateTime;
import java.util.UUID;

import entidades.principais.Cartao;
import entidades.principais.Cliente;
import enums.BandeiraCartao;
import enums.StatusCartao;
import enums.TipoCartao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class SolicitacaoCartao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name = "cartao_id", unique = true)
	private Cartao cartaoGerado;
	
	private double rendaInformada;
	
	 @Enumerated(EnumType.STRING)
	 private TipoCartao tipo;
	    
	 @Enumerated(EnumType.STRING)
	 private BandeiraCartao bandeira;
	
	 @Enumerated(EnumType.STRING)
	 private StatusCartao status = StatusCartao.SOLICITADO;
	 
	 private LocalDateTime dataSolicitacao = LocalDateTime.now();
	 
	 private String numeroProvisorio;
}
