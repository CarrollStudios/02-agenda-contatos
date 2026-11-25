package ui;

import javax.swing.SwingUtilities;

/**
 * Main: liga a agenda. Cria a janela e mostra.
 * Quem troca as telas e a JanelaAgenda.
 */
public class Main {

    public static void main(String[] args) {
        // SwingUtilities.invokeLater: abre a janela do jeito seguro do Swing
        SwingUtilities.invokeLater(() -> {
            JanelaAgenda janela = new JanelaAgenda();
            janela.setVisible(true);
        });
    }
}
