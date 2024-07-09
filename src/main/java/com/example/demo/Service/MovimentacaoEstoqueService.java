package com.example.demo.Service;

import com.example.demo.DTO.MovimentacaoEstoqueDTO;
import com.example.demo.Model.MovimentacaoEstoque;
import com.example.demo.Repository.MovimentacaoEstoqueRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public MovimentacaoEstoque salvarMovimentacaoEstoque(MovimentacaoEstoqueDTO movimentacaoEstoqueDTO) {
        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setSku(movimentacaoEstoqueDTO.getSku());
        movimentacaoEstoque.setQuantidade(movimentacaoEstoqueDTO.getQuantidade());

        return movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }
}
