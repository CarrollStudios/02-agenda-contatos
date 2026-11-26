# Resultado das Tabelas

## Estrutura da tabela `contato`

**Comando utilizado:**

```sql
DESCRIBE contato;
```

**Resultado obtido no MySQL:**

```text
+---------------+--------------------------------------+------+-----+---------+----------------+
| Field         | Type                                 | Null | Key | Default | Extra          |
+---------------+--------------------------------------+------+-----+---------+----------------+
| id_contato    | int                                  | NO   | PRI | NULL    | auto_increment |
| login_usuario | varchar(50)                          | NO   |     | NULL    |                |
| nome          | varchar(100)                         | NO   |     | NULL    |                |
| telefone      | varchar(20)                          | NO   |     | NULL    |                |
| email         | varchar(100)                         | YES  |     | NULL    |                |
| aniversario   | date                                 | YES  |     | NULL    |                |
| observacao    | varchar(255)                         | YES  |     | NULL    |                |
| favorito      | tinyint(1)                           | NO   |     | 0       |                |
| categoria     | enum('Trabalho','Família','Amigos')  | YES  |     | NULL    |                |
+---------------+--------------------------------------+------+-----+---------+----------------+
9 rows in set (0,04 sec)
```
##uma demonstração de como aparece no mysql quando criamos a nossa tabela
