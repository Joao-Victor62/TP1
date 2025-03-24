
import java.util.Scanner;
import aed3.*;

public class MenuSeries {
    
    Arquivo<Serie> arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new Arquivo<>("series", Serie.class.getConstructor());
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nAEDsIII");
            System.out.println("-------");
            System.out.println("> Início > Series");
            System.out.println("\n1 - Buscar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Alterar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }


    public void buscarSerie() {
        System.out.print("\nID da Serie: ");
        int id = console.nextInt();  // Lê o ID digitado pelo usuário
        console.nextLine();  // Limpa o buffer após o nextInt()


        if(id>0) {            
            try {
                Serie serie = arqSeries.read(id);  // Chama o método de leitura da classe Arquivo
                if (serie != null) {
                    mostrarSerie(serie);  // Exibe os detalhes do cliente encontrado
                } else {
                    System.out.println("Serie não encontrado.");
                }
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível buscar a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }   


    public void incluirSerie() {
        String nome = "";
        String sinopse = "";
        String streaming = "";
        int anoLancamento = 0;
        

        System.out.println("\nInclusão da serie");

        //leitura do nome
        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
            if(nome.length()<4)
                System.err.println("O nome do cliente deve ter no mínimo 4 caracteres.");
        } while(nome.length()<4);

        //ler a sinopse
        do {
            System.out.print("Sinopse: ");
            sinopse = console.nextLine();
        } while(sinopse.isEmpty());
        

        
        //ler o streaming
        do {
            System.out.print("Streaming: ");
            streaming = console.nextLine();
        } while(streaming.isEmpty());
            
        //ler ano de lançamento (teste para ver se valor inserido)
        do {
            System.out.print("Ano de lançamento: ");
            anoLancamento = console.nextInt();
            console.nextLine();  // Limpa o buffer após o nextInt()

        } while(anoLancamento <= 0);
        
        
        

        System.out.print("\nConfirmar a inclusão da serie? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Serie c = new Serie(nome, sinopse, streaming, anoLancamento);
                arqSeries.create(c);
                System.out.println("Serie incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a serie!");
            }
        }
    }

    public void alterarSerie() {
        System.out.print("\nDigite o ID da serie a ser alterado: ");
        int id = console.nextInt();  // Lê o ID da serie
        console.nextLine();  // Limpar o buffer após o nextInt()

        if (id > 0) {
            try {
                // Tenta ler a serie com o ID fornecido
                Serie serie = arqSeries.read(id);
                if (serie != null) {
                    System.out.println("Cliente encontrado:");
                    mostrarSerie(serie);  // Exibe os dados do cliente para confirmação

                    // Alteração de nome
                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) {
                        serie.nome = novoNome;  // Atualiza o nome se fornecido
                    }

                    // Alteração da sinopse
                    System.out.print("Nova sinopse (deixe em branco para manter o anterior): ");
                    String novaSinopse = console.nextLine();
                    if (!novaSinopse.isEmpty()) {
                        serie.sinopse = novaSinopse;  // Atualiza a sinopse se fornecido
                    }

                    // Alteração de streaming
                    System.out.print("Novo streaming (deixe em branco para manter o anterior): ");
                    String novoStreaming = console.nextLine();
                    if (!novoStreaming.isEmpty()) {
                        try {
                            serie.streaming = novoStreaming;  // Atualiza o streaming se fornecido
                        } catch (NumberFormatException e) {
                            System.err.println("Streaming inválido. Valor mantido.");
                        }
                    }

                    // Alteração de ano
                    System.out.print("Novo ano (deixe em branco para manter o anterior): ");
                    String novoAno = console.nextLine();
                    if (!novoAno.isEmpty()) {
                        try {
                            serie.anoLancamento = Integer.parseInt(novoAno);
                        } catch (Exception e) {
                            System.err.println("Ano inválido. Valor mantido.");
                        }
                    }


                    // Confirmação da alteração
                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);
                    if (resp == 'S' || resp == 's') {
                        // Salva as alterações no arquivo
                        boolean alterado = arqSeries.update(serie);
                        if (alterado) {
                            System.out.println("Serie alterado com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar a serie.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Serie não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível alterar a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }


    public void excluirSerie() {
        System.out.print("\nDigite o ID da serie a ser excluído: ");
        int id = console.nextInt();  // Lê o ID digitado pelo usuário

        if (id > 0) {
            try {
                // Tenta ler a serie com o ID fornecido
                Serie serie = arqSeries.read(id);
                if (serie != null) {
                    System.out.println("Serie encontrado:");
                    mostrarSerie(serie);  // Exibe os dados da serie para confirmação

                    System.out.print("\nConfirma a exclusão da serie? (S/N) ");
                    char resp = console.next().charAt(0);  // Lê a resposta do usuário

                    if (resp == 'S' || resp == 's') {
                        boolean excluido = arqSeries.delete(id);  // Chama o método de exclusão no arquivo
                        if (excluido) {
                            System.out.println("Serie excluído com sucesso.");
                        } else {
                            System.out.println("Erro ao excluir a serie.");
                        }
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                } else {
                    System.out.println("Serie não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível excluir a serie!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

 
    public void mostrarSerie(Serie serie) {
    if (serie != null) {
        System.out.println("\nDetalhes da serie:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", serie.nome);
        System.out.printf("Sinopse.......: %s%n", serie.sinopse);
        System.out.printf("Streaming...:   %s%n", serie.streaming);
        System.out.printf("Ano de lancamento: %s%n", serie.anoLancamento);
        System.out.println("----------------------");
    }
}
}
