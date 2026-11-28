
package banco.dao;

import java.sql.Date;
import java.util.List;

public class TesteCompletoContatoDAO {

    public static void main(String[] args) {

        ContatoDAO dao = new ContatoDAO();

        // 1. CADASTRAR
        System.out.println("\n--- TESTE DE CADASTRO ---");

        boolean cadastrado = dao.cadastrarContato(
            "hadassa01",
            "Contato Teste Completo",
            "85988887777",
            "completo@teste.com",
            Date.valueOf("2005-04-15"),
            "Teste completo do DAO",
            false,
            "Amigos"
        );

        System.out.println("Cadastro: " + cadastrado);

        // 2. LISTAR
        System.out.println("\n--- TESTE DE LISTAGEM ---");

        List<String> contatos = dao.listarContatos("hadassa01");

        for (String contato : contatos) {
            System.out.println(contato);
        }

        // 3. EDITAR
        System.out.println("\n--- TESTE DE EDIÇÃO ---");

        boolean editado = dao.editarContato(
            17,
            "hadassa01",
            "Contato Teste Editado",
            "85977776666",
            "editado@teste.com",
            Date.valueOf("2005-04-15"),
            "Contato editado pelo DAO",
            "Trabalho"
        );

        System.out.println("Edição: " + editado);
        
     // 4. MARCAR COMO FAVORITO
        System.out.println("\n--- TESTE DE FAVORITO ---");

        boolean favorito = dao.alterarFavorito(
            17,
            "hadassa01",
            true
        );

        System.out.println("Favorito ativado: " + favorito);

        // 5. DESMARCAR COMO FAVORITO
        boolean desfavorito = dao.alterarFavorito(
            17,
            "hadassa01",
            false
        );

        System.out.println("Favorito desativado: " + desfavorito);

     // 6. EXCLUIR CONTATO
        System.out.println("\n--- TESTE DE EXCLUSÃO ---");

        boolean excluido = dao.excluirContato(
            17,
            "hadassa01"
        );

        System.out.println("Exclusão: " + excluido);        
        // 4. FECHAR CONEXÃO
        dao.fecharConexao();
    }
}
