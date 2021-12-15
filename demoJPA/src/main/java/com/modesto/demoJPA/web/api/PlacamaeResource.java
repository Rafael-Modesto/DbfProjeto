package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Placamae;
import com.modesto.demoJPA.service.PlacamaeService;
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
@RequestMapping("/placasmae")
public class PlacamaeResource {
    private final Logger log = LoggerFactory.getLogger(PlacamaeResource.class);

    private final PlacamaeService placamaeService;

    public PlacamaeResource(PlacamaeService placamaeService) {
        this.placamaeService = placamaeService;
    }

    /**
     * {@code GET  /memorias/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Placamae> getPlacamae(@PathVariable Long id) {
        log.debug("REST request to get Placa-mae : {}", id);
        Optional<Placamae> placamae = placamaeService.findOne(id);
        if(placamae.isPresent()) {
            return ResponseEntity.ok().body(placamae.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Placamae>> getPlacamae(){
        List<Placamae> lista = placamaeService.findAllList();
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
    public ResponseEntity<Placamae> updatePlacamae(@RequestBody Placamae placamae) throws URISyntaxException {
        log.debug("REST request to update Placa-mae : {}", placamae);
        if (placamae.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Placa-mae id null");
        }
        Placamae result = placamaeService.save(placamae);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param placamae the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Placamae> createPlacamae(@RequestBody Placamae placamae) throws URISyntaxException {
        log.debug("REST request to save Placa-mae : {}", placamae);
        if (placamae.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo placa mae não pode terum ID");
        }
        Placamae result = placamaeService.save(placamae);
        return ResponseEntity.created(new URI("/api/placasmae/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Placamae> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Placamae> savedNotes = new ArrayList<>();
        List<Placamae> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Placamae::parseNote).collect(Collectors.toList());
        placamaeService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do pessoas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlacamae(@PathVariable Long id) {
        log.debug("REST request to delete Placa-mae : {}", id);

        placamaeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
