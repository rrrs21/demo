package com.example.demo;



public class Item {
    
    private String codigo;
    private String descricao;
    private Lote lote;
    
    public Item() {
    }
    public Item(String codigo, String descricao, Lote lote) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.lote = lote;
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
    
}
