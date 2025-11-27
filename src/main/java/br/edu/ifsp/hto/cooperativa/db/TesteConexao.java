package br.edu.ifsp.hto.cooperativa.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {

        System.out.println("Testando conexão...");

        try (Connection conn = ConnectionFactory.getConnection()) {

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexão OK! Conectado ao PostgreSQL.");
            } else {
                System.out.println("❌ Conexão NÃO estabelecida.");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERRO ao conectar:");
            e.printStackTrace();
        }
    }
}
