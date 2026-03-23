package br.com.fiap.soap.client;

import br.com.fiap.soap.dto.*;
import br.com.fiap.soap.ws.PedidoWebService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Arrays;

public class PedidoClient {

    public static void main(String[] args) throws Exception {
        // Conexao com o servico SOAP via WSDL
        URL wsdlUrl = new URL("http://localhost:8080/pedidos?wsdl");
        QName serviceName = new QName("http://ws.soap.fiap.com.br/", "PedidoService");
        Service service = Service.create(wsdlUrl, serviceName);
        PedidoWebService port = service.getPort(PedidoWebService.class);

        System.out.println("===========================================");
        System.out.println("  Cliente SOAP - Gestao de Pedidos");
        System.out.println("===========================================\n");

        // 1. Criar pedido com 2 itens
        System.out.println(">>> 1. Criando pedido...");
        CriarPedidoRequest request = new CriarPedidoRequest(
                "Joao Silva",
                Arrays.asList(
                        new ItemPedidoDTO("X-Burger", 2, 25.00),
                        new ItemPedidoDTO("Refrigerante", 2, 8.00)
                )
        );
        PedidoResponse pedido = port.criarPedido(request);
        System.out.println("Pedido criado! ID: " + pedido.getId());
        System.out.println("Cliente: " + pedido.getNomeCliente());
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("Total: R$ " + String.format("%.2f", pedido.getTotal()));
        System.out.println("Data: " + pedido.getDataCriacao());
        System.out.println();

        // 2. Listar todos os pedidos
        System.out.println(">>> 2. Listando todos os pedidos...");
        ListaPedidosResponse lista = port.listarPedidos();
        System.out.println("Total de pedidos: " + lista.getPedidos().size());
        for (PedidoResponse p : lista.getPedidos()) {
            System.out.println("  - Pedido #" + p.getId() + " | " + p.getNomeCliente()
                    + " | " + p.getStatus() + " | R$ " + String.format("%.2f", p.getTotal()));
        }
        System.out.println();

        // 3. Buscar pedido por ID
        System.out.println(">>> 3. Buscando pedido por ID...");
        PedidoResponse encontrado = port.buscarPedido(pedido.getId());
        System.out.println("Pedido #" + encontrado.getId() + " encontrado!");
        System.out.println("Itens:");
        for (ItemPedidoDTO item : encontrado.getItens()) {
            System.out.println("  - " + item.getDescricao()
                    + " | Qtd: " + item.getQuantidade()
                    + " | R$ " + String.format("%.2f", item.getPrecoUnitario()));
        }
        System.out.println();

        // 4. Tentar transicao invalida (RECEBIDO -> PRONTO)
        System.out.println(">>> 4. Tentando transicao invalida (RECEBIDO -> PRONTO)...");
        MensagemResponse respInvalida = port.atualizarStatus(pedido.getId(), "PRONTO");
        System.out.println("Sucesso: " + respInvalida.isSucesso());
        System.out.println("Mensagem: " + respInvalida.getMensagem());
        System.out.println();

        // 5. Atualizar status corretamente: RECEBIDO -> EM_PREPARO -> PRONTO -> ENTREGUE
        System.out.println(">>> 5. Atualizando status sequencialmente...");

        MensagemResponse resp1 = port.atualizarStatus(pedido.getId(), "EM_PREPARO");
        System.out.println("  RECEBIDO -> EM_PREPARO: " + resp1.getMensagem());

        MensagemResponse resp2 = port.atualizarStatus(pedido.getId(), "PRONTO");
        System.out.println("  EM_PREPARO -> PRONTO: " + resp2.getMensagem());

        MensagemResponse resp3 = port.atualizarStatus(pedido.getId(), "ENTREGUE");
        System.out.println("  PRONTO -> ENTREGUE: " + resp3.getMensagem());
        System.out.println();

        // 6. Tentar modificar pedido entregue
        System.out.println(">>> 6. Tentando modificar pedido ja entregue...");
        MensagemResponse respEntregue = port.atualizarStatus(pedido.getId(), "EM_PREPARO");
        System.out.println("Sucesso: " + respEntregue.isSucesso());
        System.out.println("Mensagem: " + respEntregue.getMensagem());
        System.out.println();

        // 7. Criar segundo pedido e cancelar
        System.out.println(">>> 7. Criando segundo pedido e cancelando...");
        CriarPedidoRequest request2 = new CriarPedidoRequest(
                "Maria Souza",
                Arrays.asList(
                        new ItemPedidoDTO("Pizza Margherita", 1, 45.00),
                        new ItemPedidoDTO("Suco Natural", 1, 12.00)
                )
        );
        PedidoResponse pedido2 = port.criarPedido(request2);
        System.out.println("Pedido #" + pedido2.getId() + " criado para " + pedido2.getNomeCliente());

        MensagemResponse respCancelar = port.cancelarPedido(pedido2.getId());
        System.out.println("Cancelamento: " + respCancelar.getMensagem());
        System.out.println();

        // 8. Verificar lista final
        System.out.println(">>> 8. Lista final de pedidos...");
        ListaPedidosResponse listaFinal = port.listarPedidos();
        System.out.println("Total de pedidos: " + listaFinal.getPedidos().size());
        for (PedidoResponse p : listaFinal.getPedidos()) {
            System.out.println("  - Pedido #" + p.getId() + " | " + p.getNomeCliente()
                    + " | " + p.getStatus() + " | R$ " + String.format("%.2f", p.getTotal()));
        }

        System.out.println("\n===========================================");
        System.out.println("  Demonstracao concluida!");
        System.out.println("===========================================");
    }
}
