package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_armazenamento")
public class Armazenamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String imagem;
    private Float valor;
    private String tipo;
    private int capacidade;
    private String fabricante;

    public static Armazenamento parseNote(String line) {
        String[] text = line.split(",");
        Armazenamento note = new Armazenamento();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
