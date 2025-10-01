package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProdutorMainView extends BaseView {

    private static final Color VERDE_PADRAO = new Color(0x6A, 0x6E, 0x2D);
    private static final Color CONTENT_BG = new Color(0xE9, 0xE9, 0xE9);
    
    // --- COMPONENTES DA TELA ---
    private JTable tabelaPedidos;
    private DefaultTableModel pedidosModel;
    private JTable tabelaDetalhes;
    private DefaultTableModel detalhesModel;
    
    // --- STATUS E CONSTANTES DA TABELA ---
    private static final String STATUS_FEITO = "Pedido Feito";
    private static final String STATUS_SEPARADO = "Separado";
    private static final String STATUS_ENTREGUE = "Entregue";
    private static final String STATUS_CANCELADO = "Cancelado";
    private static final int COL_STATUS = 5; // Índice da coluna "Status"

    // Array de status que podem ser selecionados no Pop-up
    private static final String[] STATUS_OPCOES = {
        STATUS_FEITO, 
        STATUS_SEPARADO, 
        STATUS_ENTREGUE, 
        STATUS_CANCELADO
    };
    
    // --- DADOS MOCK (Simulação) ---
    private static final Object[][] PEDIDOS_MOCK = {
        {"P001", "R$ 80.00", "01/10/2025", "05/10/2025", "Pix", STATUS_SEPARADO},
        {"P002", "R$ 45.50", "01/10/2025", "03/10/2025", "Dinheiro", STATUS_FEITO},
        {"P003", "R$ 120.00", "30/09/2025", "04/10/2025", "Cartão", STATUS_ENTREGUE}
    };
    
    private static final Object[][] DETALHES_P001_MOCK = {
        {"Tomate Maduro", "10 kg", "R$ 8.00"},
        {"Alface Crespa", "5 kg", "R$ 5.00"}
    };
    private static final Object[][] DETALHES_P002_MOCK = {
        {"Cenoura", "5 kg", "R$ 4.50"},
        {"Batata", "5 kg", "R$ 4.60"}
    };
    private static final Object[][] DETALHES_P003_MOCK = {
        {"Melancia", "30 kg", "R$ 4.00"}
    };
    

    public ProdutorMainView(){
        // 1. CHAMA O CONSTRUTOR DA BASEVIEW, definindo o título da janela.
        super("Produtor - Visão Geral de Pedidos");
        
        // 2. ADICIONA O CONTEÚDO PRINCIPAL no CENTER (área de trabalho)
        add(criarContentPanel(), BorderLayout.CENTER);
        
        // Carrega o primeiro pedido por padrão
        if (pedidosModel.getRowCount() > 0) {
             tabelaPedidos.setRowSelectionInterval(0, 0);
             updateDetalhes(0);
        }
    }

    private JPanel criarContentPanel() {
        JPanel content = new JPanel(new BorderLayout());
        
        content.add(criarTitleBar("Produtor - Pedidos Recebidos"), BorderLayout.NORTH);
        
        content.add(criarTablesPanel(), BorderLayout.CENTER);
        return content;
    }

    
    private JPanel criarTablesPanel() {
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesPanel.setBackground(CONTENT_BG); 
        tablesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        tablesPanel.add(criarSecaoPedidos());
        tablesPanel.add(Box.createVerticalStrut(30)); 
        tablesPanel.add(criarSecaoDetalhes());

        return tablesPanel;
    }

    private JComponent criarSecaoPedidos() {
        JPanel panel = new JPanel(new BorderLayout(0, 10)); 
        panel.setBackground(CONTENT_BG);
        
        JLabel titulo = new JLabel("Pedidos em Aberto/Histórico");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        panel.add(titulo, BorderLayout.NORTH);
        
        String[] colunasPedidos = {"ID Pedido", "Total (R$)", "Data Cadastro", "Data Entrega", "Pagamento", "Status"};
        
        pedidosModel = new DefaultTableModel(colunasPedidos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        for (Object[] pedido : PEDIDOS_MOCK) {
            pedidosModel.addRow(pedido);
        }

        tabelaPedidos = new JTable(pedidosModel);
        tabelaPedidos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaPedidos.setRowHeight(28);
        tabelaPedidos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listener para atualizar a tabela de detalhes
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaPedidos.getSelectedRow() != -1) {
                updateDetalhes(tabelaPedidos.getSelectedRow());
            }
        });
        
        // --- ADICIONA CLIQUE DUPLO PARA ALTERAR STATUS ---
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
        // --- FIM CLIQUE DUPLO ---
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT); 
        scrollPane.setPreferredSize(new Dimension(800, 200)); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Exibe o JOptionPane com as opções de status para alteração.
     * @param rowIndex A linha do pedido a ser alterada.
     */
    private void mostrarPopupStatus(int rowIndex) {
        String idPedido = (String) pedidosModel.getValueAt(rowIndex, 0);
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
    
    /**
     * Lógica simulada para alterar o status na tabela.
     * @param rowIndex A linha a ser atualizada.
     * @param novoStatus O novo valor de status.
     */
    private void alterarStatusPedido(int rowIndex, String novoStatus) {
        String currentStatus = (String) pedidosModel.getValueAt(rowIndex, COL_STATUS);
        
        if ((currentStatus.equals(STATUS_ENTREGUE) || currentStatus.equals(STATUS_CANCELADO)) && 
            !novoStatus.equals(currentStatus)) {
            
            JOptionPane.showMessageDialog(this, 
                "Não é possível alterar o status de um pedido já Entregue ou Cancelado.", 
                "Status Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        pedidosModel.setValueAt(novoStatus, rowIndex, COL_STATUS);
        
        String idPedido = (String) pedidosModel.getValueAt(rowIndex, 0);
        System.out.println("Status do Pedido " + idPedido + " alterado para: " + novoStatus);
    }
    
    private JComponent criarSecaoDetalhes() {
        JPanel panel = new JPanel(new BorderLayout(0, 10)); 
        panel.setBackground(CONTENT_BG);
        
        JLabel titulo = new JLabel("Detalhes do Pedido Selecionado");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        panel.add(titulo, BorderLayout.NORTH);

        String[] colunasDetalhes = {"Produto", "Quantidade", "Valor Unitário (R$)"};
        detalhesModel = new DefaultTableModel(colunasDetalhes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabelaDetalhes = new JTable(detalhesModel);
        tabelaDetalhes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaDetalhes.setRowHeight(28);
        tabelaDetalhes.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tabelaDetalhes);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(800, 250)); 
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void updateDetalhes(int rowIndex) {
        detalhesModel.setRowCount(0);

        Object[][] detalhes = null;
        String idPedido = (String) pedidosModel.getValueAt(rowIndex, 0);

        switch (idPedido) {
            case "P001":
                detalhes = DETALHES_P001_MOCK;
                break;
            case "P002":
                detalhes = DETALHES_P002_MOCK;
                break;
            case "P003":
                detalhes = DETALHES_P003_MOCK;
                break;
            default:
                break;
        }

        if (detalhes != null) {
            for (Object[] item : detalhes) {
                detalhesModel.addRow(item);
            }
        }
    }

    public static void main(String[] args){
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
            new ProdutorMainView().setVisible(true);
        });
    }
}