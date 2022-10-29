package com.finance.barra.dto;

import com.finance.barra.enums.TipoLancamento;
import com.finance.barra.model.Lancamento;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LancamentoDto {

    private Long id;
    private PlanosContasDto planosContas;
    private TipoLancamento tipo;
    private CentrosCustosDto centrosCusto;
    private SinteticosDto sintetico;
    private AnaliticosDto analitico;
    private String documento;
    private String assunto;
    private BigDecimal total;
    private LancamentosEnglobadosDto lancamentoEnglobado;
    private PessoasDto pessoa;
    private ImpostosDto imposto;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private CentrosCustosDto.RepresentationBuilder centrosRB;

        @Autowired
        private SinteticosDto.RepresentationBuilder sinteticosRB;

        @Autowired
        private AnaliticosDto.RepresentationBuilder analiticosRB;

        @Autowired
        private PlanosContasDto.RepresentationBuilder planosContasRB;

        @Autowired
        private ImpostosDto.RepresentationBuilder impostosRB;

        @Autowired
        private LancamentosEnglobadosDto.RepresentationBuilder englobadosRB;

        @Autowired
        private PessoasDto.RepresentationBuilder pessoasRB;

        public LancamentoDto of(Lancamento lancamento) {
            return LancamentoDto.builder()
                    .id(lancamento.getId())
                    .tipo(lancamento.getTipo())
                    .assunto(lancamento.getAssunto())
                    .total(lancamento.getTotal())
                    .planosContas(planosContasRB.of(lancamento.getPlanosContas()))
                    .centrosCusto(Optional.ofNullable(lancamento.getCentrosCusto())
                            .map(centrosRB::associacao)
                            .orElse(null))
                    .sintetico(Optional.ofNullable(lancamento.getSintetico())
                            .map(sinteticosRB::associacao)
                            .orElse(null))
                    .analitico(Optional.ofNullable(lancamento.getAnalitico())
                            .map(analiticosRB::associacao)
                            .orElse(null))
                    .imposto(Optional.ofNullable(lancamento.getImposto())
                            .map(impostosRB::of)
                            .orElse(null))
                    .lancamentoEnglobado(englobadosRB.associacao(lancamento.getLancamentoEnglobado()))
                    .pessoa(Optional.ofNullable(lancamento.getPessoa())
                            .map(pessoasRB::associacao)
                            .orElse(null))
                    .documento(lancamento.getDocumento())
                    .build();
        }

        public Lancamento from(LancamentoDto dto) {
            return Lancamento.builder()
                    .id(dto.getId())
                    .tipo(dto.getTipo())
                    .documento(dto.getDocumento())
                    .assunto(dto.getAssunto())
                    .total(dto.getTotal())
                    .planosContas(planosContasRB.from(dto.getPlanosContas()))
                    .centrosCusto(Optional.ofNullable(dto.getCentrosCusto())
                            .map(centrosRB::from)
                            .orElse(null))
                    .sintetico(Optional.ofNullable(dto.getSintetico())
                            .map(sinteticosRB::from)
                            .orElse(null))
                    .analitico(Optional.ofNullable(dto.getAnalitico())
                            .map(analiticosRB::from)
                            .orElse(null))
                    .imposto(Optional.ofNullable(dto.getImposto())
                            .map(impostosRB::from)
                            .orElse(null))
                    .lancamentoEnglobado(Optional.ofNullable(dto.getLancamentoEnglobado())
                            .map(englobadosRB::from)
                            .orElse(null))
                    .pessoa(Optional.ofNullable(dto.getPessoa())
                            .map(pessoasRB::from)
                            .orElse(null))
                    .build();
        }
    }

}
