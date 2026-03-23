package br.com.fiap.soap.ws;

import br.com.fiap.soap.dto.*;
import br.com.fiap.soap.service.PedidoService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "PedidoService", portName = "PedidoPort")
public class PedidoWebService {

    private final PedidoService pedidoService;

    public PedidoWebService() {
        this.pedidoService = null;
    }

    public PedidoWebService(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @WebMethod
    public PedidoResponse criarPedido(
            @WebParam(name = "pedido") CriarPedidoRequest request
    ) {
        try {
            return pedidoService.criarPedido(request);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar pedido: " + e.getMessage());
        }
    }

    @WebMethod
    public PedidoResponse buscarPedido(
            @WebParam(name = "id") long id
    ) {
        try {
            return pedidoService.buscarPedido(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar pedido: " + e.getMessage());
        }
    }

    @WebMethod
    public ListaPedidosResponse listarPedidos() {
        try {
            return pedidoService.listarPedidos();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    @WebMethod
    public MensagemResponse atualizarStatus(
            @WebParam(name = "pedidoId") long pedidoId,
            @WebParam(name = "novoStatus") String novoStatus
    ) {
        try {
            return pedidoService.atualizarStatus(pedidoId, novoStatus);
        } catch (RuntimeException e) {
            return new MensagemResponse(false, e.getMessage());
        }
    }

    @WebMethod
    public MensagemResponse cancelarPedido(
            @WebParam(name = "id") long id
    ) {
        try {
            return pedidoService.cancelarPedido(id);
        } catch (RuntimeException e) {
            return new MensagemResponse(false, e.getMessage());
        }
    }
}
