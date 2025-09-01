package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


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

        // Adiciona o espaçador para empurrar o form para cima
        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridy = 4;
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

        // Modelo que só permite edição na coluna "Quantidade"
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14)); // aumenta fonte geral
        tabela.setRowHeight(24); // linhas mais altas
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14)); // cabeçalho maior

        // Botão para adicionar produto
        Color verdePadrao = new Color(0x6A, 0x6E, 0x2D);

        JButton btnAddProduto = new JButton("Adicionar um produto");
        btnAddProduto.setBackground(verdePadrao);
        btnAddProduto.setForeground(Color.WHITE);
        btnAddProduto.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAddProduto.setFocusPainted(false);
        btnAddProduto.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        btnAddProduto.addActionListener(e -> abrirDialogAdicionarProduto(model));

        // Primeiro adiciona o botão
        formCard.add(btnAddProduto, gbc);

        // Depois a tabela logo abaixo
        gbc.gridy = 3;
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(500, 120));
        formCard.add(scrollPane, gbc);

        // Botão finalizar pedido no final da tela
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton btnFinalizarPedido = new JButton("Finalizar Pedido");
        btnFinalizarPedido.setBackground(verdePadrao);
        btnFinalizarPedido.setForeground(Color.WHITE);
        btnFinalizarPedido.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnFinalizarPedido.setFocusPainted(false);
        btnFinalizarPedido.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        formCard.add(btnFinalizarPedido, gbc);
    }


    private void abrirDialogAdicionarProduto(DefaultTableModel model) {
        try {
            // Ativa Nimbus se disponível
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            // se não tiver Nimbus, ignora
        }

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

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new CriarPedidoView().setVisible(true);
        });
    }
}