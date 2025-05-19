package com.filipeghisleni.lucasheck.transacoes;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public void limparTransacoes() {
        transacoes.clear();
    }

    public EstatisticaDTO calcularEstatisticasUltimos60Segundos() {
        ZonedDateTime agora = ZonedDateTime.now();
        List<Transacao> recentes = transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(agora.minusSeconds(60)))
                .collect(Collectors.toList());

        return calcularEstatisticas(recentes);
    }

    public EstatisticaDTO calcularEstatisticasPorPeriodo(ZonedDateTime inicio, ZonedDateTime fim) {
        List<Transacao> periodo = transacoes.stream()
                .filter(t -> !t.getDataHora().isBefore(inicio) && !t.getDataHora().isAfter(fim))
                .collect(Collectors.toList());

        return calcularEstatisticas(periodo);
    }

    public Transacao buscarUltimaTransacao() {
        return transacoes.isEmpty() ? null : transacoes.get(transacoes.size() - 1);
    }

    public boolean excluirTransacoesPorPeriodo(ZonedDateTime inicio, ZonedDateTime fim) {
        return transacoes.removeIf(t -> !t.getDataHora().isBefore(inicio) && !t.getDataHora().isAfter(fim));
    }

    private EstatisticaDTO calcularEstatisticas(List<Transacao> lista) {
        if (lista.isEmpty()) {
            return new EstatisticaDTO(0, 0.0, 0.0, 0.0, 0.0);
        }

        long count = lista.size();
        double sum = lista.stream().mapToDouble(Transacao::getValor).sum();
        double avg = sum / count;
        double min = lista.stream().mapToDouble(Transacao::getValor).min().orElse(0.0);
        double max = lista.stream().mapToDouble(Transacao::getValor).max().orElse(0.0);

        return new EstatisticaDTO(count, sum, avg, min, max);
    }
}
