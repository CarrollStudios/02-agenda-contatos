package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import model.Contato;

/**
 * CardContato: o cartao de UM contato na lista (avatar, nome, celular e estrela).
 *
 * HERANCA: "extends JPanel" quer dizer que o CardContato E um JPanel.
 * Ele herda tudo o que o JPanel ja sabe fazer (aparecer na tela, ter tamanho,
 * receber clique) e nos so acrescentamos o desenho do card.
 */
public class CardContato extends JPanel {

    private static final int ALTURA = 70;

    private Contato contato;
    private boolean mouseEmCima = false;  // hover: a borda acende em roxo
    private Runnable aoFavoritar;         // o que fazer quando clicar na estrela
    private Runnable aoAbrir;             // o que fazer quando clicar no resto do card
    private boolean destacado = false;    // borda acesa por um tempinho

    public CardContato(Contato contato, Runnable aoFavoritar, Runnable aoAbrir) {
        this.contato = contato;
        this.aoFavoritar = aoFavoritar;
        this.aoAbrir = aoAbrir;
        setOpaque(false);
        setPreferredSize(new Dimension(360, ALTURA));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, ALTURA));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEmCima = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseEmCima = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // clicou na area da estrela (lado direito)? troca o favorito
                if (e.getX() > getWidth() - 56) {
                    aoFavoritar.run();
                } else {
                    aoAbrir.run(); // clicou no resto do card: abre os detalhes
                }
            }
        });
    }

    public Contato getContato() {
        return contato;
    }

    // Acende a borda por 1,5 segundo (usado quando clica no favorito da constelacao)
    public void piscar() {
        destacado = true;
        repaint();
        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
            destacado = false;
            repaint();
        });
        timer.setRepeats(false); // toca uma vez so
        timer.start();
    }

    /**
     * SOBRESCRITA (@Override): o JPanel ja tem um paintComponent.
     * Aqui nos reescrevemos esse metodo para desenhar o nosso card.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. Fundo do card com cantos arredondados
        g2.setColor(Tema.FUNDO_SECUNDARIO);
        g2.fillRoundRect(0, 0, w - 1, h - 1, 22, 22);

        // 2. Borda fina (roxa quando o mouse esta em cima)
        g2.setColor(mouseEmCima || destacado ? Tema.ROXO : Tema.BORDA);
        g2.setStroke(new BasicStroke(destacado ? 2.2f : 1.2f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, 22, 22);

        // 3. Avatar com anel de orbita
        desenharAvatar(g2, 18, (h - 42) / 2, 42);

        // 4. Nome e celular
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.texto(16));
        g2.drawString(contato.getNome(), 82, h / 2 - 3);

        g2.setColor(Tema.TEXTO_APAGADO);
        g2.setFont(Tema.texto(13));
        g2.drawString(contato.getCelular(), 82, h / 2 + 17);

        // 5. Estrela de favorito: cheia se for favorito, so contorno se nao for
        Desenho.estrela(g2, w - 30, h / 2, 10, contato.isFavorito());

        g2.dispose();
    }

    // Desenha o circulo roxo com as iniciais e o anel (igual ao anel da logo)
    private void desenharAvatar(Graphics2D g2, int x, int y, int tamanho) {
        double cx = x + tamanho / 2.0;
        double cy = y + tamanho / 2.0;
        double inclinacao = Math.toRadians(-18); // o anel fica inclinado, como na logo

        // parte de tras do anel (fica atras do circulo)
        Graphics2D anel = (Graphics2D) g2.create();
        anel.rotate(inclinacao, cx, cy);
        anel.setColor(Tema.ROXO_ESCURO);
        anel.setStroke(new BasicStroke(2f));
        anel.drawArc(x - 9, y + tamanho / 2 - 7, tamanho + 18, 14, 0, 180);
        anel.dispose();

        // circulo com degrade (claro em cima, escuro embaixo)
        g2.setPaint(new GradientPaint(0, y, Tema.ROXO, 0, y + tamanho, Tema.ROXO_ESCURO));
        g2.fillOval(x, y, tamanho, tamanho);
        g2.setColor(new Color(0xC4B5FD));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(x, y, tamanho, tamanho);

        // parte da frente do anel: so as pontas, para nao cobrir as iniciais
        anel = (Graphics2D) g2.create();
        anel.rotate(inclinacao, cx, cy);
        anel.setColor(Tema.ROXO_ESCURO);
        anel.setStroke(new BasicStroke(2f));
        anel.drawArc(x - 9, y + tamanho / 2 - 7, tamanho + 18, 14, 180, 35);
        anel.drawArc(x - 9, y + tamanho / 2 - 7, tamanho + 18, 14, 325, 35);
        anel.dispose();

        // iniciais centralizadas
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.titulo(16));
        FontMetrics fm = g2.getFontMetrics();
        String iniciais = Desenho.iniciais(contato.getNome());
        int tx = x + (tamanho - fm.stringWidth(iniciais)) / 2;
        int ty = y + (tamanho - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(iniciais, tx, ty);
    }
}
