package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Contato;

/**
 * TelaInicial: a primeira tela da agenda.
 * De cima para baixo: cabecalho (logo + "carroll"), busca, "favoritos"
 * (a constelacao), "contatos" com a lista de cards, e o botao "+" no canto.
 *
 * Front-end x back-end: hoje a lista vem de contatos de exemplo.
 * Depois, o metodo carregarContatos() vai pedir a lista ao back-end,
 * que busca no MySQL. A tela so MOSTRA; quem guarda e o back-end.
 */
public class TelaInicial extends JPanel {

    private static final int LARGURA = 420;
    private static final int ALTURA = 680; // cabe na tela do notebook (1366 x 768)

    private List<Contato> contatos = new ArrayList<>(); // todos os contatos
    private JPanel painelLista;                           // onde os cards aparecem
    private JTextField campoBusca;
    private Constelacao constelacao;                      // a area de favoritos
    private JScrollPane rolagem;                          // a rolagem da lista
    private JPanel meio;                                  // troca entre "lista" e "vazia"
    private BotaoAdicionar botaoMais;                     // o "+" (some na agenda vazia)
    private JanelaAgenda janela;                          // a janela que troca as telas

    public TelaInicial(JanelaAgenda janela) {
        this.janela = janela;
        setLayout(new BorderLayout());
        setBackground(Tema.FUNDO);
        setPreferredSize(new Dimension(LARGURA, ALTURA));

        carregarContatos();

        // JLayeredPane: deixa o botao "+" flutuando POR CIMA da lista
        JLayeredPane camadas = new JLayeredPane();
        camadas.setPreferredSize(new Dimension(LARGURA, ALTURA));

        JPanel conteudo = montarConteudo();
        conteudo.setBounds(0, 0, LARGURA, ALTURA);
        camadas.add(conteudo, JLayeredPane.DEFAULT_LAYER);

        botaoMais = new BotaoAdicionar();
        botaoMais.setBounds(LARGURA - 98, ALTURA - 104, 68, 68);
        botaoMais.addActionListener(e -> abrirNovoContato());
        camadas.add(botaoMais, JLayeredPane.PALETTE_LAYER);

        add(camadas, BorderLayout.CENTER);
        mostrarContatos(contatos);
    }

    /**
     * Hoje: contatos de exemplo (com os campos combinados com a equipe).
     * Depois: aqui vai entrar a chamada ao metodo do back-end que lista os contatos.
     */
    private void carregarContatos() {
        contatos.add(new Contato("ana lima", "(85) 99811-2233", "ana@email.com", "prima", "05/08", true));
        contatos.add(new Contato("bruno costa", "(85) 99123-4567", "bruno@email.com", "colega de POO", "12/03", true));
        contatos.add(new Contato("carla dias", "(85) 98234-1122", "carla@email.com", "", "20/11", false));
        contatos.add(new Contato("diego alves", "(85) 99876-5544", "diego@email.com", "vizinho", "", true));
        contatos.add(new Contato("lia souza", "(85) 98765-4321", "lia@email.com", "melhor amiga", "02/02", true));
        contatos.add(new Contato("theo martins", "(85) 99654-7788", "theo@email.com", "", "", true));
    }

    // Monta a parte de cima e a lista, uma embaixo da outra (BoxLayout vertical)
    private JPanel montarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(BorderFactory.createEmptyBorder(20, 28, 0, 28));

        conteudo.add(montarCabecalho());
        conteudo.add(Box.createVerticalStrut(16));
        conteudo.add(montarBusca());
        conteudo.add(Box.createVerticalStrut(18));

