# Resultado das Consultas

## 1. Listar contatos

**Arquivo:** `listar_contatos.sql`

**Comando utilizado:**

```sql
SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
ORDER BY nome ASC;
```

**Resultado obtido no MySQL:**

```text
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
| id_contato | login_usuario | nome           | telefone    | email           | aniversario | observacao                 | favorito | categoria |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
|          3 | hadassa01     | Beatriz Santos | 85999990003 | bia@email.com   | NULL        | Colega de curso            |        1 | NULL      |
|          2 | hadassa01     | João Pedro     | 85999990002 | NULL            | 2004-08-22  | NULL                       |        0 | NULL      |
|          1 | hadassa01     | Maria Oliveira | 85999990001 | maria@email.com | 2005-04-15  | Amiga da escola e do curso |        1 | Amigos    |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
3 rows in set (0,00 sec)
```

## uma demostração de listagem por contato(pelo o nome dele)
