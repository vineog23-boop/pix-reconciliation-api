<h1 align="center">💸 Pix Reconciliation API</h1>
<p align="center">Modelagem de vendas e pagamentos para uma futura conciliação Pix.</p>
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-2563EB?style=flat-square" alt="Java: 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1.0-0F766E?style=flat-square" alt="Spring Boot: 4.1.0">
  <img src="https://img.shields.io/badge/Status-Dom%C3%ADnio%20inicial-475569?style=flat-square" alt="Status: Domínio inicial">
</p>

<p align="center"><a href="#visão-geral">Visão geral</a> · <a href="#como-executar">Execução</a> · <a href="#próximos-passos">Próximos passos</a></p>

---

## Visão geral

Projeto pessoal de backend para estudar a conciliação entre vendas e pagamentos recebidos por pequenos comerciantes. O objetivo futuro é identificar valores divergentes, pagamentos parciais e eventos duplicados.

O checkpoint atual concentra **classes de domínio Java**, sem uma API REST implementada ou integração com provedores Pix. Os exemplos e estudos utilizam dados sintéticos.

## Estado da implementação

| Componente | O que existe |
| --- | --- |
| `Sale` | Identificadores da venda e vendedor, valor em `BigDecimal` e validações de obrigatoriedade e valor positivo. |
| `PaymentEvent` | Identificador do pagamento, valor positivo e identificador externo não nulo/não vazio. |
| `Receivable` | Classe inicial ainda sem campos ou comportamento. |
| Conciliação | Casos de uso ainda não implementados. |
| HTTP | Sem controllers ou endpoints. |
| Persistência | Dependências MySQL/JPA/Flyway; conexão e migrations ainda pendentes. |

## Tecnologias

| Tecnologia | Versão / estado |
| --- | --- |
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Web MVC, Data JPA e Validation | Dependências preparadas |
| MySQL Connector/J e Flyway MySQL | Presentes no `pom.xml`, sem conexão configurada |
| Maven Wrapper | Incluído |

**PostgreSQL não está configurado nesta versão.** O `application.properties` contém apenas o nome da aplicação; variáveis como `DB_URL` e `DB_PASSWORD` ainda não são consumidas por esse arquivo.

## Organização

| Caminho em `domain/` | Conteúdo |
| --- | --- |
| `sale/Sale.java` | Modelo de venda. |
| `payment/PaymentEvent.java` | Evento de pagamento. |
| `receivable/Receivable.java` | Estrutura inicial de recebível. |

As classes de venda e pagamento não dependem de Spring ou JPA. A organização usa Clean Architecture como referência para a evolução futura; os casos de uso e adaptadores ainda serão construídos. As declarações de pacote de `Sale` e `PaymentEvent` também precisam ser alinhadas aos subdiretórios.

## Como executar

Com **JDK 21**, clone e inspecione o projeto:

```bash
git clone https://github.com/vineog23-boop/pix-reconciliation-api.git
cd pix-reconciliation-api
bash ./mvnw compile
```

No Windows: `.\mvnw.cmd compile`.

A inicialização completa com `spring-boot:run` depende da configuração de datasource e schema. Ainda não há Docker Compose, servidor de banco preparado ou endpoint para testar com Postman.

## Testes

Existe o teste inicial de contexto Spring. Sem datasource configurado, ele ainda não constitui uma execução reproduzível da aplicação. Não há testes específicos de conciliação ou das classes de domínio.

## Próximos passos

- Completar `Receivable` e alinhar os pacotes das classes.
- Testar valores monetários e identificadores.
- Implementar o primeiro caso de conciliação e suas portas de persistência.
- Configurar banco e migrations coerentes com as dependências escolhidas.
- Adicionar idempotência, pagamentos parciais e endpoints conforme os casos de uso forem concluídos.

## Autor

**Vinícius Oliveira** · [GitHub](https://github.com/vineog23-boop) · [LinkedIn](https://www.linkedin.com/in/vinícius-oliveira-1770b7306)
