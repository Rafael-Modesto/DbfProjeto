package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Gabinete;
import com.modesto.demoJPA.service.GabineteService;
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
@RequestMapping("/gabinetes")
public class GabineteResource {
    private final Logger log = LoggerFactory.getLogger(GabineteResource.class);

    private final GabineteService gabineteService;

    public GabineteResource(GabineteService gabineteService) {
        this.gabineteService = gabineteService;
    }

    /**
     * {@code GET  /gabinetes/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gabinete> getGabinete(@PathVariable Long id) {
        log.debug("REST request to get Gabinete : {}", id);
        Optional<Gabinete> gabinete = gabineteService.findOne(id);
        if(gabinete.isPresent()) {
            return ResponseEntity.ok().body(gabinete.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Gabinete>> getGabinetes(){
        List<Gabinete> lista = gabineteService.findAllList();
        //if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
       // }else{
          //  return ResponseEntity.notFound().build();
        }
   // }

    /**
     * {@code PUT  /gabinetes} : Atualiza uma memoria existente Update.
     *
     * @paramo pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Gabinete> updateGabinete(@RequestBody Gabinete gabinete) throws URISyntaxException {
        log.debug("REST request to update Gabinete : {}", gabinete);
        if (gabinete.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Gabinete id null");
        }
        Gabinete result = gabineteService.save(gabinete);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new gabinete.
     *
     * @param gabinete the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Gabinete> createMemoria(@RequestBody Gabinete gabinete) throws URISyntaxException {
        log.debug("REST request to save Gabinete : {}", gabinete);
        if (gabinete.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo gabinete não pode terum ID");
        }
        Gabinete result = gabineteService.save(gabinete);
        return ResponseEntity.created(new URI("/api/gabinetes/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Gabinete> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Gabinete> savedNotes = new ArrayList<>();
        List<Gabinete> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Gabinete::parseNote).collect(Collectors.toList());
        gabineteService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do pessoas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemoria(@PathVariable Long id) {
        log.debug("REST request to delete Gabinete : {}", id);

        gabineteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
