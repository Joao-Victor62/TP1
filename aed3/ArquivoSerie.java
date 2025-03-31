package aed3;

import aed3.ArvoreB.ArvoreBMais;
import aed3.TP1.Serie;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ArquivoSerie extends Arquivo<Serie>{
    Arquivo<Serie> arqSeries;
    ArvoreBMais<ParTituloId> indiceTitulo;

    public ArquivoSerie() throws Exception {
        super("Series", Serie.class.getConstructor());
        indiceTitulo = new ArvoreBMais<>(
                ParTituloId.class.getConstructor(),
                5,
                "./dados/"+"series"+"/indiceTitulo.db");
    }

    @Override
    public int create(Serie l) throws Exception {
        int id = super.create(l);
        indiceTitulo.create(new ParTituloId(l.getTitulo(), id));
        return id;
    }


    public Serie[] readTitulo(String titulo) throws Exception {
        if(titulo.length()==0)
            return null;
        ArrayList<ParTituloId> ptis = indiceTitulo.read(new ParTituloId(titulo, -1));
        if(ptis.size()>0) {
            Serie[] Series = new Serie[ptis.size()];
            int i=0;
            for(ParTituloId pti: ptis)
                Series[i++] = read(pti.getId());
            return Series;
        }
        else
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie l = read(id);   // na superclasse
        if(l!=null) {
            if(super.delete(id))
                return indiceTitulo.delete(new ParTituloId(l.getTitulo(), id));
        }
        return false;
    }

    @Override
    public boolean update(Serie novoSerie) throws Exception {
        Serie l = read(novoSerie.getID());    // na superclasse
        if(l!=null) {
            if(super.update(novoSerie)) {
                if(!l.getTitulo().equals(novoSerie.getTitulo())) {
                    indiceTitulo.delete(new ParTituloId(l.getTitulo(), l.getID()));
                    indiceTitulo.create(new ParTituloId(novoSerie.getTitulo(), novoSerie.getID()));
                }
                return true;
            }
        }
        return false;
    }

}

