package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_placamae")
public class Placamae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;
    private String foto;
    private Float valor;
    private String socket;
    private String fabricante;
    private String formato;
    private String tipo_memoria;

    public static Placamae parseNote(String line) {
        String[] text = line.split(",");
        Placamae note = new Placamae();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
