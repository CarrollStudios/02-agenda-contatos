package ui;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * PainelVazio: o que a tela inicial mostra quando a agenda nao tem
 * nenhum contato (a tela "agenda vazia" do design).
 * Uma constelacao apagada, a frase de convite e o botao "adicionar contato".
 *
 * E a primeira coisa que uma pessoa nova ve ao abrir a agenda.
 */
public class PainelVazio extends JPanel {

    public PainelVazio(Runnable aoAdicionar) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));

        add(Box.createVerticalGlue()); // o desenho e os textos ficam no paintComponent

        Botao adicionar = new Botao("adicionar contato", Botao.PRINCIPAL);
        adicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        adicionar.addActionListener(e -> aoAdicionar.run());
        add(adicionar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int w = getWidth();
        int cx = w / 2;
        int topo = 70;

        // 1. constelacao apagada: estrelas vazias ligadas por linhas tracejadas
        int[][] pontos = {
            {cx - 70, topo + 95}, {cx - 25, topo + 35}, {cx + 55, topo + 57}, {cx + 100, topo}
        };
        int[] extra = {cx + 2, topo + 112}; // um galho saindo da segunda estrela
        float[] tracejado = {4f, 5f};
        g2.setColor(Tema.BORDA.brighter());
        g2.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, tracejado, 0f));
        for (int i = 0; i < pontos.length - 1; i++) {
            g2.drawLine(pontos[i][0], pontos[i][1], pontos[i + 1][0], pontos[i + 1][1]);
        }
        g2.drawLine(pontos[1][0], pontos[1][1], extra[0], extra[1]);

        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < pontos.length - 1; i++) {
            g2.drawOval(pontos[i][0] - 7, pontos[i][1] - 7, 14, 14);
        }
        g2.drawOval(extra[0] - 7, extra[1] - 7, 14, 14);

        // a ultima e a estrela acesa (roxa, de 4 pontas)
        g2.setColor(Tema.ROXO);
        int ex = pontos[3][0];
        int ey = pontos[3][1];
        int r = 14;
        Path2D estrela = new Path2D.Double();
        estrela.moveTo(ex, ey - r);
        estrela.quadTo(ex, ey, ex + r, ey);
        estrela.quadTo(ex, ey, ex, ey + r);
        estrela.quadTo(ex, ey, ex - r, ey);
        estrela.quadTo(ex, ey, ex, ey - r);
        g2.fill(estrela);

        // 2. textos
        g2.setColor(Tema.TEXTO);
        g2.setFont(Tema.titulo(26));
        FontMetrics fm = g2.getFontMetrics();
        escreverCentralizado(g2, fm, "sua constelação", cx, topo + 185);
        escreverCentralizado(g2, fm, "ainda está vazia", cx, topo + 218);

        g2.setColor(Tema.TEXTO_SUAVE);
        g2.setFont(Tema.texto(14));
        fm = g2.getFontMetrics();
        escreverCentralizado(g2, fm, "adicione seu primeiro contato para", cx, topo + 258);
        escreverCentralizado(g2, fm, "acender a primeira estrela.", cx, topo + 280);
        g2.dispose();
    }

    private void escreverCentralizado(Graphics2D g2, FontMetrics fm, String texto, int cx, int y) {
        g2.drawString(texto, cx - fm.stringWidth(texto) / 2, y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(360, 480);
    }
}
