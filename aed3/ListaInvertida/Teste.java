package aed3.ListaInvertida;

import java.util.List;

import aed3.Arquivo.ArquivoAtor;
import aed3.Arquivo.ArquivoSerie;
import aed3.TP2.Model.Ator;
import aed3.TP2.Model.Serie;

public class Teste {
    public static void main(String[] args) {
        LI<Serie> li = new LI<>("series");

       
        try {
            li.listaInvertida.print();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //teste para o buscar
        li.buscar("hora da programacao na intruducao");//colcoar a frase q vais er testada
    }
}
