package com.fiap.reserva_equipamentos.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtualizarEquipamentoDTO {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Ativo é obrigatório")
    private Boolean ativo;
}
