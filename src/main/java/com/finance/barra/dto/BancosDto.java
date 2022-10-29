package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.model.Banco;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BancosDto {

    private Long id;
    private String nome;
    private String contaCorrente;
    private String label;
    private Long value;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        @Autowired
        private CentrosCustosDto.RepresentationBuilder centrosRB;

        public BancosDto of(Banco banco) {
            return BancosDto.builder()
                    .id(banco.getId())
                    .nome(banco.getNome())
                    .contaCorrente(banco.getContaCorrente())
                    .value(banco.getId())
                    .label(banco.getNome())
                    .build();
        }

        public Banco from(BancosDto dto) {
            return Banco.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .contaCorrente(dto.getContaCorrente())
                    .build();
        }

        public BancosDto associacao(Banco banco) {
            return BancosDto.builder()
                    .id(banco.getId())
                    .nome(banco.getNome())
                    .value(banco.getId())
                    .label(banco.getNome())
                    .build();
        }

        public BancosDto select(Banco banco) {
            return BancosDto.builder()
                    .id(banco.getId())
                    .nome(banco.getNome())
                    .label(banco.getNome())
                    .value(banco.getId())
                    .build();
        }
    }
}
