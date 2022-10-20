package com.finance.barra.dto;

import com.finance.barra.enums.TipoLancamento;
import com.finance.barra.model.Lancamento;
import com.finance.barra.model.PlanosContas;
import lombok.*;

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

    public static LancamentoDto of(Lancamento lancamento) {
        return LancamentoDto.builder()
                .id(lancamento.getId())
                .planosContas(PlanosContasDto.of(lancamento.getPlanosContas()))
                .tipo(lancamento.getTipo())
                .centrosCusto(CentrosCustosDto.of(lancamento.getCentrosCusto()))
                .sintetico(SinteticosDto.of(lancamento.getSintetico()))
                .analitico(AnaliticosDto.of(lancamento.getAnalitico()))
                .documento(lancamento.getDocumento())
                .beneficiario(lancamento.getBeneficiario())
                .assunto(lancamento.getAssunto())
                .total(lancamento.getTotal())
                .build();
    }

}
