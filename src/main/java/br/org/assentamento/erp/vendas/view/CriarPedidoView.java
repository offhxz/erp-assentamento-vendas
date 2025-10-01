package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.DateComponentFormatter;

// Classe Mock (simulada) para formatar a data (Necessária para o JDatePicker)
class DateLabelFormatter extends DateComponentFormatter {
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}

public class CriarPedidoView extends BaseView {

    private static final Color VERDE_PADRAO = new Color(0x6A, 0x6E, 0x2D);
    private static final Color VERMELHO_ESCURO = new Color(0xCC, 0x00, 0x00); 

    private JComboBox<String> comboProjeto; 
    private JComboBox<String> comboProdutor; 
    private JComboBox<String> comboProduto; 
    private JTextField txtQuantidade; 
    private JLabel lblValorUnitario; 
    private JLabel lblTotalItem; 
    private DefaultTableModel produtosTableModel; 
    private JTable tabelaProdutos; 

    
    // Dados Mock para Produtos e Preços (R$ per kg)
    private static final Map<String, Double> PRODUTOS_PRECOS = new HashMap<>();
    static {
        PRODUTOS_PRECOS.put("Alface Crespa", 5.50);
        PRODUTOS_PRECOS.put("Tomate Maduro", 8.90);
        PRODUTOS_PRECOS.put("Melancia", 3.20);
        PRODUTOS_PRECOS.put("Cenoura", 4.80);
        PRODUTOS_PRECOS.put("Batata", 6.00);
    }

    // Dados Mock para Projetos
    private static final String[] PROJETOS_MOCK = {
        "Projeto Raízes Fortes",
        "Projeto Hortaliças Saborosas",
        "Projeto Fruticultura Sustentável"
    };


    public CriarPedidoView(){
        // 1. CHAMA O CONSTRUTOR DA BASEVIEW
        super("Criar Pedido"); 
        
        // 2. ADICIONA O CONTEÚDO PRINCIPAL 
        add(criarContentPanel(), BorderLayout.CENTER);
    }

    private JPanel criarContentPanel() {
        // Painel central (Barra de Título + Formulário)
        JPanel content = new JPanel(new BorderLayout());
        
        // Usa o método criarTitleBar() da BaseView
        content.add(criarTitleBar("Criar Pedido"), BorderLayout.NORTH); 
        
        content.add(criarFormCard(), BorderLayout.CENTER);
        return content;
    }


    private JPanel criarFormCard(){
        // Painel do formulário usando GridBagLayout para alinhamento
        JPanel formCard = new JPanel(new GridBagLayout());
        // Usa CONTENT_BG da BaseView (presumindo que seja E9E9E9)
        formCard.setBackground(CONTENT_BG); 
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // Adiciona a nova seção de Projeto (posicionada antes dos produtos)
        adicionarSecaoProjeto(formCard);
        
        // Adiciona as seções do formulário em sequência
        adicionarSecaoProduto(formCard);
        adicionarSecaoEntrega(formCard);
        adicionarSecaoPagamento(formCard); 
        adicionarBotaoFinalizar(formCard); 

        // Espaçador para empurrar o conteúdo para o topo
        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0;
        gbcSpacer.gridy = 10; 
        gbcSpacer.weighty = 1.0; 
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        formCard.add(spacer, gbcSpacer);

        return formCard;
    }
    
    // --- MÉTODOS DE LÓGICA DE PRODUTOS/PREÇOS ---
    private void updateProductPrice() {
        String selectedProduct = (String) comboProduto.getSelectedItem();
        Double price = PRODUTOS_PRECOS.getOrDefault(selectedProduct, 0.0);
        lblValorUnitario.setText(String.format("R$ %.2f", price));
    }

    private void updateGrandTotal() {
        double grandTotal = 0.0;
        for (int i = 0; i < produtosTableModel.getRowCount(); i++) {
            String totalStr = produtosTableModel.getValueAt(i, 3).toString().replace("R$ ", "").replace(',', '.');
            try {
                grandTotal += Double.parseDouble(totalStr);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao calcular total na linha " + i + ": " + e.getMessage());
            }
        }
        lblTotalItem.setText(String.format("R$ %.2f", grandTotal));
    }
    
