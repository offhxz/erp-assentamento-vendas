package br.edu.ifsp.hto.cooperativa.vendas.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.ProjetoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ProjetoVO;

public class ConsultaProjetosView extends BaseView {

    private JTable tabela;
    private DefaultTableModel model;

    public ConsultaProjetosView() {
        super("Consulta de Projetos");

        add(criarPainel(), BorderLayout.CENTER);
        carregarProjetos();
    }

    private JPanel criarPainel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(criarTitleBar("Consulta de Projetos"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new Object[]{
                "ID", "Nome", "Data Criação", "Data Final", "Orçamento (R$)"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabela = new JTable(model);
        tabela.setRowHeight(26);

        center.add(new JScrollPane(tabela));
        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private void carregarProjetos() {
        model.setRowCount(0);

        List<ProjetoVO> lista = new ProjetoDAO().listarTodos();
        for (ProjetoVO p : lista) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getNomeProjeto(),
                    p.getDataCriacao(),
                    p.getDataFinal(),
                    p.getOrcamento()
            });
        }
    }
}
