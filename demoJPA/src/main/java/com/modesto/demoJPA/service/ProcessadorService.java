package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Processador;
import com.modesto.demoJPA.repository.ProcessadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessadorService {

    private final Logger log = LoggerFactory.getLogger(ProcessadorService.class);

    private final ProcessadorRepository processadorRepository;

    public ProcessadorService(ProcessadorRepository processadorRepository) {
        this.processadorRepository = processadorRepository;
    }

    public List<Processador> findAllList(){
        log.debug("Request to get All Processador");
        return processadorRepository.findAll();
    }

    public Optional<Processador> findOne(Long id) {
        log.debug("Request to get Processador : {}", id);
        return processadorRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Processador : {}", id);
        processadorRepository.deleteById(id);
    }

    public Processador save(Processador processador) {
        log.debug("Request to save Processador : {}", processador);
        processador = processadorRepository.save(processador);
        return processador;
    }

    public List<Processador> saveAll(List<Processador> processadores) {
        log.debug("Request to save Pessoa : {}", processadores);
        processadores = processadorRepository.saveAll(processadores);
        return processadores;
    }
}
