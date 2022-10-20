package com.finance.barra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoCentroCusto {
    SALARIO("S", "Salários"),
    SERVICOS("E", "Serviços"),
    MATERIAIS("M", "Materiais"),
    ALUGUEIS("A", "Aluguéis"),
    VIAGENS("V", "Viagens"),
    OUTROS("O", "Outros"),
    MORADIA("U", "Moradia"),
    CAMPEONATOS("C", "Campeonatos"),
    BONUS("B", "Bônus"),
    VEICULOS("R", "Veículos"),
    MANUTENCAO("G", "Manutenção");

    private String value;
    private String desc;

}
