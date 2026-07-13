# Pix Reconciliation API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow?style=for-the-badge)

## O que o projeto faz

O Pix Reconciliation API é um projeto backend para conciliar recebimentos Pix de pequenos comerciantes.

A aplicação tem como objetivo comparar os valores esperados com os pagamentos recebidos, identificar divergências e auxiliar no fechamento diário do caixa.

O projeto utiliza Clean Architecture como referência para manter as regras de negócio independentes do Spring Boot, banco de dados e HTTP.

> Este projeto utiliza dados sintéticos e não realiza movimentações financeiras reais.

## Funcionalidades

- ✅ Modelagem inicial do domínio de recebíveis
- ✅ Validação de valores monetários com `BigDecimal`
- ✅ Modelagem inicial de eventos de pagamento Pix
- ✅ Validação de identificadores obrigatórios
- ✅ Configuração inicial do PostgreSQL
- 🚧 Conciliação automática de pagamentos
- 🚧 Identificação de pagamentos divergentes
- 🚧 Tratamento de pagamentos parciais
- 🚧 Idempotência na importação de eventos Pix
- 🚧 Revisão manual de divergências
- 🚧 Fechamento diário do caixa
- 🚧 API REST
- 🚧 Testes automatizados
- 🚧 Docker Compose
- 🚧 Documentação com OpenAPI

## Tecnologias

| Tecnologia | Versão | Descrição |
|---|---:|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.1.0 | Framework da aplicação |
| Spring MVC | 4.1.0 | Desenvolvimento da API REST |
| Spring Data JPA | 4.1.0 | Persistência e acesso a dados |
| PostgreSQL | 18 | Banco de dados relacional |
| Flyway | Compatível com o projeto | Controle de migrations |
| Maven | Wrapper | Gerenciamento do projeto e dependências |
| Bean Validation | Spring Boot | Validação de dados |
| Git | - | Controle de versão |

## Como rodar

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL
- IntelliJ IDEA ou outra IDE compatível

### Configuração do banco

Crie um banco PostgreSQL chamado:

```text
fintrack
```

Configure a variável de ambiente com a senha do banco:

```text
DB_PASSWORD=sua_senha
```

As configurações padrão utilizadas pela aplicação são:

```text
DB_URL=jdbc:postgresql://localhost:5432/fintrack
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
```

A porta `5432` é a porta padrão utilizada pelo PostgreSQL neste ambiente. Caso o banco esteja configurado em outra porta, altere a variável `DB_URL`.

### Executando pela IDE

1. Abra o projeto na IntelliJ IDEA.
2. Configure a variável `DB_PASSWORD` na configuração de execução.
3. Verifique se o PostgreSQL está em execução.
4. Execute a classe principal da aplicação.

A aplicação será iniciada, por padrão, na porta:

```text
http://localhost:8080
```

### Executando pelo Maven

```bash
mvn spring-boot:run
```

Ainda não existe Docker Compose configurado para este projeto. Essa etapa será adicionada posteriormente.

## Endpoints

A API REST ainda está em desenvolvimento.

Os endpoints serão adicionados após a implementação dos casos de uso de aplicação e dos adaptadores HTTP.

## Arquitetura

O projeto segue uma abordagem inspirada nos princípios da Clean Architecture.

A regra principal é manter o domínio independente de Spring Boot, JPA, PostgreSQL e HTTP.

Estrutura atual:

```text
src/main/java/com/example/pixreconciliationapi/
├── PixReconciliationApiApplication.java
└── domain/
    ├── payment/
    │   └── PaymentEvent.java       # Evento de pagamento recebido
    ├── receivable/
    │   └── Receivable.java         # Valor esperado a receber
    └── sale/
        └── Sale.java               # Modelo inicial de venda
```

Estrutura planejada:

```text
src/main/java/com/example/pixreconciliationapi/
├── domain/
│   ├── receivable/                # Entidades e regras de recebíveis
│   ├── payment/                   # Eventos de pagamento Pix
│   ├── reconciliation/            # Regras de conciliação
│   └── shared/                    # Tipos compartilhados do domínio
├── application/
│   ├── port/
│   │   ├── in/                    # Portas de entrada dos casos de uso
│   │   └── out/                   # Portas para persistência e integrações
│   └── usecase/                   # Casos de uso da aplicação
├── adapters/
│   ├── in/
│   │   └── web/                   # Controllers e DTOs HTTP
│   └── out/
│       └── persistence/            # JPA, repositories e mapeadores
└── configuration/                 # Configuração e integração com Spring
```

Fluxo planejado:

```text
Controller
    ↓
Use Case
    ↓
Domain
    ↑
Persistence Adapter
```

O Controller recebe a requisição, o caso de uso coordena o fluxo e o domínio concentra as regras de negócio.

## Desafios enfrentados

### Modelagem do domínio

Foi necessário diferenciar venda, valor esperado e evento de pagamento, pois cada conceito representa uma parte diferente do processo financeiro.

### Regras no domínio

As validações de valores e identificadores foram colocadas nas próprias classes de domínio, mantendo os objetos sempre em um estado válido.

### Valores monetários

O projeto utiliza `BigDecimal` para evitar problemas de precisão comuns em operações financeiras com `double` ou `float`.

### Independência do Spring

As primeiras entidades foram criadas como classes Java simples, sem anotações do Spring ou do JPA.

### Conciliação de eventos

O projeto deverá controlar eventos Pix duplicados, pagamentos parciais e valores divergentes sem aplicar o mesmo pagamento duas vezes.

### Evolução incremental

A implementação está sendo feita por etapas: domínio, casos de uso, persistência, API REST, testes e infraestrutura.

## Testes

Os testes automatizados ainda serão implementados após a conclusão da primeira versão do domínio e dos casos de uso.

A estratégia planejada inclui:

- testes unitários das regras de domínio;
- testes dos casos de uso com Mockito;
- testes de integração com PostgreSQL;
- testes de idempotência;
- testes de pagamentos parciais e divergentes.

## Limitações atuais

- Não existe integração com bancos ou provedores Pix reais.
- A aplicação não movimenta dinheiro.
- Os dados utilizados serão sintéticos.
- A API REST ainda não foi implementada.
- A autenticação e autorização ainda não foram implementadas.
- O Docker Compose ainda não foi configurado.

## Autor

Vinicius Oliveira Goncalves

[GitHub](https://github.com/vineog23-boop)