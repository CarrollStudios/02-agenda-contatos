 <div align="center">
<img src="../resources/images/star-divider-1.svg" width="100%" alt="" />

<img src="https://img.shields.io/badge/🗄️_database-8B5CF6?style=for-the-badge&logoColor=white" alt="database" />

<sub>✦ agenda-contatos • Carroll Studios ✦</sub>
</div>

<br/>

<div align="center">

Modelagem, estrutura e gerenciamento do banco de dados MySQL da Agenda de Contatos.

<br/>

<img src="https://img.shields.io/badge/📈_DER-8B5CF6?style=flat-square&logoColor=white" alt="DER" />
<img src="https://img.shields.io/badge/🧩_DL-6D28D9?style=flat-square&logoColor=white" alt="DL" />
<img src="https://img.shields.io/badge/💾_SQL-EC4899?style=flat-square&logoColor=white" alt="SQL" />

</div>

<br/>

## 🪐 Estrutura

| Pasta | Conteúdo |
|---|---|
| `DER/` | Diagrama Entidade-Relacionamento |
| `DL/` | Diagrama Lógico |
| `scripts/tabelas/` | Scripts de criação das tabelas |
| `scripts/dados/` | Scripts de inserção de dados de teste |
| `scripts/consultas/` | Consultas SQL para manipulação e visualização dos contatos |
| `scripts/testes/` | Scripts utilizados nos testes das operações CRUD |

## 💾 Banco de dados

**Nome:** `agenda_contatos`

**Tabela:** `contato`

A tabela armazena as seguintes informações:

| Campo | Tipo | Descrição |
|---|---|---|
| `id_contato` | INT | Identificador do contato |
| `login_usuario` | VARCHAR(50) | Identifica o proprietário do contato |
| `nome` | VARCHAR(100) | Nome do contato |
| `telefone` | VARCHAR(20) | Telefone |
| `email` | VARCHAR(100) | E-mail (opcional) |
| `aniversario` | DATE | Data de aniversário (opcional) |
| `observacao` | VARCHAR(255) | Observações (opcional) |
| `favorito` | BOOLEAN | Indica se o contato é favorito |

## 🔐 Regras do banco

- Cada contato pertence a um usuário, identificado pelo campo `login_usuario`.
- Cada usuário possui sua própria agenda.
- Os contatos são separados pelo login do proprietário.
- O banco da Agenda não armazena senhas nem cadastra usuários.
- O campo `id_contato` é gerado automaticamente.
- Os campos de e-mail, aniversário e observação são opcionais.
- O campo `favorito` possui valor padrão `FALSE`.

## 🔎 Consultas SQL

Os scripts de consulta permitem:

- Listar contatos de um usuário.
- Listar contatos favoritos.
- Buscar contatos pelo nome.
- Ordenar contatos alfabeticamente.
- Contar contatos cadastrados.
- Listar contatos com e-mail.
- Listar contatos sem aniversário.
- Buscar contatos pelo telefone.
- Listar contatos com observações.
- Ordenar contatos por aniversário.
- Buscar por parte do telefone.
- Exibir favoritos primeiro.

## 🧪 Testes realizados

Foram realizados testes das operações CRUD:

| Operação | Descrição |
|---|---|
| CREATE | Cadastro de contatos |
| READ | Consulta de contatos |
| UPDATE | Atualização de informações e favoritos |
| DELETE | Exclusão de contatos |

Os comandos utilizados estão documentados em `scripts/testes/testar_crud.sql`.

## ⚙️ Tecnologias

- MySQL 8.0
- SQL

<br/>

<img src="../resources/images/star-divider-2.svg" width="100%" alt="" />

<div align="center">
<sub>✦ Carroll Studios • Agenda de Contatos ✦</sub>
</div>
