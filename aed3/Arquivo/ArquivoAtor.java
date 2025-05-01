package aed3.Arquivo;

import aed3.ArvoreB.ArvoreBMais;
import aed3.Hash.ParTituloId;
import aed3.TP2.Model.Ator;

import java.util.ArrayList;

public class ArquivoAtor extends Arquivo<Ator> {
    Arquivo<Ator> arqAtores;
    ArvoreBMais<ParTituloId> indiceNome;

    public ArquivoAtor() throws Exception {
        super("Atores", Ator.class.getConstructor());
        indiceNome = new ArvoreBMais<>(
                ParTituloId.class.getConstructor(),
                5,
                "./dados/" + "atores" + "/indiceNome.db");
    }

    @Override
    public int create(Ator a) throws Exception {
        int id = super.create(a);
        indiceNome.create(new ParTituloId(a.getNome(), id));
        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;
        ArrayList<ParTituloId> ptis = indiceNome.read(new ParTituloId(nome, -1));
        if (ptis.size() > 0) {
            Ator[] atores = new Ator[ptis.size()];
            int i = 0;
            for (ParTituloId pti : ptis)
                atores[i++] = read(pti.getId());
            return atores;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Ator a = read(id);
        if (a != null) {
            if (super.delete(id))
                return indiceNome.delete(new ParTituloId(a.getNome(), id));
        }
        return false;
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator a = read(novoAtor.getID());
        if (a != null) {
            if (super.update(novoAtor)) {
                if (!a.getNome().equals(novoAtor.getNome())) {
                    indiceNome.delete(new ParTituloId(a.getNome(), a.getID()));
                    indiceNome.create(new ParTituloId(novoAtor.getNome(), novoAtor.getID()));
                }
                return true;
            }
        }
        return false;
    }

}
