package com.filipeghisleni.lucasheck.transacoes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class TransacaoRequest {

    @NotNull
    @PositiveOrZero
    private Double valor;

    @NotNull
    private String dataHora;

    public Double getValor() {
        return valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}

