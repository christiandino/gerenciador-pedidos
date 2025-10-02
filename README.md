# Gerenciador de Produtos

## 📌 Descrição
O **Gerenciador de Produtos** é uma API desenvolvida em **Java** utilizando **Spring Boot** para gerenciar um sistema completo de produtos, clientes, fornecedores, pedidos, categorias e usuários com diferentes níveis de acesso (ADMIN, GERENTE, VENDEDOR e CLIENTE).  

O sistema foi projetado com foco em **segurança**, **boa organização de pacotes** e **regras de negócio robustas**, garantindo que cada usuário tenha apenas as permissões adequadas.

---

## ⚙️ Tecnologias Utilizadas
- **Linguagem:** Java 17+
- **Framework:** Spring Boot
- **Segurança:** Spring Security com JWT (token de acesso gerado a partir de chave pública e privada)
- **Banco de Dados:** PostgreSQL (gerenciado via pgAdmin 4)
- **Gerenciamento de dependências:** Maven
- **Controle de versão:** Git / GitHub

---

## 📂 Estrutura do Projeto
O projeto está organizado em pacotes, seguindo boas práticas de arquitetura:

| Pacote          | Descrição |
|-----------------|-----------|
| `config`        | Configurações do Spring, segurança e JWT |
| `controller`    | Endpoints REST para cada entidade do sistema |
| `dtos`          | Objetos de transferência de dados (Data Transfer Objects) |
| `enums`         | Enumerações do sistema (ex.: tipos de usuários, status) |
| `exceptions`    | Tratamento centralizado de erros e exceções |
| `model`         | Entidades JPA mapeadas para o banco de dados |
| `repository`    | Interfaces Spring Data JPA para acesso ao banco |
| `service`       | Lógica de negócio e regras específicas |
| `utils`         | Classes utilitárias (ex.: geração de tokens, helpers) |

---

## 🛠 Funcionalidades
O sistema possui funcionalidades completas e regras de negócio bem definidas:

### Usuários
- Criação de usuários com papéis diferenciados: **ADMIN**, **GERENTE**, **VENDEDOR** e **CLIENTE**.
- Senhas criptografadas no banco de dados.
- Cada usuário possui **limite de acesso e permissões específicas**:
  - **ADMIN:** acesso completo a todas funcionalidades.
  - **GERENTE:** pode gerenciar produtos, pedidos e clientes, mas com restrições em algumas ações críticas.
  - **VENDEDOR:** pode consultar e cadastrar produtos e pedidos, mas não possui acesso completo.
  - **CLIENTE:** apenas consulta e realiza pedidos; não pode cadastrar ou excluir produtos.

### Produtos e Pedidos
- Controle completo de produtos e categorias.
- Restrições baseadas no status do pedido:
  - Cliente não pode excluir produto se o pedido estiver em andamento.
  - Apenas usuários autorizados podem cadastrar ou remover produtos.
  - Entre outras regras
  
### Segurança
- Autenticação via **JWT (JSON Web Token)**.
- Tokens gerados com **chave privada e pública**.
- Configuração de **Spring Security** para proteger todos os endpoints.
- Controle de acesso baseado em papéis (roles).

### Regras de Negócio
- Cada ação respeita o nível de acesso do usuário.
- Todas as senhas são armazenadas criptografadas.
- Apenas usuários com permissão podem executar operações sensíveis, garantindo integridade e segurança do sistema.

---

## 📌 Endpoints Principais
Exemplo de endpoints disponíveis:

| Método | Endpoint             | Descrição |
|--------|--------------------|-----------|
| POST   | `/login`            | Autenticação e geração de token JWT |
| POST   | `/usuarios`         | Criação de novos usuários |
| GET    | `/produtos`         | Listar produtos |
| POST   | `/produtos`         | Criar produto (restrito) |
| PUT    | `/produtos/{id}`    | Atualizar produto (restrito) |
| DELETE | `/produtos/{id}`    | Excluir produto (restrito, regras aplicadas) |
| GET    | `/pedidos`          | Listar pedidos |
| POST   | `/pedidos`          | Criar pedido (restrito ao cliente ou vendedor autorizado) |
| GET    | `/clientes`         | Listar clientes (restrito) |
| POST   | `/categorias`       | Criar categorias (restrito) |
| GET    | `/fornecedores`     | Listar fornecedores |

> ⚠️ O acesso a cada endpoint é controlado pelo **Spring Security** via JWT e **anotações `@PreAuthorize`**, garantindo que apenas usuários autorizados consigam realizar certas ações.

---

## 💡 Observações
- O banco de dados PostgreSQL deve estar configurado e disponível antes de iniciar a aplicação.
- A API pode ser consumida por clientes REST (Postman, Insomnia, front-end em Angular/React).
- Inicialmente, usuários como **admin, gerente, vendedor e cliente** podem ser gerados automaticamente via configuração de inicialização.

---

## 🔑 Como rodar a aplicação
1. Clonar o repositório:
```bash
git clone https://github.com/christiandino/gerenciador-pedidos.git
