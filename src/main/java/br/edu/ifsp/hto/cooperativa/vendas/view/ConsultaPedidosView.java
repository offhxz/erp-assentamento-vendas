package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Calendar;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.DateComponentFormatter;

// Classe Mock para formatação de data (necessária para JDatePicker)
class DateLabelFormatter extends DateComponentFormatter {
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    @Override public Object stringToValue(String text) throws ParseException { return dateFormatter.parseObject(text); }
    @Override public String valueToString(Object value) throws ParseException {
        if (value != null) { Calendar cal = (Calendar) value; return dateFormatter.format(cal.getTime()); }
        return "";
    }
}


public class ConsultaPedidosView extends BaseView {

    // --- CONSTANTES DE ESTILO (Acessadas da BaseView) ---
    private static final String[] OPERADORES = {"=", ">", "<", ">=", "<="};
    private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 15); 
    private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 16); 
    
    // --- CAMPOS DE FILTRO ---
    private JComboBox<String> comboProduto;
    private JComboBox<String> comboStatus;
    private JComboBox<String> comboProjeto; 
    private JDatePickerImpl datePickerDataCadastro;
    private JComboBox<String> comboOpDataCadastro; 
    private JTextField txtValorTotal;
    private JComboBox<String> comboOpValorTotal;

    // --- DADOS MOCK (Simulação de dados existentes) ---
    private static final String[] PRODUTOS_MOCK = {"Batata", "Tomate Maduro", "Alface Crespa", "Melancia", "Cenoura", "Todos"};
    private static final String[] STATUS_MOCK = {"Pedido Feito", "Separado", "Entregue", "Cancelado", "Todos"};
    private static final String[] PROJETOS_MOCK = {
        "Projeto Raízes Fortes",
        "Projeto Hortaliças Saborosas",
        "Projeto Fruticultura Sustentável",
        "Todos"
    };
    

    public ConsultaPedidosView() {
        super("Consulta Avançada de Pedidos"); 

        add(criarContentPanel(), BorderLayout.CENTER);
    }
    
    // --- LAYOUT E ESTRUTURA ---

    private JPanel criarContentPanel() {
        JPanel content = new JPanel(new BorderLayout());
        
        content.add(criarTitleBar("Filtros de Pedidos"), BorderLayout.NORTH);
        
        content.add(criarFormPanel(), BorderLayout.CENTER);
        return content;
    }

    private JPanel criarFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CONTENT_BG); 
        form.setBorder(new EmptyBorder(25, 30, 25, 30)); 
        
        int currentRow = 0;
        
        // 1. FILTRO POR PRODUTO
        currentRow = adicionarCampoFiltro(form, currentRow, "Produto Contido:", comboProduto = new JComboBox<>(PRODUTOS_MOCK), null, false);

        // 2. FILTRO POR STATUS
        currentRow = adicionarCampoFiltro(form, currentRow, "Status:", comboStatus = new JComboBox<>(STATUS_MOCK), null, false);
        
        // 3. FILTRO POR PROJETO
        currentRow = adicionarCampoFiltro(form, currentRow, "Projeto:", comboProjeto = new JComboBox<>(PROJETOS_MOCK), null, false);
        
        // 4. FILTRO POR VALOR TOTAL (Com Operador)
        txtValorTotal = new JTextField(10);
        comboOpValorTotal = new JComboBox<>(OPERADORES);
        currentRow = adicionarCampoFiltro(form, currentRow, "Valor Total (R$):", txtValorTotal, comboOpValorTotal, true);
        
        // 5. FILTRO POR DATA CADASTRO (Com Operador)
        datePickerDataCadastro = criarDatePicker();
        comboOpDataCadastro = new JComboBox<>(OPERADORES);
        currentRow = adicionarCampoFiltro(form, currentRow, "Data Cadastro:", datePickerDataCadastro, comboOpDataCadastro, true);
        
        // 6. BARRA DE BOTÕES
        currentRow = adicionarBotaoBar(form, currentRow);
        
        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0; gbcSpacer.gridy = currentRow; 
        gbcSpacer.gridwidth = 3; 
        gbcSpacer.weighty = 1.0; 
        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        form.add(spacer, gbcSpacer);
        
        return form;
    }
    
    private int adicionarCampoFiltro(JPanel form, int row, String labelText, JComponent inputComponent, JComboBox<String> operatorCombo, boolean hasOperator) {
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 5, 12, 5);
        
        // Coluna 0: Rótulo
        JLabel label = new JLabel(labelText);
        label.setFont(FONT_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        form.add(label, gbc);

        // Coluna 1: Operador (Ajuste de tamanho)
        if (hasOperator) {
            gbc.gridx = 1; gbc.gridy = row; 
            gbc.weightx = 0; 
            gbc.ipadx = 10; 
            
            operatorCombo.setFont(FONT_INPUT); 
            
            operatorCombo.setPreferredSize(new Dimension(60, 32));
            operatorCombo.setMaximumSize(new Dimension(60, 32));
            
            form.add(operatorCombo, gbc);
            
            gbc.ipadx = 0; 
        }

        // Coluna 2 (ou 1): Componente de Entrada/Seleção (Ajuste de tamanho)
        gbc.gridx = hasOperator ? 2 : 1; 
        gbc.gridy = row;
        gbc.weightx = 1.0; 
        
        inputComponent.setFont(FONT_INPUT); 
        
        if (inputComponent instanceof JComboBox) {
             inputComponent.setPreferredSize(new Dimension(inputComponent.getPreferredSize().width, 36));
        } else if (inputComponent instanceof JTextField) {
             ((JTextField) inputComponent).setPreferredSize(new Dimension(inputComponent.getPreferredSize().width, 36));
        }
        
        form.add(inputComponent, gbc);
        
        return row + 1;
    }

    private int adicionarBotaoBar(JPanel form, int row) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bar.setOpaque(false);
        
        JButton btnLimpar = new JButton("Limpar Filtros");
        btnLimpar.setFont(btnLimpar.getFont().deriveFont(Font.PLAIN, 16f)); 
        btnLimpar.addActionListener(e -> limparFiltros());
        
        JButton btnFiltrar = new JButton("Aplicar Filtros");
        btnFiltrar.setBackground(VERDE_PADRAO);
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFont(btnFiltrar.getFont().deriveFont(Font.BOLD, 16f)); 
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        
        btnLimpar.setPreferredSize(new Dimension(btnLimpar.getPreferredSize().width, 40));
        btnFiltrar.setPreferredSize(new Dimension(btnFiltrar.getPreferredSize().width, 40));
        
        bar.add(btnLimpar);
        bar.add(btnFiltrar);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; 
        gbc.gridwidth = 3; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 0, 0, 0);
        form.add(bar, gbc);
        
        return row + 1;
    }
    
    // --- LÓGICA MOCK DE AÇÃO ---
    
    private void limparFiltros() {
        comboProduto.setSelectedIndex(PRODUTOS_MOCK.length - 1);
        comboStatus.setSelectedIndex(STATUS_MOCK.length - 1);
        comboProjeto.setSelectedIndex(PROJETOS_MOCK.length - 1); 
        if (txtValorTotal != null) txtValorTotal.setText("");
        if (comboOpValorTotal != null) comboOpValorTotal.setSelectedIndex(0);
        if (comboOpDataCadastro != null) comboOpDataCadastro.setSelectedIndex(0);
        
        Calendar cal = Calendar.getInstance();
        ((UtilDateModel)datePickerDataCadastro.getModel()).setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        JOptionPane.showMessageDialog(this, "Filtros limpos! Pronto para nova busca.");
    }

    private void aplicarFiltros() {
        String filtroProduto = (String) comboProduto.getSelectedItem();
        String filtroStatus = (String) comboStatus.getSelectedItem();
        String filtroProjeto = (String) comboProjeto.getSelectedItem(); 
        String filtroValorOp = (String) comboOpValorTotal.getSelectedItem();
        String filtroValor = txtValorTotal.getText();
        String filtroData = datePickerDataCadastro.getJFormattedTextField().getText();
        String filtroDataOp = (String) comboOpDataCadastro.getSelectedItem();

        String mensagemFiltro = String.format(
            "FILTROS APLICADOS:\nProduto: %s\nStatus: %s\nProjeto: %s\nValor: %s %s\nData Cadastro: %s %s",
            filtroProduto, filtroStatus, filtroProjeto, 
            filtroValorOp, filtroValor.isEmpty() ? "(vazio)" : "R$ " + filtroValor,
            filtroDataOp, filtroData
        );
        
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            new ProdutorMainView().setVisible(true);
        });
    }
    
    // --- MÉTODO AUXILIAR PARA CRIAR O JDATEPICKER (Reutilizado com FONT_INPUT) ---
    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Calendar cal = Calendar.getInstance();
        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);
        java.util.Properties p = new Properties(); 
        
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JTextField dateField = datePicker.getJFormattedTextField();
        dateField.setFont(FONT_INPUT); 
        dateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY.darker(), 1), 
            new EmptyBorder(6, 4, 6, 4) 
        ));
        datePicker.setBorder(null);

        return datePicker;
    }
    
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            new ConsultaPedidosView().setVisible(true);
        });
    }
}