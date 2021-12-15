package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Escolha;
import com.modesto.demoJPA.service.EscolhaService;
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
@RequestMapping("/escolhas")
public class EscolhaResource {
    private final Logger log = LoggerFactory.getLogger(EscolhaResource.class);

    private final EscolhaService escolhaService;

    public EscolhaResource(EscolhaService escolhaService) {
        this.escolhaService = escolhaService;
    }

    /**
     * {@code GET  /escolha/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Escolha> getEscolha(@PathVariable Long id) {
        log.debug("REST request to get Escolha : {}", id);
        Optional<Escolha> escolha = escolhaService.findOne(id);
        if(escolha.isPresent()) {
            return ResponseEntity.ok().body(escolha.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Escolha>> getEscolhas(){
        List<Escolha> lista = escolhaService.findAllList();
        //if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
       // }else{
          //  return ResponseEntity.notFound().build();
        }
   // }

    /**
     * {@code PUT  /escolhas} : Atualiza uma memoria existente Update.
     *
     * @paramo pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Escolha> updateEscolha(@RequestBody Escolha escolha) throws URISyntaxException {
        log.debug("REST request to update Escolha : {}", escolha);
        if (escolha.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Escolha id null");
        }
        Escolha result = escolhaService.save(escolha);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param escolha the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Escolha> createEscolha(@RequestBody Escolha escolha) throws URISyntaxException {
        log.debug("REST request to save Escolha : {}", escolha);
        if (escolha.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Uma nova escolha não pode terum ID");
        }
        Escolha result = escolhaService.save(escolha);
        return ResponseEntity.created(new URI("/api/escolhas/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Escolha> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Escolha> savedNotes = new ArrayList<>();
        List<Escolha> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Escolha::parseNote).collect(Collectors.toList());
        escolhaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do escolhas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscolha(@PathVariable Long id) {
        log.debug("REST request to delete Escolha : {}", id);

        escolhaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
