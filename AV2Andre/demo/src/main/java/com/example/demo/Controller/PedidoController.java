package com.example.demo.Controller;

import com.example.demo.DTO.PedidoDTO;
import com.example.demo.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO newPedido = pedidoService.createPedido(pedidoDTO);
        return ResponseEntity.ok(newPedido);
    }

}
