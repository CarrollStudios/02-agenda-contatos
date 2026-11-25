package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Contato;

/**
 * TelaDetalhes: mostra todas as informacoes de UM contato.
 * Abre quando a pessoa clica num card da lista ou numa estrela da constelacao.
 * Daqui ela pode favoritar, editar ou excluir o contato.
 */
public class TelaDetalhes extends JPanel {

    private static final int LARGURA = 420;
    private static final int ALTURA = 680;

    private JanelaAgenda janela;
    private Contato contato;

    public TelaDetalhes(JanelaAgenda janela, Contato contato) {
        this.janela = janela;
        this.contato = contato;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Tema.FUNDO);
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBorder(BorderFactory.createEmptyBorder(18, 28, 22, 28));

        add(montarCabecalho());
        add(Box.createVerticalStrut(6));
        add(montarAvatar());
        add(Box.createVerticalStrut(6));

        JLabel nome = new JLabel(contato.getNome(), JLabel.CENTER);
        nome.setFont(Tema.titulo(28));
        nome.setForeground(Tema.TEXTO);
        nome.setAlignmentX(Component.LEFT_ALIGNMENT);
        nome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        add(nome);
        add(Box.createVerticalStrut(18));

        add(montarInformacoes());
        add(Box.createVerticalGlue()); // empurra os botoes para baixo

        Botao editar = new Botao("editar contato", Botao.PRINCIPAL);
        editar.setAlignmentX(Component.LEFT_ALIGNMENT);
        editar.addActionListener(e -> janela.mostrarEditar(contato));
        add(editar);
        add(Box.createVerticalStrut(10));

        Botao excluir = new Botao("excluir contato", Botao.CONTORNO_PERIGO);
        excluir.setAlignmentX(Component.LEFT_ALIGNMENT);
        excluir.addActionListener(e -> janela.perguntarExcluir(contato));
        add(excluir);
    }

    // Seta de voltar | "detalhes" | estrela de favorito
    private JPanel montarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);
        cabecalho.setAlignmentX(Component.LEFT_ALIGNMENT);
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JPanel seta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.ROXO);
                g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(4, 18, 22, 18);
                g2.drawLine(4, 18, 11, 11);
                g2.drawLine(4, 18, 11, 25);
                g2.dispose();
            }
        };
        seta.setOpaque(false);
        seta.setPreferredSize(new Dimension(36, 36));
        seta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seta.setToolTipText("voltar");
        seta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                janela.mostrarInicial();
            }
        });

        JLabel titulo = new JLabel("detalhes", JLabel.CENTER);
        titulo.setFont(Tema.titulo(18));
        titulo.setForeground(Tema.TEXTO_SUAVE);

        // estrela: clicar liga/desliga o favorito
        // (depois: chamar o metodo "alterar" do back-end para salvar no banco)
        JPanel estrela = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Desenho.estrela(g2, getWidth() / 2, getHeight() / 2, 11, contato.isFavorito());
                g2.dispose();
            }
        };
        estrela.setOpaque(false);
        estrela.setPreferredSize(new Dimension(36, 36));
        estrela.setCursor(new Cursor(Cursor.HAND_CURSOR));
        estrela.setToolTipText("favorito");
        estrela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contato.setFavorito(!contato.isFavorito());
                estrela.repaint();
            }
        });

        cabecalho.add(seta, BorderLayout.WEST);
        cabecalho.add(titulo, BorderLayout.CENTER);
        cabecalho.add(estrela, BorderLayout.EAST);
        return cabecalho;
    }

    // Avatar grande com anel de orbita
    private JPanel montarAvatar() {
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                int t = 84;
                int x = (getWidth() - t) / 2;
                int y = (getHeight() - t) / 2;
                double cx = x + t / 2.0;
                double cy = y + t / 2.0;

                Graphics2D anel = (Graphics2D) g2.create();
                anel.rotate(Math.toRadians(-18), cx, cy);
                anel.setColor(Tema.ROXO_ESCURO);
                anel.setStroke(new BasicStroke(2.4f));
                anel.drawArc(x - 18, y + t / 2 - 12, t + 36, 24, 0, 180);
                anel.dispose();

                g2.setPaint(new GradientPaint(0, y, Tema.ROXO, 0, y + t, Tema.ROXO_ESCURO));
                g2.fillOval(x, y, t, t);
                g2.setColor(new Color(0xC4B5FD));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x, y, t, t);

                anel = (Graphics2D) g2.create();
                anel.rotate(Math.toRadians(-18), cx, cy);
                anel.setColor(Tema.ROXO_ESCURO);
                anel.setStroke(new BasicStroke(2.4f));
                anel.drawArc(x - 18, y + t / 2 - 12, t + 36, 24, 180, 35);
                anel.drawArc(x - 18, y + t / 2 - 12, t + 36, 24, 325, 35);
                anel.dispose();

                String iniciais = Desenho.iniciais(contato.getNome());
                g2.setColor(Tema.TEXTO);
                g2.setFont(Tema.titulo(32));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(iniciais, (int) cx - fm.stringWidth(iniciais) / 2,
                        y + (t - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setAlignmentX(Component.LEFT_ALIGNMENT);
        avatar.setPreferredSize(new Dimension(360, 100));
        avatar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return avatar;
    }

    // Caixa com as informacoes, uma por linha
    private JPanel montarInformacoes() {
        JPanel caixa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.FUNDO_SECUNDARIO);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
                g2.setColor(Tema.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
                // linhas finas separando as informacoes
                int linhas = getComponentCount();
                for (int i = 1; i < linhas; i++) {
                    int y = getComponent(i).getY();
                    g2.drawLine(0, y, getWidth(), y);
                }
                g2.dispose();
            }
        };
        caixa.setOpaque(false);
        caixa.setLayout(new BoxLayout(caixa, BoxLayout.Y_AXIS));
        caixa.setAlignmentX(Component.LEFT_ALIGNMENT);

        caixa.add(linhaInformacao("celular", contato.getCelular()));
        caixa.add(linhaInformacao("e-mail", contato.getEmail()));
        caixa.add(linhaInformacao("aniversário", contato.getAniversario()));
        caixa.add(linhaInformacao("observação", contato.getObservacao()));
        caixa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4 * 58));
        return caixa;
    }

    // Uma linha: titulo pequeno em cima e a informacao embaixo
    private JPanel linhaInformacao(String titulo, String valor) {
        JPanel linha = new JPanel();
        linha.setOpaque(false);
        linha.setLayout(new BoxLayout(linha, BoxLayout.Y_AXIS));
        linha.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        JLabel rotulo = new JLabel(titulo);
        rotulo.setFont(Tema.texto(12));
        rotulo.setForeground(Tema.TEXTO_APAGADO);

        boolean vazio = (valor == null || valor.isEmpty());
        JLabel texto = new JLabel(vazio ? "—" : valor); // "—" quando nao foi preenchido
        texto.setFont(Tema.texto(16));
        texto.setForeground(vazio ? Tema.TEXTO_APAGADO : Tema.TEXTO);

        linha.add(rotulo);
        linha.add(Box.createVerticalStrut(2));
        linha.add(texto);
        return linha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Desenho.fundoEstrelado(g2, getWidth(), getHeight());
        g2.dispose();
    }
}
