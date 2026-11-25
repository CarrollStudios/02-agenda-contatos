package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import javax.swing.JButton;

/**
 * Botao: os botoes grandes das telas da agenda, no visual da Carroll Studios.
 *
 * Existem 4 estilos (do design aprovado):
 * - PRINCIPAL: roxo cheio com a estrelinha da logo ("salvar contato")
 * - CONTORNO: so a borda ("cancelar")
 * - PERIGO: vermelho cheio ("excluir")
 * - CONTORNO_PERIGO: so a borda vermelha ("excluir contato")
 *
 * HERANCA: o Botao E um JButton. Ele herda o clique,
 * e nos so trocamos o desenho (a "roupa" do botao).
 */
public class Botao extends JButton {

    public static final int PRINCIPAL = 0;
    public static final int CONTORNO = 1;
    public static final int PERIGO = 2;
    public static final int CONTORNO_PERIGO = 3;

    private String texto;
    private int estilo;
    private boolean mouseEmCima = false;

    public Botao(String texto, int estilo) {
        this.texto = texto;
        this.estilo = estilo;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(360, 48));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        // hover: o botao fica um pouco mais claro
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { mouseEmCima = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { mouseEmCima = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        Color corTexto;

        if (estilo == PRINCIPAL) {
            g2.setColor(mouseEmCima ? new Color(0x9D76F8) : Tema.ROXO);
            g2.fillRoundRect(0, 0, w, h, 22, 22);
            corTexto = Tema.FUNDO;
        } else if (estilo == PERIGO) {
            g2.setColor(mouseEmCima ? new Color(0xFB8C8C) : Tema.ERRO);
            g2.fillRoundRect(0, 0, w, h, 22, 22);
            corTexto = Tema.FUNDO;
        } else {
            // estilos de contorno: fundo escuro e so a borda
            Color borda = (estilo == CONTORNO_PERIGO) ? Tema.ERRO : Tema.BORDA;
            g2.setColor(mouseEmCima ? Tema.FUNDO_SECUNDARIO : new Color(0, 0, 0, 0));
            g2.fillRoundRect(0, 0, w - 1, h - 1, 22, 22);
            g2.setColor(mouseEmCima && estilo == CONTORNO ? Tema.ROXO : borda);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, 22, 22);
            corTexto = (estilo == CONTORNO_PERIGO) ? Tema.ERRO : Tema.TEXTO;
        }

        // texto centralizado (o PRINCIPAL ganha a estrelinha antes)
        boolean comEstrela = (estilo == PRINCIPAL);
        g2.setFont(estilo == PRINCIPAL ? Tema.tituloNegrito(17) : Tema.texto(15));
        FontMetrics fm = g2.getFontMetrics();
        int espacoEstrela = comEstrela ? 22 : 0;
        int x = (w - espacoEstrela - fm.stringWidth(texto)) / 2;
        int y = (h - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(corTexto);
        if (comEstrela) {
            estrelaQuatroPontas(g2, x + 7, h / 2, 8);
        }
        g2.drawString(texto, x + espacoEstrela, y);
        g2.dispose();
    }

    // A estrela de 4 pontas, igual a que fica ao lado da lua na logo
    private void estrelaQuatroPontas(Graphics2D g2, int cx, int cy, int r) {
        Path2D forma = new Path2D.Double();
        forma.moveTo(cx, cy - r);
        forma.quadTo(cx, cy, cx + r, cy);
        forma.quadTo(cx, cy, cx, cy + r);
        forma.quadTo(cx, cy, cx - r, cy);
        forma.quadTo(cx, cy, cx, cy - r);
        g2.fill(forma);
    }
}
