package com.fiap.reserva_equipamentos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriarEquipamentoDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
}
