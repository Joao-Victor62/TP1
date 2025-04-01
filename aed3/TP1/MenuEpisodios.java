package aed3.TP1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import aed3.ArvoreB.ArvoreBMais;
import aed3.ArvoreB.ParIntInt;
import aed3.TP1.Episodio;
import aed3.ArquivoEpisodio;

public class MenuEpisodios {
    private int id_serie;
    private String titulo;
    private ArquivoEpisodio arqEpisodios;
    private static Scanner console = new Scanner(System.in);




    public MenuEpisodios(int id, String titulo) throws Exception
    {
        id_serie = id;
        arqEpisodios = new ArquivoEpisodio(id);
        this.titulo = titulo;
    }

    public void menu() throws Exception {

        int opcao;
        do {

            System.out.println("\n\nPUCStreaming 1.0");
            System.out.println( "-----------");
            System.out.println("> Início > " + titulo + " > Episódios");
            System.out.println("\n1 - Incluir Episódio");
            System.out.println("2 - Listar Episódio");
            System.out.println("3 - Alterar Episódio");
            System.out.println("4 - Excluir Episódio");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    listarEps();
                    //System.out.println("Opção inválida!");
                    break;
                case 3:
                    alterarEpisodio();
                    //System.out.println("Opção inválida!");
                    break;
                case 4:
                    excluirEpisodio();
                    //System.out.println("Opção inválida!");
                    break;
                case 0:
                    MenuSeries menuSerie = new MenuSeries();
                    menuSerie.menu();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public int listarEps() {
        System.out.println("Escolha o episódio que deseja mais detalhes: ");
        try {
            Episodio[] eps = arqEpisodios.readSerie(id_serie);  // Chama o método de leitura da classe Arquivo
                if (eps.length > 0) {
                    int n = 1;
                    for (Episodio e : eps) {
                        System.out.println((n++) + ": " + e.getNome());
                    }
                int o;
                do {
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }
                while(o<=0 || o>n-1);
                mostraEpisodio(eps[o-1]);
                return eps[o-1].getId();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episodio");
        String nome = "";
        int temporada = 0;
        LocalDate data = null;
        int duracao = 0;
        int id_serie = this.id_serie;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dadosCorretos = false;
        do {
            System.out.print("Título (min. de 4 letras): ");
            nome = console.nextLine();
            if(nome.length()>=4)
                dadosCorretos = true;
            else
                System.err.println("O título do episodio deve ter no mínimo 4 caracteres.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Temporada (>= 1): ");
            temporada = console.nextInt();
            if(temporada >= 1)
                dadosCorretos = true;
            else
                System.err.println("A temporada deve ser a primeira ou superior.");
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Data de lançamento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            try {
                data = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        dadosCorretos = false;
        do {
            System.out.print("Duracao em minutos: ");
            if (console.hasNextInt()) {
                duracao = console.nextInt();
                if(duracao < 128)
                    dadosCorretos = true;
            }
            if(!dadosCorretos)
                System.err.println("Duracao inválida! Por favor, insira um número válido entre 1 e 127.");
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        System.out.print("\nConfirma a inclusão do episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio e = new Episodio(nome, temporada, data, duracao, id_serie);
                arqEpisodios.create(e);

                System.out.println("Episodio incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o episódio!");
            }
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteração de episodio. Escolha episódio para alterar.");
        int id = listarEps();
        boolean dadosCorretos;

        try {
            Episodio ep = arqEpisodios.read(id);
            if (ep != null) {
               // mostraLivro(livro);  // Exibe os dados do livro para confirmação

                // Alteração de titulo
                String novoTitulo;
                dadosCorretos = false;
                do {
                    System.out.print("Novo título (deixe em branco para manter o anterior): ");
                    novoTitulo = console.nextLine();
                    if(!novoTitulo.isEmpty()) {
                        if(novoTitulo.length()>=4) {
                            ep.setNome(novoTitulo);  // Atualiza o título se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O título do livro deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração da temporada
                String novaTemporada;
                dadosCorretos = false;
                do {
                    System.out.print("Nova Temporada (deixe em branco para manter a anterior): ");
                    novaTemporada = console.nextLine();
                    if(!novaTemporada.isEmpty()) {
                        try {
                            int edicao = Integer.parseInt(novaTemporada);
                            if(edicao>0 && edicao<128) {
                                ep.setTemporada((byte)edicao);  // Atualiza a temporada, se fornecida
                                dadosCorretos = true;
                            } else
                                    System.err.println("A Temporada deve ser um número entre 1 e 127.");
                        } catch(NumberFormatException e) {
                            System.err.println("Número de temporada inválido! Por favor, insira um número válido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);



                // Alteração de data de lançamento
                String novaData;
                dadosCorretos = false;
                do {
                    System.out.print("Nova data de lançamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                    novaData = console.nextLine();
                    if (!novaData.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            ep.setData(LocalDate.parse(novaData, formatter));  // Atualiza a data de lançamento se fornecida
                            dadosCorretos = true;
                        } catch (Exception e) {
                            System.err.println("Data inválida. Valor mantido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração da duração
                String novaDuracao;
                dadosCorretos = false;
                do {
                    System.out.print("Nova duração (deixe em branco para manter a anterior): ");
                    novaDuracao = console.nextLine();
                    if(!novaDuracao.isEmpty()) {
                        try {
                            int edicao = Integer.parseInt(novaDuracao);
                            if(edicao>0 && edicao<128) {
                                ep.setDuracao((byte)edicao);  // Atualiza a duração, se fornecida
                                dadosCorretos = true;
                            } else
                                System.err.println("A Temporada deve ser um número entre 1 e 127.");
                        } catch(NumberFormatException e) {
                            System.err.println("Número de duração inválido! Por favor, insira um número válido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);


                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqEpisodios.update(ep);
                    if (alterado) {
                        System.out.println("Livro alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o livro.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                    console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o livro!");
            e.printStackTrace();
        }
        
    }


    public void excluirEpisodio() {
        System.out.println("\nExclusão de Episodio");
        int id = listarEps();
        boolean dadosCorretos = false;

        try {
            // Tenta ler o Episodio com o ID fornecido
            Episodio episodio = arqEpisodios.read(id);
            if (episodio != null) {
                System.out.print("\nConfirma a exclusão do Episodio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodios.delete(id);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episodio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Episodio.");
                    }
                    
                } else {
                    System.out.println("Exclusão cancelada.");
                }
                console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o Episodio!");
            e.printStackTrace();
        }
    }

    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.println("----------------------");
            System.out.printf("Nome......: %s%n", episodio.getNome());
            System.out.printf("Temporada....: %s%n", episodio.getTemporada());
            System.out.printf("Data de lançamento.....: %s%n", episodio.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.printf("Duração....: %s%n", episodio.getDuracao());
            //System.out.printf("Serie....: %s%n", episodio.getSerie());
            System.out.println("----------------------");
        }
    }



    public void povoar() throws Exception {
        arqEpisodios.create(new Episodio("Lucifer, Fique. Bom Diabo.", 1, LocalDate.of(2016,02,8), 50));
    }

}