    private void addProdutoToTable() {
        String produto = (String) comboProduto.getSelectedItem();
        String qtdStr = txtQuantidade.getText().trim().replace(',', '.');

        try {
            double quantity = Double.parseDouble(qtdStr);
            if (produto != null && quantity > 0) {
                double unitPrice = PRODUTOS_PRECOS.getOrDefault(produto, 0.0);
                double total = quantity * unitPrice;
                
                produtosTableModel.addRow(new Object[]{
                    produto, 
                    String.format("%.2f", quantity), 
                    String.format("R$ %.2f", unitPrice), 
                    String.format("R$ %.2f", total),
                    "X" 
                });
                
                txtQuantidade.setText("");
                comboProduto.setSelectedIndex(0);
                updateProductPrice();
                updateGrandTotal();
            } else {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser um número maior que zero!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Insira uma quantidade válida!");
        }
    }
    // --- FIM DOS MÉTODOS DE LÓGICA DE PRODUTOS/PREÇOS ---


    // --- ADICIONAR SEÇÃO PROJETO ---
    private void adicionarSecaoProjeto(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 6, 8); 

        formCard.add(criarHeader("Selecione o Projeto"), gbc);

        comboProjeto = new JComboBox<>(PROJETOS_MOCK);
        comboProjeto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboProjeto.setSelectedIndex(0);

        gbc.gridy = 1; 
        gbc.insets = new Insets(0, 8, 20, 8); 

        formCard.add(comboProjeto, gbc);
    }
    // --- FIM ADICIONAR SEÇÃO PROJETO ---


    // --- ADICIONAR SEÇÃO PRODUTO (JTable simplificada) ---
    private void adicionarSecaoProduto(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 2; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 8, 5, 8); 
        
        formCard.add(criarHeader("Produtos"), gbc);
        
        // Painel de Input de Produto/Quantidade
        JPanel painelProdutoInput = new JPanel(new GridBagLayout());
        painelProdutoInput.setBackground(CONTENT_BG); // Usa CONTENT_BG da BaseView
        painelProdutoInput.setBorder(new EmptyBorder(10, 0, 0, 0)); 
        
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(0, 5, 0, 5); gbcInput.anchor = GridBagConstraints.WEST;
        
        // 1. Seleção Produto
        comboProduto = new JComboBox<>(PRODUTOS_PRECOS.keySet().toArray(new String[0]));
        comboProduto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcInput.gridx = 0; gbcInput.gridy = 0; gbcInput.weightx = 0.5;
        gbcInput.fill = GridBagConstraints.HORIZONTAL; painelProdutoInput.add(comboProduto, gbcInput);
        
        // 2. Preço Unitário
        lblValorUnitario = new JLabel("R$ 0,00", SwingConstants.RIGHT); 
        lblValorUnitario.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbcInput.gridx = 1; gbcInput.gridy = 0; gbcInput.weightx = 0.15;
        gbcInput.fill = GridBagConstraints.NONE; painelProdutoInput.add(lblValorUnitario, gbcInput);
        
        // 3. Quantidade Texto
        txtQuantidade = new JTextField(5);
        txtQuantidade.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtQuantidade.setHorizontalAlignment(JTextField.RIGHT);
        gbcInput.gridx = 2; gbcInput.gridy = 0; gbcInput.weightx = 0.15;
        gbcInput.fill = GridBagConstraints.HORIZONTAL; painelProdutoInput.add(txtQuantidade, gbcInput);
        
        // 4. Quantidade Label
        JLabel lblQtdEmKg = new JLabel("Quantidade em kg", SwingConstants.CENTER);
        lblQtdEmKg.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblQtdEmKg.setForeground(Color.GRAY);
        gbcInput.gridx = 2; gbcInput.gridy = 1;
        gbcInput.fill = GridBagConstraints.HORIZONTAL; painelProdutoInput.add(lblQtdEmKg, gbcInput);
        
