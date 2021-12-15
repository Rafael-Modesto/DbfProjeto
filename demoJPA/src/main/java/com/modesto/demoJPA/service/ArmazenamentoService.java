package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Armazenamento;
import com.modesto.demoJPA.repository.ArmazenamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArmazenamentoService {

    private final Logger log = LoggerFactory.getLogger(ArmazenamentoService.class);

    private final ArmazenamentoRepository armazenamentoRepository;

    public ArmazenamentoService(ArmazenamentoRepository armazenamentoRepository) {
        this.armazenamentoRepository = armazenamentoRepository;
    }

    public List<Armazenamento> findAllList(){
        log.debug("Request to get All Armazenamento");
        return armazenamentoRepository.findAll();
    }

    public Optional<Armazenamento> findOne(Long id) {
        log.debug("Request to get Armazenamento : {}", id);
        return armazenamentoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Armazenamento : {}", id);
        armazenamentoRepository.deleteById(id);
    }

    public Armazenamento save(Armazenamento armazenamento) {
        log.debug("Request to save Memoria : {}", armazenamento);
        armazenamento = armazenamentoRepository.save(armazenamento);
        return armazenamento;
    }

    public List<Armazenamento> saveAll(List<Armazenamento> armazenamentos) {
        log.debug("Request to save Armazenamento : {}", armazenamentos);
        armazenamentos = armazenamentoRepository.saveAll(armazenamentos);
        return armazenamentos;
    }
}
