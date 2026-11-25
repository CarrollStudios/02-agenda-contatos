package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;

import model.Contato;

/**
 * Constelacao: a area de "favoritos".
 * Cada contato favorito vira uma estrela (bolinha roxa com o primeiro nome),
 * e as estrelas sao ligadas por linhas finas, como uma constelacao.
 *
 * Tambem e HERANCA: a Constelacao E um JPanel, so muda o desenho.
 *
 * As estrelas sao clicaveis: o clique avisa a TelaInicial qual contato foi escolhido.
 */
public class Constelacao extends JPanel {

    // Lugares das estrelas, em porcentagem da largura e da altura (0.0 a 1.0).
    // Cabem ate 7 favoritos; a ordem forma o "desenho" da constelacao.
    private static final double[][] POSICOES = {
        {0.36, 0.30}, {0.58, 0.18}, {0.80, 0.55}, {0.50, 0.68},
        {0.24, 0.80}, {0.14, 0.38}, {0.72, 0.88}
    };
    // Tamanho de cada estrela (variando, para parecer o ceu)
    private static final int[] TAMANHOS = {22, 16, 18, 13, 11, 14, 12};

    private List<Contato> favoritos = new ArrayList<>();
    private int estrelaEmCima = -1;           // qual estrela esta com o mouse em cima (-1 = nenhuma)
    private Consumer<Contato> aoClicar;       // o que fazer quando clicar numa estrela

    /**
     * Consumer<Contato>: e um "recado" que recebe um contato.
     * A TelaInicial entrega o recado, e a Constelacao usa quando alguem clica.
     */
    public Constelacao(Consumer<Contato> aoClicar) {
        this.aoClicar = aoClicar;
        setOpaque(false);
        setPreferredSize(new Dimension(360, 150));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        java.awt.event.MouseAdapter mouse = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int achou = estrelaNoPonto(e.getX(), e.getY());
                if (achou != estrelaEmCima) {
                    estrelaEmCima = achou;
                    setCursor(new java.awt.Cursor(achou >= 0 ? java.awt.Cursor.HAND_CURSOR : java.awt.Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                estrelaEmCima = -1;
                repaint();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int achou = estrelaNoPonto(e.getX(), e.getY());
                if (achou >= 0) {
                    Constelacao.this.aoClicar.accept(favoritos.get(achou));
                }
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    // Descobre se o ponto (x, y) esta em cima de alguma estrela ou do nome dela
    private int estrelaNoPonto(int px, int py) {
        int quantidade = Math.min(favoritos.size(), POSICOES.length);
        for (int i = 0; i < quantidade; i++) {
            int cx = x(i, getWidth());
            int cy = y(i, getHeight());
            // area de clique: um retangulo em volta da estrela e do nome
            if (px >= cx - 26 && px <= cx + 26 && py >= cy - 16 && py <= cy + 26) {
                return i;
            }
        }
        return -1;
    }

    // A TelaInicial chama este metodo sempre que os favoritos mudam
    public void setFavoritos(List<Contato> favoritos) {
        this.favoritos = favoritos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Sem favoritos: frase do design aprovado
        if (favoritos.isEmpty()) {
            g2.setColor(Tema.TEXTO_APAGADO);
            g2.setFont(Tema.titulo(15).deriveFont(java.awt.Font.ITALIC));
            String frase = "sua constelação está esperando a primeira estrela";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(frase, (w - fm.stringWidth(frase)) / 2, h / 2);
            g2.dispose();
            return;
        }

        int quantidade = Math.min(favoritos.size(), POSICOES.length);

        // 1. Linhas ligando uma estrela na proxima
        g2.setColor(new Color(0x4B, 0x43, 0x80));
        g2.setStroke(new BasicStroke(1.2f));
        for (int i = 0; i < quantidade - 1; i++) {
            g2.drawLine(x(i, w), y(i, h), x(i + 1, w), y(i + 1, h));
        }

        // 2. As estrelas e os nomes
        g2.setFont(Tema.texto(13));
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i < quantidade; i++) {
            int t = TAMANHOS[i];
            int cx = x(i, w);
            int cy = y(i, h);

            // brilho suave em volta (maior quando o mouse esta em cima)
            int brilho = (i == estrelaEmCima) ? 9 : 4;
            g2.setColor(new Color(0x8B, 0x5C, 0xF6, i == estrelaEmCima ? 70 : 40));
            g2.fillOval(cx - t / 2 - brilho, cy - t / 2 - brilho, t + brilho * 2, t + brilho * 2);
            // a estrela
            g2.setColor(i % 3 == 2 ? Tema.ROXO_ESCURO : Tema.ROXO);
            g2.fillOval(cx - t / 2, cy - t / 2, t, t);

            // primeiro nome embaixo
            String primeiroNome = favoritos.get(i).getNome().split(" ")[0];
            g2.setColor(i == estrelaEmCima ? Tema.ROXO : Tema.TEXTO);
            g2.drawString(primeiroNome, cx - fm.stringWidth(primeiroNome) / 2, cy + t / 2 + 15);
        }
        g2.dispose();
    }

    // Converte a porcentagem em posicao de verdade (em pixels)
    private int x(int i, int largura) {
        return (int) (POSICOES[i][0] * largura);
    }

    private int y(int i, int altura) {
        return (int) (POSICOES[i][1] * (altura - 30)) + 8;
    }
}
