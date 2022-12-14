package com.finance.barra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoLancamento {

    GASTOS_CORRENTES("G", "Gastos correntes"),
    FUNCIONARIO("F", "Pessoas"),
    FORNECEDOR("N", "Fornecedores"),
    IMPOSTOS("I", "Gastos correntes");

    private String value;
    private String desc;

}
