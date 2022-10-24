package com.finance.barra.dto;

import com.finance.barra.enums.TipoLancamento;
import com.finance.barra.model.Lancamento;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDto {

    private Long id;
    private PlanosContasDto planosContas;
    private TipoLancamento tipo;
    private CentrosCustosDto centrosCusto;
    private SinteticosDto sintetico;
    private AnaliticosDto analitico;
    private String documento;
    private String beneficiario;
    private String assunto;
    private BigDecimal total;

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

        public LancamentoDto of(Lancamento lancamento) {
            return LancamentoDto.builder()
                    .id(lancamento.getId())
                    .planosContas(planosContasRB.of(lancamento.getPlanosContas()))
                    .tipo(lancamento.getTipo())
                    .centrosCusto(centrosRB.associacao(lancamento.getCentrosCusto()))
                    .sintetico(sinteticosRB.associacao(lancamento.getSintetico()))
                    .analitico(analiticosRB.associacao(lancamento.getAnalitico()))
                    .documento(lancamento.getDocumento())
                    .beneficiario(lancamento.getBeneficiario())
                    .assunto(lancamento.getAssunto())
                    .total(lancamento.getTotal())
                    .build();
        }
    }

}
