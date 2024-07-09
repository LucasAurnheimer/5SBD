package com.example.demo.Service;

import com.example.demo.DTO.ItemPedidoDTO;
import com.example.demo.DTO.MovimentacaoEstoqueDTO;
import com.example.demo.DTO.PedidoDTO;
import com.example.demo.Model.Pedido;
import com.example.demo.Model.Produto;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

     private final ProdutoRepository produtoRepository;
     
    public PedidoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public void debitarQuantidadeProduto(ItemPedidoDTO itemPedidoDTO) {
        Produto produto = produtoRepository.findBySku(itemPedidoDTO.getProduto().getSku());
        if (produto != null) {
            int quantidadePedido = itemPedidoDTO.getQuantityPurchased();
            int quantidadeAtual = produto.getQuantity();
            if (quantidadeAtual >= quantidadePedido) {
                int novaQuantidade = quantidadeAtual - quantidadePedido;
                produto.setQuantity(novaQuantidade);
                produtoRepository.save(produto);
            } else {
                realizarCompra(itemPedidoDTO.getProduto().getSku(), quantidadePedido);
            }
        } else {
            throw new RuntimeException("Produto com SKU " + itemPedidoDTO.getProduto().getSku() + " não encontrado.");
        }
    }
    private void realizarCompra(String sku, int quantidade) {
        System.out.println("Realizando compra do produto com SKU " + sku + ", quantidade: " + quantidade);
        Produto produto = produtoRepository.findBySku(sku);
        if (produto != null) {
            produto.setQuantity(quantidade);
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Erro ao realizar compra. Produto com SKU " + sku + " não encontrado.");
        }
    }

    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = convertToEntity(pedidoDTO);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return convertToDTO(savedPedido);
    }

    public PedidoDTO getPedidoById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(this::convertToDTO).orElse(null);
    }

    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        Optional<Pedido> existingPedido = pedidoRepository.findById(id);
        if (existingPedido.isPresent()) {
            Pedido pedido = existingPedido.get();
            pedido.setPurchaseDate(pedidoDTO.getPurchaseDate());
            pedido.setPaymentsDate(pedidoDTO.getPaymentsDate());
            pedido.setBuyerEmail(pedidoDTO.getBuyerEmail());
            pedido.setBuyerName(pedidoDTO.getBuyerName());
            pedido.setCpf(pedidoDTO.getCpf());
            pedido.setBuyerPhoneNumber(pedidoDTO.getBuyerPhoneNumber());
            pedido.setShipServiceLevel(pedidoDTO.getShipServiceLevel());
            pedido.setRecipientName(pedidoDTO.getRecipientName());
            pedido.setShipAddress1(pedidoDTO.getShipAddress1());
            pedido.setShipAddress2(pedidoDTO.getShipAddress2());
            pedido.setShipAddress3(pedidoDTO.getShipAddress3());
            pedido.setShipCity(pedidoDTO.getShipCity());
            pedido.setShipState(pedidoDTO.getShipState());
            pedido.setShipPostalCode(pedidoDTO.getShipPostalCode());
            pedido.setShipCountry(pedidoDTO.getShipCountry());
            pedido.setIossNumber(pedidoDTO.getIossNumber());
            Pedido updatedPedido = pedidoRepository.save(pedido);
            return convertToDTO(updatedPedido);
        } else {
            return null;
        }
    }


    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    // Método para converter Pedido (Entidade) para PedidoDTO
    public PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        //pedidoDTO.setOrderId(pedido.getId());
        pedidoDTO.setPurchaseDate(pedido.getPurchaseDate());
        pedidoDTO.setPaymentsDate(pedido.getPaymentsDate());
        pedidoDTO.setBuyerEmail(pedido.getBuyerEmail());
        pedidoDTO.setBuyerName(pedido.getBuyerName());
        pedidoDTO.setCpf(pedido.getCpf());
        pedidoDTO.setBuyerPhoneNumber(pedido.getBuyerPhoneNumber());
        pedidoDTO.setShipServiceLevel(pedido.getShipServiceLevel());
        pedidoDTO.setRecipientName(pedido.getRecipientName());
        pedidoDTO.setShipAddress1(pedido.getShipAddress1());
        pedidoDTO.setShipAddress2(pedido.getShipAddress2());
        pedidoDTO.setShipAddress3(pedido.getShipAddress3());
        pedidoDTO.setShipCity(pedido.getShipCity());
        pedidoDTO.setShipState(pedido.getShipState());
        pedidoDTO.setShipPostalCode(pedido.getShipPostalCode());
        pedidoDTO.setShipCountry(pedido.getShipCountry());
        pedidoDTO.setIossNumber(pedido.getIossNumber());
        return pedidoDTO;
    }

    // Método para converter PedidoDTO para Pedido (Entidade)
    public Pedido convertToEntity(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        //pedido.setId(pedidoDTO.getOrderId());
        pedido.setPurchaseDate(pedidoDTO.getPurchaseDate());
        pedido.setPaymentsDate(pedidoDTO.getPaymentsDate());
        pedido.setBuyerEmail(pedidoDTO.getBuyerEmail());
        pedido.setBuyerName(pedidoDTO.getBuyerName());
        pedido.setCpf(pedidoDTO.getCpf());
        pedido.setBuyerPhoneNumber(pedidoDTO.getBuyerPhoneNumber());
        pedido.setShipServiceLevel(pedidoDTO.getShipServiceLevel());
        pedido.setRecipientName(pedidoDTO.getRecipientName());
        pedido.setShipAddress1(pedidoDTO.getShipAddress1());
        pedido.setShipAddress2(pedidoDTO.getShipAddress2());
        pedido.setShipAddress3(pedidoDTO.getShipAddress3());
        pedido.setShipCity(pedidoDTO.getShipCity());
        pedido.setShipState(pedidoDTO.getShipState());
        pedido.setShipPostalCode(pedidoDTO.getShipPostalCode());
        pedido.setShipCountry(pedidoDTO.getShipCountry());
        pedido.setIossNumber(pedidoDTO.getIossNumber());
        return pedido;
    }
}
