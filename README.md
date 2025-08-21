# 🎓 MBA Full Cycle - Domain Driven Design (Java + Spring Boot)

Este repositório contém o código-fonte do curso **Domain Driven Design (DDD)** do MBA Full Cycle, traduzido e reimplementado em **Java** com **Spring Boot**.

O sistema implementa o domínio de **venda de ingressos de eventos**, aplicando conceitos de DDD, Unit of Work, Domain Events e Integration Events, além de comunicação assíncrona via **RabbitMQ**.

---

## 🚀 Arquitetura do Projeto

A aplicação é organizada em módulos que refletem os **contextos do domínio**:

- **Events**
    - Gerenciamento de eventos, seções e assentos.
    - Serviços de criação, publicação e atualização de eventos.
    - Repositórios para persistência em MySQL.

- **Orders**
    - Criação e listagem de pedidos de ingressos.
    - Interação com clientes, seções e reservas de assentos.
    - Integração com gateway de pagamento (simulado).

- **Partners**
    - Cadastro e gerenciamento de parceiros que criam eventos.
    - Dispara **Domain Events** como `PartnerCreated`.

- **Customers**
    - Registro e listagem de clientes.

- **Stored Events**
    - Persistência de **Domain Events** no banco para garantir consistência e reprocessamento.

- **Integration Events**
    - Publicação de eventos de integração em RabbitMQ.
    - Exemplo: quando um parceiro é criado, é publicado o evento `PartnerCreatedIntegrationEvent`.

- **Emails (Consumer)**
    - Módulo que simula serviços externos consumidores de eventos.
    - Escuta a fila `emails` no RabbitMQ e processa mensagens como `PartnerCreatedIntegrationEvent`.

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA / Hibernate**
- **MySQL**
- **RabbitMQ**
- **Redis**
- **JUnit 5 + Mockito**

---

## ▶️ Executando o Projeto

   1. **Subir as dependências (MySQL, RabbitMQ e Redis):**
   
      docker-compose up -d

   2. **Rodar a aplicação (Spring Boot):**

      ./mvnw spring-boot:run

   3. **Testar a API**
         •	Exemplos de endpoints:
            •	POST /partners → cria um parceiro
            •	POST /events → cria um evento
            •	POST /events/{eventId}/sections → adiciona seção a um evento
            •	POST /events/{eventId}/orders → cria um pedido
   
## 📡 Fluxo de Eventos

O fluxo de Domain Events e Integration Events no sistema funciona da seguinte forma:

+-------------+        +------------------+        +---------------------+
|   API REST  | -----> |   Domain Layer   | -----> |   DomainEventManager |
+-------------+        +------------------+        +---------------------+
       |                        |                           |
       | cria Partner           | dispara PartnerCreated    |
       v                        v                           v
+----------------+     +--------------------+        +----------------------+
| PartnerCreated | --> | StoredEvent (MySQL)| -----> | Integration Publisher |
+----------------+     +--------------------+        +----------------------+
                                                              |
                                                              v
                                                   +---------------------+
                                                   | RabbitMQ Exchange   |
                                                   +---------------------+
                                                              |
                                                              v
                                                   +---------------------+
                                                   | Emails Consumer     |
                                                   | (simula envio)      |
                                                   +---------------------+

   1.	Um **Partner** é criado (POST /partners).
   2.	O domínio dispara um **Domain Event: PartnerCreated**.
   3.	Esse evento é persistido em **Stored Events** (MySQL).
   4.	O **Integration Event Publisher** converte em PartnerCreatedIntegrationEvent e envia para o **RabbitMQ**.
   5.	O módulo **Emails** (consumidor) escuta a fila emails e processa o evento, simulando envio de e-mails.