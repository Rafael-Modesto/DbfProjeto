package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Placamae;
import com.modesto.demoJPA.repository.PlacamaeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlacamaeService {

    private final Logger log = LoggerFactory.getLogger(PlacamaeService.class);

    private final PlacamaeRepository placamaeRepository;

    public PlacamaeService(PlacamaeRepository placamaeRepository) {
        this.placamaeRepository = placamaeRepository;
    }

    public List<Placamae> findAllList(){
        log.debug("Request to get All Pessoa");
        return placamaeRepository.findAll();
    }

    public Optional<Placamae> findOne(Long id) {
        log.debug("Request to get Placa-mae : {}", id);
        return placamaeRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Placa-mae : {}", id);
        placamaeRepository.deleteById(id);
    }

    public Placamae save(Placamae placamae) {
        log.debug("Request to save Placa-mae : {}", placamae);
        placamae = placamaeRepository.save(placamae);
        return placamae;
    }

    public List<Placamae> saveAll(List<Placamae> placasmae) {
        log.debug("Request to save Placa-mae : {}", placasmae);
        placasmae = placamaeRepository.saveAll(placasmae);
        return placasmae;
    }
}
