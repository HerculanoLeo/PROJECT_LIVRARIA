# PROJECT_LIVRARIA

Este é meu projeto pessoal de estudo com Spring Boot, nele tento por em pratica o que venho aprendendo com as meus estudos.

Futuramente quero integrar uma Single Page Application e uma aplicação mobile para consumir essa API.

# API REST

<p>Para subir a aplicação deverá importar o projeto no eclipse como um projeto Maven. </p>
<p>Subir um banco de dados PostegreSQL e criar uma base de dados com nome livraria: CREATE DATABASE livraria; </p>
<p>Rodar a carga inicial no banco, o arquivo encontra-se em: livraria_api_rest/src/main/resources/sql/inicial_access_scripts.sql. </p>
<p>Após isso editar o arquivo application.properties, encontra-se em: livraria_api_rest/src/main/resources/application.properties, com as informações referentes ao banco de dados criado como user, password e ip do banco </p>
<p>A autenticações da aplicações é por JWT e o endpoint dele encontra-se "http://endereço_do_servidor/auth", usando o método POST em Json: { "email" : "admin@teste.com", "password" : "1234" }.
