package br.edu.ifsp.hto.cooperativa.vendas.modelo.dao;

import br.edu.ifsp.hto.cooperativa.ConnectionFactory;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ProjetoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    // ----------------------------------------------
    // GERA NOVO ID SEM BUGAR PK
    // ----------------------------------------------
    private Long gerarNovoId(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(id),0) + 1 AS novo_id FROM projeto";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong("novo_id");
        }

        return 1L;
    }

    // ----------------------------------------------
    // INSERIR NOVO PROJETO (COM ID GERADO MANUAL)
    // ----------------------------------------------
    public String adicionar(ProjetoVO projeto) {

        String sql = "INSERT INTO projeto (id, nome_projeto, data_criacao, data_final, orcamento) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Long novoId = gerarNovoId(conn);
            stmt.setLong(1, novoId);

            stmt.setString(2, projeto.getNomeProjeto());
            stmt.setTimestamp(3, Timestamp.valueOf(projeto.getDataCriacao()));

            if (projeto.getDataFinal() != null)
                stmt.setTimestamp(4, Timestamp.valueOf(projeto.getDataFinal()));
            else
                stmt.setNull(4, Types.TIMESTAMP);

            stmt.setBigDecimal(5, projeto.getOrcamento());

            stmt.executeUpdate();
            return "Projeto salvo com sucesso!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO SQL ao salvar projeto: " + e.getMessage();
        }
    }

    // ----------------------------------------------
    // LISTAR TODOS
    // ----------------------------------------------
    public List<ProjetoVO> listarTodos() {

        List<ProjetoVO> lista = new ArrayList<>();

        String sql = "SELECT * FROM projeto ORDER BY nome_projeto";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                ProjetoVO p = new ProjetoVO();

                p.setId(rs.getLong("id"));
                p.setNomeProjeto(rs.getString("nome_projeto"));

                Timestamp tsCriacao = rs.getTimestamp("data_criacao");
                p.setDataCriacao(tsCriacao != null ? tsCriacao.toLocalDateTime() : null);

                Timestamp tsFinal = rs.getTimestamp("data_final");
                p.setDataFinal(tsFinal != null ? tsFinal.toLocalDateTime() : null);

                p.setOrcamento(rs.getBigDecimal("orcamento"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
