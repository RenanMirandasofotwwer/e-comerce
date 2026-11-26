# E-commerce

Projeto Spring Boot com serviços REST para clientes, produtos e categorias. Requer Java 21 e Maven (o projeto inclui o Maven Wrapper).

## Executar e testar

Na pasta `ecommerce`, execute `.\mvnw.cmd spring-boot:run` para iniciar a aplicação. O perfil `test` usa o banco H2 em memória e carrega dez clientes fictícios por meio de `import.sql`.

Para executar os testes automatizados, use `.\mvnw.cmd test`. O console H2 fica disponível em `/h2-console` durante a execução.

## API de clientes

O recurso `/clients` oferece:

- `GET /clients?page=0&linesPerPage=12&direction=ASC&orderBy=name`: busca paginada.
- `GET /clients/{id}`: busca por identificador.
- `POST /clients`: cria um cliente.
- `PUT /clients/{id}`: atualiza um cliente.
- `DELETE /clients/{id}`: remove um cliente.

Os campos do cliente são `id`, `name`, `cpf`, `income`, `birthDate` (data ISO `yyyy-MM-dd`) e `children`.
