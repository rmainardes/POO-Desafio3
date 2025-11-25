package Model.entidade;

import Model.exception.NomeProdutoVazio;
import Model.exception.QuantidadeNegativaException;

import javax.swing.*;

public class Produto {
    private int id;
    private String descricao;
    private int quantidade;


    // Getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Setter apenas para quantidade
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto(int id, String descricao, int quantidade) {
        if (descricao.trim().isEmpty() || descricao == null) {
            throw new NomeProdutoVazio("É necessário informar o nome do produto!");
        }
        if  (quantidade < 0) {
            throw new QuantidadeNegativaException("A quantidade não pode ser negativa.");
        }
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.descricao + " - " + this.quantidade;
    }
}