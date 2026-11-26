-- ==========================================
-- CONSULTA: BUSCAR POR PARTE DO TELEFONE
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND telefone LIKE '%90001%';
