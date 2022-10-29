package com.finance.barra.model;

import com.finance.barra.core.DatabaseEntity;
import com.finance.barra.enums.StatusLancamentoEnglobado;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lancamentos_englobados")
public class LancamentoEnglobado implements DatabaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bancos", referencedColumnName = "id")
    private Banco banco;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(name = "numero_cheque")
    private String numeroCheque;

    @Column(name = "status")
    private StatusLancamentoEnglobado status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "lancamentoEnglobado")
    private List<Lancamento> lancamentos;

    @PreUpdate
    @PrePersist
    public void setSinteticos() {
        if (getStatus() == null) {
            setStatus(StatusLancamentoEnglobado.EM_PROCESSAMENTO);
        }
    }

}
