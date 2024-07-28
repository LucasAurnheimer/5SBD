package com.example.demo.Service;

import com.example.demo.DTO.ItemPedidoDTO;
import com.example.demo.Model.ItemPedido;
import com.example.demo.Repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    public List<ItemPedidoDTO> getAllItemPedidos() {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        return itemPedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemPedidoDTO getItemPedidoById(Long id) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemPedido não encontrado com ID: " + id));
        return convertToDTO(itemPedido);
    }

    public ItemPedidoDTO createItemPedido(ItemPedidoDTO itemPedidoDTO) {
        ItemPedido itemPedido = convertToEntity(itemPedidoDTO);
        ItemPedido savedItemPedido = itemPedidoRepository.save(itemPedido);
        return convertToDTO(savedItemPedido);
    }

    public ItemPedidoDTO updateItemPedido(Long id, ItemPedidoDTO itemPedidoDTO) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("ItemPedido não encontrado com ID: " + id);
        }
        ItemPedido itemPedido = convertToEntity(itemPedidoDTO);
        itemPedido.setId(id);
        ItemPedido updatedItemPedido = itemPedidoRepository.save(itemPedido);
        return convertToDTO(updatedItemPedido);
    }

    public void deleteItemPedido(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("ItemPedido não encontrado com ID: " + id);
        }
        itemPedidoRepository.deleteById(id);
    }

    public ItemPedidoDTO convertToDTO(ItemPedido itemPedido) {
        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
        itemPedidoDTO.setId(itemPedido.getId());
        itemPedidoDTO.setPedido(pedidoService.convertToDTO(itemPedido.getPedido()));
        itemPedidoDTO.setProduto(produtoService.convertToDTO(itemPedido.getProduto()));
        itemPedidoDTO.setQuantityPurchased(itemPedido.getQuantityPurchased());
        return itemPedidoDTO;
    }

    public ItemPedido convertToEntity(ItemPedidoDTO itemPedidoDTO) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(itemPedidoDTO.getId());
        itemPedido.setPedido(pedidoService.convertToEntity(itemPedidoDTO.getPedido()));
        itemPedido.setProduto(produtoService.convertToEntity(itemPedidoDTO.getProduto()));
        itemPedido.setQuantityPurchased(itemPedidoDTO.getQuantityPurchased());
        return itemPedido;
    }
}
