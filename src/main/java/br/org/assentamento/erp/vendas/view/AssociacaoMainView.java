package br.org.assentamento.erp.vendas.view;

import java.awt.*;
<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
=======
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

<<<<<<< HEAD
public class AssociacaoMainView extends BaseView {

    private static final long serialVersionUID = 1L;
=======
public class AssociacaoMainView extends JFrame {
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f

    private JTable tabelaPedidos;
    private JTable tabelaAcompanhamento;
    private DefaultTableModel modeloAcompanhamento;
    private Map<Object, Object[][]> dadosDeAcompanhamento;

<<<<<<< HEAD
    private DefaultTableModel pedidosModel; 
    
    private JLabel lblTotalAcompanhamento;


    private static final String STATUS_FINALIZADO = "Finalizado";
    private static final String STATUS_EM_PROCESSO = "Em processo";
    private static final String STATUS_CANCELADO = "Cancelado";
    private static final int COL_STATUS = 5; // Coluna 5 agora (0-5)

    private static final String[] STATUS_OPCOES = {
        STATUS_FINALIZADO, 
        STATUS_EM_PROCESSO,
        STATUS_CANCELADO
    };

    // Status para a tabela Acompanhamento
    private static final String STATUS_ACOMP_ENTREGUE = "Entregue";
    private static final String STATUS_ACOMP_SEPARACAO = "Em separação";
    private static final String STATUS_ACOMP_CANCELADO_PROD = "Cancelado";
    private static final String STATUS_ACOMP_AG_PAGAMENTO = "Aguardando Pagamento";
    private static final String STATUS_ACOMP_ENVIADO = "Enviado";
    private static final int COL_STATUS_ACOMPANHAMENTO = 3; 

    private static final String[] STATUS_OPCOES_ACOMPANHAMENTO = {
        STATUS_ACOMP_SEPARACAO,
        STATUS_ACOMP_AG_PAGAMENTO,
        STATUS_ACOMP_ENVIADO,
        STATUS_ACOMP_ENTREGUE,
        STATUS_ACOMP_CANCELADO_PROD
    };

    public AssociacaoMainView() {
        super("Associação - Visão Geral de Pedidos");
        
        // Dados de exemplo devem ser inicializados ANTES de criar o painel
        inicializarDadosDeExemplo();
        add(criarContentPanel(), BorderLayout.CENTER);

        // Carrega o primeiro pedido por padrão
        if (pedidosModel.getRowCount() > 0) {
            tabelaPedidos.setRowSelectionInterval(0, 0);
            atualizarAcompanhamento();
       }
    }

    private void inicializarDadosDeExemplo() {
        dadosDeAcompanhamento = new HashMap<>();

        dadosDeAcompanhamento.put(1, new Object[][]{
            {"Tomate Cereja (Kg)", "5", "R$ 8,50", STATUS_ACOMP_SEPARACAO},
            {"Alface Crespa (Un)", "10", "R$ 3,00", STATUS_ACOMP_SEPARACAO}
        });

        dadosDeAcompanhamento.put(2, new Object[][]{
            {"Cenoura (Kg)", "8", "R$ 4,20", STATUS_ACOMP_AG_PAGAMENTO},
            {"Batata Doce (Kg)", "12", "R$ 5,00", STATUS_ACOMP_AG_PAGAMENTO}
        });

        dadosDeAcompanhamento.put(3, new Object[][]{
            {"Abóbora (Un)", "3", "R$ 7,80", STATUS_ACOMP_ENVIADO},
            {"Rúcula (Maço)", "15", "R$ 3,50", STATUS_ACOMP_ENVIADO},
            {"Brócolis (Un)", "7", "R$ 6,00", STATUS_ACOMP_ENVIADO}
        });
    }

=======
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

>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
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

<<<<<<< HEAD
        JLabel titulo = new JLabel("Associação - Pedidos Recebidos", SwingConstants.CENTER);
=======
        JLabel titulo = new JLabel("Dashboard da Associação", SwingConstants.CENTER);
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));

        bar.add(titulo, BorderLayout.CENTER);
        return bar;
    }
