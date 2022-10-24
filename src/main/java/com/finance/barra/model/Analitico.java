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
@Entity(name = "analiticos")
public class Analitico implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "analiticos")
    private List<Sintetico> sinteticos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analitico")
    private List<Lancamento> lancamentos;

    @PreUpdate
    @PrePersist
    public void setSinteticos() {
        if (!getSinteticos().isEmpty()) {
            getSinteticos().forEach(a -> a.getAnaliticos().add(this));
        }
    }
}
