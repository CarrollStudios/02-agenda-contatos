-- ==========================================
-- CONSULTA: LISTAR FAVORITOS
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND favorito = TRUE;