        // 5. Botão Add
        JButton btnAdd = new JButton("+");
        btnAdd.setBackground(VERDE_PADRAO); btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new Dimension(40, 30));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gbcInput.gridx = 3; gbcInput.gridy = 0; gbcInput.weightx = 0.05;
        gbcInput.fill = GridBagConstraints.NONE; gbcInput.anchor = GridBagConstraints.EAST;
        gbcInput.insets = new Insets(0, 5, 0, 0); painelProdutoInput.add(btnAdd, gbcInput);
        
        gbc.gridy = 3; gbc.insets = new Insets(0, 8, 4, 8); 
        formCard.add(painelProdutoInput, gbc);

        // --- Configuração da Tabela (JTable) ---
        String[] colunas = {"Produto", "Quantidade (kg)", "Valor Unit. (R$)", "Total (R$)", "Ação"}; 
        
        produtosTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) return JButton.class;
                return String.class;
            }
        };

        tabelaProdutos = new JTable(produtosTableModel);
        tabelaProdutos.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaProdutos.setRowHeight(24);
        tabelaProdutos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Define o Renderer (Aparência do Botão) para a coluna 'Ação'
        tabelaProdutos.getColumn("Ação").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton button = new JButton("Remover");
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
            button.setBackground(VERMELHO_ESCURO); 
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            return button;
        });

        tabelaProdutos.getColumnModel().getColumn(4).setMaxWidth(80); 
        tabelaProdutos.getColumnModel().getColumn(4).setMinWidth(80);
        
        // MouseListener para detectar o clique na coluna "Ação"
        tabelaProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tabelaProdutos.columnAtPoint(e.getPoint());
                int row = tabelaProdutos.rowAtPoint(e.getPoint());
                
                if (column == tabelaProdutos.getColumnModel().getColumnIndex("Ação") && row >= 0) {
                    produtosTableModel.removeRow(row);
                    updateGrandTotal();
                }
            }
        });

        // Painel para a Tabela e Total
        JPanel painelTabelaTotal = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        
        scrollPane.setPreferredSize(new Dimension(500, 150)); 
        painelTabelaTotal.add(scrollPane, BorderLayout.CENTER);
        
        // Painel Total (R$ 0,00)
        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotal.setBackground(CONTENT_BG); // Usa CONTENT_BG da BaseView
        JLabel lblTotalCompra = new JLabel("Total da compra: ");
        lblTotalCompra.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotalCompra.setForeground(VERDE_PADRAO); 
        
        lblTotalItem = new JLabel("R$ 0,00"); 
        lblTotalItem.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotalItem.setForeground(Color.RED);
        
        painelTotal.add(lblTotalCompra);
        painelTotal.add(lblTotalItem);
        
        painelTabelaTotal.add(painelTotal, BorderLayout.SOUTH);
        
        gbc.gridy = 4; 
        gbc.insets = new Insets(0, 8, 12, 8); 
        formCard.add(painelTabelaTotal, gbc);
        
        // --- Listeners ---
        comboProduto.addActionListener(e -> updateProductPrice());
        txtQuantidade.addActionListener(e -> addProdutoToTable()); 
        btnAdd.addActionListener(e -> addProdutoToTable());
        
        updateProductPrice();
    }
    // --- FIM ADICIONAR SEÇÃO PRODUTO ---


    // --- ADICIONAR SEÇÃO ENTREGA (Com espaçamento ajustado) ---
    private void adicionarSecaoEntrega(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 5; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 8, 6, 8); 

        // Usa o método criarHeader da BaseView
        formCard.add(criarHeader("Selecione a data de entrega"), gbc); 

        JDatePickerImpl datePicker = criarDatePicker();
        
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 8, 20, 8); 

        formCard.add(datePicker, gbc);
    }
    // --- FIM ADICIONAR SEÇÃO ENTREGA ---
    
    // --- ADICIONAR SEÇÃO PAGAMENTO (Com espaçamento ajustado) ---
    private void adicionarSecaoPagamento(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 7; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 6, 8);

        // Usa o método criarHeader da BaseView
        formCard.add(criarHeader("Selecione a forma de pagamento"), gbc);

        String[] opcoesPagamento = {"Dinheiro", "Pix", "Cartão"};
        JComboBox<String> comboPagamento = new JComboBox<>(opcoesPagamento);
        comboPagamento.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboPagamento.setSelectedIndex(0);

        gbc.gridy = 8; 
        gbc.insets = new Insets(0, 8, 20, 8); 

        formCard.add(comboPagamento, gbc);
    }
    // --- FIM ADICIONAR SEÇÃO PAGAMENTO ---


    // --- ADICIONAR BOTÃO FINALIZAR ---
    private void adicionarBotaoFinalizar(JPanel formCard) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 9; 
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 12, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton btnFinalizarPedido = new JButton("Finalizar Pedido");
        btnFinalizarPedido.setBackground(VERDE_PADRAO); 
        btnFinalizarPedido.setForeground(Color.WHITE);
        btnFinalizarPedido.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnFinalizarPedido.setFocusPainted(false);
        btnFinalizarPedido.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        formCard.add(btnFinalizarPedido, gbc);
    }
    // --- FIM ADICIONAR BOTÃO FINALIZAR ---
    
    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        
        Calendar cal = Calendar.getInstance();
        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);

        java.util.Properties p = new java.util.Properties();
        
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setPreferredSize(new Dimension(300, 30));
        
        return datePicker;
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
            new CriarPedidoView().setVisible(true);
        });
    }
}