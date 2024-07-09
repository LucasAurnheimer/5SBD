package com.example.demo.Controller;

import com.example.demo.DTO.ItemPedidoDTO;
import com.example.demo.Service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public List<ItemPedidoDTO> getAllItemPedidos() {
        return itemPedidoService.getAllItemPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> getItemPedidoById(@PathVariable Long id) {
        ItemPedidoDTO itemPedidoDTO = itemPedidoService.getItemPedidoById(id);
        return ResponseEntity.ok(itemPedidoDTO);
    }

    @PostMapping
    public ResponseEntity<ItemPedidoDTO> createItemPedido(@RequestBody ItemPedidoDTO itemPedidoDTO) {
        ItemPedidoDTO newItemPedido = itemPedidoService.createItemPedido(itemPedidoDTO);
        return ResponseEntity.ok(newItemPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> updateItemPedido(@PathVariable Long id, @RequestBody ItemPedidoDTO itemPedidoDTO) {
        ItemPedidoDTO updatedItemPedido = itemPedidoService.updateItemPedido(id, itemPedidoDTO);
        return ResponseEntity.ok(updatedItemPedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        itemPedidoService.deleteItemPedido(id);
        return ResponseEntity.noContent().build();
    }
}
