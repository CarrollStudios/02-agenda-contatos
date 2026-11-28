
package banco;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        Connection conexao = ConexaoMySQL.conectar();

        if (conexao != null) {
            System.out.println("Java conectado ao MySQL com sucesso!");

            try {
                conexao.close();
                System.out.println("Conexão encerrada!");
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        } else {
            System.out.println("Não foi possível conectar ao banco.");
        }
    }
}
