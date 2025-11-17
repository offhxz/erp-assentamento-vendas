package br.edu.ifsp.hto.cooperativa.vendas.modelo.dao;

import br.edu.ifsp.hto.cooperativa.db.ConnectionFactory;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ProjetoVO;
import java.sql.*;

public class ProjetoDAO {

    public String adicionar(ProjetoVO p) {
        String sql = "INSERT INTO projeto (nome_projeto, data_criacao, data_final, orcamento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
            stmt.setString(1, p.getNomeProjeto());
            stmt.setTimestamp(2, p.getDataCriacao() != null ? Timestamp.valueOf(p.getDataCriacao()) : null);
            stmt.setTimestamp(3, p.getDataFinal() != null ? Timestamp.valueOf(p.getDataFinal()) : null);
            stmt.setBigDecimal(4, p.getOrcamento());

            int affected = stmt.executeUpdate();
            
            if (affected > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) p.setId(keys.getLong(1));
                }
                return "Projeto cadastrado com sucesso!";
            }
            return "Nenhuma linha inserida.";
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro no banco: " + e.getMessage();
        }
    }
}