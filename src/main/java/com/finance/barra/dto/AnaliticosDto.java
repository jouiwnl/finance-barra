package com.finance.barra.dto;

import com.finance.barra.core.DynamicDto;
import com.finance.barra.model.Analitico;
import com.finance.barra.model.Sintetico;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnaliticosDto {

    private Long id;
    private String nome;
    private DynamicDto sintetico;

    public static AnaliticosDto of(Analitico analitico) {
        return AnaliticosDto.builder()
                .id(analitico.getId())
                .nome(analitico.getNome())
                .build();
    }
}
