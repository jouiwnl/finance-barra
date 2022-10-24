package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.CentrosCusto;
import com.finance.barra.model.QSintetico;
import com.finance.barra.model.Sintetico;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CentrosCustosDto implements Serializable {

    private Long id;
    private String nome;
    private String sigla;
    private List<SinteticosDto> sinteticos;

    @Component
    public class RepresentationBuilder {
        @Autowired
        private BasicRepository repository;

        @Autowired
        private SinteticosDto.RepresentationBuilder sinteticosRB;

        public CentrosCustosDto of(CentrosCusto centro) {
            List<SinteticosDto> sinteticos = centro.getSinteticos()
                    .stream()
                    .map(s -> repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(s.getId())))
                    .map(sinteticosRB::of)
                    .collect(Collectors.toList());

            return CentrosCustosDto.builder()
                    .id(centro.getId())
                    .nome(centro.getNome())
                    .sigla(centro.getSigla())
                    .sinteticos(sinteticos)
                    .build();
        }

        public CentrosCusto from(CentrosCustosDto dto) {
            List<Sintetico> sinteticos = dto.getSinteticos()
                    .stream()
                    .map(s -> repository.findOne(Sintetico.class, QSintetico.sintetico.id.eq(s.getId())))
                    .collect(Collectors.toList());

            return CentrosCusto.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .sigla(dto.getSigla())
                    .sinteticos(sinteticos)
                    .build();
        }

        public CentrosCustosDto associacao(CentrosCusto centro) {
            return CentrosCustosDto.builder()
                    .id(centro.getId())
                    .nome(centro.getNome())
                    .sigla(centro.getSigla())
                    .build();
        }

    }
}
