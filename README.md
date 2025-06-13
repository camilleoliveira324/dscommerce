# 🛒 DSCommerce - E-commerce API com Spring Boot

Projeto backend de um sistema de e-commerce desenvolvido com Spring Boot, contendo autenticação, autorização, cadastro de usuários, produtos e pedidos, além de operações administrativas.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot (Web, Security, Data JPA)  
- Spring Security + OAuth2 / JWT
- Banco de dados - H2
- Bean Validation
- Maven

---

## 🧠 Conceitos aplicados

- **Arquitetura em camadas**: separação clara entre controller, service, repository e modelo
- **Padrão DTO (Data Transfer Object)**: usado para trafegar dados de forma segura e eficiente entre as camadas
- **Padrão Service Layer**: centraliza regras de negócio e deixa os controllers mais limpos
- **Padrão Repository**: interface com o banco de dados usando Spring Data JPA
- **Padrão Configuration**: centralização das configurações de segurança, CORS e autenticação
- **Autenticação e autorização com Spring Security** usando OAuth2 + JWT
- **Tratamento global de exceções** com `@ControllerAdvice`
- **Validações com Bean Validation** usando anotações como `@NotBlank`, `@Size`, etc.
- **Integração com banco H2** via JPA/Hibernate

---

## 🧑‍💻 Segurança: Endpoints públicos e protegidos

| Método | Rota                | Acesso     | Descrição                                     |
|--------|---------------------|------------|-----------------------------------------------|
| POST   | `/auth/token`       | 🔓 Público | Gera token de autenticação JWT                |
| GET    | `/products`         | 🔓 Público | Lista todos os produtos (paginado)            |
| GET    | `/products/{id}`    | 🔓 Público | Busca detalhes de um produto                  |
| POST   | `/products`         | 🔐 Admin   | Cadastra um novo produto                      |
| DEL    | `/products/{id}`    | 🔐 Admin   | Deleta um produto pelo ID                     |
| PUT    | `/products/{id}`    | 🔐 Admin   | Atualiza um produto pelo ID                   |
| GET    | `/users/me`         | 🔒 Cliente | Retorna dados do usuário autenticado          |
| POST   | `/orders`           | 🔒 Cliente | Realiza um novo pedido                        |
| GET    | `/orders`           | 🔒 Cliente | Lista os pedidos do usuário autenticado       |
| GET    | `/categories`       | 🔐 Admin   | Lista todas as categorias                     |
| GET    | `/orders/{id}`      | 🔐 Admin   | Visualiza qualquer pedido pelo ID             |

---

## 📦 Como rodar o projeto localmente

### Pré-requisitos

- Java 17+
- H2 (roda em memória)
- Maven

### 🎯 Rodando com perfil de teste (H2)
```bash
git clone https://github.com/camilleoliveira324/dscommerce.git
cd dscommerce
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=test
```

---

## 🧪 Testando com Postman

Você pode usar a [coleção do Postman](https://www.postman.com/collections/dea7904f994cb87c3d12) para testar os endpoints da API.

### 🧾 Exemplo de resposta – `GET /products`

```json
{
  "content": [
    {
      "id": 1,
      "name": "Smart TV",
      "price": 2190.0,
      "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"
    }
  ]
}
```
---

## 📈 Próximos passos
- Criar testes automatizados com JUnit e Spring Boot Test
- Implementar testes de integração para autenticação e pedidos
- Adicionar paginação e filtros nas listagens de pedidos
- Permitir upload de imagem de produtos (S3, por exemplo)
- Criar um dashboard admin com métricas (ex: vendas por dia, total por produto)
- Implementar cache para endpoints públicos

---

## 👩‍💻 Autora

Feito com 💜 por [Camille Oliveira](https://github.com/camilleoliveira324)

