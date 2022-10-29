package com.finance.barra.dto;

import com.finance.barra.core.BasicRepository;
import com.finance.barra.enums.TipoPessoa;
import com.finance.barra.model.CentrosCusto;
import com.finance.barra.model.Pessoa;
import com.finance.barra.model.QCentrosCusto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PessoasDto {

    private Long id;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private CentrosCustosDto centrosCusto;
    private String label;
    private Long value;

    @Component
    public class RepresentationBuilder {

        @Autowired
        private BasicRepository repository;

        @Autowired
        private CentrosCustosDto.RepresentationBuilder centrosRB;

        public PessoasDto of(Pessoa pessoa) {
            CentrosCustosDto centro = Optional.ofNullable(pessoa.getCentrosCusto())
                    .map(c -> repository.findOne(CentrosCusto.class, QCentrosCusto.centrosCusto.id.eq(c.getId())))
                    .map(centrosRB::associacao)
                    .orElse(null);

            return PessoasDto.builder()
                    .id(pessoa.getId())
                    .nome(pessoa.getNome())
                    .cpfCnpj(pessoa.getCpfCnpj())
                    .tipoPessoa(pessoa.getTipoPessoa())
                    .centrosCusto(centro)
                    .value(pessoa.getId())
                    .label(pessoa.getNome())
                    .build();
        }

        public Pessoa from(PessoasDto dto) {
            CentrosCusto centro = Optional.ofNullable(dto.getCentrosCusto())
                    .map(c -> repository.findOne(CentrosCusto.class, QCentrosCusto.centrosCusto.id.eq(c.getId())))
                    .orElse(null);

            return Pessoa.builder()
                    .id(dto.getId())
                    .nome(dto.getNome())
                    .tipoPessoa(dto.getTipoPessoa())
                    .cpfCnpj(dto.getCpfCnpj())
                    .centrosCusto(centro)
                    .build();
        }

        public PessoasDto associacao(Pessoa pessoa) {
            return PessoasDto.builder()
                    .id(pessoa.getId())
                    .nome(pessoa.getNome())
                    .cpfCnpj(pessoa.getCpfCnpj())
                    .value(pessoa.getId())
                    .label(pessoa.getNome())
                    .build();
        }
    }
}
