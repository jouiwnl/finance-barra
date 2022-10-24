package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(mappedBy = "sinteticos")
    private List<CentrosCusto> centrosCusto;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "sinteticos_analiticos",
            joinColumns = {
                    @JoinColumn(name = "id_analiticos", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_sinteticos", referencedColumnName = "id")
            }
    )
    private List<Analitico> analiticos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sintetico")
    private List<Lancamento> lancamentos;

    @PreUpdate
    @PrePersist
    public void setAnaliticos() {
        if (!getAnaliticos().isEmpty()) {
            getAnaliticos().forEach(a -> a.getSinteticos().add(this));
        }
    }
}
