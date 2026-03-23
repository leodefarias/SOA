package br.com.fiap.soap.model;

public class ItemPedido {

    private long id;
    private String descricao;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido() {}

    public ItemPedido(long id, String descricao, int quantidade, double precoUnitario) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
}
