package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import model.Contato;

/**
 * AvisoExcluir: a caixinha "excluir contato?" que aparece POR CIMA da tela
 * de detalhes, com o fundo escurecido.
 *
 * Ela e colocada no "glass pane" da janela: uma camada de vidro
 * transparente que fica por cima de tudo (como o botao de som no jogo).
 */
public class AvisoExcluir extends JPanel {

    private static final int LARGURA_CAIXA = 330;
    private static final int ALTURA_CAIXA = 250;

    private Contato contato;

    public AvisoExcluir(JanelaAgenda janela, Contato contato) {
        this.contato = contato;
        setOpaque(false);
        setLayout(null); // posicionamos os botoes "na mao"

        // "engole" os cliques fora da caixa, para nao clicar na tela de tras
        addMouseListener(new MouseAdapter() { });

        Botao cancelar = new Botao("cancelar", Botao.CONTORNO);
        cancelar.addActionListener(e -> janela.fecharAviso());
        add(cancelar);

        // Excluir: a janela tira o contato da lista
        // (depois: o metodo "excluir" do back-end apaga do MySQL)
        Botao excluir = new Botao("excluir", Botao.PERIGO);
        excluir.addActionListener(e -> janela.excluirContato(contato));
        add(excluir);
    }

    // Posiciona os botoes dentro da caixa, centralizada na janela
    @Override
    public void doLayout() {
        int cx = (getWidth() - LARGURA_CAIXA) / 2;
        int cy = (getHeight() - ALTURA_CAIXA) / 2;
        int larguraBotao = (LARGURA_CAIXA - 24 * 2 - 12) / 2;
        getComponent(0).setBounds(cx + 24, cy + ALTURA_CAIXA - 70, larguraBotao, 46);
        getComponent(1).setBounds(cx + 24 + larguraBotao + 12, cy + ALTURA_CAIXA - 70, larguraBotao, 46);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 1. escurece a tela de tras
        g2.setColor(new Color(5, 4, 15, 190));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 2. a caixa
        int x = (getWidth() - LARGURA_CAIXA) / 2;
        int y = (getHeight() - ALTURA_CAIXA) / 2;
        g2.setColor(Tema.FUNDO_SECUNDARIO);
        g2.fillRoundRect(x, y, LARGURA_CAIXA, ALTURA_CAIXA, 26, 26);
        g2.setColor(Tema.BORDA);
        g2.drawRoundRect(x, y, LARGURA_CAIXA, ALTURA_CAIXA, 26, 26);

        // 3. lixeira dentro de um circulo avermelhado
        int cx = x + LARGURA_CAIXA / 2;
        g2.setColor(new Color(0xF8, 0x71, 0x71, 40));
        g2.fillOval(cx - 24, y + 18, 48, 48);
        g2.setColor(Tema.ERRO);
        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int ly = y + 32;
        g2.drawLine(cx - 9, ly + 3, cx + 9, ly + 3);           // tampa
        g2.drawLine(cx - 3, ly, cx + 3, ly);                   // alca
        g2.drawRoundRect(cx - 7, ly + 6, 14, 15, 3, 3);        // corpo
        g2.drawLine(cx - 2, ly + 10, cx - 2, ly + 17);
        g2.drawLine(cx + 2, ly + 10, cx + 2, ly + 17);

        // 4. textos
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.titulo(22));
        FontMetrics fm = g2.getFontMetrics();
        String titulo = "excluir contato?";
        g2.drawString(titulo, cx - fm.stringWidth(titulo) / 2, y + 98);

        g2.setColor(Tema.TEXTO_SUAVE);
        g2.setFont(Tema.texto(13));
        fm = g2.getFontMetrics();
        String linha1 = contato.getNome() + " vai sair da sua constelação.";
        String linha2 = "essa ação não pode ser desfeita.";
        g2.drawString(linha1, cx - fm.stringWidth(linha1) / 2, y + 128);
        g2.drawString(linha2, cx - fm.stringWidth(linha2) / 2, y + 148);
        g2.dispose();
        super.paintComponent(g);
    }
}
