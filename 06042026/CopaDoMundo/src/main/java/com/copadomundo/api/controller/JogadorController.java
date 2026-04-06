
package com.copadomundo.api.controller;

import com.copadomundo.api.model.Jogador;
import com.copadomundo.api.service.JogadorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService service;

    public JogadorController(JogadorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(service.listar());
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
