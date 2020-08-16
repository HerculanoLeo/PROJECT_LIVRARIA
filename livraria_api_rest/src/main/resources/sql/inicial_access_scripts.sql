INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CADASTRAR_USUARIO_GRUPOS');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_ATUALIZAR_USUARIO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CADASTRAR_USUARIO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_USUARIO_POR_ID');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_USUARIO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_LIVROS');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CADASTRAR_LIVRO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_LIVRO_POR_ID');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_ATUALIZAR_LIVRO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_AUTORES_POR_ID_LIVRO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_LIVRO_ADICIONAR_AUTOR');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_DELETE_LIVRO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_LIVRO_DELETE_AUTOR');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_GRUPOS');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_PERMISSOES');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_GRUPO_POR_ID');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CADASTRAR_GRUPO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_ATUALIZAR_GRUPO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_DELETE_GRUPO');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_AUTORES');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CONSULTA_AUTOR_POR_ID');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_CADASTRAR_AUTOR');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_ATUALIZAR_AUTOR');
INSERT INTO tb_permissoes (codigo) VALUES ('ROLE_DELETE_AUTOR');

INSERT INTO tb_grupo_usuario (id, nome) VALUES ((SELECT nextval ('sq_grupo_usuario')),'ADMINISTRADORES');
INSERT INTO tb_grupo_usuario (id, nome) VALUES ((SELECT nextval ('sq_grupo_usuario')),'SISTEMA');
INSERT INTO tb_grupo_usuario (id, nome) VALUES ((SELECT nextval ('sq_grupo_usuario')),'COMUM');

INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CADASTRAR_USUARIO_GRUPOS');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_ATUALIZAR_USUARIO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CADASTRAR_USUARIO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_USUARIO_POR_ID');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_USUARIO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_LIVROS');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CADASTRAR_LIVRO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_LIVRO_POR_ID');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_ATUALIZAR_LIVRO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_AUTORES_POR_ID_LIVRO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_LIVRO_ADICIONAR_AUTOR');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_DELETE_LIVRO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_LIVRO_DELETE_AUTOR');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_GRUPOS');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_PERMISSOES');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_GRUPO_POR_ID');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CADASTRAR_GRUPO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_ATUALIZAR_GRUPO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_DELETE_GRUPO');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_AUTORES');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CONSULTA_AUTOR_POR_ID');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_CADASTRAR_AUTOR');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_ATUALIZAR_AUTOR');
INSERT INTO tb_grupo_usuario_permissoes (id_grupo_usuario, id_permissao) VALUES ((SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'),'ROLE_DELETE_AUTOR');

INSERT INTO tb_usuario (id, email, nome, senha) VALUES ((SELECT nextval('sq_usuario')), 'admin@test.com', 'ADMIN', '$2a$10$GRruFm1WRGPZ6dGaTQ8ph.VwVnhzI3PkHSDIEjy/w7v0Ow7ZCMv86');
INSERT INTO tb_usuario_grupo_usuario(id_usuario, id_grupo_usuario) VALUES ((SELECT id FROM tb_usuario WHERE email = 'admin@test.com'), (SELECT id FROM tb_grupo_usuario WHERE nome = 'ADMINISTRADORES'));