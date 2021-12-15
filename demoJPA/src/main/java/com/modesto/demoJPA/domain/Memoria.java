package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_memoria")
public class Memoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String foto;
    private Float valor;
    private String tipo;
    private Float capacidade;
    private String fabricante;
    private Float frequencia;

    public static Memoria parseNote(String line) {
        String[] text = line.split(",");
        Memoria note = new Memoria();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
