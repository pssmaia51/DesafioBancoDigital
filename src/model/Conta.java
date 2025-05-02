package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    protected int numero;
    protected double saldo;
    protected Cliente titular;
    protected List<String> extrato;  // <- Aqui está a declaração do extrato
    protected List<Transacao> transacoes;

    public Conta(int numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
        this.transacoes = new ArrayList<>();
        this.transacoes.add(new Transacao("Abertura", 0.0));
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            transacoes.add(new Transacao("Depósito", valor));  // Adiciona a transação com data, tipo e valor
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso.");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            transacoes.add(new Transacao("Saque", valor));  // Adiciona a transação com data, tipo e valor
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void exibirExtrato() {
        System.out.println("Extrato da conta nº " + numero);
        for (String linha : extrato) {
            System.out.println(linha);
        }
        System.out.println("Saldo atual: " + saldo);
    }
}