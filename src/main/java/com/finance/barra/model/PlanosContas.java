package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "planos_contas")
public class PlanosContas implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "alocacao_contabil")
    private String alocacaoContabil;

}
