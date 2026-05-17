🛠️ DeskFlow | Back-End
API REST para o sistema de suporte técnico de TI DeskFlow.
O back-end do DeskFlow é responsável pela lógica de negócio da aplicação, gerenciamento de usuários, chamados de suporte e comunicação com o front-end.

🎯 Responsabilidades

Gerenciamento de usuários do sistema
Cadastro e gerenciamento de chamados de suporte
Controle de técnicos e atendimentos
Exposição de endpoints REST
Persistência de dados com JPA
Integração com banco de dados
Comunicação entre front-end e back-end


🗂️ Entidades do Sistema
EntidadeDescriçãoUsuarioUsuário cadastrado no sistemaChamadoChamado de suporte aberto pelo usuárioTecnicoTécnico responsável pelo atendimentoCategoriaCategoria do chamado (rede, hardware, software...)AtendimentoRegistro do atendimento realizado

🔗 Relacionamentos
RelacionamentoCardinalidadeUsuario → Chamado1 para NTecnico → Chamado1 para NChamado → CategoriaN para 1Chamado → Atendimento1 para 1

🛣️ Rotas da API
👤 Usuários
MétodoRotaDescriçãoGET/usuariosLista todos os usuáriosGET/usuarios/{id}Busca usuário por IDPOST/usuariosCadastra um usuárioPUT/usuarios/{id}Atualiza um usuárioDELETE/usuarios/{id}Remove um usuário
🎫 Chamados
MétodoRotaDescriçãoGET/chamadosLista todos os chamadosGET/chamados/{id}Busca chamado por IDPOST/chamadosAbre um chamadoPUT/chamados/{id}Atualiza um chamadoDELETE/chamados/{id}Remove um chamado
🔧 Técnicos
MétodoRotaDescriçãoGET/tecnicosLista todos os técnicosGET/tecnicos/{id}Busca técnico por IDPOST/tecnicosCadastra um técnicoPUT/tecnicos/{id}Atualiza um técnicoDELETE/tecnicos/{id}Remove um técnico
📋 Atendimentos
MétodoRotaDescriçãoGET/atendimentosLista todos os atendimentosPOST/atendimentosRegistra um atendimentoGET/atendimentos/{id}Busca atendimento por ID

🏗️ Estrutura do Projeto
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

🛠️ Tecnologias

☕ Java 17
🍃 Spring Boot 3
🗄️ Spring Data JPA
🔵 H2 Database
🌐 Spring Web
🔧 Lombok


🧪 Testes da API
Os endpoints da aplicação foram testados utilizando:
📮 Postman
Testes realizados:

✅ GET
✅ POST
✅ PUT
✅ DELETE


🔗 Repositório Front-End
🖥️ Front-End:
https://github.com/RicleyNeiva01/Prg04RicleyNeiva-FRONTEND