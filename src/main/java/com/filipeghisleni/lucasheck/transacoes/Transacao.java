package com.filipeghisleni.lucasheck.transacoes;

import java.time.ZonedDateTime;

public class Transacao {

    private double valor;
    private ZonedDateTime dataHora;

    public Transacao(double valor, ZonedDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public ZonedDateTime getDataHora() {
        return dataHora;
    }
}
