
package banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import banco.ConexaoMySQL;

public class ContatoDAO {

    private Connection conexao;

    public ContatoDAO() {
        this.conexao = ConexaoMySQL.conectar();
    }

    // CADASTRAR CONTATO
    public boolean cadastrarContato(
            String loginUsuario,
            String nome,
            String telefone,
            String email,
            Date aniversario,
            String observacao,
            boolean favorito,
            String categoria) {

        String sql = "INSERT INTO contato "
                + "(login_usuario, nome, telefone, email, aniversario, "
                + "observacao, favorito, categoria) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, loginUsuario);
            stmt.setString(2, nome);
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.setDate(5, aniversario);
            stmt.setString(6, observacao);
            stmt.setBoolean(7, favorito);
            stmt.setString(8, categoria);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar contato: " + e.getMessage());
            return false;
        }
    }

    // LISTAR CONTATOS DE UM USUARIO
    public List<String> listarContatos(String loginUsuario) {

        List<String> contatos = new ArrayList<>();

        String sql = "SELECT * FROM contato "
                + "WHERE login_usuario = ? ORDER BY nome ASC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, loginUsuario);

            try (ResultSet resultado = stmt.executeQuery()) {

                while (resultado.next()) {
                    contatos.add(
                        resultado.getInt("id_contato") + " - "
                        + resultado.getString("nome")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar contatos: " + e.getMessage());
        }

        return contatos;
    }

    // EDITAR CONTATO
    public boolean editarContato(
            int idContato,
            String loginUsuario,
            String nome,
            String telefone,
            String email,
            Date aniversario,
            String observacao,
            String categoria) {

        String sql = "UPDATE contato SET nome = ?, telefone = ?, "
                + "email = ?, aniversario = ?, observacao = ?, categoria = ? "
                + "WHERE id_contato = ? AND login_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            stmt.setDate(4, aniversario);
            stmt.setString(5, observacao);
            stmt.setString(6, categoria);
            stmt.setInt(7, idContato);
            stmt.setString(8, loginUsuario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao editar contato: " + e.getMessage());
            return false;
        }
    }

    // EXCLUIR CONTATO
    public boolean excluirContato(int idContato, String loginUsuario) {

        String sql = "DELETE FROM contato "
                + "WHERE id_contato = ? AND login_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idContato);
            stmt.setString(2, loginUsuario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir contato: " + e.getMessage());
            return false;
        }
    }

    // MARCAR OU DESMARCAR FAVORITO
    public boolean alterarFavorito(
            int idContato,
            String loginUsuario,
            boolean favorito) {

        String sql = "UPDATE contato SET favorito = ? "
                + "WHERE id_contato = ? AND login_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1, favorito);
            stmt.setInt(2, idContato);
            stmt.setString(3, loginUsuario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao alterar favorito: " + e.getMessage());
            return false;
        }
    }

    // FECHAR CONEXAO
    public void fecharConexao() {

        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

