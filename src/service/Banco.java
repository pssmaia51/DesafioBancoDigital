package service;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import model.Transacao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Banco {
    private List<Conta> contas = new ArrayList<>();

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public void excluirCliente(String nome) {
        contas.removeIf(c -> c.getCliente().getNome().equalsIgnoreCase(nome));
        System.out.println("Todas as contas do cliente \"" + nome + "\" foram removidas.");
    }

    public void consultarClientes() {
        if (contas.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("Clientes e suas contas:");
        for (Conta conta : contas) {
            String tipo = conta instanceof ContaCorrente ? "Corrente" : "Poupança";
            System.out.println("- Nome: " + conta.getCliente().getNome() +
                    " | Tipo: " + tipo + " | Nº: " + conta.getNumero());
        }
    }

    public void consultarClientesPorTipo(String tipo) {
        for (Conta conta : contas) {
            if (tipo.equalsIgnoreCase("corrente") && conta instanceof ContaCorrente ||
                    tipo.equalsIgnoreCase("poupanca") && conta instanceof ContaPoupanca) {
                System.out.println("Nome: " + conta.getCliente().getNome() +
                        " | Conta Nº: " + conta.getNumero() +
                        " | Saldo: R$ " + conta.getSaldo());
            }
        }
    }

    public void consultarSaldo(String nomeCliente, String tipoConta) {
        Conta conta = encontrarContaPorClienteENome(nomeCliente, tipoConta);
        if (conta != null) {
            System.out.println("========== EXTRATO BANCÁRIO ==========");
            System.out.println("Cliente: " + nomeCliente + " (Conta " + tipoConta + ")");
            System.out.println();
            System.out.printf("%-20s | %-10s | %-10s\n", "Data/Hora", "Tipo", "Valor (R$)");
            System.out.println("----------------------------------------------");

            if (conta.getTransacoes().isEmpty()) {
                System.out.println("Nenhuma transação registrada.");
            } else {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                for (Transacao t : conta.getTransacoes()) {
                    System.out.printf("%-20s | %-10s | %-10.2f\n",
                            t.getDataHora().format(formato), t.getTipo(), t.getValor());
                }
            }

            System.out.println("\n----------------------------------------------");
            System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
            System.out.println("==============================================\n");
        } else {
            System.out.println("Conta não encontrada.\n");
        }
    }


    public void depositar(String nomeCliente, String tipoConta, double valor) {
        Conta conta = encontrarContaPorClienteENome(nomeCliente, tipoConta);
        if (conta != null) {
            conta.depositar(valor);
            System.out.println("Depósito realizado. Novo saldo: R$ " + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void sacar(String nomeCliente, String tipoConta, double valor) {
        Conta conta = encontrarContaPorClienteENome(nomeCliente, tipoConta);
        if (conta != null) {
            boolean sucesso = conta.sacar(valor);
            if (sucesso) {
                System.out.println("Saque realizado. Novo saldo: R$ " + conta.getSaldo());
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private Conta encontrarContaPorClienteENome(String nomeCliente, String tipoConta) {
        for (Conta conta : contas) {
            if (conta.getCliente().getNome().equalsIgnoreCase(nomeCliente)) {
                if ((tipoConta.equalsIgnoreCase("corrente") && conta instanceof ContaCorrente) ||
                        (tipoConta.equalsIgnoreCase("poupanca") && conta instanceof ContaPoupanca)) {
                    return conta;
                }
            }
        }
        return null;
    }
}