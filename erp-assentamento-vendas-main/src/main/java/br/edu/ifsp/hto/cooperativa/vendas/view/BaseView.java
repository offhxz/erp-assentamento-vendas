package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifsp.hto.cooperativa.vendas.sessao.SessaoUsuario;

public abstract class BaseView extends JFrame {

    private static final Color MENU_BG = new Color(0x3C, 0x40, 0x20);
    private static final Color MENU_BTN = new Color(0xF2, 0xF2, 0xF2);

    public BaseView(String titulo) {
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(criarMenuLateral(), BorderLayout.WEST);
    }

    private JPanel criarMenuLateral() {
        // 1. Mudamos o layout principal da lateral para BorderLayout
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(180, 0));
        panelLateral.setBackground(MENU_BG);
        panelLateral.setBorder(new EmptyBorder(15, 5, 15, 5));

        // 2. Criamos um painel INTERNO apenas para agrupar os botões
        // GridLayout(0, 1) significa: "linhas ilimitadas", 1 coluna
        JPanel panelBotoes = new JPanel(new GridLayout(0, 1, 0, 8));
        panelBotoes.setBackground(MENU_BG);
        // O GridLayout interno vai respeitar a altura que definirmos no botão
        
        JButton btnHome = criarBotao("Menu Inicial");
        JButton btnCriarPedido = criarBotao("Criar Pedido");
        JButton btnCriarProjeto = criarBotao("Criar Projeto");
        JButton btnConsultarPedidos = criarBotao("Consultar Pedido");
        JButton btnConsultarProjetos = criarBotao("Consultar Projetos");

        // --- Ações (código igual ao seu) ---
        btnHome.addActionListener(e -> abrirHome());
        
        btnCriarPedido.addActionListener(e -> {
            new CriarPedidoView().setVisible(true);
            dispose();
        });

        btnCriarProjeto.addActionListener(e -> {
            new CriarProjetoView().setVisible(true);
            dispose();
        });

        btnConsultarPedidos.addActionListener(e -> {
            new ConsultaPedidosView().setVisible(true);
            dispose();
        });

        btnConsultarProjetos.addActionListener(e -> {
            new ConsultaProjetosView().setVisible(true);
            dispose();
        });

        // Regras por tipo de usuário
        String tipo = SessaoUsuario.getTipo();
        if ("produtor".equalsIgnoreCase(tipo)) {
            btnCriarProjeto.setEnabled(false);
            btnConsultarProjetos.setEnabled(false);
        }

        // 3. Adiciona os botões no painel interno
        panelBotoes.add(btnHome);
        panelBotoes.add(btnCriarPedido);
        panelBotoes.add(btnCriarProjeto);
        panelBotoes.add(btnConsultarPedidos);
        panelBotoes.add(btnConsultarProjetos);

        // 4. Adiciona o painel de botões no TOPO (NORTH) da lateral
        panelLateral.add(panelBotoes, BorderLayout.NORTH);

        return panelLateral;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(MENU_BTN);
        btn.setFocusPainted(false);
        
        // AGORA SIM: Como mudamos o layout, esse atributo vai funcionar.
        // Define a altura fixa de 40px para os botões.
        btn.setPreferredSize(new Dimension(0, 40)); 
        
        return btn;
    }

    protected JPanel criarTitleBar(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0x66, 0x66, 0x33));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lbl = new JLabel(titulo, JLabel.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));

        panel.add(lbl, BorderLayout.CENTER);
        return panel;
    }

    private void abrirHome() {
        String tipo = SessaoUsuario.getTipo();
        if ("associacao".equalsIgnoreCase(tipo)) {
            new AssociacaoMainView().setVisible(true);
        } else {
            new ProdutorMainView().setVisible(true);
        }
        dispose();
    }
}
