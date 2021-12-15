package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Placadevideo;
import com.modesto.demoJPA.service.PlacadevideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/placasdevideo")
public class PlacadevideoResource {
    private final Logger log = LoggerFactory.getLogger(PlacadevideoResource.class);

    private final PlacadevideoService placadevideoService;

    public PlacadevideoResource(PlacadevideoService placadevideoService) {
        this.placadevideoService = placadevideoService;
    }

    /**
     * {@code GET  /placasdevideo/:id} : get the "id" placadevideo.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Placadevideo> getPlacadevideo(@PathVariable Long id) {
        log.debug("REST request to get Memoria : {}", id);
        Optional<Placadevideo> placadevideo = placadevideoService.findOne(id);
        if(placadevideo.isPresent()) {
            return ResponseEntity.ok().body(placadevideo.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Placadevideo>> getPlacasdevideo(){
        List<Placadevideo> lista = placadevideoService.findAllList();
        //if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
       // }else{
          //  return ResponseEntity.notFound().build();
        }
   // }

    /**
     * {@code PUT  /memorias} : Atualiza uma memoria existente Update.
     *
     * @paramo pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Placadevideo> updatePlacadevideo(@RequestBody Placadevideo placadevideo) throws URISyntaxException {
        log.debug("REST request to update Placadevideo : {}", placadevideo);
        if (placadevideo.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Placadevideo id null");
        }
        Placadevideo result = placadevideoService.save(placadevideo);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param placadevideo the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Placadevideo> createPlacadevideo(@RequestBody Placadevideo placadevideo) throws URISyntaxException {
        log.debug("REST request to save Placadevideo : {}", placadevideo);
        if (placadevideo.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo placadevideo não pode terum ID");
        }
        Placadevideo result = placadevideoService.save(placadevideo);
        return ResponseEntity.created(new URI("/api/placasdevideo/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Placadevideo> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Placadevideo> savedNotes = new ArrayList<>();
        List<Placadevideo> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Placadevideo::parseNote).collect(Collectors.toList());
        placadevideoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do placasdevideo que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlacadevideo(@PathVariable Long id) {
        log.debug("REST request to delete Placadevideo : {}", id);

        placadevideoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
