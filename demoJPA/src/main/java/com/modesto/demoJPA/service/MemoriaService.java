package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Memoria;
import com.modesto.demoJPA.repository.MemoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoriaService {

    private final Logger log = LoggerFactory.getLogger(MemoriaService.class);

    private final MemoriaRepository memoriaRepository;

    public MemoriaService(MemoriaRepository memoriaRepository) {
        this.memoriaRepository = memoriaRepository;
    }

    public List<Memoria> findAllList(){
        log.debug("Request to get All Pessoa");
        return memoriaRepository.findAll();
    }

    public Optional<Memoria> findOne(Long id) {
        log.debug("Request to get Memoria : {}", id);
        return memoriaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Memoria : {}", id);
        memoriaRepository.deleteById(id);
    }

    public Memoria save(Memoria memoria) {
        log.debug("Request to save Memoria : {}", memoria);
        memoria = memoriaRepository.save(memoria);
        return memoria;
    }

    public List<Memoria> saveAll(List<Memoria> memorias) {
        log.debug("Request to save Pessoa : {}", memorias);
        memorias = memoriaRepository.saveAll(memorias);
        return memorias;
    }
}