        // CardLayout: como um baralho, mostra UMA carta por vez.
        // Carta "lista": favoritos + contatos. Carta "vazia": agenda sem contatos.
        meio = new JPanel(new java.awt.CardLayout());
        meio.setOpaque(false);
        meio.setAlignmentX(Component.LEFT_ALIGNMENT);
        meio.add(montarLista(), "lista");
        meio.add(new PainelVazio(() -> abrirNovoContato()), "vazia");
        conteudo.add(meio);
        return conteudo;
    }

    // Favoritos (constelacao) + titulo "contatos" + lista com rolagem
    private JPanel montarLista() {
        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        conteudo.add(criarTituloSecao("favoritos"));
        constelacao = new Constelacao(c -> janela.mostrarDetalhes(c)); // clicar na estrela abre os detalhes
        constelacao.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(constelacao);
        conteudo.add(Box.createVerticalStrut(6));

        conteudo.add(criarTituloSecao("contatos"));
        conteudo.add(Box.createVerticalStrut(10));

        // Lista com rolagem (JScrollPane), para quando tiver muitos contatos
        painelLista = new JPanel();
        painelLista.setOpaque(false);
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));

        rolagem = new JScrollPane(painelLista);
        rolagem.setOpaque(false);
        rolagem.getViewport().setOpaque(false);
        rolagem.setBorder(null);
        rolagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        rolagem.getVerticalScrollBar().setUnitIncrement(16);
        rolagem.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // barra invisivel
        conteudo.add(rolagem);

        return conteudo;
    }

    // Titulo pequeno de cada parte da tela ("favoritos", "contatos")
    private JLabel criarTituloSecao(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(Tema.titulo(18));
        titulo.setForeground(Tema.TEXTO_SUAVE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titulo;
    }

    // Cabecalho: simbolo da logo (lua com anel e estrela) + "carroll"
    private JPanel montarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout(10, 0));
        cabecalho.setOpaque(false);
        cabecalho.setAlignmentX(Component.LEFT_ALIGNMENT);
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel simbolo = new JLabel();
        java.net.URL caminho = getClass().getResource("/images/logo-simbolo.png");
        if (caminho != null) {
            Image img = new ImageIcon(caminho).getImage().getScaledInstance(54, 37, Image.SCALE_SMOOTH);
            simbolo.setIcon(new ImageIcon(img));
        }
        cabecalho.add(simbolo, BorderLayout.WEST);

        JLabel nome = new JLabel("carroll");
        nome.setFont(Tema.titulo(24));
        nome.setForeground(Tema.TEXTO);
        cabecalho.add(nome, BorderLayout.CENTER);
        return cabecalho;
    }

    // Campo de busca com lupa simples (so de linha)
    private JPanel montarBusca() {
        JPanel caixa = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Tema.FUNDO_SECUNDARIO);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
                g2.setColor(campoBusca.hasFocus() ? Tema.ROXO : Tema.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
                // lupa: um circulo e um tracinho
                g2.setColor(Tema.TEXTO_APAGADO);
                g2.setStroke(new BasicStroke(2f));
                int cy = getHeight() / 2;
                g2.drawOval(18, cy - 9, 14, 14);
                g2.drawLine(30, cy + 3, 35, cy + 8);
                g2.dispose();
            }
        };
        caixa.setOpaque(false);
        caixa.setAlignmentX(Component.LEFT_ALIGNMENT);
        caixa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        caixa.setPreferredSize(new Dimension(360, 46));
        caixa.setBorder(BorderFactory.createEmptyBorder(0, 48, 0, 16));

        campoBusca = new JTextField() {
            // Mostra "buscar contato" em cinza quando o campo esta vazio
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(Tema.TEXTO_APAGADO);
                    g2.setFont(getFont());
                    g2.drawString("buscar contato", 2, getHeight() / 2 + 5);
                    g2.dispose();
                }
            }
        };
        campoBusca.setOpaque(false);
        campoBusca.setBorder(null);
        campoBusca.setFont(Tema.texto(15));
        campoBusca.setForeground(Tema.TEXTO);
        campoBusca.setCaretColor(Tema.ROXO);
        campoBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) { caixa.repaint(); }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) { caixa.repaint(); }
        });

        // A cada letra digitada, filtra a lista
        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { buscar(); }
            public void removeUpdate(DocumentEvent e) { buscar(); }
            public void changedUpdate(DocumentEvent e) { buscar(); }
        });

        caixa.add(campoBusca, BorderLayout.CENTER);
        return caixa;
    }

    // Busca pelo nome (como o "procurar" dos roteiros). Depois pode chamar o back-end.
    private void buscar() {
        String texto = campoBusca.getText().trim().toLowerCase();
        List<Contato> encontrados = new ArrayList<>();
        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(texto)) {
                encontrados.add(c);
            }
        }
        mostrarContatos(encontrados);
    }

    // Apaga os cards antigos e cria um card para cada contato da lista
    private void mostrarContatos(List<Contato> lista) {
        painelLista.removeAll();
        atualizarConstelacao();

        // Sem nenhum contato na agenda: mostra a carta "vazia" e esconde o "+"
        boolean agendaVazia = contatos.isEmpty();
        ((java.awt.CardLayout) meio.getLayout()).show(meio, agendaVazia ? "vazia" : "lista");
        botaoMais.setVisible(!agendaVazia);

        if (lista.isEmpty()) {
            JLabel aviso = new JLabel("nenhum contato encontrado");
            aviso.setFont(Tema.texto(15));
            aviso.setForeground(Tema.TEXTO_APAGADO);
            aviso.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelLista.add(aviso);
        }

        for (Contato c : lista) {
            CardContato card = new CardContato(c, () -> trocarFavorito(c), () -> janela.mostrarDetalhes(c));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelLista.add(card);
            painelLista.add(Box.createVerticalStrut(12));
        }
        painelLista.add(Box.createVerticalStrut(90)); // espaco para o botao "+" nao cobrir o ultimo card

        painelLista.revalidate();
        painelLista.repaint();
    }

    // Clicou na estrela do card: liga ou desliga o favorito
    // (depois: chamar o metodo do back-end que salva essa mudanca no banco)
    private void trocarFavorito(Contato c) {
        c.setFavorito(!c.isFavorito());
        buscar(); // redesenha a lista e a constelacao
    }

    // Entrega para a constelacao so os contatos favoritos
    private void atualizarConstelacao() {
        List<Contato> favoritos = new ArrayList<>();
        for (Contato c : contatos) {
            if (c.isFavorito()) {
                favoritos.add(c);
            }
        }
        constelacao.setFavoritos(favoritos);
    }

    // Botao "+": pede para a janela abrir a tela "novo contato"
    private void abrirNovoContato() {
        janela.mostrarNovoContato();
    }

    /**
     * A tela "novo contato" chama este metodo quando a pessoa salva.
     * Hoje: guarda na lista da memoria (some quando fecha o programa).
     * Depois: aqui entra o metodo do back-end que grava no MySQL.
     */
    public void adicionarContato(Contato novo) {
        contatos.add(novo);
        campoBusca.setText(""); // limpa a busca e mostra a lista toda
        buscar();
        destacarContato(novo);
    }

    // Chamado quando voltamos para esta tela (um favorito pode ter mudado nos detalhes)
    public void atualizar() {
        buscar();
    }

    // Editar contato: troca o contato antigo pelo alterado, no mesmo lugar da lista
    // (depois: o back-end "alterar" atualiza no MySQL)
    public void substituirContato(Contato antigo, Contato alterado) {
        int posicao = contatos.indexOf(antigo);
        if (posicao >= 0) {
            contatos.set(posicao, alterado); // set(): troca o item daquela posicao (roteiro V.0.3.0)
        }
        buscar();
    }

    // Excluir contato: tira da lista
    // (depois: o back-end "excluir" apaga do MySQL)
    public void removerContato(Contato contato) {
        contatos.remove(contato);
        buscar();
    }

    /**
     * Contato recem-salvo: rola a lista ate o card dele e acende a borda.
     */
    public void destacarContato(Contato alvo) {
        if (!campoBusca.getText().isEmpty()) {
            campoBusca.setText(""); // se estava buscando, volta a lista toda
        }
        // invokeLater: espera o Swing terminar de arrumar a lista antes de rolar
        javax.swing.SwingUtilities.invokeLater(() -> {
            painelLista.validate();
            for (Component item : painelLista.getComponents()) {
                if (item instanceof CardContato) {
                    CardContato card = (CardContato) item;
                    if (card.getContato() == alvo) {
                        java.awt.Rectangle area = card.getBounds();
                        area.height += 90; // rola um pouco a mais, para o "+" nao cobrir o card
                        painelLista.scrollRectToVisible(area);           // rola ate o card
                        card.piscar();                                     // acende a borda
                    }
                }
            }
        });
    }

    // Fundo: cor escura + pontinhos de estrela discretos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Desenho.fundoEstrelado(g2, getWidth(), getHeight());
        g2.dispose();
    }

    /**
     * BotaoAdicionar: o botao redondo "+".
     * Tambem usa HERANCA: ele E um JButton, so muda o desenho.
     * (classe interna: fica dentro da TelaInicial porque so ela usa)
     */
    private static class BotaoAdicionar extends JButton {
        private boolean mouseEmCima = false;

        BotaoAdicionar() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setToolTipText("adicionar contato");
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) { mouseEmCima = true; repaint(); }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) { mouseEmCima = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int t = getWidth();
            // brilho em volta (circulos roxos bem transparentes)
            for (int i = 0; i < 6; i++) {
                g2.setColor(new Color(0x8B, 0x5C, 0xF6, mouseEmCima ? 22 : 14));
                g2.fillOval(i, i, t - 2 * i, t - 2 * i);
            }
            // circulo roxo
            g2.setColor(mouseEmCima ? new Color(0x9D76F8) : Tema.ROXO);
            g2.fillOval(7, 7, t - 14, t - 14);
            // sinal de +
            g2.setColor(Tema.FUNDO);
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int c = t / 2;
            g2.drawLine(c - 10, c, c + 10, c);
            g2.drawLine(c, c - 10, c, c + 10);
            g2.dispose();
        }
    }
}
