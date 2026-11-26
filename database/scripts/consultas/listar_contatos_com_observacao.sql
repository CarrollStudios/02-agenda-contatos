-- ==========================================
-- CONSULTA: CONTATOS COM OBSERVAÇÃO
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND observacao IS NOT NULL;
