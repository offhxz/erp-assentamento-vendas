package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * View: Criar Pedido
 */
@SuppressWarnings("serial")
public class CriarPedidoView extends JFrame {

    // ==========================
    // UI CONSTS / UTILS
    // ==========================
    private static final class Ui {
        static final Color VERDE       = new Color(0x6A, 0x6E, 0x2D);
        static final Color LATERAL_BG  = new Color(0x2F, 0x33, 0x20);
        static final Color FORM_BG     = new Color(0xE9, 0xE9, 0xE9);

        static final Font  FONT_14     = new Font("SansSerif", Font.PLAIN, 14);
        static final Font  FONT_B14    = new Font("SansSerif", Font.BOLD, 14);
        static final Font  FONT_B16    = new Font("SansSerif", Font.BOLD, 16);
        static final Font  FONT_B24    = new Font("SansSerif", Font.BOLD, 24);

        static final Insets INSETS_FORM = new Insets(8, 8, 12, 8);

        static final NumberFormat NF_BR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        private Ui() {}
    }

    static class HintTextField extends JTextField {
        private String hint;
        private Color hintColor = new Color(0, 0, 0, 110); // cinza sutil
        private boolean italic = true;                     // deixa a dica em itálico

        HintTextField(String hint) {
            super();
            this.hint = hint;
            // Repaint quando o texto muda (garante que a dica some/volte na hora)
            getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
                @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
                @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
            });
            // Repaint ao focar/desfocar (caso queira esconder/mostrar com foco)
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override public void focusGained(java.awt.event.FocusEvent e) { repaint(); }
                @Override public void focusLost (java.awt.event.FocusEvent e) { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (!getText().isEmpty()) return; // só pinta a dica quando vazio

            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(hintColor);

                Font f = getFont();
                if (italic) f = f.deriveFont(Font.ITALIC);
                g2.setFont(f);

                Insets in = getInsets();
                FontMetrics fm = g2.getFontMetrics();

                // Alinha a dica como o texto normal do JTextField (centralizado verticalmente)
                int x = in.left + 4;
                int availableH = getHeight() - in.top - in.bottom;
                int textY = in.top + (availableH - fm.getHeight()) / 2 + fm.getAscent();

                g2.drawString(hint, x, textY);
            } finally {
                g2.dispose();
            }
        }
    }

    private static final class Numbers {
        private Numbers() {}
        static BigDecimal parseDecimal(Object v) {
            if (v == null) return null;
            if (v instanceof BigDecimal) return (BigDecimal) v;
            if (v instanceof Number) return new BigDecimal(((Number) v).toString());
            String s = v.toString().trim().replace('.', '#').replace(',', '.').replace('#', '.');
            try { return new BigDecimal(s); } catch (Exception ex) { return null; }
        }
    }

    private static final class Renderers {
        private Renderers() {}
        static javax.swing.table.DefaultTableCellRenderer rightAligned() {
            return new javax.swing.table.DefaultTableCellRenderer() {{
                setHorizontalAlignment(SwingConstants.RIGHT);
            }};
        }
        static javax.swing.table.DefaultTableCellRenderer currencyBR() {
            NumberFormat nf = Ui.NF_BR;
            return new javax.swing.table.DefaultTableCellRenderer() {
                { setHorizontalAlignment(SwingConstants.RIGHT); }
                @Override protected void setValue(Object value) {
                    if (value == null) { setText(""); return; }
                    BigDecimal bd = Numbers.parseDecimal(value);
                    setText(bd != null ? nf.format(bd) : "");
                }
            };
        }
    }

    // Item de catálogo
    private static final class Produto {
        final String nome;
        final BigDecimal preco;
        Produto(String nome, BigDecimal preco) { this.nome = nome; this.preco = preco; }
        @Override public String toString() { return nome; }
    }

    // ==========================
    // TABLE MODEL
    // ==========================
    private static final class ProdutoTableModel extends AbstractTableModel {
        private static final int COL_NOME = 0, COL_QTD = 1, COL_UNIT = 2, COL_TOTAL = 3;

        private final String[] colunas = {
            "Produto", "Quantidade (kg)", "Valor Unit. (R$)", "Total (R$)"
        };
        private static final class Linha {
            String nome;
            BigDecimal qtd, unit, total;
            Linha(String n, BigDecimal q, BigDecimal u) {
                nome = n; qtd = q; unit = u; total = (q != null && u != null) ? u.multiply(q) : BigDecimal.ZERO;
            }
        }
        private final java.util.List<Linha> dados = new ArrayList<>();

        @Override public int getRowCount() { return dados.size(); }
        @Override public int getColumnCount() { return colunas.length; }
        @Override public String getColumnName(int column) { return colunas[column]; }
        @Override public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case COL_NOME -> String.class;
                case COL_QTD, COL_UNIT, COL_TOTAL -> BigDecimal.class;
                default -> Object.class;
            };
        }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return columnIndex == COL_QTD; }

        @Override public Object getValueAt(int rowIndex, int columnIndex) {
            Linha l = dados.get(rowIndex);
            return switch (columnIndex) {
                case COL_NOME -> l.nome;
                case COL_QTD  -> l.qtd;
                case COL_UNIT -> l.unit;
                case COL_TOTAL-> l.total;
                default -> null;
            };
        }

        @Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex != COL_QTD) return;
            Linha l = dados.get(rowIndex);
            BigDecimal qtd = Numbers.parseDecimal(aValue);
            if (qtd != null && qtd.compareTo(BigDecimal.ZERO) > 0) {
                l.qtd = qtd;
                l.total = l.unit.multiply(l.qtd);
                fireTableCellUpdated(rowIndex, COL_QTD);
                fireTableCellUpdated(rowIndex, COL_TOTAL);
            }
        }

        void addLinha(String nome, BigDecimal qtd, BigDecimal unit) {
            Linha l = new Linha(nome, qtd, unit);
            dados.add(l);
            int idx = dados.size() - 1;
            fireTableRowsInserted(idx, idx);
        }

        BigDecimal somaTotal() {
            BigDecimal soma = BigDecimal.ZERO;
            for (Linha l : dados) {
                if (l.total != null) soma = soma.add(l.total);
            }
            return soma;
        }
    }

    // ==========================
    // CAMPOS DA VIEW
    // ==========================
    private JComboBox<String> comboProdutor;
    private JTable tabelaProdutos;
    private ProdutoTableModel produtosModel;
    private JLabel lblTotalCompra;
    private JComboBox<String> comboEntrega;
    private JDatePickerImpl datePicker;
    private JComboBox<String> comboPagamento;
    private JPanel pagamentoContainer;
    private JButton btnPixAcao;
    private boolean pixPago = false;
    private JLabel lblPixQr;
    private ImageIcon pixQrIconCache;
    private JButton btnFinalizarPedido;

    // ==========================
    // CONSTRUTOR
    // ==========================
    public CriarPedidoView() {
        setTitle("Criar Pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarPainelLateral(), BorderLayout.WEST);
        add(criarContentPanel(), BorderLayout.CENTER);
    }

    // ==========================
    // ROOT PANELS
    // ==========================
    private JPanel criarContentPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.add(criarTitleBar(), BorderLayout.NORTH);
        content.add(criarFormCard(), BorderLayout.CENTER);
        return content;
    }

    private JPanel criarCardDinheiro() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel msg = new JLabel("O pagamento deve ser feito direto com o Produtor.");
        msg.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p.add(msg, BorderLayout.WEST);
        return p;
    }

    private JPanel criarCardPix() {
    // Painel do card com layout vertical simples
    JPanel p = new JPanel(new BorderLayout(0, 8)); // gap vertical de 8px
    p.setOpaque(false);

    // Linha de cima apenas pro botão (alinhado à esquerda)
    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    top.setOpaque(false);

    btnPixAcao = new JButton("Gerar QrCode");
    btnPixAcao.setFont(new Font("SansSerif", Font.BOLD, 14)); // ou Ui.FONT_B14 se você tiver
    btnPixAcao.setForeground(Color.WHITE);
    btnPixAcao.setBackground(new Color(0x6A,0x6E,0x2D));      // ou Ui.VERDE
    btnPixAcao.setFocusPainted(false);
    btnPixAcao.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    btnPixAcao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    top.add(btnPixAcao);
    p.add(top, BorderLayout.NORTH);

    // Área do QR (vazia até o usuário clicar no botão)
    JPanel qrArea = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    qrArea.setOpaque(false);

    lblPixQr = new JLabel();              // começa sem ícone
    lblPixQr.setOpaque(false);
    // (opcional) se quiser reservar espaço fixo para evitar "pulo" de layout:
    // lblPixQr.setPreferredSize(new Dimension(220, 220));

    qrArea.add(lblPixQr);
    p.add(qrArea, BorderLayout.CENTER);

    // Ação do botão
    btnPixAcao.addActionListener(e -> {
        String t = btnPixAcao.getText();
        if ("Gerar QrCode".equals(t)) {
            JOptionPane.showMessageDialog(this, "Gerando QrCode");

            // Carrega do cache ou do recurso
            if (pixQrIconCache == null) {
                pixQrIconCache = loadIcon("/icons/qrcode.png", 220, 220);
            }
            if (pixQrIconCache != null) {
                lblPixQr.setIcon(pixQrIconCache);  // exibe a imagem
                // se quiser, também dá pra mostrar um "texto alternativo":
                // lblPixQr.setText("QRCode");
            } else {
                // Caso a imagem não seja encontrada no classpath
                JOptionPane.showMessageDialog(this, "Imagem do QrCode não encontrada em /icons/qrcode.png");
            }

            btnPixAcao.setText("Já realizei o pagamento");
        } else if ("Já realizei o pagamento".equals(t)) {
            pixPago = true;
            btnPixAcao.setText("Pagamento confirmado");
            btnPixAcao.setEnabled(false);
            atualizarEstadoFinalizacao();
        }
    });

    return p;
    }

    private JPanel criarCardCartao() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.weightx = 1.0; g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 0, 6, 0);
        int y = 0;

        JTextField txtNumero = new HintTextField("Número do cartão");
        txtNumero.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.gridy = y++; p.add(txtNumero, g);

        JPanel linha = new JPanel(new GridBagLayout());
        GridBagConstraints gl = new GridBagConstraints();
        gl.fill = GridBagConstraints.HORIZONTAL;
        gl.insets = new Insets(0, 0, 0, 8);

        JTextField txtCvc = new HintTextField("CVC");
        txtCvc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gl.gridx = 0; gl.weightx = 0.3; linha.add(txtCvc, gl);

        JTextField txtVal = new HintTextField("Validade (MM/AA)");
        txtVal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gl.gridx = 1; gl.weightx = 0.7; gl.insets = new Insets(0, 0, 0, 0);
        linha.add(txtVal, gl);

        g.gridy = y++; p.add(linha, g);

        JTextField txtNome = new HintTextField("Nome impresso no cartão");
        txtNome.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.gridy = y++; p.add(txtNome, g);

        return p;
    }

    private JComponent criarTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(Ui.VERDE);
        bar.setBorder(new EmptyBorder(12, 24, 12, 24));

        JLabel titulo = new JLabel("Criar Pedido", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(Ui.FONT_B24);
        bar.add(titulo, BorderLayout.CENTER);
        return bar;
    }

    private JPanel criarFormCard() {
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Ui.FORM_BG);
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));

        adicionarSecaoProdutor(formCard);   // gridy 0 e 1
        adicionarSecaoProduto(formCard);    // gridy 2 (subpainel)
        adicionarSecaoEntrega(formCard);    // gridy 4 e 5 (+ opcional no 6)
        adicionarBotaoFinalizar(formCard);  // gridy 6 (mesmo do datePicker — mantido)
        adicionarSecaoPagamento(formCard);

        // Spacer p/ empurrar o conteúdo pra cima
        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0; gbcSpacer.gridy = 12; gbcSpacer.weighty = 1.0;
        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        formCard.add(spacer, gbcSpacer);

        return formCard;
    }

    // ==========================
    // SEÇÕES
    // ==========================
    private void adicionarSecaoProdutor(JPanel formCard) {
        GridBagConstraints gbc = baseGbc();
        gbc.gridy = 0; gbc.gridwidth = 2;
        formCard.add(criarHeader("Selecione produtor / associação"), gbc);

        gbc.gridy = 1; gbc.insets = new Insets(0, 8, 12, 8);
        comboProdutor = new JComboBox<>(new String[] { "Associação", "Fernando Silva", "Renato Cabral" });
        comboProdutor.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        comboProdutor.setFont(Ui.FONT_14);
        formCard.add(comboProdutor, gbc);
    }

    private void adicionarSecaoProduto(JPanel formCard) {
        GridBagConstraints gbc = baseGbc();
        gbc.gridy = 2; gbc.gridwidth = 2;

        JPanel secao = new JPanel(new GridBagLayout());
        secao.setOpaque(false);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.weightx = 1.0; g.fill = GridBagConstraints.HORIZONTAL;

        // Header
        g.gridy = 0; g.insets = new Insets(16, 0, 6, 0);
        secao.add(criarHeader("Produtos"), g);

        // Catálogo (mock) — idêntico ao seu
        List<Produto> catalogo = Arrays.asList(
            new Produto("Alface Crespa", new BigDecimal("5.50")),
            new Produto("Tomate Maduro", new BigDecimal("7.90")),
            new Produto("Melancia",      new BigDecimal("3.20")),
            new Produto("Cenoura",       new BigDecimal("4.80")),
            new Produto("Batata",        new BigDecimal("4.20"))
        );

        // Barra de inclusão
        g.gridy = 1; g.insets = new Insets(0, 0, 0, 0);
        secao.add(criarBarraInclusaoProdutos(catalogo), g);

        // Tabela
        produtosModel = new ProdutoTableModel();
        tabelaProdutos = new JTable(produtosModel);
        tabelaProdutos.setFont(Ui.FONT_14);
        tabelaProdutos.setRowHeight(24);
        tabelaProdutos.getTableHeader().setFont(Ui.FONT_B14);
        tabelaProdutos.getColumnModel().getColumn(1).setCellRenderer(Renderers.rightAligned());
        tabelaProdutos.getColumnModel().getColumn(2).setCellRenderer(Renderers.currencyBR());
        tabelaProdutos.getColumnModel().getColumn(3).setCellRenderer(Renderers.currencyBR());

        // Atualiza total sempre que a tabela mudar
        produtosModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE || e.getType() == TableModelEvent.INSERT ||
                e.getType() == TableModelEvent.DELETE) {
                atualizarTotais();
            }
        });

        g.gridy = 2; g.insets = new Insets(8, 0, 0, 0);
        g.fill = GridBagConstraints.BOTH; g.weighty = 1.0;
        JScrollPane scroll = new JScrollPane(tabelaProdutos);
        scroll.setPreferredSize(new Dimension(500, 160));
        secao.add(scroll, g);

        // Rodapé total
        g.gridy = 3; g.insets = new Insets(8, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL; g.weighty = 0.0;
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        JLabel lblTituloTotal = new JLabel("Total da compra:");
        lblTituloTotal.setFont(Ui.FONT_14);
        lblTotalCompra = new JLabel(Ui.NF_BR.format(BigDecimal.ZERO));
        lblTotalCompra.setFont(Ui.FONT_B16);
        rodape.add(lblTituloTotal);
        rodape.add(lblTotalCompra);
        secao.add(rodape, g);

        formCard.add(secao, gbc);
        formCard.revalidate();
        formCard.repaint();
    }

    private JPanel criarBarraInclusaoProdutos(List<Produto> catalogo) {
        JPanel barraAdd = new JPanel(new GridBagLayout());
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(4, 4, 4, 4);
        g2.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<Produto> comboProduto = new JComboBox<>(catalogo.toArray(new Produto[0]));
        comboProduto.setFont(Ui.FONT_14);
        g2.gridx = 0; g2.weightx = 1.0;
        barraAdd.add(comboProduto, g2);

        JLabel lblValorUnit = new JLabel();
        lblValorUnit.setHorizontalAlignment(SwingConstants.RIGHT);
        lblValorUnit.setFont(Ui.FONT_14);
        lblValorUnit.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,220,220)),
            new EmptyBorder(4,8,4,8)
        ));
        g2.gridx = 1; g2.weightx = 0.35;
        barraAdd.add(lblValorUnit, g2);

        JTextField txtQtd = new HintTextField("Quantidade em kg");
        txtQtd.setFont(Ui.FONT_14);
        txtQtd.setToolTipText("Quantidade em kg");
        g2.gridx = 2; g2.weightx = 0.25;
        barraAdd.add(txtQtd, g2);

        JButton btnAdd = new JButton("\u2795");
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnAdd.setBackground(Ui.VERDE);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(new EmptyBorder(6, 12, 6, 12));
        g2.gridx = 3; g2.weightx = 0.0;
        barraAdd.add(btnAdd, g2);

        // Preço inicial do combo
        Runnable atualizarPreco = () -> {
            Produto p = (Produto) comboProduto.getSelectedItem();
            lblValorUnit.setText(p != null ? Ui.NF_BR.format(p.preco) : "");
        };
        comboProduto.addActionListener(e -> atualizarPreco.run());
        atualizarPreco.run();

        Runnable tentarAdicionar = () -> {
            Produto produto = (Produto) comboProduto.getSelectedItem();
            String qtdStr = txtQtd.getText().trim();

            if (produto == null || produto.nome.isBlank() || qtdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe produto e quantidade.");
                return;
            }
            BigDecimal qtd = Numbers.parseDecimal(qtdStr);
            if (qtd == null || qtd.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.");
                return;
            }
            produtosModel.addLinha(produto.nome, qtd, produto.preco);
            comboProduto.setSelectedIndex(0);
            txtQtd.setText("");
            comboProduto.requestFocusInWindow();
        };

        btnAdd.addActionListener(e -> tentarAdicionar.run());
        txtQtd.addActionListener(e -> tentarAdicionar.run());

        return barraAdd;
    }

    private void adicionarSecaoPagamento(JPanel formCard) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(8, 8, 12, 8);

    // Header
    gbc.gridy = 7; gbc.gridwidth = 2;
    formCard.add(criarHeader("Selecione a forma de pagamento"), gbc);

    // Combo formas
    gbc.gridy = 8;
    comboPagamento = new JComboBox<>(new String[] {
        "Dinheiro (pagamento direto com o Produtor)",
        "Pix",
        "Cartão de Crédito",
        "Cartão de Débito"
    });
    comboPagamento.setFont(new Font("SansSerif", Font.PLAIN, 14));
    comboPagamento.setSelectedIndex(0);
    formCard.add(comboPagamento, gbc);

    // Container dinâmico (cards)
    pagamentoContainer = new JPanel(new CardLayout());
    pagamentoContainer.setOpaque(false);

    JPanel cardDinheiro = criarCardDinheiro();
    JPanel cardPix      = criarCardPix();
    JPanel cardCredito  = criarCardCartao();
    JPanel cardDebito   = criarCardCartao();

    pagamentoContainer.add(cardDinheiro, "DINHEIRO");
    pagamentoContainer.add(cardPix,      "PIX");
    pagamentoContainer.add(cardCredito,  "CREDITO");
    pagamentoContainer.add(cardDebito,   "DEBITO");

    gbc.gridy = 9; // abaixo do combo
    formCard.add(pagamentoContainer, gbc);

    // Troca de card ao mudar a seleção
    comboPagamento.addActionListener(e -> {
    String sel = (String) comboPagamento.getSelectedItem();
    CardLayout cl = (CardLayout) pagamentoContainer.getLayout();

    if (sel.startsWith("Dinheiro")) {
        cl.show(pagamentoContainer, "DINHEIRO");
        pixPago = false;

        // (opcional) se quiser limpar resíduos do Pix:
        if (btnPixAcao != null) { btnPixAcao.setText("Gerar QrCode"); btnPixAcao.setEnabled(true); }
        if (lblPixQr != null)   { lblPixQr.setIcon(null); }
    }
    else if ("Pix".equals(sel)) {
        cl.show(pagamentoContainer, "PIX");

        // ✅ RESET DO PIX (PASSO 5)
        pixPago = false;
        if (btnPixAcao != null) {
            btnPixAcao.setText("Gerar QrCode");
            btnPixAcao.setEnabled(true);
        }
        if (lblPixQr != null) {
            lblPixQr.setIcon(null); // esconde QR até gerar de novo
        }
    }
    else if ("Cartão de Crédito".equals(sel)) {
        cl.show(pagamentoContainer, "CREDITO");
        pixPago = false;

        // (opcional) limpar resíduos do Pix
        if (btnPixAcao != null) { btnPixAcao.setText("Gerar QrCode"); btnPixAcao.setEnabled(true); }
        if (lblPixQr != null)   { lblPixQr.setIcon(null); }
    }
    else if ("Cartão de Débito".equals(sel)) {
        cl.show(pagamentoContainer, "DEBITO");
        pixPago = false;

        // (opcional) limpar resíduos do Pix
        if (btnPixAcao != null) { btnPixAcao.setText("Gerar QrCode"); btnPixAcao.setEnabled(true); }
        if (lblPixQr != null)   { lblPixQr.setIcon(null); }
    }

    // Habilita/Desabilita "Finalizar" conforme a regra do Pix
    atualizarEstadoFinalizacao();
    });

    // Estado inicial
    ((CardLayout) pagamentoContainer.getLayout()).show(pagamentoContainer, "DINHEIRO");
    atualizarEstadoFinalizacao();
    }

    private void adicionarSecaoEntrega(JPanel formCard) {
        GridBagConstraints gbc = baseGbc();
        gbc.gridy = 4; gbc.gridwidth = 2;
        formCard.add(criarHeader("Selecione a forma de entrega"), gbc);

        String[] opcoesEntrega = { "Entrega padrão (5 dias úteis)", "Escolher data de entrega" };
        comboEntrega = new JComboBox<>(opcoesEntrega);
        comboEntrega.setFont(Ui.FONT_14);
        comboEntrega.setSelectedIndex(0);
        gbc.gridy = 5;
        formCard.add(comboEntrega, gbc);

        // Cria o datePicker
        datePicker = criarDatePicker();
        datePicker.setPreferredSize(new Dimension(200, 30));

        comboEntrega.addActionListener(e -> {
            if ("Escolher data de entrega".equals(comboEntrega.getSelectedItem())) {
                if (datePicker.getParent() == null) {
                    gbc.gridy = 6; // ⚠ mesmo gridy do botão — mantido
                    formCard.add(datePicker, gbc);
                    formCard.revalidate();
                    formCard.repaint();
                }
            } else {
                if (datePicker.getParent() != null) {
                    formCard.remove(datePicker);
                    formCard.revalidate();
                    formCard.repaint();
                }
            }
        });
    }

    private void adicionarBotaoFinalizar(JPanel formCard) {
        GridBagConstraints gbc = baseGbc();
        gbc.gridy = 11;
        gbc.gridwidth = 2;

        JPanel faixa = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        faixa.setOpaque(false);

        Color verde = Ui.VERDE;
        JButton btnFinalizarPedido = new JButton("\u2714 Finalizar Pedido");
        btnFinalizarPedido.setFont(Ui.FONT_B14);
        btnFinalizarPedido.setForeground(Color.WHITE);
        btnFinalizarPedido.setBackground(verde);
        btnFinalizarPedido.setOpaque(true);
        btnFinalizarPedido.setContentAreaFilled(true);
        btnFinalizarPedido.setFocusPainted(false);
        btnFinalizarPedido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFinalizarPedido.setBorder(new CompoundBorder(new LineBorder(verde.darker(), 1, true),
                                                       new EmptyBorder(12, 22, 12, 22)));

        btnFinalizarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered (java.awt.event.MouseEvent e) { btnFinalizarPedido.setBackground(verde.darker()); }
            @Override public void mouseExited  (java.awt.event.MouseEvent e) { btnFinalizarPedido.setBackground(verde); }
            @Override public void mousePressed (java.awt.event.MouseEvent e) { btnFinalizarPedido.setBackground(verde.darker().darker()); }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) { btnFinalizarPedido.setBackground(verde.darker()); }
        });

        faixa.add(btnFinalizarPedido);
        formCard.add(faixa, gbc);
    }

    private JPanel criarPainelLateral() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(Ui.LATERAL_BG);
        side.setPreferredSize(new Dimension(240, 0));
        side.setBorder(new EmptyBorder(20, 12, 20, 12));

        JButton btnMenu = new JButton("Menu Inicial");
        btnMenu.setFocusPainted(false);
        btnMenu.setBackground(Color.WHITE);
        btnMenu.setForeground(Color.DARK_GRAY);
        btnMenu.setBorder(new EmptyBorder(8, 12, 8, 12));
        side.add(btnMenu, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setVerticalAlignment(SwingConstants.CENTER);

        URL url = getClass().getResource("/icons/logo.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
        } else {
            lblLogo.setText("Logo");
            lblLogo.setForeground(Color.WHITE);
        }

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(lblLogo);
        side.add(centerWrap, BorderLayout.SOUTH);

        return side;
    }

    // ==========================
    // HELPERS
    // ==========================
    private JPanel criarHeader(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Ui.VERDE);
        p.setBorder(new EmptyBorder(8, 12, 8, 12));
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(Ui.FONT_B14);
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private GridBagConstraints baseGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = Ui.INSETS_FORM;
        return gbc;
    }

    private void atualizarTotais() {
        lblTotalCompra.setText(Ui.NF_BR.format(produtosModel.somaTotal()));
    }

    private void atualizarEstadoFinalizacao() {
    // Regra: somente PIX bloqueia até confirmar
    if (comboPagamento == null || btnFinalizarPedido == null) return;
    boolean isPix = "Pix".equals(comboPagamento.getSelectedItem());
    btnFinalizarPedido.setEnabled(!isPix || pixPago);
    }
    
    private ImageIcon loadIcon(String path, int w, int h) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setDate(2025, 8, 1); // 1/set/2025 (mês 0-based)
        model.setSelected(true);
        Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        // Usa sua implementação existente
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    // ==========================
    // MAIN
    // ==========================
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus não disponível, usando padrão.");
        }
        SwingUtilities.invokeLater(() -> new CriarPedidoView().setVisible(true));
    }
}