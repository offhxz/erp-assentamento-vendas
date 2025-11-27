package br.edu.ifsp.hto.cooperativa.vendas.modelo.dao;

import br.edu.ifsp.hto.cooperativa.db.ConnectionFactory;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.ItemPedidoVO;
import br.edu.ifsp.hto.cooperativa.vendas.modelo.vo.PedidoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // ... (Métodos auxiliares gerarNovoIdPedido e gerarNovoIdItem mantêm iguais) ...
    private Long gerarNovoIdPedido(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(id),0) + 1 AS novo_id FROM pedido";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong("novo_id");
        }
        return 1L;
    }
    private Long gerarNovoIdItem(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(id),0) + 1 AS novo_id FROM item_pedido";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getLong("novo_id");
        }
        return 1L;
    }

    // ... (Métodos salvarPedido e salvarItens mantêm iguais) ...
    public Long salvarPedido(PedidoVO p) {
        String sql = "INSERT INTO pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            Long novoId = gerarNovoIdPedido(conn);
            stmt.setLong(1, novoId);
            if (p.getProjetoId() != null) stmt.setLong(2, p.getProjetoId()); else stmt.setNull(2, Types.BIGINT);
            stmt.setLong(3, p.getAssociadoId());
            stmt.setTimestamp(4, Timestamp.valueOf(p.getDataCriacao()));
            stmt.setLong(5, p.getStatusPedidoId());
            stmt.setBigDecimal(6, p.getValorTotal());
            stmt.executeUpdate();
            return novoId;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void salvarItens(Long pedidoId, List<ItemPedidoVO> itens) {

        String sql = "INSERT INTO item_pedido " +
                "(id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // CORREÇÃO: Pegamos o ID inicial APENAS UMA VEZ
            Long proximoId = gerarNovoIdItem(conn);

            for (ItemPedidoVO item : itens) {

                // Usamos a variável local e incrementamos ela manualmente
                stmt.setLong(1, proximoId++); 
                
                stmt.setLong(2, pedidoId);
                stmt.setLong(3, item.getProdutoId());
                stmt.setBigDecimal(4, item.getQuantidadeTotal());
                stmt.setBigDecimal(5, item.getValorUnitario());
                stmt.setBigDecimal(6, item.getValorTotal());

                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("ERRO salvarItens: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // =============== LISTAGENS ALTERADAS ======================

    /** Usado pela ASSOCIAÇÃO – traz TODOS os pedidos do sistema COM DESCRIÇÃO DO STATUS */
    public List<PedidoVO> listarTodos() {
        List<PedidoVO> lista = new ArrayList<>();
        
        // SQL Busca a descrição na tabela status_pedido
        String sql = "SELECT p.*, s.descricao AS status_desc " +
                     "FROM pedido p " +
                     "INNER JOIN status_pedido s ON p.status_pedido_id = s.id " +
                     "ORDER BY p.data_criacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Usado pelo PRODUTOR – traz só pedidos daquele associado COM DESCRIÇÃO DO STATUS */
    public List<PedidoVO> listarPorAssociado(Long associadoId) {
        List<PedidoVO> lista = new ArrayList<>();
        
        String sql = "SELECT p.*, s.descricao AS status_desc " +
                     "FROM pedido p " +
                     "INNER JOIN status_pedido s ON p.status_pedido_id = s.id " +
                     "WHERE p.associado_id = ? " +
                     "ORDER BY p.data_criacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, associadoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPedido(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Criei esse método auxiliar para não repetir código de mapeamento
    private PedidoVO mapearPedido(ResultSet rs) throws SQLException {
        PedidoVO p = new PedidoVO();
        p.setId(rs.getLong("id"));
        p.setProjetoId((Long) rs.getObject("projeto_id"));
        p.setAssociadoId(rs.getLong("associado_id"));
        p.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
        p.setStatusPedidoId(rs.getLong("status_pedido_id"));
        p.setValorTotal(rs.getBigDecimal("valor_total"));
        
        // --- AQUI ESTÁ A CORREÇÃO ---
        // Pegamos a string que veio do JOIN (status_desc) e jogamos na VO
        p.setStatusDescricao(rs.getString("status_desc"));
        
        return p;
    }

    // ... (listarItens mantém igual) ...
    public List<ItemPedidoVO> listarItens(Long pedidoId) {
        List<ItemPedidoVO> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_pedido WHERE pedido_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pedidoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemPedidoVO item = new ItemPedidoVO();
                item.setId(rs.getLong("id"));
                item.setPedidoId(pedidoId);
                item.setProdutoId(rs.getLong("produto_id"));
                item.setQuantidadeTotal(rs.getBigDecimal("quantidade_total"));
                item.setValorUnitario(rs.getBigDecimal("valor_unitario"));
                item.setValorTotal(rs.getBigDecimal("valor_total"));
                itens.add(item);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return itens;
    }
}