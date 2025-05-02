package service;

import model.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class Banco {
    private Map<String, Cliente> clientes;

    public Banco() {
        this.clientes = new HashMap<>();
    }

    public void adicionarCliente(String nome, String cpf, boolean cc, boolean cp) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            cliente = new Cliente(nome, cpf);
            clientes.put(cpf, cliente);
            System.out.println("Cliente " + nome + " adicionado com sucesso.");
        }

        if (cc) {
            adicionarContaAoCliente(cliente, "corrente");
        }
        if (cp) {
            adicionarContaAoCliente(cliente, "poupanca");
        }
    }

    public void excluirCliente(String cpf) {
        Cliente cliente = clientes.remove(cpf);
        if (cliente != null) {
            System.out.println("Cliente com CPF " + cpf + " excluído com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public Cliente consultarClientePorCpf(String cpf) {
        Cliente cliente = clientes.get(cpf);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNome() + " | CPF: " + cpf);
        } else {
            System.out.println("Cliente não encontrado.");
        }
        return cliente;
    }

    public Cliente consultarClientePorNome(String nome) {
        for (Cliente c : clientes.values()) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Cliente encontrado: " + c.getNome() + " | CPF: " + c.getCpf());
                return c;
            }
        }
        System.out.println("Cliente não encontrado.");
        return null;
    }

    public void consultarTodosClientes() {
        for (Cliente c : clientes.values()) {
            System.out.println("Nome: " + c.getNome() + " | CPF: " + c.getCpf());
            if (c.getContaCorrente() != null) System.out.println(" - Conta Corrente");
            if (c.getContaPoupanca() != null) System.out.println(" - Conta Poupança");
            if (c.getContaCorrente() == null && c.getContaPoupanca() == null)
                System.out.println(" - Sem conta cadastrada");
        }
    }

    public void consultarClientesPorTipoConta(String tipoConta) {
        for (Cliente c : clientes.values()) {
            if (tipoConta.equalsIgnoreCase("corrente") && c.getContaCorrente() != null) {
                System.out.println(c.getNome() + " - CPF: " + c.getCpf());
            } else if (tipoConta.equalsIgnoreCase("poupanca") && c.getContaPoupanca() != null) {
                System.out.println(c.getNome() + " - CPF: " + c.getCpf());
            }
        }
    }

    public String getCpfPorNome(String nome) {
        Cliente cliente = consultarClientePorNome(nome);
        return cliente != null ? cliente.getCpf() : null;
    }

    public boolean clienteTemConta(String cpf, String tipoConta) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) return false;
        if (tipoConta.equalsIgnoreCase("corrente")) return cliente.getContaCorrente() != null;
        if (tipoConta.equalsIgnoreCase("poupanca")) return cliente.getContaPoupanca() != null;
        return false;
    }

    public void adicionarContaAoCliente(Cliente cliente, String tipoConta) {
        tipoConta = tipoConta.toLowerCase();
        switch (tipoConta) {
            case "corrente":
                if (cliente.getContaCorrente() == null) {
                    ContaCorrente cc = new ContaCorrente(1234, cliente);  // Exemplo de número da conta
                    cliente.setContaCorrente(cc);
                    System.out.println("Conta corrente criada com sucesso.");
                } else {
                    System.out.println("Cliente já possui conta corrente.");
                }
                break;
            case "poupanca":
                if (cliente.getContaPoupanca() == null) {
                    ContaPoupanca cp = new ContaPoupanca(5678, cliente);  // Exemplo de número da conta
                    cliente.setContaPoupanca(cp);
                    System.out.println("Conta poupança criada com sucesso.");
                } else {
                    System.out.println("Cliente já possui conta poupança.");
                }
                break;
            default:
                System.out.println("Tipo de conta inválido. Use 'corrente' ou 'poupanca'.");
                break;
        }
    }

    private static int contadorContas = 1;

    private int gerarNumeroConta() {
        return contadorContas++;
    }


    public void consultarSaldoEExtrato(String cpf) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        // Exibir informações gerais do cliente
        System.out.println("===============================================");
        System.out.println("               EXTRATO BANCÁRIO               ");
        System.out.println("===============================================");
        System.out.println("Nome do Cliente: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("-----------------------------------------------");

        // Consultar e exibir as contas (Corrente e Poupança)
        if (cliente.getContaCorrente() != null) {
            ContaCorrente cc = cliente.getContaCorrente();
            System.out.println("\nConta Corrente - Nº " + cc.getNumero());
            exibirExtratoConta(cc);
        } else {
            System.out.println("\nConta Corrente não cadastrada.");
        }

        if (cliente.getContaPoupanca() != null) {
            ContaPoupanca cp = cliente.getContaPoupanca();
            System.out.println("\nConta Poupança - Nº " + cp.getNumero());

            exibirExtratoConta(cp);
        } else {
            System.out.println("\nConta Poupança não cadastrada.");
        }

        System.out.println("===============================================");
    }

    // Método para exibir extrato de uma conta
    private void exibirExtratoConta(Conta conta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("=========================================");
        System.out.println("Extrato da Conta Nº: " + conta.getNumero());
        System.out.println("-----------------------------------------");
        System.out.printf("%-20s %-10s %10s\n", "Data", "Tipo", "Valor");
        System.out.println("-----------------------------------------");

        for (Transacao t : conta.getTransacoes()) {
            String data = t.getData().format(formatter);
            String tipo = t.getTipo();
            double valor = t.getValor();
            System.out.printf("%-20s %-10s R$ %8.2f\n", data, tipo, valor);
        }

        System.out.println("-----------------------------------------");
        System.out.printf("Saldo atual: R$ %.2f\n", conta.getSaldo());
        System.out.println("=========================================\n");
    }


    public void depositar(String cpf, String tipoConta, double valor) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        Conta conta = tipoConta.equalsIgnoreCase("corrente") ? cliente.getContaCorrente() : cliente.getContaPoupanca();
        if (conta == null) {
            System.out.println("O cliente não possui conta " + tipoConta + ". Deseja criar? (s/n)");
            return;
        }
        conta.depositar(valor);
        System.out.println("Depósito de R$ " + valor + " realizado com sucesso.");
    }

    public void sacar(String cpf, String tipoConta, double valor) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        Conta conta = tipoConta.equalsIgnoreCase("corrente") ? cliente.getContaCorrente() : cliente.getContaPoupanca();
        if (conta == null) {
            System.out.println("O cliente não possui conta " + tipoConta + ".");
            return;
        }
        if (conta.sacar(valor)) {
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void transferirEntreContasDoMesmoCliente(String cpf, String origem, String destino, double valor) {
        Cliente cliente = clientes.get(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Conta contaOrigem = origem.equalsIgnoreCase("corrente") ? cliente.getContaCorrente() : cliente.getContaPoupanca();
        Conta contaDestino = destino.equalsIgnoreCase("corrente") ? cliente.getContaCorrente() : cliente.getContaPoupanca();

        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }
        if (contaOrigem.sacar(valor)) {
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }
}


