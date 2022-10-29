package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import com.finance.barra.enums.TipoPessoa;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pessoas")
public class Pessoa implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centros_custos", referencedColumnName = "id")
    private CentrosCusto centrosCusto;

}
