package com.example.demo.Service;

import com.example.demo.DTO.ClienteDTO;
import com.example.demo.Model.Cliente;
import com.example.demo.Repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    public ClienteDTO getClienteById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(this::convertToDTO).orElse(null);
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> existingCliente = clienteRepository.findById(id);
        if (existingCliente.isPresent()) {
            Cliente cliente = existingCliente.get();
            cliente.setBuyerName(clienteDTO.getBuyerName());
            cliente.setBuyerEmail(clienteDTO.getBuyerEmail());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setBuyerPhoneNumber(clienteDTO.getBuyerPhoneNumber());
            Cliente updatedCliente = clienteRepository.save(cliente);
            return convertToDTO(updatedCliente);
        } else {
            return null;
        }
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setBuyerName(clienteDTO.getBuyerName());
        cliente.setBuyerEmail(clienteDTO.getBuyerEmail());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setBuyerPhoneNumber(clienteDTO.getBuyerPhoneNumber());
        return cliente;
    }

    ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setBuyerName(cliente.getBuyerName());
        clienteDTO.setBuyerEmail(cliente.getBuyerEmail());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setBuyerPhoneNumber(cliente.getBuyerPhoneNumber());
        return clienteDTO;
    }
}
