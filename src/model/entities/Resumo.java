package model.entities;

/**
 *
 * @author danilodsf
 */
public class Resumo {
    
    private Conta conta;
    private Double entrada;
    private Double saida;
    private Double saldo;
    
    public Resumo() {
        
    }

    public Resumo(Conta conta, Double entrada, Double saida, Double saldo) {
        this.conta = conta;
        this.entrada = entrada;
        this.saida = saida;
        this.saldo = saldo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getSaida() {
        return saida;
    }

    public void setSaida(Double saida) {
        this.saida = saida;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Resumo{" + "conta=" + conta + ", entrada=" + entrada + ", saida=" + saida + ", saldo=" + saldo + '}';
    }
}
