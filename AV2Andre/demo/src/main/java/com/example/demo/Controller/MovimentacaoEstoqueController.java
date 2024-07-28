package com.example.demo.Controller;

import com.example.demo.DTO.MovimentacaoEstoqueDTO;
import com.example.demo.Service.MovimentacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Autowired
    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @PostMapping("/movimentar")
    public ResponseEntity<String> movimentarEstoque(@RequestBody MovimentacaoEstoqueDTO movimentacaoEstoqueDTO) {
        try {
            movimentacaoEstoqueService.salvarMovimentacaoEstoque(movimentacaoEstoqueDTO);
            return ResponseEntity.ok("Movimentação de estoque realizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao movimentar estoque: " + e.getMessage());
        }
    }
}
