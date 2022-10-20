package com.finance.barra.dto;

import com.finance.barra.model.Sintetico;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SinteticosDto {

    private Long id;
    private String nome;
    private List<AnaliticosDto> analiticos;

    public static SinteticosDto of(Sintetico sintetico) {
        List<AnaliticosDto> analiticos = sintetico.getAnaliticos()
                .stream()
                .map(AnaliticosDto::of)
                .collect(Collectors.toList());

        return SinteticosDto.builder()
                .id(sintetico.getId())
                .nome(sintetico.getNome())
                .analiticos(analiticos)
                .build();
    }

}
