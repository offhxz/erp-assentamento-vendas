package br.org.assentamento.erp.vendas.view;

import javax.swing.*;
import java.awt.*;
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
        gbc.insets = new Insets(8, 8, 6, 8);

        formCard.add(criarHeader("Selecione o Produto"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 8, 12, 8);

        String[] colunas = {"Produto", "Quantidade"};
        Object[][] dados = {
            {"Alface Crespa", ""},
            {"Tomate Maduro", ""},
            {"Melancia", ""},
            {"Melancia", ""},
            {"Melancia", ""},
            {"Melancia", ""},
            {"Melancia", ""}
        };

        // Modelo que só permite edição na coluna "Quantidade"
        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14)); // aumenta fonte geral
        tabela.setRowHeight(24); // linhas mais altas
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14)); // cabeçalho maior

        // Scroll
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(500, 120)); // limita altura inicial

        formCard.add(scrollPane, gbc);

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
            lblLogo.setText("Logo");
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
