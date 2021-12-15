package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Gabinete;
import com.modesto.demoJPA.repository.GabineteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GabineteService {

    private final Logger log = LoggerFactory.getLogger(GabineteService.class);

    private final GabineteRepository gabineteRepository;

    public GabineteService(GabineteRepository gabineteRepository) {
        this.gabineteRepository = gabineteRepository;
    }

    public List<Gabinete> findAllList(){
        log.debug("Request to get All Gabinete");
        return gabineteRepository.findAll();
    }

    public Optional<Gabinete> findOne(Long id) {
        log.debug("Request to get Gabinete : {}", id);
        return gabineteRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Gabinete : {}", id);
        gabineteRepository.deleteById(id);
    }

    public Gabinete save(Gabinete gabinete) {
        log.debug("Request to save Gabinete : {}", gabinete);
        gabinete = gabineteRepository.save(gabinete);
        return gabinete;
    }

    public List<Gabinete> saveAll(List<Gabinete> gabinetes) {
        log.debug("Request to save Pessoa : {}", gabinetes);
        gabinetes = gabineteRepository.saveAll(gabinetes);
        return gabinetes;
    }
}
