
package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConexaoMySQL {

    public static Connection conectar() {

        Properties propriedades = new Properties();

        try (FileInputStream arquivo = new FileInputStream("config.properties")) {

            propriedades.load(arquivo);

            String url = propriedades.getProperty("db.url");
            String usuario = propriedades.getProperty("db.usuario");
            String senha = propriedades.getProperty("db.senha");

            Connection conexao = DriverManager.getConnection(url, usuario, senha);

            System.out.println("Conexão com o banco realizada com sucesso!");

            return conexao;

        } catch (IOException e) {
            System.out.println("Erro ao carregar config.properties: " + e.getMessage());
            return null;

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
            return null;
        }
    }
}
