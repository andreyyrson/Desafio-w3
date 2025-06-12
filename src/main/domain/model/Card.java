package main.domain.model;

@Entity
@Table(name = "cartoes")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "titular_id", nullable = false)
    private Titular titular;

    private String numero;
    private String tipo; // "credito" ou "debito"
    private String status;
    private String senhaHash;

    private LocalDateTime criadoEm;
}
