package ui;

import model.Contato;

/**
 * TelaEditarContato: o formulario para alterar um contato.
 *
 * HERANCA: "extends TelaNovoContato" quer dizer que esta tela E um
 * formulario de contato. Ela herda TUDO (campos, avatar, favorito,
 * validacao, botao) e so muda o que e diferente.
 *
 * SOBRESCRITA (@Override): os metodos tituloDaTela(), fraseDaTela(),
 * textoDoBotao(), voltar() e concluir() existem na classe mae,
 * e aqui nos escrevemos uma versao nova deles.
 */
public class TelaEditarContato extends TelaNovoContato {

    private Contato original; // o contato como estava antes de editar

    public TelaEditarContato(JanelaAgenda janela, Contato original) {
        super(janela); // "super": monta o formulario da classe mae primeiro
        this.original = original;

        // preenche os campos com os dados que o contato ja tem
        campoNome.setTexto(original.getNome());
        campoCelular.setTexto(original.getCelular());
        campoEmail.setTexto(original.getEmail());
        campoAniversario.setTexto(original.getAniversario());
        campoObservacao.setTexto(original.getObservacao());
        setFavorito(original.isFavorito());
    }

    @Override
    protected String tituloDaTela() {
        return "editar contato";
    }

    @Override
    protected String fraseDaTela() {
        return "ajuste o brilho desta estrela";
    }

    @Override
    protected String textoDoBotao() {
        return "salvar alterações";
    }

    // Seta de voltar: volta para os detalhes, sem mudar nada
    @Override
    protected void voltar() {
        janela.mostrarDetalhes(original);
    }

    // Salvar: troca o contato antigo pelo novo
    // (depois: aqui entra o metodo "alterar" do back-end, que atualiza no MySQL)
    @Override
    protected void concluir(Contato alterado) {
        janela.salvarEdicao(original, alterado);
    }
}
