package br.edu.ifsp.hto.cooperativa.vendas.view;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.AssociadoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.PedidoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ProdutoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ProjetoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ItemPedidoVO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.PedidoVO;
import br.edu.ifsp.hto.cooperativa.vendas.sessao.SessaoUsuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CriarPedidoView extends BaseView {

    private static final Color BG = new Color(0xE9, 0xE9, 0xE9);

    private JComboBox<Object> comboAssociado;
    private JComboBox<Object> comboProduto;
    private JComboBox<String> comboProjeto;

    private JTextField campoQuantidade;
    private JTextField campoValorUnitario;

    private JTable tabelaItens;
    private DefaultTableModel itensModel;

    private final List<ItemPedidoVO> itensPedido = new ArrayList<>();
    private BigDecimal totalPedido = BigDecimal.ZERO;

    public CriarPedidoView() {
        super("Criar Pedido");
        add(criarPainel(), BorderLayout.CENTER);

        // Carregamentos iniciais
        if ("associacao".equalsIgnoreCase(SessaoUsuario.getTipo())) {
            carregarAssociados();
            carregarProjetos();
        } else {
            configurarProdutor();
        }
        
        carregarProdutos(); // Carrega os produtos no combo

        // --- AQUI ESTÁ A MÁGICA DO VALOR DINÂMICO ---
        // Sempre que mudar o item selecionado no comboProduto, roda o método atualizarPreco
        comboProduto.addActionListener(e -> atualizarPreco());
    }

    private JPanel criarPainel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        panel.add(criarTitleBar("Criar Pedido"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(25, 25, 25, 25));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Adiciona os cards na vertical
        center.add(criarCardFormulario());
        center.add(Box.createVerticalStrut(25)); // Espaço
        center.add(criarCardTabela());
        center.add(Box.createVerticalStrut(25)); // Espaço
        center.add(criarRodape());

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // --- AQUI ESTÁ A MÁGICA DO POSICIONAMENTO (GridBagLayout) ---
    private JPanel criarCardFormulario() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Configurações de posicionamento
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10); // Margens (Cima, Esq, Baixo, Dir)
        gbc.fill = GridBagConstraints.HORIZONTAL; // Esticar horizontalmente

        // --- Título ---
        JLabel titulo = new JLabel("Dados do Pedido");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.gridwidth = 4; // Título ocupa 4 colunas
        gbc.insets = new Insets(0, 10, 20, 10);
        card.add(titulo, gbc);

        // Reseta configurações para os campos
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridwidth = 1;

        // --- LINHA 1: Associado e Projeto ---
        
        // Coluna 0: Label Associado
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; 
        card.add(new JLabel("Associado:"), gbc);

        // Coluna 1: Combo Associado
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5; // Cresce 50%
        comboAssociado = new JComboBox<>();
        card.add(comboAssociado, gbc);

        // Coluna 2: Label Projeto
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        card.add(new JLabel("Projeto:"), gbc);

        // Coluna 3: Combo Projeto
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.5; // Cresce 50%
        comboProjeto = new JComboBox<>();
        card.add(comboProjeto, gbc);

        // --- LINHA 2: Produto (Ocupa a linha toda) ---
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        card.add(new JLabel("Produto:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; 
        gbc.gridwidth = 3; // Estica pelas colunas 1, 2 e 3
        gbc.weightx = 1.0;
        comboProduto = new JComboBox<>();
        card.add(comboProduto, gbc);
        
        gbc.gridwidth = 1; // Reseta largura

        // --- LINHA 3: Quantidade e Valor ---

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        card.add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.5;
        campoQuantidade = new JTextField();
        card.add(campoQuantidade, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0;
        card.add(new JLabel("Valor Unitário (R$):"), gbc);

        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 0.5;
        campoValorUnitario = new JTextField();

        campoValorUnitario.setEditable(false); // Trava a edição manual
        campoValorUnitario.setBackground(new Color(230, 230, 230));
        card.add(campoValorUnitario, gbc);

        // --- LINHA 4: Botões ---
        
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.setBackground(Color.WHITE);
        
        JButton btnAdd = new JButton("Adicionar Item");
        btnAdd.setBackground(new Color(60, 179, 113)); // Verde
        btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> adicionarItem());

        JButton btnRemove = new JButton("Remover Item");
        btnRemove.setBackground(new Color(220, 53, 69)); // Vermelho
        btnRemove.setForeground(Color.WHITE);
        btnRemove.addActionListener(e -> removerItem());

        panelBotoes.add(btnAdd);
        panelBotoes.add(btnRemove);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 10, 10, 10);
        card.add(panelBotoes, gbc);

        return card;
    }

    private JPanel criarCardTabela() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Itens do Pedido");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        card.add(titulo, BorderLayout.NORTH);

        itensModel = new DefaultTableModel(new Object[]{
                "Produto", "Quantidade", "Valor Unitário", "Subtotal"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaItens = new JTable(itensModel);
        tabelaItens.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tabelaItens);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(BG);

        JButton btnSalvar = new JButton("Salvar Pedido");
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSalvar.addActionListener(e -> {
    try {
        System.out.println("DEBUG: Clicou no botão salvar.");
        salvarPedido();
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Erro ao salvar pedido:\n" + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
});

        rodape.add(btnSalvar);

        return rodape;
    }

    // ---------------- LÓGICA DE NEGÓCIO ----------------

    // Atualiza o campo de valor quando seleciona no combo
    private void atualizarPreco() {
        Object item = comboProduto.getSelectedItem();
        
        // Verifica se o item é realmente um Produto (e não nulo ou string "Selecione...")
        if (item instanceof ProdutoDAO.Produto) {
            ProdutoDAO.Produto p = (ProdutoDAO.Produto) item;
            
            ProdutoDAO dao = new ProdutoDAO();
            BigDecimal precoPPA = dao.buscarPrecoPPA(p.getId());
            
            campoValorUnitario.setText(precoPPA.toString());
        } else {
             campoValorUnitario.setText("0.00");
        }
    }

    private void carregarAssociados() {
        var lista = new AssociadoDAO().listarTodos();
        comboAssociado.removeAllItems();
        for (var a : lista) comboAssociado.addItem(a);
    }

    private void configurarProdutor() {
    // limpa o combo
    comboAssociado.removeAllItems();

    var dao = new AssociadoDAO();
    var todos = dao.listarTodos();
    Long idSessao = SessaoUsuario.getAssociadoId();

    Object selecionado = null;

    // adiciona TODOS os associados no combo
    for (var a : todos) {
        comboAssociado.addItem(a);
        // guarda qual é o associado logado pra deixar pré-selecionado
        if (a.getId().equals(idSessao)) {
            selecionado = a;
        }
    }

    // se achou o da sessão, deixa selecionado
    if (selecionado != null) {
        comboAssociado.setSelectedItem(selecionado);
    }

    // agora o produtor PODE trocar o associado se quiser
    comboAssociado.setEnabled(true);

    // mantém o comportamento antigo do projeto: "Sem projeto" e travado
    comboProjeto.removeAllItems();
    comboProjeto.addItem("Sem projeto");
    comboProjeto.setSelectedIndex(0);
    comboProjeto.setEnabled(false);
}


    private void carregarProdutos() {
        var lista = new ProdutoDAO().listarTodos();
        comboProduto.removeAllItems();
        // Dica: Adicione um item vazio ou selecione nulo no inicio para obrigar o usuario a escolher
        // comboProduto.addItem("Selecione..."); 
        for (var p : lista) comboProduto.addItem(p);
        
        // Força atualizar o preço do primeiro item carregado (se houver)
        if (!lista.isEmpty()) atualizarPreco();
    }

    private void carregarProjetos() {
        var lista = new ProjetoDAO().listarTodos();
        comboProjeto.removeAllItems();
        comboProjeto.addItem("Sem projeto");
        for (var p : lista) comboProjeto.addItem(p.getNomeProjeto());
    }

    private void adicionarItem() {
        try {
            Object itemSelecionado = comboProduto.getSelectedItem();
            
            if (itemSelecionado == null || !(itemSelecionado instanceof ProdutoDAO.Produto)) {
                JOptionPane.showMessageDialog(this, "Selecione um produto válido.");
                return;
            }
            
            ProdutoDAO.Produto prod = (ProdutoDAO.Produto) itemSelecionado;

            String qtdStr = campoQuantidade.getText().replace(",", ".");
            String valStr = campoValorUnitario.getText().replace(",", ".");
            
            if(qtdStr.isEmpty() || valStr.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Preencha quantidade e valor.");
                 return;
            }

            BigDecimal qtd = new BigDecimal(qtdStr);
            BigDecimal unit = new BigDecimal(valStr);
            BigDecimal subtotal = qtd.multiply(unit);

            ItemPedidoVO item = new ItemPedidoVO();
            item.setProdutoId(prod.getId());
            item.setQuantidadeTotal(qtd);
            item.setValorUnitario(unit);
            item.setValorTotal(subtotal);

            itensPedido.add(item);

            itensModel.addRow(new Object[]{
                    prod.getNome(), qtd, unit, subtotal
            });

            totalPedido = totalPedido.add(subtotal);

            campoQuantidade.setText("");
            // Opcional: limpar valor unitario ou manter o do produto selecionado
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Valores inválidos. Use ponto para decimais.");
        }
    }

    private void removerItem() {
        int row = tabelaItens.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para remover.");
            return;
        }

        ItemPedidoVO item = itensPedido.get(row);
        totalPedido = totalPedido.subtract(item.getValorTotal());

        itensPedido.remove(row);
        itensModel.removeRow(row);
    }

    private void voltarParaHome() {
        String tipo = SessaoUsuario.getTipo();

        // Verifica quem está logado para abrir a home certa
        if ("associacao".equalsIgnoreCase(tipo)) {
            new AssociacaoMainView().setVisible(true);
        } else {
            // Assume que é produtor se não for associação
            new ProdutorMainView().setVisible(true);
        }
        
        // Fecha a tela atual de Criar Pedido
        dispose();
    }

    private void salvarPedido() {
    System.out.println("DEBUG: Clicou no botão salvar."); // Linha pra debug

    if (itensPedido.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Adicione ao menos um item ao pedido.");
        return;
    }

    try {
        PedidoVO pedido = new PedidoVO();
        Long associadoId;
        Long projetoId = null;

        if ("produtor".equalsIgnoreCase(SessaoUsuario.getTipo())) {
            associadoId = SessaoUsuario.getAssociadoId();
        } else {
            AssociadoDAO.Associado assoc = (AssociadoDAO.Associado) comboAssociado.getSelectedItem();
            if (assoc == null) {
                JOptionPane.showMessageDialog(this, "Selecione um associado.");
                return;
            }
            associadoId = assoc.getId();

            String selProj = (String) comboProjeto.getSelectedItem();
            if (selProj != null && !"Sem projeto".equals(selProj)) {
                var projeto = new ProjetoDAO().listarTodos().stream()
                        .filter(p -> p.getNomeProjeto().equals(selProj))
                        .findFirst()
                        .orElse(null);
                if (projeto != null) projetoId = projeto.getId();
            }
        }

        pedido.setAssociadoId(associadoId);
        pedido.setProjetoId(projetoId);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatusPedidoId(1L);
        pedido.setValorTotal(totalPedido);

        for (ItemPedidoVO item : itensPedido) {
            pedido.adicionarItem(item);
        }

        PedidoDAO dao = new PedidoDAO();
        Long id = dao.salvarPedido(pedido);

        if (id != null) {
            dao.salvarItens(id, itensPedido);
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            voltarParaHome();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido no banco.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Erro ao salvar pedido:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
}

