package com.example.demo;



public class Item {
    
    private String invoice;
    private Integer sequencia;
    private String codigo;
    private String descricao;
    private Integer quantidade;
    private Lote lote;
    
    
    
    public Item() {
    }
    public Item(String codigo, String descricao, Lote lote) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.lote = lote;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Lote getLote() {
        return lote;
    }
    public void setLote(Lote lote) {
        this.lote = lote;
    }
    public String getInvoice() {
        return invoice;
    }
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
    public Integer getSequencia() {
        return sequencia;
    }
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }
        
}
