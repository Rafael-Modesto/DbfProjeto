package com.modesto.demoJPA.web.api;

import com.modesto.demoJPA.domain.Alimentacao;
import com.modesto.demoJPA.service.AlimentacaoService;
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
@RequestMapping("/alimentacoes")
public class AlimentacaoResource {
    private final Logger log = LoggerFactory.getLogger(AlimentacaoResource.class);

    private final AlimentacaoService alimentacaoService;

    public AlimentacaoResource(AlimentacaoService alimentacaoService) {
        this.alimentacaoService = alimentacaoService;
    }

    /**
     * {@code GET  /alimentacoes/:id} : get the "id" memoria.
     *
     * @param id o id da memoria que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Alimentacao> getAlimentacao(@PathVariable Long id) {
        log.debug("REST request to get Alimentacao : {}", id);
        Optional<Alimentacao> alimentacao = alimentacaoService.findOne(id);
        if(alimentacao.isPresent()) {
            return ResponseEntity.ok().body(alimentacao.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Alimentacao>> getAlimentacoes(){
        List<Alimentacao> lista = alimentacaoService.findAllList();
        //if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
       // }else{
          //  return ResponseEntity.notFound().build();
        }
   // }

    /**
     * {@code PUT  /alimentacoes} : Atualiza uma memoria existente Update.
     *
     * @paramo pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o pessoa atualizado,
     * ou com status {@code 400 (Bad Request)} se o pessoa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o pessoa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Alimentacao> updateAlimentacao(@RequestBody Alimentacao alimentacao) throws URISyntaxException {
        log.debug("REST request to update Alimentacao : {}", alimentacao);
        if (alimentacao.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Alimentacao id null");
        }
        Alimentacao result = alimentacaoService.save(alimentacao);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param alimentacao the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoa, or with status {@code 400 (Bad Request)} if the pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Alimentacao> createAlimentacao(@RequestBody Alimentacao alimentacao) throws URISyntaxException {
        log.debug("REST request to save Alimentacao : {}", alimentacao);
        if (alimentacao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo alimentacao não pode terum ID");
        }
        Alimentacao result = alimentacaoService.save(alimentacao);
        return ResponseEntity.created(new URI("/api/alimentacoes/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Alimentacao> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Alimentacao> savedNotes = new ArrayList<>();
        List<Alimentacao> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Alimentacao::parseNote).collect(Collectors.toList());
        alimentacaoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do alimentacoes que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlimentacao(@PathVariable Long id) {
        log.debug("REST request to delete Alimentacao : {}", id);

        alimentacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
