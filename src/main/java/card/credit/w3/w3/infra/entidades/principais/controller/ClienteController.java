package card.credit.w3.w3.infra.entidades.principais.controller;

import card.credit.w3.w3.domain.models.Cartao;
import card.credit.w3.w3.domain.models.Cliente;
import card.credit.w3.w3.infra.entidades.principais.repository.CartaoRepository;
import card.credit.w3.w3.infra.entidades.principais.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService; 

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid Cliente cliente) {
        Cliente novoCliente = clienteService.criarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @Operation(description = "Busca o cliente pelo id")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Retorna o cliente"),
    		@ApiResponse(responseCode = "500", description = "Não existe um cliente com esse id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid Cliente cliente) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cpf}/cartoes")
    public ResponseEntity<List<CartaoResponse>> listarCartoesDoCliente(@PathVariable String cpf) {
        List<Cartao> cartoes = clienteService.buscarPorCpf(cpf);
        List<CartaoResponse> resposta = cartoes.stream()
            .map(CartaoResponse::new)
            .toList();
        return ResponseEntity.ok(resposta);
    }
    
    public record CartaoResponse(String numeroCartao, BigDecimal limite, LocalDateTime criadoEm) {
        public CartaoResponse(Cartao cartao) {
            this(cartao.getNumeroCartao(), cartao.getLimite(), cartao.getDataCriacaoCartao());
        }
    }
}