INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_CONSULTA_USUARIOS','R');
INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_CONSULTA_USUARIO_ID','R');
INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_CADASTRO_ROOT','R');
INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_ATUALIZA_USUARIO_CLIENTE','R');
INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_ATUALIZA_USUARIO_ROOT','R');
/*INSERT INTO tb_permissao (codigo, tipo) VALUES ('ROLE_','R');*/


INSERT INTO tb_perfil (id, nome, tipo, padrao) VALUES ((SELECT nextval ('sq_perfil')),'ROOT','R', true);
INSERT INTO tb_perfil (id, nome, tipo, padrao) VALUES ((SELECT nextval ('sq_perfil')),'ADMINISTRADOR','A', true);
INSERT INTO tb_perfil (id, nome, tipo, padrao) VALUES ((SELECT nextval ('sq_perfil')),'CLIENTE','C', true);

INSERT INTO tb_perfil_permissao (id_perfil, id_permissao) VALUES ((SELECT id FROM tb_perfil WHERE nome = 'ROOT'), 'ROLE_CONSULTA_USUARIOS');
INSERT INTO tb_perfil_permissao (id_perfil, id_permissao) VALUES ((SELECT id FROM tb_perfil WHERE nome = 'ROOT'), 'ROLE_CONSULTA_USUARIO_ID');
INSERT INTO tb_perfil_permissao (id_perfil, id_permissao) VALUES ((SELECT id FROM tb_perfil WHERE nome = 'ROOT'), 'ROLE_CADASTRO_ROOT');
INSERT INTO tb_perfil_permissao (id_perfil, id_permissao) VALUES ((SELECT id FROM tb_perfil WHERE nome = 'ROOT'), 'ROLE_ATUALIZA_USUARIO_CLIENTE');
INSERT INTO tb_perfil_permissao (id_perfil, id_permissao) VALUES ((SELECT id FROM tb_perfil WHERE nome = 'ROOT'), 'ROLE_ATUALIZA_USUARIO_ROOT');

                                     
INSERT INTO tb_usuario (id, email, nome, senha, tp_usuario, idioma, id_perfil) VALUES ((SELECT nextval('sq_usuario')), 'admin@test.com', 'ADMIN', '$2a$10$GRruFm1WRGPZ6dGaTQ8ph.VwVnhzI3PkHSDIEjy/w7v0Ow7ZCMv86', 'R', 'BR', (SELECT id FROM tb_perfil WHERE nome = 'ROOT'));

INSERT INTO tb_configuracao (codigo, valor) VALUES ('perfil.root.padrao', (SELECT id FROM tb_perfil p WHERE p.nome = 'ROOT' AND tipo = 'R' AND padrao = true));
INSERT INTO tb_configuracao (codigo, valor) VALUES ('perfil.administrador.padrao', (SELECT id FROM tb_perfil p WHERE p.nome = 'ADMINISTRADOR' AND tipo = 'A' AND padrao = true));
INSERT INTO tb_configuracao (codigo, valor) VALUES ('perfil.cliente.padrao', (SELECT id FROM tb_perfil p WHERE p.nome = 'CLIENTE' AND tipo = 'C' AND padrao = true));