import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import service.Banco;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco();
        int opcao;

        do {
            System.out.println("Desafio Dio native - Desenvolvido por Paulo Sergio Maia - 2025");
            System.out.println("\n--- MENU NEU BANCO DIGITAL ---");
            System.out.println("1 - Adicionar Cliente");
            System.out.println("2 - Excluir Cliente");
            System.out.println("3 - Consultar todos os Clientes");
            System.out.println("4 - Consultar Clientes por tipo de conta");
            System.out.println("5 - Consultar saldo do Cliente");
            System.out.println("6 - Depositar");
            System.out.println("7 - Sacar");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF do cliente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Tipo da conta (corrente/poupanca): ");
                    String tipo = scanner.nextLine();
                    Cliente cliente = new Cliente(nome, cpf);
                    Conta conta = tipo.equalsIgnoreCase("corrente") ?
                            new ContaCorrente(cliente) : new ContaPoupanca(cliente);
                    banco.adicionarConta(conta);
                    System.out.println("Conta criada com sucesso.");
                    break;

                case 2:
                    System.out.print("Nome do cliente a excluir: ");
                    String nomeExcluir = scanner.nextLine();
                    banco.excluirCliente(nomeExcluir);
                    break;

                case 3:
                    banco.consultarClientes();
                    break;

                case 4:
                    System.out.print("Tipo da conta a consultar (corrente/poupanca): ");
                    String tipoConsulta = scanner.nextLine();
                    banco.consultarClientesPorTipo(tipoConsulta);
                    break;

                case 5:
                    System.out.print("Nome do cliente: ");
                    String nomeSaldo = scanner.nextLine();
                    System.out.print("Tipo da conta (corrente/poupanca): ");
                    String tipoSaldo = scanner.nextLine();
                    banco.consultarSaldo(nomeSaldo, tipoSaldo);
                    break;

                case 6:
                    System.out.print("Nome do cliente: ");
                    String nomeDep = scanner.nextLine();
                    System.out.print("Tipo da conta: ");
                    String tipoDep = scanner.nextLine();
                    System.out.print("Valor do depósito: ");
                    double valDep = scanner.nextDouble();
                    scanner.nextLine();
                    banco.depositar(nomeDep, tipoDep, valDep);
                    break;

                case 7:
                    System.out.print("Nome do cliente: ");
                    String nomeSaq = scanner.nextLine();
                    System.out.print("Tipo da conta: ");
                    String tipoSaq = scanner.nextLine();
                    System.out.print("Valor do saque: ");
                    double valSaq = scanner.nextDouble();
                    scanner.nextLine();
                    banco.sacar(nomeSaq, tipoSaq, valSaq);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}