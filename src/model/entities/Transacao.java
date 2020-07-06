package model.entities;

import java.util.Date;
import java.util.Objects;

/**
 * Entidade Transação
 *
 * @author danilodsf
 */
public class Transacao {
    
    private Integer id;
    private String natureza;
    private String descricao;
    private Date data;
    private Double valor;
    
    private Conta conta;
    
    public Transacao() {
        
    }
    
    public Transacao(Integer id, String natureza, String descricao, Date data, Double valor, Conta conta) {
        this.id = id;
        this.natureza = natureza;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transacao other = (Transacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transacao{" + "id=" + id + ", natureza=" + natureza + ", descricao=" + descricao + ", data=" + data + ", valor=" + valor + ", conta=" + conta + '}';
    }
}
