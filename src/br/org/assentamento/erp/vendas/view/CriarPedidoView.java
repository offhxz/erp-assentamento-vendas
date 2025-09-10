package br.org.assentamento.erp.vendas.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

public class CriarPedidoView extends JFrame {

    private JComboBox<String> comboProdutor;

    public CriarPedidoView(){

        setTitle("Criar Pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarPainelLateral(), BorderLayout.WEST);
        add(criarContentPanel(), BorderLayout.CENTER);

    }

    private JPanel criarContentPanel() {

        JPanel content = new JPanel(new BorderLayout());
        content.add(criarTitleBar(), BorderLayout.NORTH);
        content.add(criarFormCard(), BorderLayout.CENTER);

        return content;

    }

    private JComponent criarTitleBar() {

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0x6A,0x6E,0x2D));
        bar.setBorder(new EmptyBorder(12, 24, 12, 24));

        JLabel titulo = new JLabel("Criar Pedido", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));

        bar.add(titulo, BorderLayout.CENTER);
        return bar;

    }

    private JPanel criarFormCard(){

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(new Color(0xE9, 0xE9, 0xE9));
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));

        adicionarSecaoProdutor(formCard);
        adicionarSecaoProduto(formCard);
        adicionarSecaoEntrega(formCard);
        adicionarBotaoFinalizar(formCard);

        // Adiciona o espaçador para empurrar o form para cima
        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0;
        gbcSpacer.gridy = 7; 
        gbcSpacer.weighty = 1.0;

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        formCard.add(spacer, gbcSpacer);

        return formCard;
    }

    private JPanel criarHeader(String texto){

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(0x6A, 0x6E, 0x2D));
        p.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        p.add(l, BorderLayout.CENTER);

        return p;

    } 

    private void adicionarSecaoProduto(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 12, 8);

        JPanel secao = new JPanel(new GridBagLayout());
        secao.setOpaque(false);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1.0;
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridy = 0;
        g.insets = new Insets(16, 0, 6, 0);
        secao.add(criarHeader("Produtos"), g);

        java.util.List<Produto> catalogo = java.util.Arrays.asList(
            new Produto("Alface Crespa", new java.math.BigDecimal("5.50")),
            new Produto("Tomate Maduro", new java.math.BigDecimal("7.90")),
            new Produto("Melancia", new java.math.BigDecimal("3.20")),
            new Produto("Cenoura", new java.math.BigDecimal("4.80")),
            new Produto("Batata", new java.math.BigDecimal("4.20"))
        );

        String[] colunas = {"Produto", "Quantidade (kg)", "Valor Unit. (R$)", "Total (R$)"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.setRowHeight(24);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabela.getColumnModel().getColumn(1).setCellRenderer(rightAlignRenderer());
        tabela.getColumnModel().getColumn(2).setCellRenderer(currencyRendererBR());
        tabela.getColumnModel().getColumn(3).setCellRenderer(currencyRendererBR());

        final java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        final JLabel lblTotalCompra = new JLabel(nf.format(java.math.BigDecimal.ZERO));
        lblTotalCompra.setFont(new Font("SansSerif", Font.BOLD, 16));

        model.addTableModelListener(e -> {
            if (e.getFirstRow() >= 0 && e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int r = e.getFirstRow();
                java.math.BigDecimal q = parseDecimal(model.getValueAt(r, 1));
                java.math.BigDecimal v = parseDecimal(model.getValueAt(r, 2));
                java.math.BigDecimal tot = (q != null && v != null) ? v.multiply(q) : null;
                if (tot != null) model.setValueAt(tot, r, 3);
            }
            atualizarTotais(model, lblTotalCompra, nf);
        });

        g.gridy = 1;
        g.insets = new Insets(0, 0, 0, 0);
        secao.add(criarBarraInclusaoProdutos(model, tabela, catalogo), g);

        g.gridy = 2;
        g.insets = new Insets(8, 0, 0, 0);
        g.fill = GridBagConstraints.BOTH;
        g.weighty = 1.0;
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(500, 160));
        secao.add(scroll, g);

        g.gridy = 3;
        g.insets = new Insets(8, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weighty = 0.0;
        JPanel rodape = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 6));
        JLabel lblTituloTotal = new JLabel("Total da compra:");
        lblTituloTotal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rodape.add(lblTituloTotal);
        rodape.add(lblTotalCompra);
        secao.add(rodape, g);

        formCard.add(secao, gbc);
        formCard.revalidate();
        formCard.repaint();
    }

    private JPanel criarBarraInclusaoProdutos(DefaultTableModel model, JTable tabela, java.util.List<Produto> catalogo) {
        JPanel barraAdd = new JPanel(new GridBagLayout());

        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(4, 4, 4, 4);
        g2.fill = GridBagConstraints.HORIZONTAL;

        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 14);
        Color verdePadrao = new Color(0x6A, 0x6E, 0x2D);

        JComboBox<Produto> comboProduto = new JComboBox<>(new javax.swing.DefaultComboBoxModel<>(catalogo.toArray(new Produto[0])));
        comboProduto.setFont(fontePadrao);
        g2.gridx = 0; g2.weightx = 1.0;
        barraAdd.add(comboProduto, g2);

        JLabel lblValorUnit = new JLabel();
        lblValorUnit.setHorizontalAlignment(SwingConstants.RIGHT);
        lblValorUnit.setFont(fontePadrao);
        lblValorUnit.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,220,220)),
            BorderFactory.createEmptyBorder(4,8,4,8)
        ));
        g2.gridx = 1; g2.weightx = 0.35;
        barraAdd.add(lblValorUnit, g2);

        JTextField txtQtd = new JTextField();
        txtQtd.setFont(fontePadrao);
        txtQtd.setToolTipText("Quantidade em kg");
        g2.gridx = 2; g2.weightx = 0.25;
        barraAdd.add(txtQtd, g2);

        JButton btnAdd = new JButton("\u2795");
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnAdd.setBackground(verdePadrao);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        g2.gridx = 3; g2.weightx = 0.0;
        barraAdd.add(btnAdd, g2);

        java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        Runnable atualizarPreco = () -> {
            Produto p = (Produto) comboProduto.getSelectedItem();
            lblValorUnit.setText(p != null ? nf.format(p.preco) : "");
        };
        comboProduto.addActionListener(e -> atualizarPreco.run());
        atualizarPreco.run();

        Runnable tentarAdicionar = () -> adicionarProdutoNaTabela(model, comboProduto, txtQtd, tabela);
        btnAdd.addActionListener(e -> tentarAdicionar.run());
        txtQtd.addActionListener(e -> tentarAdicionar.run());

        return barraAdd;
    }

    private void adicionarProdutoNaTabela(DefaultTableModel model,
                                        JComboBox<Produto> comboProduto,
                                        JTextField txtQtd,
                                        JTable tabela) {
        Produto produto = (Produto) comboProduto.getSelectedItem();
        String qtdStr = txtQtd.getText().trim();

        if (produto == null || produto.nome.isBlank() || qtdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe produto e quantidade.");
            return;
        }

        java.math.BigDecimal qtd = parseDecimal(qtdStr);
        if (qtd == null || qtd.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }

        java.math.BigDecimal val = produto.preco;
        java.math.BigDecimal total = val.multiply(qtd);
        model.addRow(new Object[]{produto.nome, qtd, val, total});

        comboProduto.setSelectedIndex(0);
        txtQtd.setText("");
        comboProduto.requestFocusInWindow();
    }

    private void atualizarTotais(DefaultTableModel model, JLabel lblTotalCompra, java.text.NumberFormat nf) {
        java.math.BigDecimal soma = java.math.BigDecimal.ZERO;
        for (int i = 0; i < model.getRowCount(); i++) {
            java.math.BigDecimal tot = parseDecimal(model.getValueAt(i, 3));
            if (tot != null) soma = soma.add(tot);
        }
        lblTotalCompra.setText(nf.format(soma));
    }

    private javax.swing.table.DefaultTableCellRenderer rightAlignRenderer() {
        return new javax.swing.table.DefaultTableCellRenderer() {{
            setHorizontalAlignment(SwingConstants.RIGHT);
        }};
    }

    private javax.swing.table.DefaultTableCellRenderer currencyRendererBR() {
        java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        return new javax.swing.table.DefaultTableCellRenderer() {
            { setHorizontalAlignment(SwingConstants.RIGHT); }
            @Override protected void setValue(Object value) {
                if (value == null) { setText(""); return; }
                java.math.BigDecimal bd = parseDecimal(value);
                setText(bd != null ? nf.format(bd) : "");
            }
        };
    }

    private java.math.BigDecimal parseDecimal(Object v) {
        if (v == null) return null;
        if (v instanceof java.math.BigDecimal) return (java.math.BigDecimal) v;
        if (v instanceof Number) return new java.math.BigDecimal(((Number) v).toString());
        String s = v.toString().trim().replace('.', '#').replace(',', '.').replace('#', '.');
        try { return new java.math.BigDecimal(s); } catch (Exception ex) { return null; }
    }

    private static class Produto {
        final String nome;
        final java.math.BigDecimal preco;
        Produto(String nome, java.math.BigDecimal preco) { this.nome = nome; this.preco = preco; }
        @Override public String toString() { return nome; }
    }

    private void adicionarBotaoFinalizar(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 12, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel faixa = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        faixa.setOpaque(false);

        Color verde = new Color(0x6A, 0x6E, 0x2D);

        JButton btnFinalizarPedido = new JButton("\u2714 Finalizar Pedido");
        btnFinalizarPedido.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnFinalizarPedido.setForeground(Color.WHITE);
        btnFinalizarPedido.setBackground(verde);
        btnFinalizarPedido.setOpaque(true);
        btnFinalizarPedido.setContentAreaFilled(true);
        btnFinalizarPedido.setFocusPainted(false);
        btnFinalizarPedido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFinalizarPedido.setBorder(new CompoundBorder(
            new LineBorder(verde.darker(), 1, true),
            new EmptyBorder(12, 22, 12, 22)
        ));

        btnFinalizarPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btnFinalizarPedido.setBackground(verde.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btnFinalizarPedido.setBackground(verde);
            }
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                btnFinalizarPedido.setBackground(verde.darker().darker());
            }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) {
                btnFinalizarPedido.setBackground(verde.darker());
            }
        });

        faixa.add(btnFinalizarPedido);
        formCard.add(faixa, gbc);

        // Se quiser que Enter finalize:
        // getRootPane().setDefaultButton(btnFinalizarPedido);
    }

    private void adicionarSecaoProdutor(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 6, 8);

        formCard.add(criarHeader("Selecione produtor / associação"), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 8, 12, 8);

        comboProdutor = new JComboBox<>(new String[] {
                "Associação", "Fernando Silva", "Renato Cabral"
        });
        comboProdutor.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        comboProdutor.setFont(new Font("SansSerif", Font.PLAIN, 14)); // aumenta fonte

        formCard.add(comboProdutor, gbc);
    }

    private JPanel criarPainelLateral() {

        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(new Color(0x2F, 0x33, 0x20));
        side.setPreferredSize(new Dimension(240, 0));
        side.setBorder(new javax.swing.border.EmptyBorder(20, 12, 20, 12));

        JButton btnMenu = new JButton("Menu Inicial");
        btnMenu.setFocusPainted(false);
        btnMenu.setBackground(Color.WHITE);
        btnMenu.setForeground(Color.DARK_GRAY);
        btnMenu.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        side.add(btnMenu, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setVerticalAlignment(SwingConstants.CENTER);

        java.net.URL url = getClass().getResource("/icons/logo.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(120,120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
            // lblLogo.setText("Logo"); // Removido para não sobrepor a imagem
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

    private void adicionarSecaoEntrega(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4; // posição da seção
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 12, 8);

        formCard.add(criarHeader("Selecione a forma de entrega"), gbc);

        String[] opcoesEntrega = {"Entrega padrão (5 dias úteis)", "Escolher data de entrega"};
        JComboBox<String> comboEntrega = new JComboBox<>(opcoesEntrega);
        comboEntrega.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboEntrega.setSelectedIndex(0);

        gbc.gridy = 5;
        formCard.add(comboEntrega, gbc);

        // Cria o datePicker, mas não adiciona ainda
        JDatePickerImpl datePicker = criarDatePicker();
        datePicker.setPreferredSize(new Dimension(200, 30));

        comboEntrega.addActionListener(e -> {
            if ("Escolher data de entrega".equals(comboEntrega.getSelectedItem())) {
                // Adiciona o datePicker se ainda não estiver adicionado
                if (datePicker.getParent() == null) {
                    gbc.gridy = 6;
                    formCard.add(datePicker, gbc);
                    formCard.revalidate();
                    formCard.repaint();
                }
            } else {
                // Remove o datePicker se estiver
                if (datePicker.getParent() != null) {
                    formCard.remove(datePicker);
                    formCard.revalidate();
                    formCard.repaint();
                }
            }
        });
    }

    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        model.setDate(2025, 8, 1);
        model.setSelected(true);

        java.util.Properties p = new java.util.Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setPreferredSize(new Dimension(200, 30));
        return datePicker;
    }




    public static void main(String[] args){
        // Tenta aplicar o Nimbus antes de criar qualquer JFrame
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // se não encontrar Nimbus, ignora
            System.out.println("Nimbus não disponível, usando padrão.");
        }
        SwingUtilities.invokeLater(() -> {
            new CriarPedidoView().setVisible(true);
        });
    }
}