package com.finance.barra.dto;

import com.finance.barra.model.CentrosCusto;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CentrosCustosDto implements Serializable {
    private Long id;
    private String nome;
    private String sigla;
    private List<SinteticosDto> sinteticos;

    public static CentrosCustosDto of(CentrosCusto centro) {
        List<SinteticosDto> sinteticos = centro.getSinteticos()
                .stream()
                .map(SinteticosDto::of)
                .collect(Collectors.toList());

        return CentrosCustosDto.builder()
                .id(centro.getId())
                .nome(centro.getNome())
                .sigla(centro.getSigla())
                .sinteticos(sinteticos)
                .build();
    }
}
