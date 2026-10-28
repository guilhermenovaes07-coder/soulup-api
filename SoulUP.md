# 🌱 SoulUp API

API RESTful do **SoulUp**, uma plataforma de gamificação de ações sustentáveis. O usuário registra ações como reciclagem, ganha **Ecoa Points** e XP para o seu avatar, conquista **Selos Verdes** e acumula **créditos de carbono**.

Projeto da disciplina **Domain Driven Design using Java** (FIAP), 4º Sprint.

## 🔗 Links

| | |
|---|---|
| 🚀 **Deploy** | https://soulup-api-x6cl.onrender.com |
| ❤️ **Status da API** | https://soulup-api-x6cl.onrender.com/soulup |
| 📄 **Documentação (PDF)** | [`docs/SoulUp_Documentacao_Sprint4.pdf`](docs/SoulUp_Documentacao_Sprint4.pdf) |

> O deploy usa o plano gratuito do Render: depois de um tempo sem uso, o serviço "dorme" e a primeira requisição pode levar cerca de 50 segundos.

## 👥 Grupo Dome

- Arthur Cordão RM 570313
- Guilherme Novaes RM 570444
- Jeferson Faria RM573038
- Josué Menezes RM569220

## 🛠️ Tecnologias

- Java 21
- Spring Boot 4.1.1 (Spring Web e Validation)
- Oracle Database (JDBC)
- Maven
- Docker (deploy no Render)

## 🧱 Arquitetura

O projeto segue o padrão em camadas visto em aula, dentro da pasta [`soulup`](soulup):

| Pacote | Responsabilidade |
|---|---|
| `to` | Transfer Objects: uma classe por tabela do MER, com Bean Validation |
| `dao` | Acesso ao banco com JDBC (`ConnectionFactory` e uma DAO por entidade) |
| `bo` | Regras de negócio |
| `resource` | Endpoints REST e `CorsFilter` |

## 📡 Endpoints

Todos os caminhos começam em `/soulup`.

| Recurso | Caminho |
|---|---|
| Usuários | `/soulup/usuario` (+ `/{codigo}/avatar`, `/acoes`, `/selos`, `/creditos`) |
| Avatares | `/soulup/avatar` |
| Categorias de ação | `/soulup/categoria` |
| Ações sustentáveis | `/soulup/acao` (+ `/categoria/{codigoCategoria}`) |
| Registros de ação | `/soulup/registro-acao` (+ `PATCH /{codigo}/aprovar` e `/{codigo}/rejeitar`) |
| Selos verdes | `/soulup/selo` |
| Conquistas de selo | `/soulup/conquista` |
| Créditos de carbono | `/soulup/credito-carbono` |

Cada recurso tem `GET` (lista), `GET /{codigo}`, `POST`, `PUT /{codigo}` e `DELETE /{codigo}`. A tabela completa, com os códigos de status, está no PDF da documentação.

### Exemplo

```http
POST /soulup/usuario
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria@soulup.io",
  "senha": "senha123",
  "dataNascimento": "2000-04-15"
}
```

## ▶️ Como executar

**Requisitos:** JDK 21 e acesso ao banco Oracle com as tabelas criadas (script em [`docs/soulup_ddl.sql`](docs/soulup_ddl.sql)).

1. Defina as variáveis de ambiente (as credenciais não ficam no código):

   | Variável | Descrição |
   |---|---|
   | `DB_URL` | URL JDBC do Oracle |
   | `DB_USER` | Usuário do banco |
   | `DB_PASSWORD` | Senha do banco |

2. Entre na pasta do projeto e execute:

   ```bash
   cd soulup
   ./mvnw clean -DskipTests package
   java -jar target/soulup-0.0.1-SNAPSHOT.jar
   ```

   Ou rode a classe `SoulUpApplication` pela IDE.

3. Teste em `http://localhost:8080/soulup`.

## ☁️ Deploy

Publicado no **Render** como Web Service Docker, com `Root Directory` em `soulup` e as variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD` configuradas no painel.
