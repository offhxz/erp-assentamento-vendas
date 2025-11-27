package br.edu.ifsp.hto.cooperativa.vendas.modelo.vo;

import java.math.BigDecimal;

public class ItemPedidoVO {

    private Long id = null;
    private Long pedidoId;
    private Long produtoId;
    private BigDecimal quantidadeTotal;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public ItemPedidoVO() {}

    public Long getId() {
        return id == null || id == 0 ? null : id;
    }

    public void setId(Long id) {
        this.id = (id != null && id == 0) ? null : id;
    }

    public Long getPedidoId() {
        return pedidoId == null || pedidoId == 0 ? null : pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = (pedidoId != null && pedidoId == 0) ? null : pedidoId;
    }

    public Long getProdutoId() {
        return produtoId == null || produtoId == 0 ? null : produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = (produtoId != null && produtoId == 0) ? null : produtoId;
    }

    public BigDecimal getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(BigDecimal quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}
