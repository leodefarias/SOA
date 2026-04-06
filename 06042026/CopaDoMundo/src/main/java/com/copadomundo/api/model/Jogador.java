
package com.copadomundo.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String posicao;
    private double peso;
    private int idade;
    private int copasDisputadas;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Selecao time;
}
