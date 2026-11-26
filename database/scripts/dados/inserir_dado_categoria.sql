
USE agenda_contatos;

INSERT INTO contato
(login_usuario, nome, telefone, email, aniversario, observacao, favorito, categoria)
VALUES
('hadassa01', 'Maria Oliveira', '85999990001', 'maria@email.com', '2005-04-15', 'Amiga da escola', TRUE, 'Amigos'),
('hadassa01', 'João Pedro', '85999990002', NULL, '2004-08-22', NULL, FALSE, 'Trabalho'),
('hadassa01', 'Beatriz Santos', '85999990003', 'bia@email.com', NULL, 'Colega de curso', TRUE, 'Família'),
('isabelly02', 'Lucas Ferreira', '85999990004', 'lucas@email.com', '2003-12-10', NULL, FALSE, 'Amigos'),
('isabelly02', 'Fernanda Pessoa', '85999990005', NULL, NULL, 'Amiga da família', TRUE, NULL);
