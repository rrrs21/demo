package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lote {
    private String numero;
    private Date fabricacao;
    private Date validade;

    public Lote(String numero, Date fabricacao, Date validade) {
        this.numero = numero;
        this.fabricacao = fabricacao;
        this.validade = validade;
    }
    public Lote() {
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getFabricacao() {
        return fabricacao;
    }
    public void setFabricacao(Date fabricacao) {
        this.fabricacao = fabricacao;
    }
    public Date getValidade() {
        return validade;
    }
    public void setValidade(Date validade) {
        this.validade = validade;
    }
    
}
