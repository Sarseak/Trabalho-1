package com.filipeghisleni.lucasheck.transacoes;

import jakarta.validation.constraints.NotNull;

public class PeriodoRequest {

    @NotNull
    private String dataInicial;

    @NotNull
    private String dataFinal;

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }
}
