package com.finance.barra.dto;

import com.finance.barra.enums.TipoCargo;
import com.finance.barra.model.Usuario;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    private String nomeCompleto;
    private String user;
    private TipoCargo tipo;

    public static UsuarioDto of(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nomeCompleto(usuario.getNomeCompleto())
                .tipo(usuario.getTipo())
                .user(usuario.getUser())
                .build();
    }

}
