package br.com.fiap.soap.service;

import br.com.fiap.soap.dto.*;
import br.com.fiap.soap.model.ItemPedido;
import br.com.fiap.soap.model.Pedido;
import br.com.fiap.soap.model.StatusPedido;
import br.com.fiap.soap.repository.PedidoRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PedidoService {

    private final PedidoRepository repository;
    private final AtomicLong itemIdGenerator = new AtomicLong(1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public PedidoResponse criarPedido(CriarPedidoRequest request) {
        // Validacao: nome do cliente obrigatorio
        if (request.getNomeCliente() == null || request.getNomeCliente().trim().isEmpty()) {
            throw new RuntimeException("Nome do cliente e obrigatorio");
        }

        // Validacao: pelo menos 1 item
        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new RuntimeException("Pedido deve conter pelo menos 1 item");
        }

        // Validacao: cada item deve ter quantidade > 0 e preco > 0
        for (ItemPedidoDTO itemDTO : request.getItens()) {
            if (itemDTO.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade do item '" + itemDTO.getDescricao() + "' deve ser maior que zero");
            }
            if (itemDTO.getPrecoUnitario() <= 0) {
                throw new RuntimeException("Preco do item '" + itemDTO.getDescricao() + "' deve ser maior que zero");
            }
        }

        // Criacao do pedido
        Pedido pedido = new Pedido();
        pedido.setNomeCliente(request.getNomeCliente());

        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : request.getItens()) {
            ItemPedido item = new ItemPedido(
                    itemIdGenerator.getAndIncrement(),
                    itemDTO.getDescricao(),
                    itemDTO.getQuantidade(),
                    itemDTO.getPrecoUnitario()
            );
            itens.add(item);
        }
        pedido.setItens(itens);

        repository.salvar(pedido);
        return toResponse(pedido);
    }

    public PedidoResponse buscarPedido(long id) {
        Pedido pedido = repository.buscarPorId(id);
        if (pedido == null) {
            throw new RuntimeException("Pedido com ID " + id + " nao encontrado");
        }
        return toResponse(pedido);
    }

    public ListaPedidosResponse listarPedidos() {
        List<Pedido> todos = repository.listarTodos();
        List<PedidoResponse> responses = new ArrayList<>();
        for (Pedido pedido : todos) {
            responses.add(toResponse(pedido));
        }
        return new ListaPedidosResponse(responses);
    }

    public MensagemResponse atualizarStatus(long pedidoId, String novoStatus) {
        Pedido pedido = repository.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new RuntimeException("Pedido com ID " + pedidoId + " nao encontrado");
        }

        // Nao pode modificar pedido ja entregue
        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Pedido ja foi entregue e nao pode ser modificado");
        }

        StatusPedido novo;
        try {
            novo = StatusPedido.valueOf(novoStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status invalido: " + novoStatus
                    + ". Valores aceitos: RECEBIDO, EM_PREPARO, PRONTO, ENTREGUE");
        }

        validarTransicaoStatus(pedido.getStatus(), novo);

        pedido.setStatus(novo);
        repository.salvar(pedido);

        return new MensagemResponse(true,
                "Status do pedido " + pedidoId + " atualizado para " + novo);
    }

    public MensagemResponse cancelarPedido(long id) {
        Pedido pedido = repository.buscarPorId(id);
        if (pedido == null) {
            throw new RuntimeException("Pedido com ID " + id + " nao encontrado");
        }

        // Cancelamento apenas para pedidos RECEBIDO
        if (pedido.getStatus() != StatusPedido.RECEBIDO) {
            throw new RuntimeException("Apenas pedidos com status RECEBIDO podem ser cancelados. "
                    + "Status atual: " + pedido.getStatus());
        }

        repository.remover(id);
        return new MensagemResponse(true, "Pedido " + id + " cancelado com sucesso");
    }

    private void validarTransicaoStatus(StatusPedido atual, StatusPedido novo) {
        // Transicao deve ser sequencial: RECEBIDO -> EM_PREPARO -> PRONTO -> ENTREGUE
        if (novo.ordinal() != atual.ordinal() + 1) {
            throw new RuntimeException("Transicao de status invalida: " + atual + " -> " + novo
                    + ". A proxima transicao valida e: " + atual + " -> " + getProximoStatus(atual));
        }
    }

    private String getProximoStatus(StatusPedido atual) {
        StatusPedido[] valores = StatusPedido.values();
        if (atual.ordinal() + 1 < valores.length) {
            return valores[atual.ordinal() + 1].name();
        }
        return "N/A (status final)";
    }

    private PedidoResponse toResponse(Pedido pedido) {
        List<ItemPedidoDTO> itensDTO = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            itensDTO.add(new ItemPedidoDTO(
                    item.getDescricao(),
                    item.getQuantidade(),
                    item.getPrecoUnitario()
            ));
        }

        return new PedidoResponse(
                pedido.getId(),
                pedido.getNomeCliente(),
                pedido.getStatus().name(),
                itensDTO,
                pedido.getTotal(),
                pedido.getDataCriacao().format(FORMATTER)
        );
    }
}
