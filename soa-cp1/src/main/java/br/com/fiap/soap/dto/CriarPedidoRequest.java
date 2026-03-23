package br.com.fiap.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CriarPedidoRequest {

    private String nomeCliente;
    private List<ItemPedidoDTO> itens;

    public CriarPedidoRequest() {}

    public CriarPedidoRequest(String nomeCliente, List<ItemPedidoDTO> itens) {
        this.nomeCliente = nomeCliente;
        this.itens = itens;
    }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    @XmlElementWrapper(name = "itens")
    @XmlElement(name = "item")
    public List<ItemPedidoDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoDTO> itens) { this.itens = itens; }
}
