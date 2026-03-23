package br.com.fiap.soap.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemPedidoDTO {

    private String descricao;
    private int quantidade;
    private double precoUnitario;

    public ItemPedidoDTO() {}

    public ItemPedidoDTO(String descricao, int quantidade, double precoUnitario) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
}
