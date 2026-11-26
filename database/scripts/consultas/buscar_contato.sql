-- ==========================================
-- CONSULTA: BUSCAR CONTATO PELO NOME
-- =========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND nome LIKE '%Maria%';
