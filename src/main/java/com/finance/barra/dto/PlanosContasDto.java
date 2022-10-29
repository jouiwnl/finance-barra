package com.finance.barra.dto;

import com.finance.barra.model.PlanosContas;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PlanosContasDto {

    private Long id;
    private String codigo;
    private String descricao;
    private String alocacaoContabil;
    private String label;
    private Long value;

    @Component
    public class RepresentationBuilder {
        public PlanosContasDto of(PlanosContas planosContas) {
            return PlanosContasDto.builder()
                    .id(planosContas.getId())
                    .alocacaoContabil(planosContas.getAlocacaoContabil())
                    .codigo(planosContas.getCodigo())
                    .descricao(planosContas.getDescricao())
                    .value(planosContas.getId())
                    .label(planosContas.getDescricao())
                    .build();
        }

        public PlanosContas from(PlanosContasDto dto) {
            return PlanosContas.builder()
                    .id(dto.getId())
                    .alocacaoContabil(dto.getAlocacaoContabil())
                    .codigo(dto.getCodigo())
                    .descricao(dto.getDescricao())
                    .build();
        }
    }

}
