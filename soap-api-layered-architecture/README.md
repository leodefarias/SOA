# SOAP API - Sistema de Gestão de Pedidos (Arquitetura em Camadas)

## Contexto de Implantação

Este projeto simula um **sistema de gestão de pedidos para um restaurante** que precisa integrar diferentes setores (atendimento, cozinha e entrega) através de uma API SOAP. O serviço permite criar pedidos, acompanhar o status de preparo e calcular totais automaticamente.

A escolha de SOAP se justifica pela necessidade de um contrato formal (WSDL) entre os sistemas, garantindo interoperabilidade e tipagem forte nas mensagens trocadas — essencial em ambientes corporativos onde múltiplos sistemas precisam se comunicar de forma padronizada.

## Problemas Resolvidos

- **Gestão manual de pedidos:** Elimina controle por papel/planilha, centralizando pedidos em um serviço acessível por qualquer sistema
- **Falta de rastreabilidade:** Fluxo de status obrigatório (RECEBIDO → EM_PREPARO → PRONTO → ENTREGUE) garante visibilidade do pedido em tempo real
- **Erros de operação:** Validações impedem pedidos incompletos, transições de status inválidas e modificações em pedidos já entregues
- **Integração entre sistemas:** WSDL permite que qualquer plataforma (Java, .NET, Python) consuma o serviço

## Arquitetura em Camadas

```
br.com.fiap.soap/
├── model/           → Entidades de domínio (Pedido, ItemPedido, StatusPedido)
├── dto/             → Objetos de transferência JAXB (requests e responses)
├── repository/      → Camada de acesso a dados (armazenamento in-memory)
├── service/         → Lógica de negócio e validações
├── ws/              → Endpoint SOAP (@WebService)
├── publisher/       → Publicação do serviço (servidor HTTP embutido)
└── client/          → Consumidor SOAP (demonstra todas as operações)
```

### Fluxo de Dependências
```
Client → [SOAP/HTTP] → WebService (ws/) → Service → Repository → Model
                              ↓
                           DTOs (dto/)
```

## Regras de Negócio

1. Pedido deve ter nome do cliente preenchido e pelo menos 1 item
2. Cada item deve ter quantidade > 0 e preço unitário > 0
3. Transição de status deve ser sequencial: `RECEBIDO → EM_PREPARO → PRONTO → ENTREGUE`
4. Não é possível pular etapas de status nem voltar a um status anterior
5. Pedidos com status `ENTREGUE` não podem ser modificados
6. Cancelamento só é permitido para pedidos com status `RECEBIDO`
7. Total do pedido é calculado automaticamente (soma dos subtotais dos itens)

## Operações SOAP

| Operação | Descrição |
|----------|-----------|
| `criarPedido` | Cria um novo pedido com itens |
| `buscarPedido` | Busca um pedido pelo ID |
| `listarPedidos` | Lista todos os pedidos |
| `atualizarStatus` | Atualiza o status de um pedido (validando transição) |
| `cancelarPedido` | Cancela um pedido (apenas se RECEBIDO) |

## Boas Práticas Aplicadas

- **Separação de responsabilidades:** Cada camada tem uma responsabilidade única e bem definida
- **Injeção de dependência via construtor:** Desacoplamento entre camadas sem uso de frameworks
- **DTOs separados do modelo de domínio:** O modelo interno não é exposto diretamente via SOAP
- **Thread safety:** `ConcurrentHashMap` e `AtomicLong` no repositório para concorrência segura
- **Validação na camada de serviço:** Regras de negócio centralizadas em um único ponto
- **Tratamento de erros amigável:** Mensagens claras de erro via `MensagemResponse` ao invés de SOAP Faults genéricos
- **Contrato formal:** WSDL gerado automaticamente define o contrato da API

## Tecnologias

- Java 21
- Maven
- JAX-WS (jaxws-rt 2.3.7)
- JAXB (jaxb-api 2.3.1)
- Servidor HTTP embutido (javax.xml.ws.Endpoint)

## Como Executar

### 1. Compilar o projeto
```bash
cd soap-api-layered-architecture
mvn clean compile
```

### 2. Iniciar o servidor SOAP
```bash
mvn exec:java -Dexec.mainClass="br.com.fiap.soap.publisher.ServicePublisher"
```
O serviço estará disponível em: `http://localhost:8080/pedidos?wsdl`

### 3. Executar o cliente consumidor (em outro terminal)
```bash
mvn exec:java -Dexec.mainClass="br.com.fiap.soap.client.PedidoClient"
```

O cliente demonstra todas as operações: criação de pedidos, listagem, busca, atualização de status (incluindo cenários de erro), e cancelamento.

## Próximas Features

- **Persistência em banco de dados:** Substituir armazenamento in-memory por JPA/Hibernate
- **Autenticação WS-Security:** Adicionar autenticação e autorização nas operações SOAP
- **Notificações:** Webhook/callback quando o status de um pedido muda
- **Dashboard de métricas:** Relatórios de pedidos por período, faturamento, tempo médio de preparo
- **Multi-tenant:** Suporte a múltiplos restaurantes no mesmo serviço
- **Histórico de status:** Registrar data/hora de cada transição de status
