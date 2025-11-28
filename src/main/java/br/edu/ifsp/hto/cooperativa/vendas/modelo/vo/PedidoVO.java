package br.edu.ifsp.hto.cooperativa.vendas.modelo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoVO {

    private Long id;
    private Long projetoId;
    private String projetoNome; // <— AQUI
    private Long associadoId;
    private LocalDateTime dataCriacao;
    private Long statusPedidoId;
    private String statusDescricao;
    private BigDecimal valorTotal;
    private String nomeProjeto;


    private List<ItemPedidoVO> itens = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }

    public String getProjetoNome() { return projetoNome; } // <—
    public void setProjetoNome(String projetoNome) { this.projetoNome = projetoNome; } // <—

    public Long getAssociadoId() { return associadoId; }
    public void setAssociadoId(Long associadoId) { this.associadoId = associadoId; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public Long getStatusPedidoId() { return statusPedidoId; }
    public void setStatusPedidoId(Long statusPedidoId) { this.statusPedidoId = statusPedidoId; }

    public String getStatusDescricao() { return statusDescricao; }
    public void setStatusDescricao(String statusDescricao) { this.statusDescricao = statusDescricao; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public List<ItemPedidoVO> getItens() { return itens; }
    public void adicionarItem(ItemPedidoVO item) { itens.add(item); }

    public String getNomeProjeto() {
    return nomeProjeto;
}

public void setNomeProjeto(String nomeProjeto) {
    this.nomeProjeto = nomeProjeto;
}

}
