package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_placadevideo")
public class Placadevideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String imagem;
    private Float valor;
    private String socket;
    private int tdp;
    private String fabricante;
    private Float frequencia;

    public static Placadevideo parseNote(String line) {
        String[] text = line.split(",");
        Placadevideo note = new Placadevideo();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
