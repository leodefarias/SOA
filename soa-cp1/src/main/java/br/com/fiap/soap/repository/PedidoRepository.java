package br.com.fiap.soap.repository;

import br.com.fiap.soap.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PedidoRepository {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == 0) {
            pedido.setId(idGenerator.getAndIncrement());
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public Pedido buscarPorId(long id) {
        return pedidos.get(id);
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    public boolean remover(long id) {
        return pedidos.remove(id) != null;
    }
}
