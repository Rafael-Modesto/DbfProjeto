package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Armazenamento;
import com.modesto.demoJPA.service.ArmazenamentoService;
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
@RequestMapping("/armazenamentos")
public class ArmazenamentoResource {
    private final Logger log = LoggerFactory.getLogger(ArmazenamentoResource.class);

    private final ArmazenamentoService armazenamentoService;

    public ArmazenamentoResource(ArmazenamentoService armazenamentoService) {
        this.armazenamentoService = armazenamentoService;
    }

    /**
     * {@code GET  /armazenamentos/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Armazenamento> getArmazenamento(@PathVariable Long id) {
        log.debug("REST request to get Armazenamento : {}", id);
        Optional<Armazenamento> armazenamento = armazenamentoService.findOne(id);
        if(armazenamento.isPresent()) {
            return ResponseEntity.ok().body(armazenamento.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Armazenamento>> getArmazenamentos(){
        List<Armazenamento> lista = armazenamentoService.findAllList();
        //if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
       // }else{
          //  return ResponseEntity.notFound().build();
        }
   // }

    /**
     * {@code PUT  /armazenamentos} : Atualiza uma memoria existente Update.
     *
     * @paramo pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Armazenamento> updateArmazenamento(@RequestBody Armazenamento armazenamento) throws URISyntaxException {
        log.debug("REST request to update Armazenamento : {}", armazenamento);
        if (armazenamento.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Armazenamento id null");
        }
        Armazenamento result = armazenamentoService.save(armazenamento);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param armazenamento the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Armazenamento> createArmazenamento(@RequestBody Armazenamento armazenamento) throws URISyntaxException {
        log.debug("REST request to save Armazenamento : {}", armazenamento);
        if (armazenamento.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo armazenamento não pode terum ID");
        }
        Armazenamento result = armazenamentoService.save(armazenamento);
        return ResponseEntity.created(new URI("/api/armazenamentos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Armazenamento> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Armazenamento> savedNotes = new ArrayList<>();
        List<Armazenamento> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Armazenamento::parseNote).collect(Collectors.toList());
        armazenamentoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do pessoas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArmazenamento(@PathVariable Long id) {
        log.debug("REST request to delete Armazenamento : {}", id);

        armazenamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
