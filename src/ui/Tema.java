package ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Tema: guarda as cores e as letras da identidade visual da Carroll Studios.
 *
 * Por que uma classe so para isso? Para nao repetir os mesmos codigos de cor
 * em todas as telas. Se um dia a cor mudar, muda so aqui.
 *
 * "static final" quer dizer: pertence a classe (nao precisa de "new")
 * e nao muda nunca (e uma constante).
 */
public class Tema {

    // Paleta da Carroll Studios
    public static final Color FUNDO = new Color(0x0D0B1E);            // fundo principal
    public static final Color FUNDO_SECUNDARIO = new Color(0x1A1730); // cards e campos
    public static final Color ROXO = new Color(0x8B5CF6);             // destaque
    public static final Color ROXO_ESCURO = new Color(0x6D28D9);
    public static final Color TEXTO = new Color(0xE9E4FF);            // texto claro

    // Cores de apoio (variacoes da paleta)
    public static final Color TEXTO_SUAVE = new Color(0xB3A9E0);      // titulos pequenos
    public static final Color TEXTO_APAGADO = new Color(0x8A82B0);    // telefone, dica da busca
    public static final Color BORDA = new Color(0x2E2950);            // bordas finas
    public static final Color ERRO = new Color(0xF87171);             // vermelho suave (campo errado)

    // Letras: titulos com serifa (elegantes) e o resto sem serifa (facil de ler).
    // Se o computador nao tiver a letra, usamos uma parecida que todo Java tem.
    private static final String LETRA_TITULO = escolherLetra("Georgia", "Serif");
    private static final String LETRA_TEXTO = escolherLetra("Segoe UI", "SansSerif");

    public static Font titulo(int tamanho) {
        return new Font(LETRA_TITULO, Font.PLAIN, tamanho);
    }

    public static Font tituloNegrito(int tamanho) {
        return new Font(LETRA_TITULO, Font.BOLD, tamanho);
    }

    public static Font tituloItalico(int tamanho) {
        return new Font(LETRA_TITULO, Font.ITALIC, tamanho);
    }

    public static Font texto(int tamanho) {
        return new Font(LETRA_TEXTO, Font.PLAIN, tamanho);
    }

    // Procura a letra na lista de letras instaladas no computador
    private static String escolherLetra(String preferida, String reserva) {
        String[] instaladas = java.awt.GraphicsEnvironment
                .getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String nome : instaladas) {
            if (nome.equalsIgnoreCase(preferida)) {
                return preferida;
            }
        }
        return reserva;
    }
}
