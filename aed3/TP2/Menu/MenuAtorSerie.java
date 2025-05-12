package aed3.TP2.Menu;

import aed3.Arquivo.ArquivoAtor;
import aed3.Arquivo.ArquivoAtuacao;
import aed3.Arquivo.ArquivoSerie;
import aed3.ArvoreB.*;
import aed3.TP2.Model.Ator;
import aed3.TP2.Model.Atuacao;

import java.util.Scanner;

public class MenuAtorSerie {
    private int id_serie;
    private ArquivoAtor arqAtor;
    private ArquivoSerie arqSeries;

    private static Scanner console = new Scanner(System.in);

    public MenuAtorSerie(int id_serie) throws Exception {
        this.id_serie = id_serie;
        arqAtor = new ArquivoAtor();
        arqSeries = new ArquivoSerie();
    }

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("\nMENU DE ELENCO - " + arqSeries.read(id_serie).getTitulo());
            System.out.println("1 - Adicionar ator");
            System.out.println("2 - Remover ator");
            System.out.println("3 - Listar elenco");
            System.out.println("4 - Alterar personagem");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirAtorSerie();
                    break;
                case 2:
                    excluirAtor();
                    break;
                case 3:
                    listarAtoresSerie();
                    break;
                case 4:
                    alterarAtorSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void incluirAtorSerie() {
        try {
            MenuAtorPrincipal menuAtor = new MenuAtorPrincipal();
            int idAtor = menuAtor.buscarAtorNome();
            if (idAtor < 0) return;

            System.out.print("Nome do personagem interpretado na série: ");
            String nomePersonagem = console.nextLine();

            ArquivoAtuacao arqAtuacao = new ArquivoAtuacao();
            Atuacao novaAtuacao = new Atuacao(-1, idAtor, id_serie, nomePersonagem);
            int id = arqAtuacao.create(novaAtuacao);

            if (id > 0) {
                System.out.println("Atuação adicionada com sucesso!");
            } else {
                System.out.println("Erro ao adicionar atuação.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao incluir ator na série.");
            e.printStackTrace();
        }
    }

   public boolean listarAtoresSerie() {
        boolean existe = false;
        try {
            ArquivoAtuacao arqAtuacao = new ArquivoAtuacao();

            System.out.println("\nElenco da série:");
            System.out.println("----------------------------");

            for (Atuacao atuacao : arqAtuacao.readPorSerie(id_serie)) {
                Ator ator = arqAtor.read(atuacao.getIdAtor());
                if (ator != null) {
                    existe = true;
                    System.out.printf("Ator: %s - Personagem: %s%n", ator.getNome(), atuacao.getPapel());
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar elenco.");
            e.printStackTrace();
        }
        return existe;
    }

    public void alterarAtorSerie() {
        try {
            MenuAtorPrincipal menuAtor = new MenuAtorPrincipal();
            int idAtor = menuAtor.buscarAtorNome();
            if (idAtor < 0) return;

            ArquivoAtuacao arqAtuacao = new ArquivoAtuacao();
            Atuacao atuacao = arqAtuacao.readBySerieEAtor(id_serie, idAtor);

            if (atuacao == null) {
                System.out.println("Este ator não está associado a esta série.");
                return;
            }

            System.out.printf("Personagem atual: %s%n", atuacao.getPapel());
            System.out.print("Novo nome do personagem: ");
            String novoNome = console.nextLine();
            atuacao.setPapel(novoNome);

            boolean sucesso = arqAtuacao.update(atuacao);
            if (sucesso) {
                System.out.println("Nome do personagem alterado com sucesso.");
            } else {
                System.out.println("Erro ao alterar o personagem.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao alterar personagem.");
            e.printStackTrace();
        }
    }

    public void excluirAtor() {
        try {
            MenuAtorPrincipal menuAtor = new MenuAtorPrincipal();
            int idAtor = menuAtor.buscarAtorNome();
            if (idAtor < 0) return;
            ArquivoAtuacao arqAtuacao = new ArquivoAtuacao();
            boolean ok = arqAtuacao.deleteBySerieEAtor(id_serie, idAtor);

            if (ok) {
                System.out.println("Ator removido do elenco com sucesso.");
            } else {
                System.out.println("Ator não encontrado na série.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao excluir ator da série.");
            e.printStackTrace();
        }
    }
}
