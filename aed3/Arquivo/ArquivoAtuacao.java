package aed3.Arquivo;

import aed3.ArvoreB.ArvoreBMais;
import aed3.TP2.Model.Atuacao;
import aed3.ArvoreB.ParIdAtorIdAtuacao;
import aed3.ArvoreB.ParIdSerieIdAtuacao;

import java.util.ArrayList;

public class ArquivoAtuacao extends Arquivo<Atuacao> {

    private ArvoreBMais<ParIdAtorIdAtuacao> indiceAtorSerie;
    private ArvoreBMais<ParIdSerieIdAtuacao> indiceSerieAtor;

    public ArquivoAtuacao() throws Exception {
        super("Atuacoes", Atuacao.class.getConstructor());

        indiceAtorSerie = new ArvoreBMais<>(
                ParIdAtorIdAtuacao.class.getConstructor(),
                5,
                "./dados/atuacoes/indiceAtorSerie.db");

        indiceSerieAtor = new ArvoreBMais<>(
                ParIdSerieIdAtuacao.class.getConstructor(),
                5,
                "./dados/atuacoes/indiceSerieAtor.db");
    }

    @Override
    public int create(Atuacao a) throws Exception {
        int id = super.create(a);
        indiceAtorSerie.create(new ParIdAtorIdAtuacao(a.getIdAtor(), id));
        indiceSerieAtor.create(new ParIdSerieIdAtuacao(a.getIdSerie(), id));
        return id;
    }

    public int[] readPorAtor(int idAtor) throws Exception {
        ArrayList<ParIdAtorIdAtuacao> pares = indiceAtorSerie.read(new ParIdAtorIdAtuacao(idAtor, -1));
        int[] listAtuacoes = new int[pares.size()];
        int i = 0;
        for (ParIdAtorIdAtuacao par : pares) {
            listAtuacoes[i++] = par.getIdAtuacao();
        }
        return listAtuacoes;
    }

    public Atuacao[] readPorAtor2(int idAtor) throws Exception {
        ArrayList<ParIdAtorIdAtuacao> pares = indiceAtorSerie.read(new ParIdAtorIdAtuacao(idAtor, -1));
        if (pares.size() > 0) {
            Atuacao[] atuacoes = new Atuacao[pares.size()];
            int i = 0;
            for (ParIdAtorIdAtuacao par : pares)
                atuacoes[i++] = read(par.getIdAtuacao());
            return atuacoes;
        }
        return null;
    }

    public Atuacao[] readPorSerie(int idSerie) throws Exception {
        ArrayList<ParIdSerieIdAtuacao> pares = indiceSerieAtor.read(new ParIdSerieIdAtuacao(idSerie, -1));
        if (pares.size() > 0) {
            Atuacao[] atuacoes = new Atuacao[pares.size()];
            int i = 0;
            for (ParIdSerieIdAtuacao par : pares)
                atuacoes[i++] = read(par.getIdAtuacao());
            return atuacoes;
        }
        return null;
    }



    public Atuacao readBySerieEAtor(int idSerie, int idAtor) throws Exception {
        Atuacao[] atuacoes = readPorSerie(idSerie);
        if (atuacoes != null) {
            for (Atuacao atuacao : atuacoes) {
                if (atuacao.getIdAtor() == idAtor) {
                    return atuacao;
                }
            }
        }
        return null;
    }

    public boolean deleteBySerieEAtor(int idSerie, int idAtor) throws Exception {
        Atuacao[] atuacoes = readPorSerie(idSerie);
        if (atuacoes != null) {
            for (Atuacao atuacao : atuacoes) {
                if (atuacao.getIdAtor() == idAtor) {
                    return delete(atuacao.getId());
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Atuacao a = read(id);
        if (a != null) {
            if (super.delete(id)) {
                indiceAtorSerie.delete(new ParIdAtorIdAtuacao(a.getIdAtor(), id));
                indiceSerieAtor.delete(new ParIdSerieIdAtuacao(a.getIdSerie(), id));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Atuacao novaAtuacao) throws Exception {
        Atuacao a = read(novaAtuacao.getId());
        if (a != null) {
            if (super.update(novaAtuacao)) {
                if (a.getIdAtor() != novaAtuacao.getIdAtor() || a.getIdSerie() != novaAtuacao.getIdSerie()) {
                    indiceAtorSerie.delete(new ParIdAtorIdAtuacao(a.getIdAtor(), a.getId()));
                    indiceSerieAtor.delete(new ParIdSerieIdAtuacao(a.getIdSerie(), a.getId()));

                    indiceAtorSerie.create(new ParIdAtorIdAtuacao(novaAtuacao.getIdAtor(), novaAtuacao.getId()));
                    indiceSerieAtor.create(new ParIdSerieIdAtuacao(novaAtuacao.getIdSerie(), novaAtuacao.getId()));
                }
                return true;
            }
        }
        return false;
    }

    public int getLastId() throws Exception
    {
        return super.getLastId();
    }
}
