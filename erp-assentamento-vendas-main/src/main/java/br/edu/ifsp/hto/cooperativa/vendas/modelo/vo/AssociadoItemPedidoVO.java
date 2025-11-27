package br.edu.ifsp.hto.cooperativa.vendas.modelo.vo;

import java.math.BigDecimal;

public class AssociadoItemPedidoVO {

    private Long id;
    private Long associadoId;
    private Long itemPedidoId;
    private BigDecimal quantidadeAtribuida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociadoId() {
        return associadoId;
    }

    public void setAssociadoId(Long associadoId) {
        this.associadoId = associadoId;
    }

    public Long getItemPedidoId() {
        return itemPedidoId;
    }

    public void setItemPedidoId(Long itemPedidoId) {
        this.itemPedidoId = itemPedidoId;
    }

    public BigDecimal getQuantidadeAtribuida() {
        return quantidadeAtribuida;
    }

    public void setQuantidadeAtribuida(BigDecimal quantidadeAtribuida) {
        this.quantidadeAtribuida = quantidadeAtribuida;
    }
}
