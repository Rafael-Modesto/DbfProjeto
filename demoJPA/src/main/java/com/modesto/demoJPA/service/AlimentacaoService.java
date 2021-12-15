    package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Alimentacao;
import com.modesto.demoJPA.repository.AlimentacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentacaoService {

    private final Logger log = LoggerFactory.getLogger(AlimentacaoService.class);

    private final AlimentacaoRepository alimentacaoRepository;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository) {
        this.alimentacaoRepository = alimentacaoRepository;
    }

    public List<Alimentacao> findAllList(){
        log.debug("Request to get All Alimentacao");
        return alimentacaoRepository.findAll();
    }

    public Optional<Alimentacao> findOne(Long id) {
        log.debug("Request to get Alimentacao : {}", id);
        return alimentacaoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Alimentacao : {}", id);
        alimentacaoRepository.deleteById(id);
    }

    public Alimentacao save(Alimentacao alimentacao) {
        log.debug("Request to save Memoria : {}", alimentacao);
        alimentacao = alimentacaoRepository.save(alimentacao);
        return alimentacao;
    }

    public List<Alimentacao> saveAll(List<Alimentacao> alimentacoes) {
        log.debug("Request to save Pessoa : {}", alimentacoes);
        alimentacoes = alimentacaoRepository.saveAll(alimentacoes);
        return alimentacoes;
    }
}
