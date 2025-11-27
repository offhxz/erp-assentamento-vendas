package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ItemPedidoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.PedidoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ProdutoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ItemPedidoVO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.PedidoVO;

public class AssociacaoMainView extends BaseView {

    private static final Color BG = new Color(0xE9, 0xE9, 0xE9);

    private JTable tabelaPedidos;
    private JTable tabelaItens;
    private DefaultTableModel pedidosModel;
    private DefaultTableModel itensModel;
    private JLabel lblTotal;

    public AssociacaoMainView() {
        super("Associação - Pedidos");

        add(criarPainelPrincipal(), BorderLayout.CENTER);
        carregarPedidos();
    }

    private JPanel criarPainelPrincipal() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        panel.add(criarTitleBar("Associação — Pedidos Recebidos"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BG);
        center.setBorder(new EmptyBorder(25, 25, 25, 25));

        center.add(criarCardPedidos());
        center.add(Box.createVerticalStrut(25));
        center.add(criarCardItens());

        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel criarCardPedidos() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Pedidos Cadastrados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        card.add(titulo, BorderLayout.NORTH);

        String[] col = {"ID", "Projeto", "Data", "Total (R$)", "Status"};

        pedidosModel = new DefaultTableModel(col, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaPedidos = new JTable(pedidosModel);
        tabelaPedidos.setRowHeight(28);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarItens();
        });

        card.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);

        return card;
    }

    private JPanel criarCardItens() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Itens do Pedido Selecionado");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        card.add(titulo, BorderLayout.NORTH);

        String[] col = {"Produto", "Quantidade", "Valor Unitário", "Subtotal"};

        itensModel = new DefaultTableModel(col, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelaItens = new JTable(itensModel);
        tabelaItens.setRowHeight(28);

        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTotal.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(Color.WHITE);
        rodape.add(lblTotal);

        card.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        card.add(rodape, BorderLayout.SOUTH);

        return card;
    }

    // =====================================================
    // CARREGA PEDIDOS (SEM ALTERAR DAO NEM VO)
    // =====================================================
    private void carregarPedidos() {

        pedidosModel.setRowCount(0);

        PedidoDAO dao = new PedidoDAO();
        List<PedidoVO> lista = dao.listarTodos();

        for (PedidoVO p : lista) {

            String dataStr = (p.getDataCriacao() != null)
                ? p.getDataCriacao().toLocalDate().toString()
                : "";

            pedidosModel.addRow(new Object[]{
                p.getId(),
                // Mantém o projeto como estava (ID ou "Avulso")
                p.getProjetoId() != null ? p.getProjetoId() : "Avulso", 
                dataStr,
                p.getValorTotal(),
                
                // --- MUDANÇA AQUI ---
                // Exibe a descrição (ex: "Aberto") em vez do ID (1)
                p.getStatusDescricao() 
            });
        }
    }

    // =====================================================
    // CARREGA ITENS DO PEDIDO SELECIONADO
    // =====================================================
    private void carregarItens() {
        // Limpa a tabela antes de carregar
        itensModel.setRowCount(0);
        lblTotal.setText("Total: R$ 0,00"); // Reseta o label

        int linha = tabelaPedidos.getSelectedRow();
        if (linha == -1) return;

        // Pega o ID da linha selecionada
        Long pedidoId = Long.valueOf(tabelaPedidos.getValueAt(linha, 0).toString());

        // USA A NOVA DAO QUE VOCÊ CRIOU
        ItemPedidoDAO itemDao = new ItemPedidoDAO(); 
        List<ItemPedidoVO> itens = itemDao.listarPorPedido(pedidoId);

        ProdutoDAO prodDAO = new ProdutoDAO();
        double total = 0;

        for (ItemPedidoVO item : itens) {

            // Busca o nome do produto
            ProdutoDAO.Produto prod = prodDAO.buscarPorId(item.getProdutoId());
            
            // Se prod vir nulo, é porque a ProdutoDAO não achou o ID no banco
            String nomeProduto = (prod != null) ? prod.getNome() : "Produto #" + item.getProdutoId();

            double subtotal = item.getValorTotal().doubleValue();
            total += subtotal;

            itensModel.addRow(new Object[]{
                    nomeProduto,
                    item.getQuantidadeTotal(),
                    item.getValorUnitario(),
                    subtotal
            });
        }

        lblTotal.setText(String.format("Total: R$ %.2f", total));
    }
}
