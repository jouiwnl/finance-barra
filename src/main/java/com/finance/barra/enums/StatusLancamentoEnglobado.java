package com.finance.barra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StatusLancamentoEnglobado {

    EM_PROCESSAMENTO("E", "Em processamento"),
    HOMOLOGADO("H", "Homologado");

    private String cod;
    private String descricao;

}
