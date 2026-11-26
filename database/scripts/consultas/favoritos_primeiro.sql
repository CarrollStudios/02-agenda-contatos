-- ==========================================
-- CONSULTA: FAVORITOS PRIMEIRO
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
ORDER BY favorito DESC, nome ASC;
