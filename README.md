# Projeto: Sistema de Filmes e Séries

Este projeto é uma API desenvolvida em Spring Boot como parte do curso de formação em Java Backend do Santander Coders 2024 em conjunto com a Ada Tech.

Esta API utiliza conexão com a API externa The Movie Database (TMDB) para buscar informações de filmes e séries.

## Tecnologias Utilizadas
* Spring Boot: Framework principal para desenvolvimento da API.
* PostgreSQL: Banco de dados para persistência dos dados de usuários, filmes e séries.
* Docker: Orquestração de containers para facilitar o ambiente de desenvolvimento e produção.
* Feign Client: Para simplificar as chamadas HTTP para a API externa TMDB.
* [TMDB API](https://developer.themoviedb.org/docs/getting-started): API externa para consulta de filmes e séries .
* Swagger: Documentação e simulação das requisições de forma interativa.

## Requisitos
- Docker e Docker Compose instalados.
- Java 17+ instalado.
- Token de API para o TMDB (disponível após criação de conta em TMDB).

## Executando o projeto
1. Clone o repositório
2. Acesse application.properties e insira seu token da API do TMDB:
  `tmdb.api.token=${API_TMDB_TOKEN}`
3. Verifique as configurações do Docker Compose e PostgreSQL no arquivo docker-compose.yml
4. Execute o seguinte comando para criar e iniciar os containers
`docker-compose up --build`
5. Acesse a documentação Swagger do projeto no caminho abaixo:
http://localhost:8090/swagger-ui.html

## Contribuição
Contribuições são bem-vindas! Abra uma issue para discutir as mudanças antes de submeter uma pull request.
