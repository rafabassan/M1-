# Aula 4 - Integração com ViaCEP

Projeto cumulativo das Aulas 1, 2, 3 e 4 do Java Spring Fundamentals.

## Tecnologias
- Java 21
- Spring Boot 3.2.0
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Flyway
- ViaCEP
- Postman

## Banco
Crie um banco PostgreSQL chamado `crud`.

Configuração padrão:
- URL: `jdbc:postgresql://localhost:5432/crud`
- usuário: `postgres`
- senha: `postgres`

Se necessário, altere as variáveis `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` ou o `application.properties`.

As migrations Flyway criam a tabela `product`, inserem dados e adicionam/preenchem `distribution_center` com:
- `MOGI_DAS_CRUZES` = Mogi das Cruzes
- `RECIFE` = Recife
- `PORTO_ALEGRE` = Porto Alegre

## Executar
No terminal, na pasta do projeto:

```bash
mvn spring-boot:run
```

Ou:

```bash
mvn clean package
java -jar target/crud-0.0.1-SNAPSHOT.jar
```

## Endpoint principal da Aula 4

```http
GET http://localhost:8080/product/{id}/availability?cep={cep}
```

Exemplo:

```http
GET http://localhost:8080/product/p1/availability?cep=08773380
```

Fluxo:
1. Busca o produto pelo ID.
2. Consulta `https://viacep.com.br/ws/{cep}/json/`.
3. Obtém a cidade (`localidade`) do CEP.
4. Compara a cidade com o `distributionCenter` do produto.
5. Retorna `available: true` quando forem iguais; caso contrário, `false`.

Exemplo de resposta:

```json
{
  "productId": "p1",
  "productName": "Notebook Lenovo",
  "distributionCenter": "Mogi das Cruzes",
  "cityFromCep": "Mogi das Cruzes",
  "cep": "08773-380",
  "available": true
}
```

## Resiliência
- CEP com formato inválido: HTTP 400.
- CEP inexistente: HTTP 400.
- Produto inexistente/inativo: HTTP 404.
- ViaCEP indisponível ou sem resposta: HTTP 503.

## Endpoints mantidos das Aulas 1 a 3
- `GET /product`
- `GET /product/{id}`
- `POST /product`
- `PUT /product/{id}`
- `DELETE /product/{id}` (inativação)
- `GET /product?category=...`
- `GET /product/distribution-center/{distributionCenter}`
- `GET /product/distribution-center/{distributionCenter}/category?category=...`
- `GET /product/distribution-center/count`
- `GET /product/price/greater-than?price=...`
- `GET /product/name?name=...`
- `GET /product/top-expensive`

## Postman
A collection está em:
`postman/Aula4-ViaCEP.postman_collection.json`

Importe o arquivo no Postman e inicie a aplicação antes dos testes.
