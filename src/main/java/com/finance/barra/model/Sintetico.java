package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sinteticos")
public class Sintetico implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centros_custos", referencedColumnName = "id")
    private CentrosCusto centrosCusto;

    @OneToMany(mappedBy = "sintetico", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Analitico> analiticos;

}
