package com.finance.barra.dto;

import com.finance.barra.enums.TipoCargo;
import com.finance.barra.model.Funcionario;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDto {

    private Long id;
    private String nomeCompleto;
    private String usuario;
    private TipoCargo tipo;

    public static FuncionarioDto of(Funcionario funcionario) {
        return FuncionarioDto.builder()
                .id(funcionario.getId())
                .nomeCompleto(funcionario.getNomeCompleto())
                .tipo(funcionario.getTipo())
                .usuario(funcionario.getUsuario())
                .build();
    }

}
