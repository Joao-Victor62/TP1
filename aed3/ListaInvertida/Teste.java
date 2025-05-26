package aed3.ListaInvertida;

import aed3.Arquivo.ArquivoAtor;
import aed3.Arquivo.ArquivoSerie;
import aed3.TP2.Model.Ator;
import aed3.TP2.Model.Serie;

public class Teste {
    public static void main(String[] args) {
        try {
            ArquivoSerie arquivoSerie = new ArquivoSerie();
            LI<Serie> lista = new LI<Serie>("Series");
            Serie serie = arquivoSerie.read(1);
            lista.insert(serie.getNome(), serie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
