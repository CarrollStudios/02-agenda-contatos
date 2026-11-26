-- ==========================================
-- CONSULTA: ORDENAR CONTATOS
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
ORDER BY nome ASC;
