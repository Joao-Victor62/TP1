package aed3.TP2;
import java.util.Scanner;
import aed3.*;
import aed3.ArvoreB.ArvoreBMais;
import aed3.ArvoreB.ParIntInt;

public class MenuSeries {
    
    ArquivoSerie arqSeries;
    ArvoreBMais<ParTituloId> indiceTitulo;
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception {
        arqSeries = new ArquivoSerie();
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
            System.out.println("4 - Listar");
            System.out.println("5 - Excluir");
            System.out.println("6 - Buscar serie e episodio");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    buscarSerieTitulo();
                    break;
                case 2:
                    incluirSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    listarSeries();
                    break;
                case 5:
                    excluirSerie();
                    break;
                case 6:
                    buscarSerieEEp();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public int buscarSerieTitulo() {
        System.out.println("\nBusca de série por título");
        System.out.print("\nTítulo: ");
        String titulo = console.nextLine();  // Lê o título digitado pelo usuário

        if(titulo.isEmpty())
            return -1;

        try {
            Serie[] series = arqSeries.readTitulo(titulo);  // Chama o método de leitura da classe Arquivo
            if (series.length>0) {
                int n=1;
                for(Serie l : series) {
                    System.out.println((n++)+": "+l.getTitulo());
                }
                System.out.print("Escolha o serie: ");
                int o;
                do {
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarSerie(series[o-1]);  // Exibe os detalhes do livro encontrado
                return series[o-1].id;
            } else {
                System.out.println("Nenhuma serie encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar serie por titulo!");
            e.printStackTrace();
        }
        return -1;
    }

    public void buscarSerieEEp() {
       int id = buscarSerieTitulo();
       if(id != -1)
       {
           try {
               menuEpisodioSerie(id);
           }
           catch(Exception e) {
               System.out.println("Erro ao entrar no menu de episódios!");
           }
       }

    }

    public void listarSeries()
    {
        try
        {
            Serie serie;
            for (int i = 1; i<=arqSeries.getLastId(); i++)
            {
                serie = arqSeries.read(i);
                mostrarSerie(serie);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar ID em arquivo!");
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
                System.out.println("Serie incluída com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a serie!");
                e.printStackTrace();
            }
        }
    }

    public void alterarSerie() {
        int id = buscarSerieTitulo();  // Lê o ID da serie
        System.out.println("Pressione enter para seguir com a alteração.");
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

        int id = buscarSerieTitulo();
        if (id > 0) {
            try {
                // Tenta ler a serie com o ID fornecido
                Serie serie = arqSeries.read(id);
                if (serie != null) {
                    //System.out.println("Serie encontrado:");
                    //mostrarSerie(serie);  // Exibe os dados da serie para confirmação

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
    public void menuEpisodioSerie(int serie) throws Exception {
        System.out.print("\nDeseja adicionar, excluir ou atualizar algum episódio a série selecionada? (S/N) ");
        char resp = console.nextLine().charAt(0);
        MenuEpisodios menuEpisodio = new MenuEpisodios(serie, arqSeries.read(serie).getTitulo());
        menuEpisodio.menu();
    }
}
