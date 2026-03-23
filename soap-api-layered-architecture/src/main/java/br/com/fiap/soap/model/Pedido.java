package br.com.fiap.soap.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private long id;
    private String nomeCliente;
    private List<ItemPedido> itens;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.status = StatusPedido.RECEBIDO;
        this.dataCriacao = LocalDateTime.now();
    }

    public double getTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
