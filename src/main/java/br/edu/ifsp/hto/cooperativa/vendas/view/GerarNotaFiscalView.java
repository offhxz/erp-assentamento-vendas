package br.edu.ifsp.hto.cooperativa.vendas.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

import br.edu.ifsp.hto.cooperativa.vendas.modelo.dao.PedidoDAO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ItemPedidoVO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.VendaVO;

import br.edu.ifsp.hto.cooperativa.notafiscal.controlador.NotaFiscalEletronicaControlador;
import br.edu.ifsp.hto.cooperativa.notafiscal.modelo.dto.NotaFiscalEletronicaTO;

public class GerarNotaFiscalView extends BaseView {

    private JTextField campoIdPedido;

    public GerarNotaFiscalView() {
        super("Gerar Nota Fiscal");
        add(criarPainel(), BorderLayout.CENTER);
    }

    private JPanel criarPainel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xE9, 0xE9, 0xE9));

        panel.add(criarTitleBar("Gerar Nota Fiscal"), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(new Color(0xE9, 0xE9, 0xE9));
        center.setBorder(new EmptyBorder(25, 25, 25, 25));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(criarCardFormulario());
        center.add(Box.createVerticalStrut(25));
        center.add(criarRodape());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(0xE9, 0xE9, 0xE9));
        wrapper.add(center, BorderLayout.NORTH);

        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel criarCardFormulario() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ID do Pedido ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        card.add(criarLabel("ID do Pedido:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        campoIdPedido = new JTextField();
        estilizarCampo(campoIdPedido);
        card.add(campoIdPedido, gbc);

        return card;
    }

    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(new Color(0xE9, 0xE9, 0xE9));
        rodape.setBorder(new EmptyBorder(0, 0, 10, 0));

        JButton btnGerarNotaFiscal = new JButton("Gerar Nota Fiscal");
        btnGerarNotaFiscal.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnGerarNotaFiscal.setBackground(new Color(60, 179, 113));
        btnGerarNotaFiscal.setForeground(Color.WHITE);
        btnGerarNotaFiscal.setFocusPainted(false);
        btnGerarNotaFiscal.setPreferredSize(new Dimension(180, 45));

        btnGerarNotaFiscal.addActionListener(e -> gerarNotaFiscal());
        rodape.add(btnGerarNotaFiscal);

        return rodape;
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return lbl;
    }

    private void estilizarCampo(JComponent campo) {
        campo.setPreferredSize(new Dimension(0, 30));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    // ------------------------------------
    // LÓGICA DE GERAÇÃO DA NFE
    // ------------------------------------

    private void gerarNotaFiscal() {
        try {
            if (campoIdPedido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira o ID do Pedido.");
                return;
            }

            Long idPedido = Long.parseLong(campoIdPedido.getText().trim());

            PedidoDAO pedidoDAO = new PedidoDAO();
            VendaVO venda = pedidoDAO.buscarVendaPorId(idPedido);

            if (venda == null) {
                JOptionPane.showMessageDialog(this, "Venda/Pedido não encontrado.");
                return;
            }

            // Itens da venda
            List<ItemPedidoVO> produtosVendidos = pedidoDAO.listarItens(idPedido);

            if (produtosVendidos == null || produtosVendidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum item encontrado para este pedido.");
                return;
            }

            // Dados fixos por enquanto
            BigDecimal valorFrete = BigDecimal.ZERO;
            String dadosAdicionais = "Gerado pelo sistema ERP Assentamento.";

            // Chamando o controlador novo
            NotaFiscalEletronicaControlador controlador = new NotaFiscalEletronicaControlador();

            NotaFiscalEletronicaTO nfeTO =
                    controlador.emitirNotaFiscalEletronica(
                            venda,
                            venda.getAssociadoId(), // clienteID temporário
                            produtosVendidos,
                            valorFrete,
                            dadosAdicionais
                    );

            if (nfeTO != null && nfeTO.notaFiscalEletronica != null) {
                JOptionPane.showMessageDialog(this, "Nota Fiscal gerada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao gerar nota fiscal.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do Pedido inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar Nota Fiscal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
