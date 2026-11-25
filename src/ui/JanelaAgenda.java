package ui;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Contato;

/**
 * JanelaAgenda: a janela da agenda (a "televisao", como a TelaJogo no jogo).
 * Ela fica aberta o tempo todo e TROCA a tela que aparece dentro dela.
 *
 * Caminho entre as telas:
 *   inicial --(+)--> novo contato --(salvar)--> inicial
 *   inicial --(card ou estrela)--> detalhes --(editar)--> editar --(salvar)--> detalhes
 *   detalhes --(excluir)--> aviso "excluir contato?" --(excluir)--> inicial
 *
 * A TelaInicial e criada uma vez so e guardada aqui, para a lista
 * de contatos nao se perder quando vamos para outra tela e voltamos.
 */
public class JanelaAgenda extends JFrame {

    private TelaInicial telaInicial;

    public JanelaAgenda() {
        super("Agenda de Contatos • Carroll Studios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // icone da janela (o simbolo da Carroll Studios)
        java.net.URL caminho = getClass().getResource("/images/logo-simbolo.png");
        if (caminho != null) {
            List<Image> icones = new ArrayList<>();
            icones.add(new ImageIcon(caminho).getImage());
            setIconImages(icones);
        }

        telaInicial = new TelaInicial(this);
        trocarTela(telaInicial);
        pack();                       // ajusta a janela ao tamanho da tela
        setLocationRelativeTo(null);  // abre no meio do monitor
    }

    // ---------- TROCAR DE TELA ----------

    public void mostrarInicial() {
        telaInicial.atualizar(); // redesenha (algum favorito pode ter mudado)
        trocarTela(telaInicial);
    }

    public void mostrarNovoContato() {
        trocarTela(new TelaNovoContato(this));
    }

    public void mostrarDetalhes(Contato contato) {
        trocarTela(new TelaDetalhes(this, contato));
    }

    public void mostrarEditar(Contato contato) {
        trocarTela(new TelaEditarContato(this, contato));
    }

    // ---------- AVISO "EXCLUIR CONTATO?" ----------

    public void perguntarExcluir(Contato contato) {
        setGlassPane(new AvisoExcluir(this, contato));
        getGlassPane().setVisible(true);
    }

    public void fecharAviso() {
        getGlassPane().setVisible(false);
    }

    // ---------- O QUE AS TELAS PEDEM (depois: viram chamadas ao back-end) ----------

    // novo contato -> "salvar contato"   (back-end: adicionar)
    public void salvarNovoContato(Contato novo) {
        mostrarInicial();
        telaInicial.adicionarContato(novo);
    }

    // editar contato -> "salvar alteracoes"   (back-end: alterar)
    public void salvarEdicao(Contato antigo, Contato alterado) {
        telaInicial.substituirContato(antigo, alterado);
        mostrarDetalhes(alterado);
    }

    // aviso -> "excluir"   (back-end: excluir)
    public void excluirContato(Contato contato) {
        fecharAviso();
        telaInicial.removerContato(contato);
        mostrarInicial();
    }

    // Tira a tela atual e coloca a nova no lugar
    private void trocarTela(JPanel tela) {
        setContentPane(tela);
        revalidate();
        repaint();
    }
}
