
package com.copadomundo.api.service;

import com.copadomundo.api.model.*;
import com.copadomundo.api.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final SelecaoRepository timeRepository;

    public JogadorService(JogadorRepository jogadorRepository, SelecaoRepository timeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.timeRepository = timeRepository;
    }

    public List<Jogador> listar() {
        return jogadorRepository.findAll();
    }

    public Jogador salvar(Long timeId, Jogador jogador) {
        Selecao time = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        jogador.setTime(time);
        return jogadorRepository.save(jogador);
    }
}
