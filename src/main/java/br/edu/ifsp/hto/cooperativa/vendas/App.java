package br.edu.ifsp.hto.cooperativa.vendas;

import javax.swing.JOptionPane;

import br.edu.ifsp.hto.cooperativa.vendas.sessao.SessaoUsuario;
import br.edu.ifsp.hto.cooperativa.vendas.view.AssociacaoMainView;
import br.edu.ifsp.hto.cooperativa.vendas.view.ProdutorMainView;

public class App {

    public static void main(String[] args) {

        String[] opcoes = { "Associação", "Produtor" };

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Escolha o tipo de usuário:",
                "ERP Assentamento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        // Se fechar a janelinha sem escolher nada
        if (escolha == JOptionPane.CLOSED_OPTION || escolha == -1) {
            System.exit(0);
        }

        if (escolha == 0) {
            // ===== Sessão de ASSOCIAÇÃO =====
            // Troque 1L pelo ID da associação na tabela "associado"
            SessaoUsuario.setAssociadoId(1L);
            SessaoUsuario.setTipo("associacao");

            new AssociacaoMainView().setVisible(true);

        } else if (escolha == 1) {
            // ===== Sessão de PRODUTOR =====
            // Troque 2L pelo ID do produtor na tabela "associado"
            SessaoUsuario.setAssociadoId(2L);
            SessaoUsuario.setTipo("produtor");

            new ProdutorMainView().setVisible(true);
        }
    }
}
