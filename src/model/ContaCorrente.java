package model;

public class ContaCorrente extends Conta {
    protected int numeroConta;
    public ContaCorrente(int numero, Cliente titular) {
        super(numero, titular);
    }
}
