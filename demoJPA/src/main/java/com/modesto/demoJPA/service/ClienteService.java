package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Cliente;
import com.modesto.demoJPA.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAllList(){
        log.debug("Request to get All Cliente");
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }

    public Cliente save(Cliente cliente) {
        log.debug("Request to save Memoria : {}", cliente);
        cliente = clienteRepository.save(cliente);
        return cliente;
    }

    public List<Cliente> saveAll(List<Cliente> clientes) {
        log.debug("Request to save Pessoa : {}", clientes);
        clientes = clienteRepository.saveAll(clientes);
        return clientes;
    }
}
