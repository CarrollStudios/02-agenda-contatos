-- ==========================================
-- CONSULTA: CONTAR CONTATOS
-- ==========================================

USE agenda_contatos;

SELECT COUNT(*) AS total_contatos
FROM contato
WHERE login_usuario = 'hadassa01';
