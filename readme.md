# Galleria Bank - Backend e Banco de Dados



Este repositório contém a configuração necessária para executar o serviço de backend da Galleria Bank e seu banco de dados PostgreSQL utilizando Docker e Docker Compose.

## Pré-requisitos

Para executar este projeto, você precisa ter o **Docker** e o **Docker Compose** instalados em sua máquina.

*   **Docker:** [Guia de Instalação do Docker](https://docs.docker.com/get-docker/)
*   **Docker Compose:** Geralmente vem incluído com as instalações mais recentes do Docker. Caso contrário, siga o [Guia de Instalação do Docker Compose](https://docs.docker.com/compose/install/).

## Estrutura do Projeto

O projeto é composto por dois arquivos principais de configuração para o ambiente Docker:

1.  `docker-compose.yml`: Define os serviços (`postgres` e `backend`), as redes e os volumes.
2.  `Dockerfile`: Contém as instruções para construir a imagem do serviço de backend (`backend`).

## 1. Configuração do Banco de Dados (PostgreSQL)

O serviço de banco de dados é configurado no `docker-compose.yml` com as seguintes variáveis de ambiente:

| Variável | Valor | Descrição |
| :--- | :--- | :--- |
| `POSTGRES_DB` | `galleria_bank` | Nome do banco de dados. |
| `POSTGRES_USER` | `admin` | Usuário do banco de dados. |
| `POSTGRES_PASSWORD` | `admin` | Senha do banco de dados. |

O banco de dados estará acessível na porta `5432` da sua máquina.

## 2. Configuração do Backend

O serviço de backend é uma aplicação Java (construída via Maven) que se conecta ao banco de dados.

### Variáveis de Ambiente do Backend

As seguintes variáveis de ambiente são usadas para configurar a conexão com o banco de dados e o JWT:

| Variável | Valor | Descrição |
| :--- | :--- | :--- |
| `DB_URL` | `jdbc:postgresql://postgres:5432/galleria_bank` | URL de conexão com o PostgreSQL (o nome do host é o nome do serviço no Docker Compose). |
| `DB_USER` | `admin` | Usuário do banco de dados. |
| `DB_PASS` | `admin` | Senha do banco de dados. |
| `JWT_SECRET` | `super-secret` | Chave secreta para assinatura de tokens JWT. |
| `JWT_EXPIRATION` | `86400000` | Tempo de expiração do token JWT em milissegundos (24 horas). |

O backend estará acessível na porta `8888` da sua máquina.

## 3. Como Executar o Projeto

Siga os passos abaixo para colocar o projeto em funcionamento.

### Passo 1: Construir e Iniciar os Serviços

No diretório raiz do projeto (onde estão o `docker-compose.yml` e o `Dockerfile`), execute o seguinte comando:

```bash
docker compose up --build -d
```

*   `up`: Inicia os serviços definidos no `docker-compose.yml`.
*   `--build`: Garante que a imagem do serviço `backend` seja construída a partir do `Dockerfile` antes de iniciar.
*   `-d`: Executa os contêineres em modo *detached* (segundo plano).

### Passo 2: Verificar o Status dos Contêineres

Para verificar se os serviços foram iniciados corretamente, execute:

```bash
docker compose ps
```

Você deverá ver os contêineres `postgres_bank` e `galleria_backend` com o status `Up`.

### Passo 3: Acessar a Aplicação

O serviço de backend estará disponível em:

```
http://localhost:8888
```

### Passo 4: Visualizar os Logs (Opcional)

Se precisar monitorar a inicialização ou depurar o backend, você pode visualizar os logs em tempo real:

```bash
docker compose logs -f backend
```

## 4. Parar e Remover os Contêineres

Para parar e remover os contêineres, as redes e os volumes criados pelo Docker Compose, execute:

```bash
docker compose down -v
```

*   `down`: Para e remove os contêineres e as redes.
*   `-v`: Remove também o volume de dados do PostgreSQL (`postgres_data`), garantindo um ambiente limpo para a próxima execução. **Use com cautela, pois isso apagará todos os dados do banco de dados.**

Se você quiser apenas parar os contêineres sem remover o volume de dados (para manter o estado do banco), use apenas:

```bash
docker compose stop
```
