package br.com.fiap.soap.publisher;

import br.com.fiap.soap.repository.PedidoRepository;
import br.com.fiap.soap.service.PedidoService;
import br.com.fiap.soap.ws.PedidoWebService;

import javax.xml.ws.Endpoint;

public class ServicePublisher {

    public static void main(String[] args) {
        // Montagem das dependencias (injecao via construtor)
        PedidoRepository repository = new PedidoRepository();
        PedidoService service = new PedidoService(repository);
        PedidoWebService webService = new PedidoWebService(service);

        // Publicacao do servico SOAP
        String url = "http://localhost:8080/pedidos";
        Endpoint.publish(url, webService);

        System.out.println("===========================================");
        System.out.println("  SOAP WebService - Gestao de Pedidos");
        System.out.println("===========================================");
        System.out.println("Servico rodando em: " + url);
        System.out.println("WSDL disponivel em: " + url + "?wsdl");
        System.out.println("===========================================");
    }
}
