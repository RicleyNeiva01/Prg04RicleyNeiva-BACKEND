# 🛠️ DeskFlow | Back-End

API REST para o sistema de suporte técnico de TI DeskFlow.

O back-end do DeskFlow é responsável pela lógica de negócio da aplicação, gerenciamento de usuários, chamados de suporte e comunicação com o front-end.

---

## 🎯 Responsabilidades

- Gerenciamento de usuários do sistema
- Cadastro e gerenciamento de chamados de suporte
- Controle de técnicos e atendimentos
- Exposição de endpoints REST
- Persistência de dados com JPA
- Integração com banco de dados
- Comunicação entre front-end e back-end

---

## 🗂️ Entidades do Sistema

| Entidade      | Descrição                                          |
|---------------|----------------------------------------------------|
| Usuario       | Usuário cadastrado no sistema                      |
| Chamado       | Chamado de suporte aberto pelo usuário             |
| Tecnico       | Técnico responsável pelo atendimento               |
| Categoria     | Categoria do chamado (rede, hardware, software...) |
| Atendimento   | Registro do atendimento realizado                  |

---

## 🔗 Relacionamentos

| Relacionamento          | Cardinalidade |
|-------------------------|---------------|
| Usuario → Chamado       | 1 para N      |
| Tecnico → Chamado       | 1 para N      |
| Chamado → Categoria     | N para 1      |
| Chamado → Atendimento   | 1 para 1      |

---

## 🛣️ Rotas da API

### 👤 Usuários

| Método | Rota             | Descrição              |
|--------|------------------|------------------------|
| GET    | /usuarios        | Lista todos os usuários|
| GET    | /usuarios/{id}   | Busca usuário por ID   |
| POST   | /usuarios        | Cadastra um usuário    |
| PUT    | /usuarios/{id}   | Atualiza um usuário    |
| DELETE | /usuarios/{id}   | Remove um usuário      |

### 🎫 Chamados

| Método | Rota             | Descrição              |
|--------|------------------|------------------------|
| GET    | /chamados        | Lista todos os chamados|
| GET    | /chamados/{id}   | Busca chamado por ID   |
| POST   | /chamados        | Abre um chamado        |
| PUT    | /chamados/{id}   | Atualiza um chamado    |
| DELETE | /chamados/{id}   | Remove um chamado      |

### 🔧 Técnicos

| Método | Rota             | Descrição              |
|--------|------------------|------------------------|
| GET    | /tecnicos        | Lista todos os técnicos|
| GET    | /tecnicos/{id}   | Busca técnico por ID   |
| POST   | /tecnicos        | Cadastra um técnico    |
| PUT    | /tecnicos/{id}   | Atualiza um técnico    |
| DELETE | /tecnicos/{id}   | Remove um técnico      |

### 📋 Atendimentos

| Método | Rota                | Descrição                  |
|--------|---------------------|----------------------------|
| GET    | /atendimentos       | Lista todos os atendimentos|
| POST   | /atendimentos       | Registra um atendimento    |
| GET    | /atendimentos/{id}  | Busca atendimento por ID   |

---

## 🏗️ Estrutura do Projeto

```
📦 Prg04RicleyNeiva-BACKEND
└── 📂 src
    └── 📂 main
        ├── 📂 java
        │   └── 📂 br
        │       └── 📂 com
        │           └── 📂 ifba
        │               └── 📂 prg04deskflow
        │                   ├── 📂 controller
        │                   │   └── 📄 UsuarioController.java
        │                   │
        │                   ├── 📂 model
        │                   │   └── 📄 Usuario.java
        │                   │
        │                   ├── 📂 repository
        │                   │   └── 📄 UsuarioRepository.java
        │                   │
        │                   ├── 📂 service
        │                   │   └── 📄 UsuarioService.java
        │                   │
        │                   └── 📄 DeskflowBackendApplication.java
        │
        └── 📂 resources
            └── 📄 application.properties
```

---

## 🛠️ Tecnologias

- ☕ Java 17
- 🍃 Spring Boot 3
- 🗄️ Spring Data JPA
- 🔵 H2 Database
- 🌐 Spring Web
- 🔧 Lombok

---

## 🧪 Testes da API

Os endpoints da aplicação foram testados utilizando:

📮 **Postman**

Testes realizados:
- ✅ GET
- ✅ POST
- ✅ PUT
- ✅ DELETE

---

## 🔗 Repositório Front-End

🖥️ **Front-End:**
https://github.com/RicleyNeiva01/Prg04RicleyNeiva-FRONTEND
