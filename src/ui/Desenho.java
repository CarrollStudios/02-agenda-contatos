package ui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * Desenho: classe ajudante com desenhos que mais de uma tela usa
 * (a estrela de favorito e as iniciais do avatar).
 * Os metodos sao "static": chamamos direto, sem "new" (ex.: Desenho.estrela(...)).
 */
public class Desenho {

    // Estrela de 5 pontas com centro em (cx, cy). "cheia" = pintada por dentro.
    public static void estrela(Graphics2D g2, int cx, int cy, int raio, boolean cheia) {
        Path2D forma = new Path2D.Double();
        for (int i = 0; i < 10; i++) {
            double r = (i % 2 == 0) ? raio : raio * 0.45; // ponta e "vale" se alternam
            double angulo = Math.toRadians(-90 + i * 36);
            double x = cx + r * Math.cos(angulo);
            double y = cy + r * Math.sin(angulo);
            if (i == 0) {
                forma.moveTo(x, y);
            } else {
                forma.lineTo(x, y);
            }
        }
        forma.closePath();

        if (cheia) {
            g2.setColor(Tema.ROXO);
            g2.fill(forma);
        } else {
            g2.setColor(Tema.TEXTO_APAGADO);
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(forma);
        }
    }

    // Fundo das telas: pontinhos de estrela discretos.
    // Random(7): o "7" faz o sorteio sair sempre igual, entao as estrelas nao pulam.
    public static void fundoEstrelado(Graphics2D g2, int largura, int altura) {
        java.util.Random sorteio = new java.util.Random(7);
        for (int i = 0; i < 70; i++) {
            int x = sorteio.nextInt(largura);
            int y = sorteio.nextInt(altura);
            int t = 1 + sorteio.nextInt(2); // tamanho 1 ou 2
            g2.setColor(new java.awt.Color(0x8B, 0x80, 0xC8, 90 + t * 30));
            g2.fillOval(x, y, t + 1, t + 1);
        }
    }

    // Iniciais para o avatar: "bruno costa" -> "BC"
    public static String iniciais(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "";
        }
        String[] partes = nome.trim().split("\\s+");
        String resultado = partes[0].substring(0, 1);
        if (partes.length > 1) {
            resultado += partes[partes.length - 1].substring(0, 1);
        }
        return resultado.toUpperCase();
    }
}
