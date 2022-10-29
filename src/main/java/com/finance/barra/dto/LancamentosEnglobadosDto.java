package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.core.HibernateUtils;
import com.finance.barra.enums.StatusLancamentoEnglobado;
import com.finance.barra.model.Banco;
import com.finance.barra.model.Lancamento;
import com.finance.barra.model.LancamentoEnglobado;
import com.finance.barra.model.QLancamento;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LancamentosEnglobadosDto {

    private Long id;
    private BancosDto banco;
    private LocalDate dataLancamento;
    private String numeroCheque;
    private StatusLancamentoEnglobado status;
    private List<LancamentoDto> lancamentos = new ArrayList<>();

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        @Autowired
        private LancamentoDto.RepresentationBuilder lancamentosRB;

        @Autowired
        private BancosDto.RepresentationBuilder bancosRB;

        public LancamentosEnglobadosDto of(LancamentoEnglobado lancamentoEnglobado) {
            List<LancamentoDto> lancamentosInside = lancamentoEnglobado.getLancamentos() != null
                            ? lancamentoEnglobado.getLancamentos()
                                .stream()
                                .map(l -> repository.findOne(Lancamento.class, QLancamento.lancamento.id.eq(l.getId())))
                                .map(lancamentosRB::of)
                                .collect(Collectors.toList())
                            : new ArrayList<>();

            return LancamentosEnglobadosDto.builder()
                    .id(lancamentoEnglobado.getId())
                    .banco(bancosRB.of(lancamentoEnglobado.getBanco()))
                    .dataLancamento(lancamentoEnglobado.getDataLancamento())
                    .numeroCheque(lancamentoEnglobado.getNumeroCheque())
                    .lancamentos(lancamentosInside)
                    .status(lancamentoEnglobado.getStatus())
                    .build();
        }

        public LancamentosEnglobadosDto list(LancamentoEnglobado lancamentoEnglobado) {
            return LancamentosEnglobadosDto.builder()
                    .id(lancamentoEnglobado.getId())
                    .banco(bancosRB.of(lancamentoEnglobado.getBanco()))
                    .dataLancamento(lancamentoEnglobado.getDataLancamento())
                    .numeroCheque(lancamentoEnglobado.getNumeroCheque())
                    .status(lancamentoEnglobado.getStatus())
                    .build();
        }

        public LancamentoEnglobado from(LancamentosEnglobadosDto dto) {
            List<Lancamento> lancamentosInside = dto.getLancamentos() != null
                            ? dto.getLancamentos()
                                .stream()
                                .map(l -> repository.findOne(Lancamento.class, QLancamento.lancamento.id.eq(l.getId())))
                                .collect(Collectors.toList())
                            : new ArrayList<>();

            return LancamentoEnglobado.builder()
                    .id(dto.getId())
                    .banco(Optional.ofNullable(dto.getBanco())
                            .map(bancosRB::from)
                            .orElse(null))
                    .dataLancamento(dto.getDataLancamento())
                    .numeroCheque(dto.getNumeroCheque())
                    .lancamentos(lancamentosInside)
                    .status(dto.getStatus() == null ? StatusLancamentoEnglobado.EM_PROCESSAMENTO : dto.getStatus())
                    .build();
        }

        public LancamentosEnglobadosDto associacao(LancamentoEnglobado lancamentoEnglobado) {
            return LancamentosEnglobadosDto.builder()
                    .id(lancamentoEnglobado.getId())
                    .dataLancamento(lancamentoEnglobado.getDataLancamento())
                    .status(lancamentoEnglobado.getStatus())
                    .build();
        }
    }
}
