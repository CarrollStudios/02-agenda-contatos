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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Contato;

/**
 * TelaNovoContato: o formulario para adicionar um contato.
 * Abre quando a pessoa clica no "+" da tela inicial.
 *
 * Campos combinados com a equipe: nome, celular, e-mail,
 * aniversario, observacao e favorito.
 *
 * Front-end x back-end: esta tela so CONFERE se os campos estao
 * preenchidos do jeito certo e monta o contato. Quem grava no banco
 * e o back-end (ver o metodo concluir()).
 *
 * A TelaEditarContato HERDA desta classe: e o mesmo formulario,
 * so muda o titulo, o texto do botao e o que acontece ao salvar.
 * Por isso alguns itens sao "protected": a classe filha pode usar.
 */
public class TelaNovoContato extends JPanel {

    private static final int LARGURA = 420;
    private static final int ALTURA = 680;

    protected JanelaAgenda janela;

    protected CampoTexto campoNome;
    protected CampoTexto campoCelular;
    protected CampoTexto campoEmail;
    protected CampoTexto campoAniversario;
    protected CampoTexto campoObservacao;

    protected boolean favorito = false;
    private JPanel avatar;        // circulo com as iniciais (muda enquanto digita o nome)
    private JLabel textoFavorito; // "marcar como favorito" / "favorito"

    public TelaNovoContato(JanelaAgenda janela) {
        this.janela = janela;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Tema.FUNDO);
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBorder(BorderFactory.createEmptyBorder(18, 28, 22, 28));

        add(montarCabecalho());
        add(Box.createVerticalStrut(8));
        add(montarAvatar());
        add(Box.createVerticalStrut(4));
        add(montarFavorito());
        add(Box.createVerticalStrut(10));

        campoNome = new CampoTexto("nome completo", "joão silva");
        campoCelular = new CampoTexto("celular", "(85) 99999-9999");
        campoEmail = new CampoTexto("e-mail", "joao@email.com");
        campoAniversario = new CampoTexto("aniversário", "ddmm");
        campoObservacao = new CampoTexto("observação", "colega de POO...");

        add(campoNome);
        add(campoCelular);
        add(campoEmail);
        add(montarLinhaAniversarioObservacao());

        add(Box.createVerticalGlue()); // empurra o botao para baixo

        Botao botaoSalvar = new Botao(textoDoBotao(), Botao.PRINCIPAL);
        botaoSalvar.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoSalvar.addActionListener(e -> salvar());
        add(botaoSalvar);

