-- ==========================================
-- CONSULTA: BUSCAR POR TELEFONE
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND telefone = '85999990001';
