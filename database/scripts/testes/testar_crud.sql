-- ==========================================
-- TESTES CRUD
-- PROJETO: AGENDA DE CONTATOS
-- ==========================================

USE agenda_contatos;

-- ==========================================
-- 1. CREATE - CADASTRAR CONTATO
-- ==========================================

-- INSERT INTO contato
-- (login_usuario, nome, telefone, email, aniversario, observacao, favorito)
-- VALUES
-- ('hadassa01', 'Carolina Mendes', '85999990006',
-- 'carolina@email.com', '2006-06-20', 'Amiga de infância', FALSE);


-- ==========================================
-- 2. READ - CONSULTAR CONTATO
-- ==========================================

-- SELECT *
-- FROM contato
-- WHERE login_usuario = 'hadassa01';


-- ==========================================
-- 3. UPDATE - ATUALIZAR OBSERVAÇÃO
-- ==========================================

-- UPDATE contato
-- SET observacao = 'Amiga da escola e do curso'
-- WHERE id_contato = 1
-- AND login_usuario = 'hadassa01';


-- ==========================================
-- 4. UPDATE - MARCAR COMO FAVORITO
-- ==========================================

-- UPDATE contato
-- SET favorito = TRUE
-- WHERE id_contato = 2
-- AND login_usuario = 'hadassa01';


-- ==========================================
-- 5. UPDATE - DESMARCAR FAVORITO
-- ==========================================

-- UPDATE contato
-- SET favorito = FALSE
-- WHERE id_contato = 2
-- AND login_usuario = 'hadassa01';


-- ==========================================
-- 6. DELETE - EXCLUIR CONTATO
-- ==========================================

-- DELETE FROM contato
-- WHERE id_contato = 6
-- AND login_usuario = 'hadassa01';


-- ==========================================
-- 7. CONFERIR OS CONTATOS
-- ==========================================

SELECT *
FROM contato
ORDER BY id_contato;