<<<<<<< HEAD

    @Override
    protected JPanel criarHeader(String texto) {
=======
    
    private JPanel criarHeader(String texto) {
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(0x6A, 0x6E, 0x2D));
        p.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        p.add(l, BorderLayout.CENTER);

        return p;
    }

<<<<<<< HEAD

=======
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
    private JScrollPane criarDashboardCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(0xE9, 0xE9, 0xE9));
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
<<<<<<< HEAD

=======
        
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
<<<<<<< HEAD

        // --- Pedidos Recentes ---
        card.add(criarHeader("Pedidos Recentes"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 24, 0);
        String[] colunasPedidos = {"Nº Pedido", "Cliente", "Data do Pedido", "Orçamento Total", "Saldo Disponível", "Status"};
        
        Object[][] dadosPedidosMock = {
            {1, "alguem ai", "10/06/2025", "R$ 150,75", STATUS_FINALIZADO},
            {2, "Siclano de tal", "10/06/2025", "R$ 89,90", STATUS_CANCELADO},
            {3, "Fulano de tal", "10/06/2025", "R$ 234,50", STATUS_EM_PROCESSO}
        };
        
        // --- Processa os dados para calcular o saldo ---
        Object[][] dadosTabelaFinal = new Object[dadosPedidosMock.length][6];
        for (int i = 0; i < dadosPedidosMock.length; i++) {
            int pedidoId = (int) dadosPedidosMock[i][0];
            String orcamentoStr = ((String) dadosPedidosMock[i][3]).replace("R$ ", "").replace(".", "").replace(",", ".");
            double orcamentoTotal = 0.0;
            
            try { orcamentoTotal = Double.parseDouble(orcamentoStr); } catch (NumberFormatException e) {}

            double totalGasto = 0.0;
            Object[][] items = dadosDeAcompanhamento.getOrDefault(pedidoId, new Object[][]{});

            for (Object[] item : items) {
                try {
                    String valorUnitStr = ((String) item[2]).replace("R$ ", "").replace(".", "").replace(",", ".");
                    String qtdStr = ((String) item[1]).replace(",", ".");
                    
                    double valorUnit = Double.parseDouble(valorUnitStr);
                    double qtd = Double.parseDouble(qtdStr);
                    totalGasto += (valorUnit * qtd);
                } catch (Exception e) {
                    System.err.println("Erro ao calcular total do item: " + e.getMessage());
                }
            }
            
            double saldo = orcamentoTotal - totalGasto;

            dadosTabelaFinal[i][0] = dadosPedidosMock[i][0]; // Nº Pedido
            dadosTabelaFinal[i][1] = dadosPedidosMock[i][1]; // Cliente
            dadosTabelaFinal[i][2] = dadosPedidosMock[i][2]; // Data
            dadosTabelaFinal[i][3] = dadosPedidosMock[i][3]; // Orçamento Total
            dadosTabelaFinal[i][4] = String.format("R$ %.2f", saldo); // Saldo Disponível
            dadosTabelaFinal[i][5] = dadosPedidosMock[i][4]; // Status
        }
        
        pedidosModel = new DefaultTableModel(dadosTabelaFinal, colunasPedidos) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaPedidos = new JTable(pedidosModel); 

        tabelaPedidos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaPedidos.setRowHeight(28);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader header = tabelaPedidos.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        
        card.add(new JScrollPane(tabelaPedidos), gbc);

        // --- Acompanhamento de Produtos ---
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(criarHeader("Acompanhamento de Produtos"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0); // Remove o inset de baixo
        String[] colunasProdutos = {"Produto", "Quantidade", "Valor Unitário", "Status do pedido"};
        
        // Usa o criarTabelaEstilizada mas salva o modelo
        tabelaAcompanhamento = criarTabelaEstilizada(colunasProdutos, new Object[][]{});
        modeloAcompanhamento = (DefaultTableModel) tabelaAcompanhamento.getModel();
        card.add(new JScrollPane(tabelaAcompanhamento), gbc);

        // --- Painel de Total de Acompanhamento ---
        gbc.gridy++;
        gbc.insets = new Insets(4, 8, 24, 8); // Adiciona padding
        
        JPanel painelTotalAcompanhamento = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotalAcompanhamento.setBackground(new Color(0xE0, 0xE0, 0xE0));
        painelTotalAcompanhamento.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.GRAY));

        JLabel lblTotalTexto = new JLabel("Total dos Itens:");
        lblTotalTexto.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        lblTotalAcompanhamento = new JLabel("R$ 0,00");
        lblTotalAcompanhamento.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotalAcompanhamento.setForeground(Color.RED.darker());
        
        painelTotalAcompanhamento.add(lblTotalTexto);
        painelTotalAcompanhamento.add(lblTotalAcompanhamento);
        
        card.add(painelTotalAcompanhamento, gbc);


        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) atualizarAcompanhamento();
        });

        tabelaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tabelaPedidos.rowAtPoint(e.getPoint());
                    int col = tabelaPedidos.columnAtPoint(e.getPoint());
                    
                    if (row != -1 && col == COL_STATUS) { 
                        tabelaPedidos.setRowSelectionInterval(row, row);
                        mostrarPopupStatus(row); 
                    }
                }
            }
        });

        tabelaAcompanhamento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    int row = tabelaAcompanhamento.rowAtPoint(e.getPoint());
                    int col = tabelaAcompanhamento.columnAtPoint(e.getPoint());
                    
                    if (row != -1 && col == COL_STATUS_ACOMPANHAMENTO) { 
                        tabelaAcompanhamento.setRowSelectionInterval(row, row);
                        mostrarPopupStatusAcompanhamento(row); 
                    }
                }
            }
        });

        // --- Visão Geral de Projetos ---
