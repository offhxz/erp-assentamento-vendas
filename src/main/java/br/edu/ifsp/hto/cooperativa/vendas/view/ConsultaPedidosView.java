package br.edu.ifsp.hto.cooperativa.vendas.view;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.PedidoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.PedidoVO;
import br.edu.ifsp.hto.cooperativa.sessao.SessaoUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaPedidosView extends BaseView {

    private JTable tabela;
    private DefaultTableModel model;

    public ConsultaPedidosView() {
        super("Consultar Pedidos");

        add(criarPainel(), BorderLayout.CENTER);
        carregarPedidos();
    }

    private JPanel criarPainel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xE9, 0xE9, 0xE9));

        JLabel titulo = new JLabel("Pedidos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(titulo, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
                "ID", "Associado", "Projeto", "Data", "Valor Total"
        }, 0);

        tabela = new JTable(model);
        tabela.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tabela);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void carregarPedidos() {

        model.setRowCount(0);

        PedidoDAO dao = new PedidoDAO();
        List<PedidoVO> lista;

        if ("produtor".equals(SessaoUsuario.getTipo())) {
            lista = dao.listarPorAssociado(SessaoUsuario.getAssociadoId());
        } else {
            lista = dao.listarTodos();
        }

        for (PedidoVO p : lista) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getAssociadoId(),
                    p.getProjetoId() == null ? "Sem projeto" : p.getProjetoId(),
                    p.getDataCriacao(),
                    p.getValorTotal()
            });
        }
    }

}
