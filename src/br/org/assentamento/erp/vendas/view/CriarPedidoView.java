package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        String[] colunas = {"Produto", "Quantidade"};

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

        Color verdePadrao = new Color(0x6A, 0x6E, 0x2D);

        JButton btnAddProduto = new JButton("Adicionar um produto");
        btnAddProduto.setBackground(verdePadrao);
        btnAddProduto.setForeground(Color.WHITE);
        btnAddProduto.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAddProduto.setFocusPainted(false);
        btnAddProduto.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        btnAddProduto.addActionListener(e -> abrirDialogAdicionarProduto(model));

        formCard.add(btnAddProduto, gbc);

        gbc.gridy = 3;
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(500, 120));
        formCard.add(scrollPane, gbc);
    }

    private void adicionarBotaoFinalizar(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 12, 8); // mesmo espaçamento dos outros botões
        gbc.fill = GridBagConstraints.HORIZONTAL; // igual aos outros

        Color verdePadrao = new Color(0x6A, 0x6E, 0x2D);

        JButton btnFinalizarPedido = new JButton("Finalizar Pedido");
        btnFinalizarPedido.setBackground(verdePadrao);
        btnFinalizarPedido.setForeground(Color.WHITE);
        btnFinalizarPedido.setFont(new Font("SansSerif", Font.BOLD, 14)); // mesma fonte dos outros
        btnFinalizarPedido.setFocusPainted(false);
        btnFinalizarPedido.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // mesma borda

        formCard.add(btnFinalizarPedido, gbc);
    }


    private void abrirDialogAdicionarProduto(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Adicionar Produto", true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 14);
        Font fonteNegrito = new Font("SansSerif", Font.BOLD, 14);
        Color verdeEscuro = new Color(0x6A, 0x6E, 0x2D);

        // Label e combo de produtos
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Produto:"), gbc);

        JComboBox<String> comboProduto = new JComboBox<>(new String[]{
            "Alface Crespa", "Tomate Maduro", "Melancia", "Cenoura", "Batata"
        });
        comboProduto.setFont(fontePadrao);
        gbc.gridx = 1;
        dialog.add(comboProduto, gbc);

        // Label e campo quantidade
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Quantidade (kg):"), gbc);

        JTextField txtQtd = new JTextField(10);
        txtQtd.setFont(fontePadrao);
        gbc.gridx = 1;
        dialog.add(txtQtd, gbc);

        // Botão confirmar
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnOk = new JButton("Adicionar");
        btnOk.setBackground(verdeEscuro);
        btnOk.setForeground(Color.WHITE);
        btnOk.setFont(fonteNegrito);
        btnOk.setFocusPainted(false);
        btnOk.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btnOk.addActionListener(e -> {
            String produto = (String) comboProduto.getSelectedItem();
            String qtd = txtQtd.getText().trim();

            if (produto != null && !qtd.isEmpty()) {
                model.addRow(new Object[]{produto, qtd});
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!");
            }
        });
        dialog.add(btnOk, gbc);

        dialog.setVisible(true);
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