        prepararValidacao();
    }

    // ---------- TEXTOS (a TelaEditarContato troca estes) ----------

    protected String tituloDaTela() {
        return "novo contato";
    }

    protected String fraseDaTela() {
        return "adicione uma nova estrela à sua constelação";
    }

    protected String textoDoBotao() {
        return "salvar contato";
    }

    // Seta de voltar: no novo contato, volta para a tela inicial
    protected void voltar() {
        janela.mostrarInicial();
    }

    // ---------- PARTES DA TELA ----------

    // Seta de voltar + "novo contato" + frase pequena
    private JPanel montarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout(12, 0));
        cabecalho.setOpaque(false);
        cabecalho.setAlignmentX(Component.LEFT_ALIGNMENT);
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        // seta de voltar (desenhada), volta para a tela inicial
        JPanel seta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.ROXO);
                g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int y = 18;
                g2.drawLine(4, y, 22, y);
                g2.drawLine(4, y, 11, y - 7);
                g2.drawLine(4, y, 11, y + 7);
                g2.dispose();
            }
        };
        seta.setOpaque(false);
        seta.setPreferredSize(new Dimension(28, 36));
        seta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        seta.setToolTipText("voltar");
        seta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                voltar();
            }
        });
        cabecalho.add(seta, BorderLayout.WEST);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel(tituloDaTela());
        titulo.setFont(Tema.titulo(26));
        titulo.setForeground(Tema.TEXTO);
        JLabel frase = new JLabel(fraseDaTela());
        frase.setFont(Tema.tituloItalico(13));
        frase.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6)); // folguinha para a ultima letra nao cortar
        frase.setForeground(Tema.TEXTO_SUAVE);
        textos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        textos.add(titulo);
        textos.add(frase);
        cabecalho.add(textos, BorderLayout.CENTER);
        return cabecalho;
    }

    // Avatar grande com anel de orbita e as iniciais do nome digitado
    private JPanel montarAvatar() {
        avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                int t = 72;
                int x = (getWidth() - t) / 2;
                int y = (getHeight() - t) / 2;
                double cx = x + t / 2.0;
                double cy = y + t / 2.0;

                // parte de tras do anel
                Graphics2D anel = (Graphics2D) g2.create();
                anel.rotate(Math.toRadians(-18), cx, cy);
                anel.setColor(Tema.ROXO_ESCURO);
                anel.setStroke(new BasicStroke(2.4f));
                anel.drawArc(x - 16, y + t / 2 - 11, t + 32, 22, 0, 180);
                anel.dispose();

                // circulo com degrade
                g2.setPaint(new GradientPaint(0, y, Tema.ROXO, 0, y + t, Tema.ROXO_ESCURO));
                g2.fillOval(x, y, t, t);
                g2.setColor(new Color(0xC4B5FD));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x, y, t, t);

                // pontas da frente do anel
                anel = (Graphics2D) g2.create();
                anel.rotate(Math.toRadians(-18), cx, cy);
                anel.setColor(Tema.ROXO_ESCURO);
                anel.setStroke(new BasicStroke(2.4f));
                anel.drawArc(x - 16, y + t / 2 - 11, t + 32, 22, 180, 35);
                anel.drawArc(x - 16, y + t / 2 - 11, t + 32, 22, 325, 35);
                anel.dispose();

                // iniciais do nome (vazio enquanto nada foi digitado)
                String iniciais = Desenho.iniciais(campoNome == null ? "" : campoNome.getTexto());
                if (!iniciais.isEmpty()) {
                    g2.setColor(Tema.TEXTO);
                    g2.setFont(Tema.titulo(28));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(iniciais, (int) cx - fm.stringWidth(iniciais) / 2,
                            y + (t - fm.getHeight()) / 2 + fm.getAscent());
                }
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setAlignmentX(Component.LEFT_ALIGNMENT);
        avatar.setPreferredSize(new Dimension(360, 84));
        avatar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));
        return avatar;
    }

    // Estrela + "marcar como favorito" (clicar liga e desliga)
    private JPanel montarFavorito() {
        JPanel linha = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 6, 0));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));

        JPanel estrela = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Desenho.estrela(g2, getWidth() / 2, getHeight() / 2, 9, favorito);
                g2.dispose();
            }
        };
        estrela.setOpaque(false);
        estrela.setPreferredSize(new Dimension(22, 22));

        textoFavorito = new JLabel("marcar como favorito");
        textoFavorito.setFont(Tema.titulo(14));
        textoFavorito.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
        textoFavorito.setForeground(Tema.TEXTO_SUAVE);

        linha.add(estrela);
        linha.add(textoFavorito);
        linha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setFavorito(!favorito);
            }
        });
        return linha;
    }

    // Liga ou desliga o favorito e atualiza a estrela e o texto
    protected void setFavorito(boolean valor) {
        favorito = valor;
        textoFavorito.setText(favorito ? "favorito ✦ está na constelação" : "marcar como favorito");
        textoFavorito.setForeground(favorito ? Tema.ROXO : Tema.TEXTO_SUAVE);
        repaint();
    }

    // Aniversario (pequeno) e observacao (grande) lado a lado
    private JPanel montarLinhaAniversarioObservacao() {
        JPanel linha = new JPanel(new BorderLayout(12, 0));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        campoAniversario.setPreferredSize(new Dimension(118, 86));
        linha.add(campoAniversario, BorderLayout.WEST);
        linha.add(campoObservacao, BorderLayout.CENTER);
        return linha;
    }

    // ---------- VALIDACAO (conferir os campos) ----------

    /**
     * Valida cada campo quando a pessoa SAI dele (focusLost).
     * O nome tambem atualiza as iniciais do avatar a cada letra.
     */
    private void prepararValidacao() {
        campoNome.getCampo().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { avatar.repaint(); }
            public void removeUpdate(DocumentEvent e) { avatar.repaint(); }
            public void changedUpdate(DocumentEvent e) { avatar.repaint(); }
        });

        validarAoSair(campoNome, () -> validarNome());
        validarAoSair(campoCelular, () -> validarCelular());
        validarAoSair(campoEmail, () -> validarEmail());
        validarAoSair(campoAniversario, () -> validarAniversario());
        validarAoSair(campoObservacao, () -> validarObservacao());
    }

    private void validarAoSair(CampoTexto campo, Runnable validacao) {
        campo.getCampo().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validacao.run();
            }
        });
    }

    // Cada metodo devolve true (certo) ou false (errado) e pinta a borda

    private boolean validarNome() {
        if (campoNome.getTexto().isEmpty()) {
            campoNome.marcarErro("digite o nome do contato");
            return false;
        }
        campoNome.marcarCerto();
        return true;
    }

    private boolean validarCelular() {
        String numeros = campoCelular.getTexto().replaceAll("[^0-9]", ""); // so os numeros
        if (numeros.length() != 10 && numeros.length() != 11) {
            campoCelular.marcarErro("use DDD + número, ex.: (85) 99999-9999");
            return false;
        }
        // deixa bonitinho: 85999991111 -> (85) 99999-1111
        String ddd = numeros.substring(0, 2);
        String resto = numeros.substring(2);
        int meio = resto.length() - 4;
        campoCelular.setTexto("(" + ddd + ") " + resto.substring(0, meio) + "-" + resto.substring(meio));
        campoCelular.marcarCerto();
        return true;
    }

    private boolean validarEmail() {
        String email = campoEmail.getTexto();
        // precisa ter algo antes do @, o @, algo depois e um ponto
        if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            campoEmail.marcarErro("e-mail inválido, ex.: joao@email.com");
            return false;
        }
        campoEmail.marcarCerto();
        return true;
    }

    // Aniversario e opcional: vazio pode. Se preencher, aceita "0503", "05/03",
    // "5-3" ou "5 3": a barra nao e obrigatoria, o campo coloca sozinho.
    private boolean validarAniversario() {
        String data = campoAniversario.getTexto();
        if (data.isEmpty()) {
            campoAniversario.limparMarcacao();
            return true;
        }
        int dia = -1;
        int mes = -1;
        if (data.matches("\\d{4}")) {
            // so numeros: os 2 primeiros sao o dia, os 2 ultimos o mes (0503 -> 05/03)
            dia = Integer.parseInt(data.substring(0, 2));
            mes = Integer.parseInt(data.substring(2, 4));
        } else if (data.matches("\\d{1,2}\\D+\\d{1,2}")) {
            // com qualquer separador no meio: barra, traco, ponto ou espaco
            String[] partes = data.split("\\D+");
            dia = Integer.parseInt(partes[0]);
            mes = Integer.parseInt(partes[1]);
        }
        if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12) {
            campoAniversario.setTexto(String.format("%02d/%02d", dia, mes)); // mostra com a barra
            campoAniversario.marcarCerto();
            return true;
        }
        campoAniversario.marcarErro("dia e mês, ex.: 0503");
        return false;
    }

    // Observacao e opcional
    private boolean validarObservacao() {
        if (campoObservacao.getTexto().isEmpty()) {
            campoObservacao.limparMarcacao();
        } else {
            campoObservacao.marcarCerto();
        }
        return true;
    }

    // ---------- SALVAR ----------

    /**
     * Confere tudo. Se estiver certo, monta o contato e chama o concluir().
     */
    private void salvar() {
        // "&" (e nao "&&") para conferir TODOS os campos e pintar todos os errados de uma vez
        boolean tudoCerto = validarNome() & validarCelular() & validarEmail()
                & validarAniversario() & validarObservacao();
        if (!tudoCerto) {
            return; // fica na tela, com os campos errados em vermelho
        }

        Contato contato = new Contato(
                campoNome.getTexto(),
                campoCelular.getTexto(),
                campoEmail.getTexto(),
                campoObservacao.getTexto(),
                campoAniversario.getTexto(),
                favorito);
        concluir(contato);
    }

    // O que fazer com o contato pronto. No novo contato: adicionar.
    protected void concluir(Contato contato) {
        janela.salvarNovoContato(contato);
    }

    // Fundo com os pontinhos de estrela
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Desenho.fundoEstrelado(g2, getWidth(), getHeight());
        g2.dispose();
    }
}
