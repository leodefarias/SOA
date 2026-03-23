package br.com.fiap.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PedidoResponse {

    private long id;
    private String nomeCliente;
    private String status;
    private List<ItemPedidoDTO> itens;
    private double total;
    private String dataCriacao;

    public PedidoResponse() {}

    public PedidoResponse(long id, String nomeCliente, String status,
                          List<ItemPedidoDTO> itens, double total, String dataCriacao) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.status = status;
        this.itens = itens;
        this.total = total;
        this.dataCriacao = dataCriacao;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @XmlElementWrapper(name = "itens")
    @XmlElement(name = "item")
    public List<ItemPedidoDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoDTO> itens) { this.itens = itens; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }
}
