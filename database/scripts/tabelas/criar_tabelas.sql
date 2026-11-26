-- ==========================================
-- TABELA: CONTATO
-- ==========================================

USE agenda_contatos;

CREATE TABLE IF NOT EXISTS contato (
    id_contato INT NOT NULL AUTO_INCREMENT,
    login_usuario VARCHAR(50) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    aniversario DATE,
    observacao VARCHAR(255),
    favorito BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id_contato)
);
