# e-Commerce Java API 

Esta é uma simples aplicação Spring Boot que demonstra o consumo de API para cadastro de produtos para venda eletrônica através de endereços REST com autenticação de usuário.

## Funcionalidades

- Registro de usuário: Permite que os usuários registrem com nome de usuário e senha.
- Autenticação de usuário: Valida as credenciais do usuário e gera um token JWT para as requisições da API subsequentes.
- Atenticação baseada em JWT: Segura que os endereços de API sejam impedidas por JWT tokens.
- Retorno de detalhes do usuário: Permite que seja acessado os detalhes do usuário com JWT Token.
- Ajuste de autenticação dinâmica: Habilita a mudança de autoridade de nível em tempo de execução com Spring Boot Actuator.
- Cadastro de novos produtos para apresentar para venda.
- Cadastro de prateleiras que definem a disponibilização dos produtos que estão à venda. A prateleira pode ter produtos de que estão contidas em outras prateleiras também, isso possiblita que sejam criadas categorias para separar produtos em acessos diferentes.
- Cadastro de cestas de compras para permitir recuperar a cesta de compras para fazer transações de pagamento e entrega. Cada usuário tem uma cesta de compras que contém ao menos um produto.
- Listagem de produtos por id de prateleira. A lista de produtos por prateleira serve para separar por categoria o que deve ser apresentado. Os produtos de exemplo de mesma categoria pode pertencer a uma categoria diversa ou categoria especifica do produto que está a venda.

## Funcionalidades Futuras

- Checkout da cesta de compras.
- Gerenciamento de entrega.
- Adicionar produtos à cesta de compras.

## Tecnologias Usadas

- Java
- Spring Boot
- Spring Security
- Spring Data JDBC
- MySQL Server
- JWT (JSON Web Tokens)
- Spring Boot Actuator
- Swagger
- JUnit

## Prerequisitos

- Java 17 ou mais recente instalado em seu computador.
- Maven instalado em seu computador.

## Começando

Clone este repositório em seu computador:

```
git clone https://github.com/pqnoje/easy-peazy-api.git
```

Navegue até o diretório do projeto:

```
cd easy-peazy-api
```

Construa o projeto com Maven:

```
mvn clean package
```

Rode a aplicação:

  - **Usando o Maven** <br/>``` mvn spring-boot:run```
  - **Pelo arquivo jar**
    Crie o arquivo jar usando o comando ```mvn clean install``` depois execute
    <br/>```java -jar target/<jar_filename>.jar```
  - **Direto pela IDE**
    <br/>```Botão direito em  Application.java e clique em 'Run'```
    <br/><br/>

Assim que a aplicação rodar você pode acessar os endereços da API através de chamadas HTTP pelo endereço URL: http://localhost:8080.

## Endereços de API

### Registro de usuários

- **URL:** `/api/register`
- **Method:** `POST`
- **Request Body:**
  ```json
{
    "username": "user",
    "password": "access123",
    "basket": {
        "description": "Cesta de compras do Jefferson",
        "products": []
    }
}
  ```
- **Resposta:** `"Registration successful"`

### Autenticação de usuário

- **URL:** `/api/authenticate`
- **Method:** `POST`
- **Request Body:**
```json
{
    "username": "user",
    "password": "access123"
}
```
- **Resposta:** JWT token

### Retornar os detalhes do usuário

- **URL:** `/api/user`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Resposta:** Um objeto JSON com os detalhes do usuário

### Cadastra um nova prateleira

- **URL:** `/api/shelf`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Request Body:**
```json
{
    "type": "Masculino",
    "description": "Vestuário Masculino"
}
```
- **Resposta:** Um objeto JSON com os detalhes da prateleira

### Cadastra um novo produto

- **URL:** `/api/product`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Request Body:**
```json
{
    "type": "Camiseta",
    "description": "Camiseta nova de manga curta.",
    "price": 29.99,
    "shelfs": [
        {
            "id": 1
        }
    ]
}
```
- **Resposta:** Um objeto JSON com os detalhes do produto

### Atualiza um produto

- **URL:** `/api/product`
- **Method:** `PUT`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Request Body:**
```json
{
    "type": "Camiseta",
    "description": "Camiseta nova.",
    "price": 34.99,
    "shelfs": [{
        "id": 1
    },{
        "id": 2
    }]
}
```
- **Resposta:** Um objeto JSON com os detalhes do produto

### Deleta um produto

- **URL:** `/api/product/id`
- **Method:** `DELETE`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Uma mensagem dizendo que o produto foi deletado

### Detalhes da prateleira

- **URL:** `/api/shelf/id`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Um objeto JSON com os detalhes da prateleira

http://localhost:8080/api/shelf/1

### Detalhes do produto

- **URL:** `/api/product/id`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Um objeto JSON com os detalhes do produto

### Lista de todos os produtos de uma prateleira

- **URL:** `/api/product/shelf/id`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Uma lista de todos os produtos cadastrados para a prateleira informada


## Swagger

- Para acessar o Swagger acesse a URI: /swagger-ui/index.html

## Testes unitários

- Para rodar os testes unitários rode o comando `mvn install`.

## Configuração

- A chave secreta de JWT e a expiração do token podem ser configuradas no arquivo `application.properties`.
- O banco de dados em memória H2 é usado por propósito de demonstração. Você pode alterar o banco de dados ao configurar em `application.properties`.

## Segurança

- As senhas são criptografadas usando algorítimos BCrypt para armazenar no banco de dados.
- Os endereços HTTP são seguros por Spring Security e JWT Tokens.
- Os tokens JWT são validados antes de garantir acesso aos endereços HTTP protegidos.

## Building With Docker on Linux

# Prerequisites

- Docker Desktop Installed

# Commands

```
docker build --platform linux/amd64 -t ecommerce . 
```

```
docker run -p 8080:8080 -t ecommerce
```



