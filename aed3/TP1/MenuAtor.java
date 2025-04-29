package aed3.TP1;
import java.util.Scanner;
import aed3.*;
import aed3.ArvoreB.ArvoreBMais;
import aed3.ArvoreB.ParIntInt;

public class MenuAtor {
    
    ArquivoAtor arqAtor;
    ArvoreBMais<ParTituloId> indiceNome;
    private static Scanner console = new Scanner(System.in);

    public MenuAtor() throws Exception {
        arqAtor = new ArquivoAtor();
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Ator");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Listar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarAtorNome();
                    break;
                case 2:
                    incluirAtor();
                    break;
                case 3:
                    alterarAtor();
                    break;
                case 4:
                    listarAtores();
                    break;
                case 5:
                    excluirAtor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public int buscarAtorNome() {
        System.out.println("\nBusca de ator por nome");
        System.out.print("\nNome: ");
        String nome = console.nextLine();

        if(nome.isEmpty())
            return -1;

        try {
            Ator[] atores = arqAtor.readNome(nome);
            if (atores.length > 0) {
                int n = 1;
                for(Ator a : atores) {
                    System.out.println((n++)+": "+a.getNome());
                }
                System.out.print("Escolha o ator: ");
                int o;
                do {
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o <= 0 || o > n - 1)
                        System.out.println("Escolha um número entre 1 e "+(n - 1));
                } while(o <= 0 || o > n - 1);
                mostrarAtor(atores[o - 1]);
                return atores[o - 1].id;
            } else {
                System.out.println("Nenhum ator encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar ator por nome!");
            e.printStackTrace();
        }
        return -1;
    }

    public void listarAtores() {
        try {
            Ator ator;
            for (int i = 1; i <= arqAtor.getLastId(); i++) {
                ator = arqAtor.read(i);
                mostrarAtor(ator);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar ID em arquivo!");
        }
    }

    public void incluirAtor() {
        String nome = "";

        System.out.println("\nInclusão do ator");

        //leitura do nome
        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length() == 0)
                return;
            if(nome.length() < 4)
                System.err.println("O nome do ator deve ter no mínimo 4 caracteres.");
        } while(nome.length() < 4);

        System.out.print("\nConfirmar a inclusão do ator? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp == 'S' || resp == 's') {
            try {
                Ator a = new Ator(nome);
                arqAtor.create(a);
                System.out.println("Ator incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o ator!");
                e.printStackTrace();
            }
        }
    }

    public void alterarAtor() {
        int id = buscarAtorNome();
        System.out.println("Pressione enter para seguir com a alteração.");
        console.nextLine();  // Limpar o buffer após o nextInt()
        if (id > 0) {
            try {
                Ator ator = arqAtor.read(id);
                if (ator != null) {
                    System.out.println("Ator encontrado:");
                    mostrarAtor(ator);

                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) {
                        ator.nome = novoNome;
                    }

                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);
                    if (resp == 'S' || resp == 's') {
                        boolean alterado = arqAtor.update(ator);
                        if (alterado) {
                            System.out.println("Ator alterado com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar o ator.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Ator não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível alterar o ator!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void excluirAtor() {
        int id = buscarAtorNome();
        if (id > 0) {
            try {
                Ator ator = arqAtor.read(id);
                if (ator != null) {
                    System.out.print("\nConfirma a exclusão do ator? (S/N) ");
                    char resp = console.next().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        boolean excluido = arqAtor.delete(id);
                        if (excluido) {
                            System.out.println("Ator excluído com sucesso.");
                        } else {
                            System.out.println("Erro ao excluir o ator.");
                        }
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                } else {
                    System.out.println("Ator não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível excluir o ator!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    public void mostrarAtor(Ator ator) {
        if (ator != null) {
            System.out.println("\nDetalhes do ator:");
            System.out.println("----------------------");
            System.out.printf("Nome......: %s%n", ator.nome);
            System.out.println("----------------------");
        }
    }
}
