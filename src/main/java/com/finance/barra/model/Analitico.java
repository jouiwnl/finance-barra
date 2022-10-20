package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "analiticos")
public class Analitico implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sinteticos", referencedColumnName = "id")
    private Sintetico sintetico;

}
