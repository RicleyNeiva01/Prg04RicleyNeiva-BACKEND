# 🛠️ DeskFlow | Back-End

API REST desenvolvida em **Java Spring Boot** para o sistema de suporte técnico **DeskFlow**.

O objetivo do projeto é gerenciar usuários, técnicos, chamados, atendimentos e comentários, fornecendo uma API segura para comunicação com o Front-End desenvolvido em React.

---

# 🚀 Tecnologias Utilizadas

- ☕ Java 17
- 🍃 Spring Boot 3
- 🌐 Spring Web
- 🗄️ Spring Data JPA
- 🐘 PostgreSQL (Supabase)
- 🔐 Spring Security
- 🔑 JWT (JSON Web Token)
- 📦 Lombok
- 🔄 ModelMapper
- ☁️ Railway (Deploy)

---

# 📌 Funcionalidades

## 👤 Usuários

- Cadastro de usuários
- Atualização de usuários
- Exclusão de usuários
- Listagem de usuários
- Busca por ID
- Login com autenticação JWT
- Recuperação de senha

---

## 👨‍🔧 Técnicos

- Cadastro
- Atualização
- Exclusão
- Listagem
- Busca por ID

---

## 🎫 Chamados

- Abertura de chamados
- Atualização de chamados
- Exclusão
- Consulta por ID
- Listagem
- Alteração de Status
- Definição de prioridade
- Associação com categoria
- Associação com técnico responsável

Status disponíveis:

- ABERTO
- EM_ANDAMENTO
- CONCLUIDO
- CANCELADO

Prioridades:

- BAIXA
- MEDIA
- ALTA
- URGENTE

---

## 🗂️ Categorias

- Cadastro
- Atualização
- Exclusão
- Listagem

Exemplos:

- Hardware
- Software
- Rede
- Impressora
- Internet

---

## 💬 Comentários

Cada chamado pode receber comentários durante o atendimento.

Funcionalidades:

- Adicionar comentário
- Listar comentários do chamado
- Buscar comentário por ID
- Atualizar comentário
- Excluir comentário

---

## 📋 Atendimentos

O sistema registra o atendimento realizado pelo técnico responsável.

Funcionalidades:

- Registrar atendimento
- Consultar atendimento
- Atualizar atendimento

---

# 🔐 Segurança

O projeto utiliza:

- Spring Security
- JWT Authentication
- BCrypt Password Encoder

As rotas protegidas exigem autenticação via Token JWT.

Rotas públicas:

- Login
- Cadastro de usuário
- Recuperação de senha

---

# 🛣️ Endpoints Principais

## Autenticação

| Método | Endpoint |
|---------|----------|
| POST | /auth/login |
| POST | /auth/recuperar-senha |

---

## Usuários

| Método | Endpoint |
|---------|----------|
| GET | /usuarios |
| GET | /usuarios/{id} |
| POST | /usuarios |
| PUT | /usuarios/{id} |
| DELETE | /usuarios/{id} |

---

## Técnicos

| Método | Endpoint |
|---------|----------|
| GET | /tecnicos |
| GET | /tecnicos/{id} |
| POST | /tecnicos |
| PUT | /tecnicos/{id} |
| DELETE | /tecnicos/{id} |

---

## Categorias

| Método | Endpoint |
|---------|----------|
| GET | /categorias |
| GET | /categorias/{id} |
| POST | /categorias |
| PUT | /categorias/{id} |
| DELETE | /categorias/{id} |

---

## Chamados

| Método | Endpoint |
|---------|----------|
| GET | /chamados |
| GET | /chamados/{id} |
| POST | /chamados |
| PUT | /chamados/{id} |
| DELETE | /chamados/{id} |

---

## Comentários

| Método | Endpoint |
|---------|----------|
| GET | /comentarios |
| GET | /comentarios/{id} |
| POST | /comentarios |
| PUT | /comentarios/{id} |
| DELETE | /comentarios/{id} |

---

## Atendimentos

| Método | Endpoint |
|---------|----------|
| GET | /atendimentos |
| GET | /atendimentos/{id} |
| POST | /atendimentos |
| PUT | /atendimentos/{id} |

---

# 🗃️ Banco de Dados

O sistema utiliza:

- PostgreSQL
- Supabase

Persistência realizada através do Spring Data JPA.

---

# ☁️ Deploy

### Backend

Hospedado no **Railway**

### Banco

Hospedado no **Supabase**

---

# 🧪 Testes

A API foi validada utilizando:

- ✅ Postman

Operações testadas:

- GET
- POST
- PUT
- DELETE

---

# 🖥️ Front-End

Repositório:

**https://github.com/RicleyNeiva01/Prg04RicleyNeiva-FRONTEND**

Deploy:

**https://prg04-ricley-neiva-frontend.vercel.app**

---

# 👨‍💻 Desenvolvedor

**Ricley Neiva**
