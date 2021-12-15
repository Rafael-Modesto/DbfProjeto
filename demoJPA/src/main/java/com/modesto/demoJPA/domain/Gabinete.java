package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_gabinete")
public class Gabinete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String imagem;
    private Float valor;
    private Boolean iluminacao;
    private String fabricante;

    public static Gabinete parseNote(String line) {
        String[] text = line.split(",");
        Gabinete note = new Gabinete();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
