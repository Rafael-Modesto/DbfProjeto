package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_processador")
public class Processador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String imagem;
    private String socket;
    private Float valor;
    private String fabricante;
    private Float frequencia;
    private int tdp;

    public static Processador parseNote(String line) {
        String[] text = line.split(",");
        Processador note = new Processador();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
