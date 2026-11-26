-- ==========================================
-- CONSULTA: ORDENAR POR ANIVERSÁRIO
-- ==========================================

USE agenda_contatos;

SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND aniversario IS NOT NULL
ORDER BY MONTH(aniversario), DAY(aniversario);
