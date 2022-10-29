package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import com.finance.barra.enums.TipoLancamento;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "lancamentos")
public class Lancamento implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_planos_contas", referencedColumnName = "id")
    private PlanosContas planosContas;

    @Column(name = "tipo")
    private TipoLancamento tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centros_custos", referencedColumnName = "id")
    private CentrosCusto centrosCusto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sinteticos", referencedColumnName = "id")
    private Sintetico sintetico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_analiticos", referencedColumnName = "id")
    private Analitico analitico;

    @Column(name = "documento")
    private String documento;

    @Column(name = "assunto")
    private String assunto;

    @Column(name = "total", scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lancamentos_englobados", referencedColumnName = "id")
    private LancamentoEnglobado lancamentoEnglobado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoas", referencedColumnName = "id")
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_impostos", referencedColumnName = "id")
    private Imposto imposto;

}
