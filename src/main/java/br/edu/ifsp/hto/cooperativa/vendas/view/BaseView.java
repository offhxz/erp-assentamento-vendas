package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// Classe base que todas as outras Views irão estender
public class BaseView extends JFrame {

    // --- CONSTANTES DE ESTILO (Acessíveis a classes filhas) ---
    protected static final Color VERDE_PADRAO = new Color(0x6A, 0x6E, 0x2D);
    protected static final Color LATERAL_BG = new Color(0x2F, 0x33, 0x20); 
    protected static final Color CONTENT_BG = new Color(0xE9, 0xE9, 0xE9);
    
    // Lista de botões que estarão sempre visíveis
    private static final String BOTAO_MENU = "Menu Inicial";
    private static final String BOTAO_CRIAR_PEDIDO = "Criar Pedido";
    private static final String BOTAO_CRIAR_PROJETO = "Criar Projeto";
    private static final String BOTAO_CONSULTAR_PEDIDO = "Consultar Pedido";
    
    // --- Interface para Navegação (Para simular o redirecionamento de tela) ---
    private final transient NavigationHandler navigationHandler;

    /**
     * Construtor da View Base. Configura o layout principal e a barra lateral.
     */
    public BaseView(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Inicializa o handler de navegação (simulação)
        this.navigationHandler = new DefaultNavigationHandler(this);

        // Adiciona a barra lateral fixa
        add(criarPainelLateral(), BorderLayout.WEST);
    }
    
    // --- LÓGICA DE NAVEGAÇÃO ---

    private void navigateTo(String screenName) {
        this.navigationHandler.navigateTo(screenName);
    }
    
    // Interface para manipular a navegação (separação de responsabilidade)
    private interface NavigationHandler {
        void navigateTo(String screenName);
    }
    
    // Implementação Padrão do Navegador (Realiza a troca de tela)
    private static class DefaultNavigationHandler implements NavigationHandler {
        private final JFrame currentFrame;

        public DefaultNavigationHandler(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

        @Override
        public void navigateTo(String screenName) {
            
            // Fecha a janela atual, exceto se for o Menu Inicial e esta já for a ProdutorMainView
            if (!screenName.equals(BOTAO_MENU) || !(currentFrame instanceof ProdutorMainView)) {
                 this.currentFrame.dispose();
            }

            // Simula a abertura da tela correta
            SwingUtilities.invokeLater(() -> {
                try {
                    JFrame nextScreen = null;
                    
                    switch (screenName) {
                        case BOTAO_CRIAR_PEDIDO: 
                            nextScreen = new CriarPedidoView(); 
                            break;

                        case BOTAO_CRIAR_PROJETO: 
                            nextScreen = new CriarProjetoView(); 
                            break;
                            
                        case BOTAO_CONSULTAR_PEDIDO:
                            nextScreen = new ConsultaPedidosView();
                            break;
                            
                        case BOTAO_MENU:
                            nextScreen = new ProdutorMainView();
                            break;
                            
                        default:
                            System.err.println("Navegação não mapeada: " + screenName);
                            return;
                    }
                    
                    // Exibe a próxima tela, se for diferente da atual
                    if (nextScreen != null) {
                         nextScreen.setVisible(true);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(currentFrame, 
                        "Erro ao carregar a tela " + screenName + ": " + ex.getMessage(), 
                        "Erro de Navegação", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
        }
    }


    // --- CRIAÇÃO DA BARRA LATERAL (FIXA) ---

    private JPanel criarPainelLateral() {
        JPanel side = new JPanel(new BorderLayout());
        side.setBackground(LATERAL_BG);
        side.setPreferredSize(new Dimension(240, 0));
        side.setBorder(new EmptyBorder(20, 12, 20, 12));

        // Container para os botões de navegação
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Adiciona os botões: Menu, Criar Pedido, Consultar Pedido
        buttonPanel.add(criarBotaoLateral(BOTAO_MENU));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonPanel.add(criarBotaoLateral(BOTAO_CRIAR_PEDIDO));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonPanel.add(criarBotaoLateral(BOTAO_CRIAR_PROJETO));
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonPanel.add(criarBotaoLateral(BOTAO_CONSULTAR_PEDIDO));
        

        side.add(buttonPanel, BorderLayout.NORTH);

        // Área da Logo (Mock)
        JLabel lblLogo = new JLabel("Logo");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        // Centraliza a Logo na parte inferior
        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(lblLogo);
        side.add(centerWrap, BorderLayout.SOUTH);
        
        return side;
    }
    
    // Método auxiliar para padronizar o estilo dos botões laterais E ADICIONAR AÇÃO
    protected JButton criarBotaoLateral(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.DARK_GRAY);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(10, 12, 10, 12));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        
        // Adiciona a lógica de navegação ao botão
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateTo(texto);
            }
        });
        
        return btn;
    }

    // Método auxiliar para criar a barra de título (reutilizado pelas classes filhas)
    protected JComponent criarTitleBar(String tituloTexto) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(VERDE_PADRAO); 
        bar.setBorder(new EmptyBorder(12, 24, 12, 24));
        JLabel titulo = new JLabel(tituloTexto, SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        bar.add(titulo, BorderLayout.CENTER);
        return bar;
    }
    
    // Método auxiliar para padronizar o header das seções de formulário (reutilizado)
    protected JPanel criarHeader(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(VERDE_PADRAO); 
        p.setBorder(new EmptyBorder(8, 12, 8, 12));
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 14f));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new ProdutorMainView().setVisible(true));
    }
}