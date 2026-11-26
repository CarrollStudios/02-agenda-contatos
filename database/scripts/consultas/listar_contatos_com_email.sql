-- ==========================================
-- CONSULTA: LISTAR CONTATOS COM E-MAIL
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND email IS NOT NULL;
