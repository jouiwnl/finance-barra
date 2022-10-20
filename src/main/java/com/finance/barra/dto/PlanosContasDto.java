package com.finance.barra.dto;

import com.finance.barra.model.PlanosContas;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanosContasDto {

    private Long id;
    private String codigo;
    private String descricao;
    private String alocacaoContabil;

    public static PlanosContasDto of(PlanosContas planosContas) {
        return PlanosContasDto.builder()
                .id(planosContas.getId())
                .alocacaoContabil(planosContas.getAlocacaoContabil())
                .codigo(planosContas.getCodigo())
                .descricao(planosContas.getDescricao())
                .build();
    }

}
