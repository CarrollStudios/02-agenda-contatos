## 3. Listar favoritos primeiro

**Arquivo:** `favoritos_primeiro.sql`

**Comando utilizado:**

```sql id="t8s2mw"
SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
ORDER BY favorito DESC, nome ASC;
```

**Resultado obtido no MySQL:**

```text id="k9p3za"
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
| id_contato | login_usuario | nome           | telefone    | email           | aniversario | observacao                 | favorito | categoria |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
|          3 | hadassa01     | Beatriz Santos | 85999990003 | bia@email.com   | NULL        | Colega de curso            |        1 | NULL      |
|          1 | hadassa01     | Maria Oliveira | 85999990001 | maria@email.com | 2005-04-15  | Amiga da escola e do curso |        1 | Amigos    |
|          2 | hadassa01     | João Pedro     | 85999990002 | NULL            | 2004-08-22  | NULL                       |        0 | NULL      |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
3 rows in set (0,00 sec)
```
####demonstração feita pra mostra como aparece no mysql por listagem de contatos favoritos
