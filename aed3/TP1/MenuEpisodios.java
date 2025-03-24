package TP1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP1.Episodio;
import TP1.ArquivoEpisodio;

public class MenuEpisodios {

    ArquivoEpisodio arqEpisodios;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodios() throws Exception {
        arqEpisodios = new ArquivoEpisodio();
    }

    public void menu() {

        int opcao;
        do {

            System.out.println("\n\nPUCStreaming 1.0");
            System.out.println( "-----------");
            System.out.println("> Início > Episódios");
            System.out.println("\n1 - Incluir");
            System.out.println("2 - Buscar por Título");
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
                    incluirEpisodio();
                    break;
                case 2:
                    //buscarEpisodiosTitulo();
                    System.out.println("Opção inválida!");
                    break;
                case 3:
                    //alterarEpisodio();
                    System.out.println("Opção inválida!");
                    break;
                case 4:
                    //excluirEpisodio();
                    System.out.println("Opção inválida!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }
    /* 
    public void buscarEpisodiosTitulo() {
        System.out.println("\nBusca de livro por título");
        System.out.print("\nTítulo: ");
        String titulo = console.nextLine();  // Lê o título digitado pelo usuário

        if(titulo.isEmpty())
            return; 

        try {
            Episodio[] Episodios = arqEpisodios.readTitulo(titulo);  // Chama o método de leitura da classe Arquivo
            if (Episodios.length>0) {
                int n=1;
                for(Livro l : Episodios) {
                    System.out.println((n++)+": "+l.getTitulo());
                }
                System.out.print("Escolha o livro: ");
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
                mostraLivro(Episodios[o-1]);  // Exibe os detalhes do livro encontrado
            } else {
                System.out.println("Nenhum livro encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar os Episodios!");
            e.printStackTrace();
        }
    }   
    */

    public void incluirEpisodio() {
        System.out.println("\nInclusão de Episodio");
        String nome = "";
        int temporada = 0;
        LocalDate data = null;
        int duracao = 0;
        int id_serie = 0;
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

        dadosCorretos = false;
        do {
            System.out.print("Id da serie: ");
            if (console.hasNextInt()) {
                id_serie = console.nextInt();
                dadosCorretos = true;
            } else {
                System.err.println("Serie não existe! Por favor, insira um número válido.");
            }
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
    /* 
    public void alterarLivro() {
        System.out.println("\nAlteração de livro");
        String isbn;
        boolean dadosCorretos;

        dadosCorretos = false;
        do {
            System.out.print("\nISBN (13 dígitos): ");
            isbn = console.nextLine();  // Lê o ISBN digitado pelo usuário

            if(isbn.isEmpty())
                return; 

            // Validação do ISBN (13 dígitos e composto apenas por números)
            if (Livro.isValidISBN13(isbn)) 
                dadosCorretos = true;  // ISBN válido
            else 
                System.out.println("ISBN inválido. O ISBN deve conter exatamente 13 dígitos numéricos, sem pontos e traços.");
        } while (!dadosCorretos);


        try {
            // Tenta ler o livro com o ID fornecido
            Livro livro = arqEpisodios.readISBN(isbn);
            if (livro != null) {
                mostraLivro(livro);  // Exibe os dados do livro para confirmação

                // Alteração de ISBN
                String novoIsbn;
                dadosCorretos = false;
                do {
                    System.out.print("Novo ISBN (deixe em branco para manter o anterior): ");
                    novoIsbn = console.nextLine();
                    if(!novoIsbn.isEmpty()) {
                        if(Livro.isValidISBN13(novoIsbn)) {
                            livro.setIsbn(novoIsbn);  // Atualiza o ISBN se fornecido
                            dadosCorretos = true;
                        } else 
                            System.err.println("O ISBN deve ter exatamente 13 dígitos.");
                    } else 
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de titulo
                String novoTitulo;
                dadosCorretos = false;
                do {
                    System.out.print("Novo título (deixe em branco para manter o anterior): ");
                    novoTitulo = console.nextLine();
                    if(!novoTitulo.isEmpty()) {
                        if(novoTitulo.length()>=4) {
                            livro.setTitulo(novoTitulo);  // Atualiza o título se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O título do livro deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de autor
                String novoAutor;
                dadosCorretos = false;
                do {
                    System.out.print("Novo autor (deixe em branco para manter o anterior): ");
                    novoAutor = console.nextLine();
                    if(!novoAutor.isEmpty()) {
                        if(novoAutor.length()>=4) {
                            livro.setAutor(novoAutor);  // Atualiza o título se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome do autor deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração da edição
                String novaEdicao;
                dadosCorretos = false;
                do {
                    System.out.print("Nova edição (deixe em branco para manter a anterior): ");
                    novaEdicao = console.nextLine();
                    if(!novaEdicao.isEmpty()) {
                        try {
                            int edicao = Integer.parseInt(novaEdicao);
                            if(edicao>0 && edicao<128) {
                                livro.setEdicao((byte)edicao);  // Atualiza a edição, se fornecida
                                dadosCorretos = true;
                            } else
                                    System.err.println("A edição deve ser um número entre 1 e 127.");
                        } catch(NumberFormatException e) {
                            System.err.println("Número de edição inválido! Por favor, insira um número válido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de preço
                String novoPreco;
                dadosCorretos = false;
                do {
                    System.out.print("Novo preço (deixe em branco para manter o anterior): ");
                    novoPreco = console.nextLine();
                    if(!novoPreco.isEmpty()) {
                        try {
                            livro.setPreco(Float.parseFloat(novoPreco));  // Atualiza o preço, se fornecido
                            dadosCorretos = true;
                        } catch(NumberFormatException e) {
                            System.err.println("Preço inválido! Por favor, insira um número válido.");
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
                            livro.setDataLancamento(LocalDate.parse(novaData, formatter));  // Atualiza a data de lançamento se fornecida
                        } catch (Exception e) {
                            System.err.println("Data inválida. Valor mantido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqEpisodios.update(livro);
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
        System.out.println("\nExclusão de livro");
        int id;
        boolean dadosCorretos = false;

        System.out.print("\nId: ");
        id = console.nextInt();  // Lê o ISBN digitado pelo usuário
            if(id<0)
                return; 
        try {
            // Tenta ler o livro com o ID fornecido
            Episodio episodio = arqEpisodios.readISBN(id);
            if (episodio != null) {
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);  // Exibe os dados do Episodio para confirmação

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
    */
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