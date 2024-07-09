package com.example.demo.Service;

import com.example.demo.DTO.ClienteDTO;
import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.Model.Cliente;
import com.example.demo.Model.Produto;
import com.example.demo.Repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> getAllProdutos() {
        return produtoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO getProdutoById(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return convertToDTO(produto);
    }

    public ProdutoDTO createProduto(ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        Produto savedProduto = produtoRepository.save(produto);
        return convertToDTO(savedProduto);
    }

    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setSku(produtoDTO.getSku());
        produto.setProductName(produtoDTO.getProductName());
        produto.setQuantity(produtoDTO.getQuantity());
        produto.setCurrency(produtoDTO.getCurrency());
        produto.setItemPrice(produtoDTO.getItemPrice());
        Produto updatedProduto = produtoRepository.save(produto);
        return convertToDTO(updatedProduto);
    }

    public void deleteProduto(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produtoRepository.delete(produto);
    }

    ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setSku(produto.getSku());
        dto.setProductName(produto.getProductName());
        dto.setQuantity(produto.getQuantity());
        dto.setCurrency(produto.getCurrency());
        dto.setItemPrice(produto.getItemPrice());
        return dto;
    }

    Produto convertToEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setSku(produtoDTO.getSku());
        produto.setProductName(produtoDTO.getProductName());
        produto.setQuantity(produtoDTO.getQuantity());
        produto.setCurrency(produtoDTO.getCurrency());
        produto.setItemPrice(produtoDTO.getItemPrice());
        return produto;
    }
}
