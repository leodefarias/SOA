package br.com.fiap.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ListaPedidosResponse {

    private List<PedidoResponse> pedidos;

    public ListaPedidosResponse() {}

    public ListaPedidosResponse(List<PedidoResponse> pedidos) {
        this.pedidos = pedidos;
    }

    @XmlElement(name = "pedido")
    public List<PedidoResponse> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoResponse> pedidos) { this.pedidos = pedidos; }
}
