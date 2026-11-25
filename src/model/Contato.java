package model;

/**
 * ============================================================
 *  CLASSE PROVISORIA - SO PARA TESTAR AS TELAS (front-end)
 * ============================================================
 * O modelo oficial do contato e do back-end (Yasmim e Isabelly).
 * Elas podem completar ou trocar esta classe, mas as telas usam
 * estes metodos: getNome(), getCelular(), getEmail(),
 * getObservacao(), getAniversario(), isFavorito() e setFavorito().
 *
 * Campos combinados com a equipe: nome, celular, e-mail,
 * observacao, aniversario e favorito.
 *
 * Encapsulamento: os atributos sao "private" (ninguem mexe direto);
 * para ler usamos os "get", e para mudar usamos os "set".
 */
public class Contato {

    private String nome;
    private String celular;
    private String email;
    private String observacao;
    private String aniversario; // ex.: "12/03"
    private boolean favorito;

    // Construtor: e chamado quando fazemos "new Contato(...)"
    public Contato(String nome, String celular, String email,
                   String observacao, String aniversario, boolean favorito) {
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.observacao = observacao;
        this.aniversario = aniversario;
        this.favorito = favorito;
    }

    public String getNome() { return nome; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public String getObservacao() { return observacao; }
    public String getAniversario() { return aniversario; }
    public boolean isFavorito() { return favorito; }

    public void setFavorito(boolean favorito) { this.favorito = favorito; }
}
