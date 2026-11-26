USE agenda_contatos;

-- Adicionar a coluna categoria
ALTER TABLE contato
ADD COLUMN categoria ENUM('Trabalho', 'Família', 'Amigos') NULL;

-- Conferir a estrutura atualizada da tabela
DESCRIBE contato;
