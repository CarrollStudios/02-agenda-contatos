-- ==========================================
-- CONSULTA: CONTATOS SEM ANIVERSÁRIO

-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND aniversario IS NULL;
