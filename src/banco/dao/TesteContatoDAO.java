package banco.dao;

import java.sql.Date;

public class TesteContatoDAO {

    public static void main(String[] args) {

        ContatoDAO dao = new ContatoDAO();

        boolean cadastrado = dao.cadastrarContato(
            "hadassa01",
            "Contato Teste DAO",
            "85999998888",
            "teste@exemplo.com",
            Date.valueOf("2005-04-15"),
            "Contato criado para testar o DAO",
            false,
            "Amigos"
        );

        if (cadastrado) {
            System.out.println("Contato cadastrado com sucesso!");
        } else {
            System.out.println("Não foi possível cadastrar o contato.");
        }

        dao.fecharConexao();
    }
}