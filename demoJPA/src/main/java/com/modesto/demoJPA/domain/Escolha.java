package com.modesto.demoJPA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_escolha")
public class Escolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idProcessador;
    private int idPlacaMae;
    private int idMemoria;
    private int qMemoria;
    private int idPlacaDeVideo;
    private int idArmazenamento;
    private int idAlimentacao;
    private int idGabinete;


    public static Escolha parseNote(String line) {
        String[] text = line.split(",");
        Escolha note = new Escolha();
        note.setId(Long.parseLong(text[0]));
        return note;
    }
}
