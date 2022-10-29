package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.Analitico;
import com.finance.barra.model.CentrosCusto;
import com.finance.barra.model.QAnalitico;
import com.finance.barra.model.Sintetico;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SinteticosDto {

    private Long id;
    private String nome;
    private List<AnaliticosDto> analiticos = new ArrayList<>();
    private Long value;
    private String label;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        @Autowired
        private AnaliticosDto.RepresentationBuilder analiticosRB;

        public SinteticosDto of(Sintetico sintetico) {
            List<AnaliticosDto> analiticos = sintetico != null
                    ? sintetico.getAnaliticos()
                        .stream()
                        .map(a -> repository.findOne(Analitico.class, QAnalitico.analitico.id.eq(a.getId())))
                        .map(analiticosRB::of)
                        .collect(Collectors.toList())
                    : new ArrayList<>();

            return SinteticosDto.builder()
                    .id(sintetico.getId())
                    .nome(sintetico.getNome())
                    .analiticos(analiticos)
                    .label(sintetico.getNome())
                    .value(sintetico.getId())
                    .build();
        }

        public Sintetico from(SinteticosDto dto) {
            List<Analitico> analiticos = dto.getAnaliticos() != null
                    ? dto.getAnaliticos()
                        .stream()
                        .map(a -> this.repository.findOne(Analitico.class, QAnalitico.analitico.id.eq(a.getId())))
                        .collect(Collectors.toList())
                    : new ArrayList<>();

            return Sintetico.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .analiticos(analiticos)
                    .build();
        }

        public SinteticosDto associacao(Sintetico sintetico) {
            return SinteticosDto.builder()
                    .id(sintetico.getId())
                    .nome(sintetico.getNome())
                    .label(sintetico.getNome())
                    .value(sintetico.getId())
                    .build();
        }
    }
}
