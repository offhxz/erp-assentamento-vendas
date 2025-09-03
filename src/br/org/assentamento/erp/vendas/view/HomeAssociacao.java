package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class HomeAssociacao extends JFrame {

    private static final Color COR_SIDEBAR = new Color(0x4A, 0x4A, 0x2A); // Verde escuro
    private static final Color COR_HEADER = new Color(0x9A, 0xA6, 0x5A); // Verde oliva claro
    private static final Color COR_LINHA_PAR = Color.WHITE;
    private static final Color COR_LINHA_IMPAR = new Color(0xF5, 0xF5, 0xF5); // Cinza muito claro
    private static final Color COR_GRADE = new Color(0xE0, 0xE0, 0xE0);

    public void CriarPedidoView() {
        setTitle("Associação");
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
        content.setBackground(Color.WHITE);

        content.add(criarTitleBar(), BorderLayout.NORTH);
        content.add(criarMainContent(), BorderLayout.CENTER);

        return content;
    }

    private JPanel criarTitleBar() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel titleLabel = new JLabel("Associação");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel criarMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(criarTabelaProjetos(), BorderLayout.NORTH);

        JPanel centralPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centralPanel.setBackground(Color.WHITE);

        centralPanel.add(criarTabelaProdutos());
        centralPanel.add(criarTabelaPedidos());

        mainPanel.add(centralPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel criarTabelaProjetos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] colunasProjetos = { "Nº Pedido", "Cliente", "Data do pedido" };
        Object[][] dadosProjetos = {
                { 1, "alguem ai", "10/06/2025" },
                { 2, "Siclano de tal", "10/06/2025" },
                { 3, "Fulano de tal", "10/06/2025" }
        };

        JTable tabelaProjetos = criarTabelaEstilizada(colunasProjetos, dadosProjetos);
        JScrollPane scrollProjetos = new JScrollPane(tabelaProjetos);

        int alturaProjetos = (tabelaProjetos.getRowHeight() * dadosProjetos.length)
                + tabelaProjetos.getTableHeader().getPreferredSize().height;
        scrollProjetos.setPreferredSize(new Dimension(0, alturaProjetos));
        scrollProjetos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollProjetos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollProjetos.getViewport().setBackground(Color.WHITE);

        panel.add(scrollProjetos, BorderLayout.CENTER);
        return panel;
    }

    private JPanel criarTabelaProdutos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] colunasProdutos = { "Produto", "Quantidade", "Status do pedido" };
        Object[][] dadosProdutos = new Object[10][3];

        JTable tabelaProdutos = criarTabelaEstilizada(colunasProdutos, dadosProdutos);
        JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);

        int alturaProdutos = (tabelaProdutos.getRowHeight() * dadosProdutos.length)
                + tabelaProdutos.getTableHeader().getPreferredSize().height;
        scrollProdutos.setPreferredSize(new Dimension(0, alturaProdutos));
        scrollProdutos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollProdutos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollProdutos.getViewport().setBackground(Color.WHITE);

        panel.add(scrollProdutos, BorderLayout.CENTER);
        return panel;
    }

    private JPanel criarTabelaPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] colunasPedidos = { "Nº Projeto", "Cliente", "Data final", "Valor Total" };
        Object[][] dadosPedidos = {
                { 1, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 34, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 245, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" }
        };

        JTable tabelaPedidos = criarTabelaEstilizada(colunasPedidos, dadosPedidos);

        String[] colunasProdutosPedido = { "Produto", "Quantidade", "Status do pedido", "Valor total" };
        Object[][] dadosProdutosPedido = new Object[10][4];

        JTable tabelaProdutosPedido = criarTabelaEstilizada(colunasProdutosPedido, dadosProdutosPedido);

        JPanel combinedPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        combinedPanel.setBackground(Color.WHITE);

        JScrollPane scrollPedidos = new JScrollPane(tabelaPedidos);
        int alturaPedidos = (tabelaPedidos.getRowHeight() * dadosPedidos.length)
                + tabelaPedidos.getTableHeader().getPreferredSize().height;
        scrollPedidos.setPreferredSize(new Dimension(0, alturaPedidos));
        scrollPedidos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPedidos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPedidos.getViewport().setBackground(Color.WHITE);

        JScrollPane scrollProdutosPedido = new JScrollPane(tabelaProdutosPedido);
        int alturaProdutosPedido = (tabelaProdutosPedido.getRowHeight() * dadosProdutosPedido.length)
                + tabelaProdutosPedido.getTableHeader().getPreferredSize().height;
        scrollProdutosPedido.setPreferredSize(new Dimension(0, alturaProdutosPedido));
        scrollProdutosPedido.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollProdutosPedido.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollProdutosPedido.getViewport().setBackground(Color.WHITE);

        combinedPanel.add(scrollPedidos);
        combinedPanel.add(scrollProdutosPedido);

        panel.add(combinedPanel, BorderLayout.CENTER);
        return panel;
    }

    private JTable criarTabelaEstilizada(String[] colunas, Object[][] dados) {
        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.setRowHeight(30);

        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        tabela.getTableHeader().setBackground(COR_HEADER);
        tabela.getTableHeader().setForeground(Color.WHITE); // TEXTO BRANCO

        JTableHeader header = tabela.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(COR_HEADER);
                label.setForeground(Color.WHITE); // TEXTO BRANCO
                label.setFont(new Font("SansSerif", Font.BOLD, 16));
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(COR_LINHA_PAR);
                    } else {
                        c.setBackground(COR_LINHA_IMPAR);
                    }
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        });

        tabela.setShowGrid(true);
        tabela.setGridColor(COR_GRADE);

        return tabela;
    }

    private JPanel criarPainelLateral() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(COR_SIDEBAR);
        side.setPreferredSize(new Dimension(200, 0));

        // Menu hambúrguer
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(COR_SIDEBAR);
        topPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel menuIcon = new JLabel("☰");
        menuIcon.setForeground(Color.WHITE);
        menuIcon.setFont(new Font("SansSerif", Font.BOLD, 18));
        topPanel.add(menuIcon);

        side.add(topPanel, BorderLayout.NORTH);

        // Botões
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setBackground(COR_SIDEBAR);
        botoesPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        String[] nomesBotoes = {
                "Criar Projeto",
                "Criar Pedido",
                "Produtores",
                "Falar com SAC"
        };

        for (String nome : nomesBotoes) {
            JButton btn = new JButton(nome);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.DARK_GRAY);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            btn.setHorizontalAlignment(SwingConstants.LEFT);

            btn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Clicou em: " + nome));

            botoesPanel.add(btn);
            botoesPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        side.add(botoesPanel, BorderLayout.CENTER);

        // Logo inferior
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(COR_SIDEBAR);
        logoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setVerticalAlignment(SwingConstants.CENTER);

        java.net.URL url = getClass().getResource("/icons/logo.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
        } else {
            lblLogo.setText("Logo");
            lblLogo.setForeground(Color.WHITE);
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 40));
        }

        logoPanel.add(lblLogo, BorderLayout.CENTER);
        side.add(logoPanel, BorderLayout.SOUTH);

        return side;
    }

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

        SwingUtilities.invokeLater(() -> {
            HomeAssociacao frame = new HomeAssociacao();
            frame.CriarPedidoView();
            frame.setVisible(true);
        });
    }
}
