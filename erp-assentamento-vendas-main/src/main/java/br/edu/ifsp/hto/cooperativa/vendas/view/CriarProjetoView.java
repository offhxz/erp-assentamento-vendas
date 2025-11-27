package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ProjetoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ProjetoVO;
import br.edu.ifsp.hto.cooperativa.vendas.sessao.SessaoUsuario;

public class CriarProjetoView extends BaseView {

    private static final Color BG = new Color(0xE9, 0xE9, 0xE9);
    private static final Color BTN_SUCCESS = new Color(60, 179, 113); // Verde igual do Pedido

    private JTextField campoNome;
    private JTextField campoOrcamento;
    private JFormattedTextField campoDataFinal;

    public CriarProjetoView() {
        super("Criar Projeto");
        add(criarPainel(), BorderLayout.CENTER);
    }

    private JPanel criarPainel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        panel.add(criarTitleBar("Criar Projeto"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(25, 25, 25, 25));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Adiciona o card
        center.add(criarCardFormulario());
        center.add(Box.createVerticalStrut(25));
        center.add(criarRodape());

        // Empurra tudo para cima para não ficar centralizado verticalmente se sobrar espaço
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG);
        wrapper.add(center, BorderLayout.NORTH);

        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel criarCardFormulario() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        
        // Borda estilo "Card" (igual CriarPedido)
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        // --- 1. Nome do Projeto ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        card.add(criarLabel("Nome do Projeto:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        campoNome = new JTextField();
        estilizarCampo(campoNome);
        card.add(campoNome, gbc);

        // --- 2. Orçamento ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        card.add(criarLabel("Orçamento (R$):"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        campoOrcamento = new JTextField();
        estilizarCampo(campoOrcamento);
        card.add(campoOrcamento, gbc);

        // --- 3. Data Final ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        card.add(criarLabel("Data Final (dd/mm/aaaa):"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            campoDataFinal = new JFormattedTextField(mask);
        } catch (Exception e) {
            campoDataFinal = new JFormattedTextField();
        }
        estilizarCampo(campoDataFinal);
        card.add(campoDataFinal, gbc);

        return card;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(BG);
        rodape.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton btnSalvar = new JButton("Salvar Projeto");
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSalvar.setBackground(BTN_SUCCESS);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new Dimension(180, 45)); // Botão grande igual Pedido
        
        btnSalvar.addActionListener(e -> salvarProjeto());
        rodape.add(btnSalvar);

        return rodape;
    }

    // --- MÉTODOS DE ESTILO (Reutilizados do CriarPedido) ---

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return lbl;
    }

    private void estilizarCampo(JComponent campo) {
        campo.setPreferredSize(new Dimension(0, 30)); // Altura fixa bonita
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    // --- LÓGICA (Campos que você pediu) ---

    private void salvarProjeto() {
        try {
            if (campoNome.getText().trim().isEmpty() || campoOrcamento.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o nome e o orçamento.");
                return;
            }

            ProjetoVO projeto = new ProjetoVO();
            projeto.setNomeProjeto(campoNome.getText());
            projeto.setDataCriacao(LocalDateTime.now());
            
            String orcamentoStr = campoOrcamento.getText().replace(",", ".");
            projeto.setOrcamento(new BigDecimal(orcamentoStr));

            String dataStr = campoDataFinal.getText();
            if (dataStr != null && !dataStr.contains("_")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataStr, formatter);
                
                // Define data final às 23:59:59
                LocalDateTime dataFinal = data.atTime(23, 59, 59);
                projeto.setDataFinal(dataFinal);
            } else {
                projeto.setDataFinal(null); 
            }

            ProjetoDAO dao = new ProjetoDAO();
            String msg = dao.adicionar(projeto);

            JOptionPane.showMessageDialog(this, msg);

            if (msg != null && msg.toLowerCase().contains("sucesso")) {
                voltarParaHome();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void voltarParaHome() {
        String tipo = SessaoUsuario.getTipo();
        if ("associacao".equalsIgnoreCase(tipo)) {
            new AssociacaoMainView().setVisible(true);
        } else if ("produtor".equalsIgnoreCase(tipo)) {
            new ProdutorMainView().setVisible(true);
        }
        dispose();
    }
}