package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_alimentacao")
public class Alimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String imagem;
    private Float valor;
    private String transformador;
    private int capacidade;
    private String fabricante;
    private int eficiencia;

    public static Alimentacao parseNote(String line) {
        String[] text = line.split(",");
        Alimentacao note = new Alimentacao();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
