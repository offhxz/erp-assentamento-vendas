package br.org.assentamento.erp.vendas.view;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

// Necessário para o JDatePicker (Certifique-se de que a biblioteca JDatePicker está no seu classpath)
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

public class CriarProjetoView extends BaseView {

    // --- COR CONSTANTE DEFINIDA GLOBALMENTE ---
    private static final Color VERDE_PADRAO = new Color(0x6A, 0x6E, 0x2D);
    private static final Color LATERAL_BG = new Color(0x2F, 0x33, 0x20); 
    private static final Color FORM_BG = new Color(0xE9, 0xE9, 0xE9);
    private static final Color CAMPO_BG = Color.WHITE;
    
    // --- CAMPOS DE INPUT OBRIGATÓRIOS ---
    private JTextField txtNomeProjeto;
    private JTextField txtNomeEmpresaOrgao;
    private JFormattedTextField txtCNPJ;
    private JDatePickerImpl datePickerInicio;
    private JDatePickerImpl datePickerFim;
    private JTextField txtOrcamentoTotal;
    
    // Construtor Simples
    public CriarProjetoView(){
        // 1. CHAMA O CONSTRUTOR DA BASEVIEW
        super("Criar Pedido"); 
        
        // 2. ADICIONA O CONTEÚDO PRINCIPAL 
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
        bar.setBackground(VERDE_PADRAO); 
        bar.setBorder(new EmptyBorder(12, 24, 12, 24));
        JLabel titulo = new JLabel("Criar Novo Projeto", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        bar.add(titulo, BorderLayout.CENTER);
        return bar;
    }

    private JPanel criarFormCard(){
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(FORM_BG);
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        int currentRow = 0; 

        // --- SEÇÃO DADOS BÁSICOS ---
        currentRow = adicionarSecaoDadosBasicos(formCard, currentRow);
        
        // --- SEÇÃO DATAS E VALORES ---
        currentRow = adicionarSecaoDatasValores(formCard, currentRow);
        
        // --- BOTÃO FINALIZAR ---
        currentRow = adicionarBotaoFinalizar(formCard, currentRow); 

        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0;
        gbcSpacer.gridy = currentRow; 
        gbcSpacer.weighty = 1.0; 
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        formCard.add(spacer, gbcSpacer);

        return formCard;
    }
    
    // --- FUNÇÃO PARA CRIAR INPUT COM ESTILO SIMPLES ---
    private JComponent criarInputSimples(JTextField field, String labelText, boolean isMasked) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(Color.GRAY.darker());
        panel.add(label, BorderLayout.NORTH);
        
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(CAMPO_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY.darker(), 1), 
            new EmptyBorder(6, 8, 6, 8)
        ));
        
        if(isMasked) {
             JFormattedTextField maskedField = (JFormattedTextField) field;
             panel.add(maskedField, BorderLayout.CENTER);
        } else {
             panel.add(field, BorderLayout.CENTER);
        }

        return panel;
    }
    
    /** Adiciona a seção de Nome, Empresa/Órgão e CNPJ. */
    private int adicionarSecaoDadosBasicos(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 8, 6, 8); 
        
        formCard.add(criarHeader("Dados Básicos do Projeto"), gbc);
        
        // 1. Nome do Projeto
        txtNomeProjeto = new JTextField(20);
        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 8, 8);
        formCard.add(criarInputSimples(txtNomeProjeto, "Nome do Projeto *", false), gbc);

        // 2. Nome da Empresa/Órgão Público
        txtNomeEmpresaOrgao = new JTextField(20);
        gbc.gridy = ++startRow;
        formCard.add(criarInputSimples(txtNomeEmpresaOrgao, "Empresa ou Órgão Público *", false), gbc);

        // 3. CNPJ (com máscara)
        try {
            MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
            txtCNPJ = new JFormattedTextField(mf);
        } catch (ParseException e) {
            txtCNPJ = new JFormattedTextField();
        }
        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 20, 8);
        formCard.add(criarInputSimples(txtCNPJ, "CNPJ *", true), gbc);
        
        return ++startRow;
    }
    
    /** Adiciona a seção de Datas e Orçamento. */
    private int adicionarSecaoDatasValores(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 6, 8); 
        
        formCard.add(criarHeader("Datas e Orçamento"), gbc);
        
        // Container para alinhamento de 2 campos na mesma linha (Data Início/Fim)
        JPanel painelDatas = new JPanel(new GridBagLayout());
        painelDatas.setOpaque(false);
        GridBagConstraints gbcDatas = new GridBagConstraints();
        gbcDatas.fill = GridBagConstraints.HORIZONTAL;
        gbcDatas.insets = new Insets(0, 0, 0, 8);
        
        // 1. Data Início (JDatePicker)
        JPanel wrapInicio = new JPanel(new BorderLayout());
        wrapInicio.setOpaque(false);
        wrapInicio.add(new JLabel("Início *") {{ 
            setFont(new Font("SansSerif", Font.BOLD, 12)); 
            setForeground(Color.GRAY.darker()); 
        }}, BorderLayout.NORTH);
        datePickerInicio = criarDatePicker();
        wrapInicio.add(datePickerInicio, BorderLayout.CENTER);
        
        gbcDatas.gridx = 0; gbcDatas.weightx = 0.5;
        painelDatas.add(wrapInicio, gbcDatas);

        // 2. Data Fim (JDatePicker)
        JPanel wrapFim = new JPanel(new BorderLayout());
        wrapFim.setOpaque(false);
        wrapFim.add(new JLabel("Fim *") {{ 
            setFont(new Font("SansSerif", Font.BOLD, 12)); 
            setForeground(Color.GRAY.darker()); 
        }}, BorderLayout.NORTH);
        datePickerFim = criarDatePicker();
        wrapFim.add(datePickerFim, BorderLayout.CENTER);
        
        gbcDatas.gridx = 1; gbcDatas.weightx = 0.5;
        gbcDatas.insets = new Insets(0, 0, 0, 0); 
        painelDatas.add(wrapFim, gbcDatas);

        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 8, 8);
        formCard.add(painelDatas, gbc);
        
        // 3. Orçamento Total
        txtOrcamentoTotal = new JTextField(15);
        
        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 20, 8);
        formCard.add(criarInputSimples(txtOrcamentoTotal, "Orçamento Total (R$) *", false), gbc);
        
        return ++startRow;
    }
    
    // --- MÉTODO AUXILIAR PARA CRIAR O JDATEPICKER ---
    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        
        Calendar cal = Calendar.getInstance();
        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);

        java.util.Properties p = new Properties(); 
        
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Estilo do campo de texto do JDatePicker
        JTextField dateField = datePicker.getJFormattedTextField();
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateField.setBackground(CAMPO_BG);
        dateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY.darker(), 1), 
            new EmptyBorder(6, 8, 6, 8)
        ));
        
        JButton calendarButton = (JButton) datePicker.getComponent(1);
        calendarButton.setFocusPainted(false);
        datePicker.setBorder(null);

        return datePicker;
    }


    /** Adiciona o botão de Finalizar. */
    private int adicionarBotaoFinalizar(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 12, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel faixa = new JPanel(new BorderLayout());
        faixa.setOpaque(false);
        
        JButton btnFinalizarProjeto = new JButton("Criar Projeto");
        btnFinalizarProjeto.setBackground(VERDE_PADRAO); 
        btnFinalizarProjeto.setForeground(Color.WHITE);
        btnFinalizarProjeto.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnFinalizarProjeto.setFocusPainted(false);
        btnFinalizarProjeto.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        btnFinalizarProjeto.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Ação simulada: Projeto Criado!\nDatas selecionadas: " + datePickerInicio.getJFormattedTextField().getText() + " a " + datePickerFim.getJFormattedTextField().getText());
        });

        faixa.add(btnFinalizarProjeto, BorderLayout.CENTER);
        formCard.add(faixa, gbc);
        return ++startRow;
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
            new CriarProjetoView().setVisible(true);
        });
    }
}