# Resultado dos Dados

## Contatos cadastrados

**Comando utilizado:**

```sql
SELECT *
FROM contato
ORDER BY id_contato;
```

**Resultado obtido no MySQL:**

```text
+------------+---------------+-----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
| id_contato | login_usuario | nome            | telefone    | email           | aniversario | observacao                 | favorito | categoria |
+------------+---------------+-----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
|          1 | hadassa01     | Maria Oliveira  | 85999990001 | maria@email.com | 2005-04-15  | Amiga da escola e do curso |        1 | Amigos    |
|          2 | hadassa01     | João Pedro      | 85999990002 | NULL            | 2004-08-22  | NULL                       |        0 | NULL      |
|          3 | hadassa01     | Beatriz Santos  | 85999990003 | bia@email.com   | NULL        | Colega de curso            |        1 | NULL      |
|          4 | isabelly02    | Lucas Ferreira  | 85999990004 | lucas@email.com | 2003-12-10  | NULL                       |        0 | NULL      |
|          5 | isabelly02    | Fernanda Pessoa | 85999990005 | NULL            | NULL        | Amiga da família           |        1 | NULL      |
+------------+---------------+-----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
5 rows in set (0,09 sec)
```
######uma demostração dos dados cadastrado no nosso mysql
