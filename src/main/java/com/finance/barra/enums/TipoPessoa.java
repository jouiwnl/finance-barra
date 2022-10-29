package com.finance.barra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoPessoa {

    FUNCIONARIO("U", "Funcionário"),
    FORNECEDOR("F", "Fornecedor");

    private String value;
    private String desc;

}
