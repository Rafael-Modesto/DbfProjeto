package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Processador;
import com.modesto.demoJPA.service.ProcessadorService;
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
@RequestMapping("/processadores")
public class ProcessadorResource {
    private final Logger log = LoggerFactory.getLogger(ProcessadorResource.class);

    private final ProcessadorService processadorService;

    public ProcessadorResource(ProcessadorService processadorService) {
        this.processadorService = processadorService;
    }

    /**
     * {@code GET  /processadores/:id} : get the "id" processador.
     *
     * @param id o id da processador que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Processador> getProcessador(@PathVariable Long id) {
        log.debug("REST request to get Processador : {}", id);
        Optional<Processador> processador = processadorService.findOne(id);
        if(processador.isPresent()) {
            return ResponseEntity.ok().body(processador.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Processador>> getProcessadores(){
        List<Processador> lista = processadorService.findAllList();
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
    public ResponseEntity<Processador> updateMemoria(@RequestBody Processador processador) throws URISyntaxException {
        log.debug("REST request to update Processador : {}", processador);
        if (processador.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Processador id null");
        }
        Processador result = processadorService.save(processador);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param processador the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Processador> createProcessador(@RequestBody Processador processador) throws URISyntaxException {
        log.debug("REST request to save Memoria : {}", processador);
        if (processador.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo pessoa não pode terum ID");
        }
        Processador result = processadorService.save(processador);
        return ResponseEntity.created(new URI("/api/processadores/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Processador> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Processador> savedNotes = new ArrayList<>();
        List<Processador> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Processador::parseNote).collect(Collectors.toList());
        processadorService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do pessoas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessador(@PathVariable Long id) {
        log.debug("REST request to delete Processador : {}", id);

        processadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
