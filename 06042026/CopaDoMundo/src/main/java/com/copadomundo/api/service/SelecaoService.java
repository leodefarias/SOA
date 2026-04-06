
package com.copadomundo.api.service;

import com.copadomundo.api.model.Jogador;
import com.copadomundo.api.model.Selecao;
import com.copadomundo.api.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SelecaoService {
    private final SelecaoRepository selecaoRepository;
    private final JogadorRepository jogadorRepository;

    public SelecaoService(JogadorRepository jogadorRepository, SelecaoRepository timeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.selecaoRepository = timeRepository;
    }

    public List<Selecao> listar() {
        return selecaoRepository.findAll();
    }

    public Selecao salvar(Selecao time) {
        return selecaoRepository.save(time);
    }

    public Jogador salvar(Long timeId, Jogador jogador) {
        Selecao time = selecaoRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));

        jogador.setTime(time);
        return jogadorRepository.save(jogador);
    }
}
