package main;

import model.*;
import service.Banco;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco();

        int opcao;
        do {
            System.out.println("\n====== BANCO DIGITAL ======");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Excluir Cliente");
            System.out.println("3. Consultar Cliente por CPF");
            System.out.println("4. Consultar Cliente por Nome");
            System.out.println("5. Consultar Todos os Clientes");
            System.out.println("6. Consultar Clientes por Tipo de Conta");
            System.out.println("7. Depositar");
            System.out.println("8. Sacar");
            System.out.println("9. Consultar Saldo e Extrato");
            System.out.println("10. Transferência entre Contas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Deseja criar conta corrente (s/n)? ");
                    boolean cc = scanner.nextLine().equalsIgnoreCase("s");
                    System.out.print("Deseja criar conta poupança (s/n)? ");
                    boolean cp = scanner.nextLine().equalsIgnoreCase("s");
                    banco.adicionarCliente(nome, cpf, cc, cp);
                    break;
                case 2:
                    System.out.print("CPF do cliente: ");
                    banco.excluirCliente(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("CPF do cliente: ");
                    banco.consultarClientePorCpf(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Nome do cliente: ");
                    banco.consultarClientePorNome(scanner.nextLine());
                    break;
                case 5:
                    banco.consultarTodosClientes();
                    break;
                case 6:
                    System.out.print("Digite o tipo da conta (corrente/poupanca): ");
                    banco.consultarClientesPorTipoConta(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Deseja buscar por CPF ou nome? (cpf/nome): ");
                    String escolhaDep = scanner.nextLine();
                    String idDep = "";
                    if (escolhaDep.equalsIgnoreCase("cpf")) {
                        System.out.print("CPF do cliente: ");
                        idDep = scanner.nextLine();
                    } else {
                        System.out.print("Nome do cliente: ");
                        idDep = banco.getCpfPorNome(scanner.nextLine());
                        if (idDep == null) break;
                    }
                    System.out.print("Tipo da conta (corrente/poupanca): ");
                    String tipoDep = scanner.nextLine();
                    System.out.print("Valor: ");
                    double valorDep = scanner.nextDouble();
                    scanner.nextLine();
                    if (!banco.clienteTemConta(idDep, tipoDep)) {
                        System.out.println("Este cliente não possui conta " + tipoDep + ". Deseja criá-la agora? (s/n)");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            // Buscar o cliente pelo CPF para passar para o método
                            Cliente clienteDep = banco.consultarClientePorCpf(idDep);
                            banco.adicionarContaAoCliente(clienteDep, tipoDep);
                        } else {
                            System.out.println("Depósito não realizado.");
                            break;
                        }
                    }
                    banco.depositar(idDep, tipoDep, valorDep);
                    break;
                case 8:
                    System.out.print("Deseja buscar por CPF ou nome? (cpf/nome): ");
                    String escolhaSaq = scanner.nextLine();
                    String idSaq = "";
                    if (escolhaSaq.equalsIgnoreCase("cpf")) {
                        System.out.print("CPF do cliente: ");
                        idSaq = scanner.nextLine();
                    } else {
                        System.out.print("Nome do cliente: ");
                        idSaq = banco.getCpfPorNome(scanner.nextLine());
                        if (idSaq == null) break;
                    }
                    System.out.print("Tipo da conta (corrente/poupanca): ");
                    String tipoSaq = scanner.nextLine();
                    System.out.print("Valor: ");
                    double valorSaq = scanner.nextDouble();
                    scanner.nextLine();
                    if (!banco.clienteTemConta(idSaq, tipoSaq)) {
                        System.out.println("Este cliente não possui conta " + tipoSaq + ".");
                        break;
                    }
                    banco.sacar(idSaq, tipoSaq, valorSaq);
                    break;
                case 9:
                    System.out.print("CPF do cliente: ");
                    String cpfSaldo = scanner.nextLine();
                    banco.consultarSaldoEExtrato(cpfSaldo);
                    break;
                case 10:
                    System.out.print("Deseja buscar por CPF ou nome? (cpf/nome): ");
                    String escolhaTransf = scanner.nextLine();
                    String idTransf = "";
                    if (escolhaTransf.equalsIgnoreCase("cpf")) {
                        System.out.print("CPF do cliente: ");
                        idTransf = scanner.nextLine();
                    } else {
                        System.out.print("Nome do cliente: ");
                        idTransf = banco.getCpfPorNome(scanner.nextLine());
                        if (idTransf == null) break;
                    }
                    System.out.print("Transferir de (corrente/poupanca): ");
                    String origem = scanner.nextLine();
                    System.out.print("Para (corrente/poupanca): ");
                    String destino = scanner.nextLine();
                    System.out.print("Valor: ");
                    double valorTransf = scanner.nextDouble();
                    scanner.nextLine();
                    if (!banco.clienteTemConta(idTransf, origem)) {
                        System.out.println("Cliente não possui conta " + origem + ".");
                        break;
                    }
                    if (!banco.clienteTemConta(idTransf, destino)) {
                        System.out.println("Cliente não possui conta " + destino + ". Deseja criá-la agora? (s/n)");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            // Buscar o cliente pelo CPF para passar para o método
                            Cliente clienteTransf = banco.consultarClientePorCpf(idTransf);
                            banco.adicionarContaAoCliente(clienteTransf, destino);
                        } else {
                            System.out.println("Transferência não realizada.");
                            break;
                        }
                    }
                    banco.transferirEntreContasDoMesmoCliente(idTransf, origem, destino, valorTransf);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}