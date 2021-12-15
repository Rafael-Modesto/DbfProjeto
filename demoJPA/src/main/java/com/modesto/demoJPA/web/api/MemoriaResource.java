package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Memoria;
import com.modesto.demoJPA.service.MemoriaService;
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
@RequestMapping("/memorias")
public class MemoriaResource {
    private final Logger log = LoggerFactory.getLogger(MemoriaResource.class);

    private final MemoriaService memoriaService;

    public MemoriaResource(MemoriaService memoriaService) {
        this.memoriaService = memoriaService;
    }

    /**
     * {@code GET  /memorias/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Memoria> getMemoria(@PathVariable Long id) {
        log.debug("REST request to get Memoria : {}", id);
        Optional<Memoria> memoria = memoriaService.findOne(id);
        if(memoria.isPresent()) {
            return ResponseEntity.ok().body(memoria.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Memoria>> getMemorias(){
        List<Memoria> lista = memoriaService.findAllList();
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
    public ResponseEntity<Memoria> updateMemoria(@RequestBody Memoria memoria) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", memoria);
        if (memoria.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Memoria id null");
        }
        Memoria result = memoriaService.save(memoria);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param memoria the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Memoria> createMemoria(@RequestBody Memoria memoria) throws URISyntaxException {
        log.debug("REST request to save Memoria : {}", memoria);
        if (memoria.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo pessoa não pode terum ID");
        }
        Memoria result = memoriaService.save(memoria);
        return ResponseEntity.created(new URI("/api/memorias/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Memoria> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Memoria> savedNotes = new ArrayList<>();
        List<Memoria> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Memoria::parseNote).collect(Collectors.toList());
        memoriaService.saveAll(notes).forEach(savedNotes::add);
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
        log.debug("REST request to delete Memoria : {}", id);

        memoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
