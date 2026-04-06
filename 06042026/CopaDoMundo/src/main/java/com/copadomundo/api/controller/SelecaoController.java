
package com.copadomundo.api.controller;

import com.copadomundo.api.model.Jogador;
import com.copadomundo.api.model.Selecao;
import com.copadomundo.api.service.SelecaoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/selecao")
public class SelecaoController {

    private final SelecaoService service;

    public SelecaoController(SelecaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Selecao>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Selecao> criar(@RequestBody Selecao time) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(time));
    }

    @PostMapping("/{timeId}")
    public ResponseEntity<?> criar(@PathVariable Long timeId, @RequestBody Jogador jogador) {
        try {
            Jogador novo = service.salvar(timeId, jogador);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
