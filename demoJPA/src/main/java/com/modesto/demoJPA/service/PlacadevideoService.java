package com.modesto.demoJPA.service;

import com.modesto.demoJPA.domain.Placadevideo;
import com.modesto.demoJPA.repository.PlacadevideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlacadevideoService {

    private final Logger log = LoggerFactory.getLogger(PlacadevideoService.class);

    private final PlacadevideoRepository placadevideoRepository;

    public PlacadevideoService(PlacadevideoRepository placadevideoRepository) {
        this.placadevideoRepository = placadevideoRepository;
    }

    public List<Placadevideo> findAllList(){
        log.debug("Request to get All Placa de video");
        return placadevideoRepository.findAll();
    }

    public Optional<Placadevideo> findOne(Long id) {
        log.debug("Request to get Placa de video : {}", id);
        return placadevideoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Placa de video : {}", id);
        placadevideoRepository.deleteById(id);
    }

    public Placadevideo save(Placadevideo placadevideo) {
        log.debug("Request to save Memoria : {}", placadevideo);
        placadevideo = placadevideoRepository.save(placadevideo);
        return placadevideo;
    }

    public List<Placadevideo> saveAll(List<Placadevideo> placasdevideo) {
        log.debug("Request to save Placa de video : {}", placasdevideo);
        placasdevideo = placadevideoRepository.saveAll(placasdevideo);
        return placasdevideo;
    }
}
