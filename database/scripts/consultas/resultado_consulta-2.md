## 2. Ordenar contatos por aniversário

**Arquivo:** `ordenar_por_aniversario.sql`

**Comando utilizado:**

```sql
SELECT *
FROM contato
WHERE login_usuario = 'hadassa01'
AND aniversario IS NOT NULL
ORDER BY MONTH(aniversario), DAY(aniversario);
```

**Resultado obtido no MySQL:**

```text
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
| id_contato | login_usuario | nome           | telefone    | email           | aniversario | observacao                 | favorito | categoria |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
|          1 | hadassa01     | Maria Oliveira | 85999990001 | maria@email.com | 2005-04-15  | Amiga da escola e do curso |        1 | Amigos    |
|          2 | hadassa01     | João Pedro     | 85999990002 | NULL            | 2004-08-22  | NULL                       |        0 | NULL      |
+------------+---------------+----------------+-------------+-----------------+-------------+----------------------------+----------+-----------+
2 rows in set (0,00 sec)
```
###mais uma demonstração agora por ordenação
