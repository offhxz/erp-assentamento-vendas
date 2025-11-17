package br.edu.ifsp.hto.cooperativa.vendas.view;

import br.edu.ifsp.hto.cooperativa.vendas.controller.ProjetoController;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.DateComponentFormatter;

// --- FORMATTER DE DATA (MANTIDO IGUAL) ---
class DateLabelFormatter extends DateComponentFormatter {
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    @Override
    public Object stringToValue(String text) throws ParseException { return dateFormatter.parseObject(text); }
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) { Calendar cal = (Calendar) value; return dateFormatter.format(cal.getTime()); }
        return "";
    }
}

public class CriarProjetoView extends BaseView {

    private static final Color VERDE_PADRAO = new Color(0x6A, 0x6E, 0x2D);
    private static final Color FORM_BG = new Color(0xE9, 0xE9, 0xE9);
    private static final Color CAMPO_BG = Color.WHITE;
    
    // --- CAMPOS DE INPUT (REMOVIDOS EMPRESA E CNPJ) ---
    private JTextField txtNomeProjeto;
    private JDatePickerImpl datePickerInicio;
    private JDatePickerImpl datePickerFim;
    private JTextField txtOrcamentoTotal;
    
    public CriarProjetoView(){
        super("Criar Pedido"); 
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

        // --- SEÇÃO DADOS BÁSICOS (AGORA SÓ TEM O NOME) ---
        currentRow = adicionarSecaoDadosBasicos(formCard, currentRow);
        
        // --- SEÇÃO DATAS E VALORES ---
        currentRow = adicionarSecaoDatasValores(formCard, currentRow);
        
        // --- BOTÃO FINALIZAR ---
        currentRow = adicionarBotaoFinalizar(formCard, currentRow); 

        GridBagConstraints gbcSpacer = new GridBagConstraints();
        gbcSpacer.gridx = 0; gbcSpacer.gridy = currentRow; 
        gbcSpacer.weighty = 1.0; 
        formCard.add(new JPanel(){{setOpaque(false);}}, gbcSpacer);

        return formCard;
    }
    
    private JComponent criarInputSimples(JTextField field, String labelText) {
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
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    // --- SEÇÃO DADOS BÁSICOS (SIMPLIFICADA) ---
    private int adicionarSecaoDadosBasicos(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 8, 6, 8); 
        
        formCard.add(criarHeader("Dados do Projeto"), gbc);
        
        // 1. Nome do Projeto
        txtNomeProjeto = new JTextField(20);
        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 20, 8); // Mais espaço embaixo pois removemos os outros campos
        formCard.add(criarInputSimples(txtNomeProjeto, "Nome do Projeto *"), gbc);

        // REMOVIDO: Empresa e CNPJ
        
        return ++startRow;
    }
    
    // --- SEÇÃO DATAS E VALORES (MANTIDA) ---
    private int adicionarSecaoDatasValores(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 6, 8); 
        
        formCard.add(criarHeader("Datas e Orçamento"), gbc);
        
        JPanel painelDatas = new JPanel(new GridBagLayout());
        painelDatas.setOpaque(false);
        GridBagConstraints gbcDatas = new GridBagConstraints();
        gbcDatas.fill = GridBagConstraints.HORIZONTAL;
        gbcDatas.insets = new Insets(0, 0, 0, 8);
        
        // Data Início
        JPanel wrapInicio = new JPanel(new BorderLayout());
        wrapInicio.setOpaque(false);
        wrapInicio.add(new JLabel("Início *") {{ 
            setFont(new Font("SansSerif", Font.BOLD, 12)); setForeground(Color.GRAY.darker()); 
        }}, BorderLayout.NORTH);
        datePickerInicio = criarDatePicker();
        wrapInicio.add(datePickerInicio, BorderLayout.CENTER);
        gbcDatas.gridx = 0; gbcDatas.weightx = 0.5;
        painelDatas.add(wrapInicio, gbcDatas);

        // Data Fim
        JPanel wrapFim = new JPanel(new BorderLayout());
        wrapFim.setOpaque(false);
        wrapFim.add(new JLabel("Fim *") {{ 
            setFont(new Font("SansSerif", Font.BOLD, 12)); setForeground(Color.GRAY.darker()); 
        }}, BorderLayout.NORTH);
        datePickerFim = criarDatePicker();
        wrapFim.add(datePickerFim, BorderLayout.CENTER);
        gbcDatas.gridx = 1; gbcDatas.weightx = 0.5; gbcDatas.insets = new Insets(0, 0, 0, 0); 
        painelDatas.add(wrapFim, gbcDatas);

        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 8, 8);
        formCard.add(painelDatas, gbc);
        
        // Orçamento
        txtOrcamentoTotal = new JTextField(15);
        gbc.gridy = ++startRow;
        gbc.insets = new Insets(0, 8, 20, 8);
        formCard.add(criarInputSimples(txtOrcamentoTotal, "Orçamento Total (R$) *"), gbc);
        
        return ++startRow;
    }
    
    private JDatePickerImpl criarDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Calendar cal = Calendar.getInstance();
        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        // Estilização básica do datepicker...
        return datePicker;
    }

    // --- BOTÃO FINALIZAR (ATUALIZADO) ---
    private int adicionarBotaoFinalizar(JPanel formCard, int startRow) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = startRow; 
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 12, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton btnFinalizarProjeto = new JButton("Salvar Projeto");
        btnFinalizarProjeto.setBackground(VERDE_PADRAO); 
        btnFinalizarProjeto.setForeground(Color.WHITE);
        btnFinalizarProjeto.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // AÇÃO DO BOTÃO ATUALIZADA
        btnFinalizarProjeto.addActionListener(e -> {
            ProjetoController controller = new ProjetoController();

            String nome = txtNomeProjeto.getText();
            String orcamento = txtOrcamentoTotal.getText();
            Date dataInicio = (Date) datePickerInicio.getModel().getValue();
            Date dataFim = (Date) datePickerFim.getModel().getValue();

            // Agora passa apenas os 4 parâmetros que o Controller espera
            controller.salvarProjeto(nome, dataInicio, dataFim, orcamento);
        });

        formCard.add(btnFinalizarProjeto, gbc);
        return ++startRow;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            new CriarProjetoView().setVisible(true);
        });
    }
}