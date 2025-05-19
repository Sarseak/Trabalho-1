package com.filipeghisleni.lucasheck.transacoes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> receberTransacao(@Valid @RequestBody TransacaoRequest request) {
        try {
            ZonedDateTime dataHora = ZonedDateTime.parse(request.getDataHora());

            if (dataHora.isAfter(ZonedDateTime.now())) {
                return ResponseEntity.unprocessableEntity().build(); // 422
            }

            Transacao transacao = new Transacao(request.getValor(), dataHora);
            service.adicionarTransacao(transacao);

            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    @DeleteMapping
    public ResponseEntity<?> limparTransacoes() {
        service.limparTransacoes();
        return ResponseEntity.ok().build(); // 200
    }

    @GetMapping("/ultima")
    public ResponseEntity<?> buscarUltimaTransacao() {
        Transacao ultima = service.buscarUltimaTransacao();
        return (ultima == null)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(ultima);
    }

    @GetMapping("/estatistica")
    public EstatisticaDTO estatisticasRecentes() {
        return service.calcularEstatisticasUltimos60Segundos();
    }

    @PostMapping("/periodo")
    public EstatisticaDTO estatisticasPorPeriodo(@Valid @RequestBody PeriodoRequest request) {
        ZonedDateTime inicio = ZonedDateTime.parse(request.getDataInicial());
        ZonedDateTime fim = ZonedDateTime.parse(request.getDataFinal());
        return service.calcularEstatisticasPorPeriodo(inicio, fim);
    }

    @DeleteMapping("/periodo")
    public ResponseEntity<?> excluirPorPeriodo(@Valid @RequestBody PeriodoRequest request) {
        ZonedDateTime inicio = ZonedDateTime.parse(request.getDataInicial());
        ZonedDateTime fim = ZonedDateTime.parse(request.getDataFinal());

        boolean removido = service.excluirTransacoesPorPeriodo(inicio, fim);
        return removido ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
