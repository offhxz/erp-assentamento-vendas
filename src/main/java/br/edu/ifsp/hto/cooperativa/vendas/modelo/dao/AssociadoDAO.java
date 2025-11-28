package br.edu.ifsp.hto.cooperativa.vendas.modelo.dao;

import br.edu.ifsp.hto.cooperativa.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssociadoDAO {

    public static class Associado {
        private Long id;
        private String nome;

        public Associado(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() { return id; }
        public String getNome() { return nome; }
        @Override public String toString() { return nome; }
    }

    public List<Associado> listarTodos() {

        List<Associado> lista = new ArrayList<>();

        String sql = "SELECT id, razao_social FROM associado ORDER BY razao_social";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Associado(
                        rs.getLong("id"),
                        rs.getString("razao_social")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
