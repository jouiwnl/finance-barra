package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.Analitico;
import com.finance.barra.model.Imposto;
import com.finance.barra.model.QSintetico;
import com.finance.barra.model.Sintetico;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ImpostosDto {

    private Long id;
    private String nome;
    private String sigla;
    private String label;
    private Long value;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        public ImpostosDto of(Imposto imposto) {
            return ImpostosDto.builder()
                    .id(imposto.getId())
                    .nome(imposto.getNome())
                    .sigla(imposto.getSigla())
                    .label(imposto.getNome())
                    .value(imposto.getId())
                    .build();
        }

        public Imposto from(ImpostosDto dto) {
            return Imposto.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .sigla(dto.getSigla())
                    .build();
        }
    }

}
