package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "centros_custos")
public class CentrosCusto implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "centros_custo_sinteticos",
            joinColumns = {
                    @JoinColumn(name = "id_sinteticos", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_centros_custos", referencedColumnName = "id")
            }
    )
    private List<Sintetico> sinteticos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centrosCusto")
    private List<Lancamento> lancamentos;

    @PreUpdate
    @PrePersist
    public void setSinteticos() {
        if (!getSinteticos().isEmpty()) {
            getSinteticos().forEach(a -> a.getCentrosCusto().add(this));
        }
    }

}
