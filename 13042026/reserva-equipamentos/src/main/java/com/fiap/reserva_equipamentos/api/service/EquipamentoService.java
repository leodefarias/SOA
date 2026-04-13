package com.fiap.reserva_equipamentos.api.service;

import com.fiap.reserva_equipamentos.api.domain.Equipamento;
import com.fiap.reserva_equipamentos.api.dto.AtualizarEquipamentoDTO;
import com.fiap.reserva_equipamentos.api.dto.CriarEquipamentoDTO;
import com.fiap.reserva_equipamentos.api.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository repo;
    public EquipamentoService(EquipamentoRepository repo) { this.repo = repo; }

    public List<Equipamento> listarAtivos() { return repo.findByAtivoTrue(); }
    public List<Equipamento> buscar(String termo) { return repo.findByDescricaoContainingIgnoreCase(termo); }

    public Equipamento criar(CriarEquipamentoDTO dto) {
        Equipamento equipamento = new Equipamento();
        equipamento.setDescricao(dto.getDescricao());
        return repo.save(equipamento);
    }

    public Equipamento buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado: " + id));
    }

    public Equipamento atualizar(Long id, AtualizarEquipamentoDTO dto) {
        Equipamento equipamento = buscarPorId(id);
        equipamento.setDescricao(dto.getDescricao());
        equipamento.setAtivo(dto.getAtivo());
        return repo.save(equipamento);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}