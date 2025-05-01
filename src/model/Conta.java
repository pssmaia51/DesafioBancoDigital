package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private static int contador = 1;

    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    private List<Transacao> transacoes = new ArrayList<>();

    public Conta(Cliente cliente) {
        this.numero = contador++;
        this.cliente = cliente;
        this.saldo = 0;
    }

    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            transacoes.add(new Transacao("Saque", valor));
            return true;
        }
        return false;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            transacoes.add(new Transacao("Depósito", valor));
        }
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
