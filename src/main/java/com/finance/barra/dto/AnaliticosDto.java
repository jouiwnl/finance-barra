package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.Analitico;
import com.finance.barra.model.QSintetico;
import com.finance.barra.model.Sintetico;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class AnaliticosDto {

    private Long id;
    private String nome;
    private List<SinteticosDto> sinteticos = new ArrayList<>();
    private Long value;
    private String label;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        @Autowired
        private SinteticosDto.RepresentationBuilder sinteticosRB;

        public AnaliticosDto of(Analitico analitico) {
            List<SinteticosDto> sinteticos = analitico.getSinteticos() != null
                    ? analitico.getSinteticos()
                        .stream()
                        .map(s -> this.repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(s.getId())))
                        .map(sinteticosRB::associacao)
                        .collect(Collectors.toList())
                    : new ArrayList<>();

            return AnaliticosDto.builder()
                    .id(analitico.getId())
                    .nome(analitico.getNome())
                    .sinteticos(sinteticos)
                    .label(analitico.getNome())
                    .value(analitico.getId())
                    .build();
        }

        public Analitico from(AnaliticosDto dto) {
            List<Sintetico> sinteticos = dto.getSinteticos() != null
                    ? dto.getSinteticos()
                        .stream()
                        .map(s -> this.repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(s.getId())))
                        .collect(Collectors.toList())
                    : new ArrayList<>();

            return Analitico.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .sinteticos(sinteticos)
                    .build();
        }

        public AnaliticosDto associacao(Analitico analitico) {
            List<SinteticosDto> sinteticosDto = analitico.getSinteticos() != null
                    ? analitico.getSinteticos()
                        .stream()
                        .map(s -> this.repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(s.getId())))
                        .map(sinteticosRB::associacao)
                        .collect(Collectors.toList())
                    : new ArrayList<>();

            return AnaliticosDto.builder()
                    .id(analitico.getId())
                    .nome(analitico.getNome())
                    .sinteticos(sinteticosDto)
                    .label(analitico.getNome())
                    .value(analitico.getId())
                    .build();
        }
    }
}
