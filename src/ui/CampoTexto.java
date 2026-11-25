package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * CampoTexto: um campo do formulario com o titulo em cima
 * (ex.: "nome completo") e a caixa arredondada embaixo.
 *
 * Por que uma classe so para isso? Porque a tela "novo contato" tem 5 campos
 * iguais, e a tela "editar contato" vai usar os mesmos. Assim nao repetimos codigo.
 *
 * Validacao visual (do design aprovado):
 * - borda roxa quando o campo esta certo;
 * - borda vermelha suave e uma mensagem embaixo quando esta errado.
 */
public class CampoTexto extends JPanel {

    // os 3 jeitos que a borda pode ficar
    private static final int NORMAL = 0;
    private static final int CERTO = 1;
    private static final int ERRADO = 2;

    private JTextField campo;
    private JLabel mensagem;   // a frase vermelha embaixo, quando tem erro
    private String dica;       // texto cinza quando o campo esta vazio (ex.: "dd/mm")
    private int estado = NORMAL;

    public CampoTexto(String titulo, String dica) {
        this.dica = dica;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // 1. Titulo em cima
        JLabel rotulo = new JLabel(titulo);
        rotulo.setFont(Tema.titulo(15));
        rotulo.setForeground(Tema.TEXTO_SUAVE);
        rotulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(rotulo);
        add(javax.swing.Box.createVerticalStrut(5));

        // 2. Caixa arredondada (desenhada por nos) com o campo de digitar dentro
        JPanel caixa = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.FUNDO_SECUNDARIO);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                if (estado == ERRADO) {
                    g2.setColor(Tema.ERRO);
                } else if (estado == CERTO || campo.hasFocus()) {
                    g2.setColor(Tema.ROXO);
                } else {
                    g2.setColor(Tema.BORDA);
                }
                g2.setStroke(new BasicStroke(1.4f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };
        caixa.setOpaque(false);
        caixa.setAlignmentX(Component.LEFT_ALIGNMENT);
        caixa.setPreferredSize(new Dimension(160, 42));
        caixa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        caixa.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));

        campo = new JTextField() {
            // dica cinza quando esta vazio
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !CampoTexto.this.dica.isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(Tema.TEXTO_APAGADO);
                    g2.setFont(getFont());
                    g2.drawString(CampoTexto.this.dica, 2, getHeight() / 2 + 5);
                    g2.dispose();
                }
            }
        };
        campo.setOpaque(false);
        campo.setBorder(null);
        campo.setFont(Tema.texto(15));
        campo.setForeground(Tema.TEXTO);
        campo.setCaretColor(Tema.ROXO);
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { caixa.repaint(); }
            @Override
            public void focusLost(FocusEvent e) { caixa.repaint(); }
        });
        caixa.add(campo, BorderLayout.CENTER);
        add(caixa);

        // 3. Mensagem de erro (fica vazia quando esta tudo certo)
        add(javax.swing.Box.createVerticalStrut(3));
        mensagem = new JLabel(" ");
        mensagem.setFont(Tema.texto(12));
        mensagem.setForeground(Tema.ERRO);
        mensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(mensagem);
    }

    public String getTexto() {
        return campo.getText().trim();
    }

    public void setTexto(String texto) {
        campo.setText(texto);
    }

    // Da acesso ao campo de digitar (para a tela "ouvir" quando a pessoa digita ou sai do campo)
    public JTextField getCampo() {
        return campo;
    }

    // Borda roxa: campo certo
    public void marcarCerto() {
        estado = CERTO;
        mensagem.setText(" ");
        repaint();
    }

    // Borda vermelha e a frase embaixo: campo errado
    public void marcarErro(String texto) {
        estado = ERRADO;
        mensagem.setText(texto);
        repaint();
    }

    // Volta ao normal (ex.: campo opcional que ficou vazio)
    public void limparMarcacao() {
        estado = NORMAL;
        mensagem.setText(" ");
        repaint();
    }
}