=======
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
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 8, 0);
        card.add(criarHeader("Visão Geral de Projetos"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 24, 0);
<<<<<<< HEAD
        String[] colunasProjetos = {"Nº Projeto", "Cliente", "Data final", "Valor Total"};
        Object[][] dadosProjetos = {
            {1, "Fulano de tal", "--/--/----", "ORÇ. R$15.000"},
            {34, "Fulano de tal", "--/--/----", "ORÇ. R$15.000"},
            {245, "Fulano de tal", "--/--/----", "ORÇ. R$15.000"}
=======
        String[] colunasProjetos = { "Nº Projeto", "Cliente", "Data final", "Valor Total" };
        Object[][] dadosProjetos = {
                { 1, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 34, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" },
                { 245, "Fulano de tal", "00/00/0000", "ORÇ. R$15,000" }
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
        };
        card.add(new JScrollPane(criarTabelaEstilizada(colunasProjetos, dadosProjetos)), gbc);

        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private void atualizarAcompanhamento() {
        int selectedRow = tabelaPedidos.getSelectedRow();
<<<<<<< HEAD
        String[] colunasProdutos = {"Produto", "Quantidade", "Valor Unitário", "Status do pedido"};
        
        double totalItens = 0.0;
=======
        String[] colunasProdutos = { "Produto", "Quantidade", "Valor Unitário", "Status do pedido" };
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f

        if (selectedRow != -1) {
            Object pedidoId = tabelaPedidos.getValueAt(selectedRow, 0);
            Object[][] novosDados = dadosDeAcompanhamento.getOrDefault(pedidoId, new Object[][]{});
            modeloAcompanhamento.setDataVector(novosDados, colunasProdutos);
<<<<<<< HEAD
            
            for (Object[] item : novosDados) {
                try {
                    String valorUnitStr = ((String) item[2]).replace("R$ ", "").replace(".", "").replace(",", ".");
                    String qtdStr = ((String) item[1]).replace(",", ".");
                    
                    double valorUnit = Double.parseDouble(valorUnitStr);
                    double qtd = Double.parseDouble(qtdStr);
                    totalItens += (valorUnit * qtd);
                } catch (Exception e) {
                    System.err.println("Erro ao calcular total do item: " + e.getMessage());
                }
            }
        } else {
            modeloAcompanhamento.setDataVector(new Object[][]{}, colunasProdutos);
        }
        
        if (lblTotalAcompanhamento != null) {
            lblTotalAcompanhamento.setText(String.format("R$ %.2f", totalItens));
        }
    }

    private void mostrarPopupStatus(int rowIndex) {
        Object idPedido = pedidosModel.getValueAt(rowIndex, 0); 
        String statusAtual = (String) pedidosModel.getValueAt(rowIndex, COL_STATUS);
        
        JComboBox<String> comboStatus = new JComboBox<>(STATUS_OPCOES);
        comboStatus.setSelectedItem(statusAtual);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Selecione o novo status para o Pedido " + idPedido + ":"), BorderLayout.NORTH);
        panel.add(comboStatus, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Alterar Status do Pedido",
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String novoStatus = (String) comboStatus.getSelectedItem();
            if (novoStatus != null && !novoStatus.equals(statusAtual)) {
                alterarStatusPedido(rowIndex, novoStatus); 
            }
        }
    }
    
    private void alterarStatusPedido(int rowIndex, String novoStatus) {
        String currentStatus = (String) pedidosModel.getValueAt(rowIndex, COL_STATUS);
        
        if ((currentStatus.equals(STATUS_FINALIZADO) || currentStatus.equals(STATUS_CANCELADO)) && 
            !novoStatus.equals(currentStatus)) {
            
            JOptionPane.showMessageDialog(this, 
                "Não é possível alterar o status de um pedido já Finalizado ou Cancelado.", 
                "Status Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        pedidosModel.setValueAt(novoStatus, rowIndex, COL_STATUS);
        
        Object idPedido = pedidosModel.getValueAt(rowIndex, 0);
        System.out.println("Status do Pedido " + idPedido + " alterado para: " + novoStatus);
    }

    private void mostrarPopupStatusAcompanhamento(int rowIndex) {
        Object produtoNome = modeloAcompanhamento.getValueAt(rowIndex, 0); 
        String statusAtual = (String) modeloAcompanhamento.getValueAt(rowIndex, COL_STATUS_ACOMPANHAMENTO);
        
        JComboBox<String> comboStatus = new JComboBox<>(STATUS_OPCOES_ACOMPANHAMENTO);
        comboStatus.setSelectedItem(statusAtual);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Selecione o novo status para o produto " + produtoNome + ":"), BorderLayout.NORTH);
        panel.add(comboStatus, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Alterar Status do Produto",
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String novoStatus = (String) comboStatus.getSelectedItem();
            if (novoStatus != null && !novoStatus.equals(statusAtual)) {
                alterarStatusAcompanhamento(rowIndex, novoStatus); 
            }
        }
    }
    
    private void alterarStatusAcompanhamento(int rowIndex, String novoStatus) {
        String currentStatus = (String) modeloAcompanhamento.getValueAt(rowIndex, COL_STATUS_ACOMPANHAMENTO);
        
        if ((currentStatus.equals(STATUS_ACOMP_ENTREGUE) || currentStatus.equals(STATUS_ACOMP_ENVIADO) || currentStatus.equals(STATUS_ACOMP_CANCELADO_PROD)) && 
            !novoStatus.equals(currentStatus)) {
            
            JOptionPane.showMessageDialog(this, 
                "Não é possível alterar o status de um item já finalizado (Entregue, Enviado ou Cancelado).", 
                "Status Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloAcompanhamento.setValueAt(novoStatus, rowIndex, COL_STATUS_ACOMPANHAMENTO);
        
        Object produtoNome = modeloAcompanhamento.getValueAt(rowIndex, 0);
        System.out.println("Status do Produto " + produtoNome + " alterado para: " + novoStatus);
=======
        } else {
            modeloAcompanhamento.setDataVector(new Object[][]{}, colunasProdutos);
        }
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f
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
<<<<<<< HEAD
        } catch (Exception ignored) {}
=======
        } catch (Exception e) {
            // Nimbus não disponível, usa o padrão.
        }
>>>>>>> d6bdda011ce259f6ff598a30b8b0171a8cf2899f

        SwingUtilities.invokeLater(() -> new AssociacaoMainView().setVisible(true));
    }
}