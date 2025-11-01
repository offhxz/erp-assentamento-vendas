package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AssociacaoMainView extends JFrame {

    private JTable tabelaPedidos;
    private JTable tabelaAcompanhamento;
    private DefaultTableModel modeloAcompanhamento;
    private Map<Object, Object[][]> dadosDeAcompanhamento;

    public AssociacaoMainView() {
        inicializarDadosDeExemplo();

        setTitle("Dashboard da Associação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(criarPainelLateral(), BorderLayout.WEST);
        add(criarContentPanel(), BorderLayout.CENTER);
    }


    private void inicializarDadosDeExemplo() {
        dadosDeAcompanhamento = new HashMap<>();

        // Produtos para o Pedido Nº 1
        dadosDeAcompanhamento.put(1, new Object[][]{
                {"Tomate Cereja (Kg)", "5", "R$ 8,50", "Em separação"},
                {"Alface Crespa (Un)", "10", "R$ 3,00", "Em separação"}
        });

        // Produtos para o Pedido Nº 2
        dadosDeAcompanhamento.put(2, new Object[][]{
                {"Cenoura (Kg)", "8", "R$ 4,20", "Aguardando Pagamento"},
                {"Batata Doce (Kg)", "12", "R$ 5,00", "Aguardando Pagamento"}
        });

        // Produtos para o Pedido Nº 3
        dadosDeAcompanhamento.put(3, new Object[][]{
                {"Abóbora (Un)", "3", "R$ 7,80", "Enviado"},
                {"Rúcula (Maço)", "15", "R$ 3,50", "Enviado"},
                {"Brócolis (Un)", "7", "R$ 6,00", "Enviado"}
        });
    }

    private JPanel criarPainelLateral() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(new Color(0x2F, 0x33, 0x20));
        side.setPreferredSize(new Dimension(240, 0));
        side.setBorder(new EmptyBorder(20, 12, 20, 12));

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setOpaque(false);

        String[] nomesBotoes = {"Criar Projeto", "Criar Pedido", "Produtores", "Falar com SAC"};

        for (String nome : nomesBotoes) {
            JButton btn = new JButton(nome);
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.DARK_GRAY);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            
            botoesPanel.add(btn);
            botoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        side.add(botoesPanel, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        java.net.URL url = getClass().getResource("logo.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaled));
        } else {
            System.err.println("Arquivo 'logo.png' não encontrado!");
            lblLogo.setText("Logo não encontrada");
            lblLogo.setForeground(Color.WHITE);
        }

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(lblLogo);
        side.add(centerWrap, BorderLayout.SOUTH);

        return side;
    }

    private JPanel criarContentPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.add(criarTitleBar(), BorderLayout.NORTH);
        content.add(criarDashboardCard(), BorderLayout.CENTER);
        return content;
    }

    private JComponent criarTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0x6A, 0x6E, 0x2D));
        bar.setBorder(new EmptyBorder(12, 24, 12, 24));

        JLabel titulo = new JLabel("Dashboard da Associação", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));

        bar.add(titulo, BorderLayout.CENTER);
        return bar;
    }
    
    private JPanel criarHeader(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(0x6A, 0x6E, 0x2D));
        p.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        p.add(l, BorderLayout.CENTER);

        return p;
    }

    private JScrollPane criarDashboardCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(0xE9, 0xE9, 0xE9));
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(criarHeader("Pedidos Recentes"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 24, 0);
        String[] colunasPedidos = { "Nº Pedido", "Cliente", "Data do pedido", "Total do Pedido" };
        Object[][] dadosPedidos = {
                { 1, "alguem ai", "10/06/2025", "R$ 150,75" },
                { 2, "Siclano de tal", "10/06/2025", "R$ 89,90" },
                { 3, "Fulano de tal", "10/06/2025", "R$ 234,50" }
        };
        tabelaPedidos = criarTabelaEstilizada(colunasPedidos, dadosPedidos);
        card.add(new JScrollPane(tabelaPedidos), gbc);
        
        // ---  Acompanhamento de Produtos ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(criarHeader("Acompanhamento de Produtos"), gbc);
        
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 24, 0);
        String[] colunasProdutos = { "Produto", "Quantidade", "Valor Unitário", "Status do pedido" };
        Object[][] dadosProdutosVazio = {};
        tabelaAcompanhamento = criarTabelaEstilizada(colunasProdutos, dadosProdutosVazio);
        modeloAcompanhamento = (DefaultTableModel) tabelaAcompanhamento.getModel();
        card.add(new JScrollPane(tabelaAcompanhamento), gbc);
        
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarAcompanhamento();
            }
        });

        // ---  Visão Geral de Projetos ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(criarHeader("Visão Geral de Projetos"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 24, 0);
        String[] colunasProjetos = { "Nº Projeto", "Cliente", "Data final", "Valor Total" };
        Object[][] dadosProjetos = {
                { 1, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 34, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 245, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" }
        };
        card.add(new JScrollPane(criarTabelaEstilizada(colunasProjetos, dadosProjetos)), gbc);

        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private void atualizarAcompanhamento() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        String[] colunasProdutos = { "Produto", "Quantidade", "Valor Unitário", "Status do pedido" };

        if (selectedRow != -1) {
            Object pedidoId = tabelaPedidos.getValueAt(selectedRow, 0);
            Object[][] novosDados = dadosDeAcompanhamento.getOrDefault(pedidoId, new Object[][]{});
            modeloAcompanhamento.setDataVector(novosDados, colunasProdutos);
        } else {
            modeloAcompanhamento.setDataVector(new Object[][]{}, colunasProdutos);
        }
    }

    private JTable criarTabelaEstilizada(String[] colunas, Object[][] dados) {
        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tabela = new JTable(model);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.setRowHeight(28);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        return tabela;
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
            // Nimbus não disponível, usa o padrão.
        }

        SwingUtilities.invokeLater(() -> new AssociacaoMainView().setVisible(true));
    }
}