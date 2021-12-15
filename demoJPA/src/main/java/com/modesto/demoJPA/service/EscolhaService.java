package com.modesto.demoJPA.service;
import com.modesto.demoJPA.domain.Escolha;
import com.modesto.demoJPA.repository.EscolhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EscolhaService {

    private final Logger log = LoggerFactory.getLogger(EscolhaService.class);

    private final EscolhaRepository escolhaRepository;

    public EscolhaService(EscolhaRepository escolhaRepository) {
        this.escolhaRepository = escolhaRepository;
    }

    public List<Escolha> findAllList(){
        log.debug("Request to get All Escolha");
        return escolhaRepository.findAll();
    }

    public Optional<Escolha> findOne(Long id) {
        log.debug("Request to get Escolha : {}", id);
        return escolhaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Escolha : {}", id);
        escolhaRepository.deleteById(id);
    }

    public Escolha save(Escolha escolha) {
        log.debug("Request to save Escolha : {}", escolha);
        escolha = escolhaRepository.save(escolha);
        return escolha;
    }

    public List<Escolha> saveAll(List<Escolha> escolhas) {
        log.debug("Request to save Escolha : {}", escolhas);
        escolhas = escolhaRepository.saveAll(escolhas);
        return escolhas;
    }
}
