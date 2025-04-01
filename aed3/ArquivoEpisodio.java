package aed3;

import aed3.ArvoreB.ArvoreBMais;
import aed3.ArvoreB.ParIntInt;
import aed3.TP1.Episodio;
import java.util.ArrayList;
//import TP1.ArvoreBMais;
import aed3.RegistroHashExtensivel;
import aed3.Arquivo;
import aed3.TP1.Serie;

public class ArquivoEpisodio extends Arquivo<Episodio>
{
    Arquivo<Episodio> arqEpisodios;
    ArvoreBMais<ParIntInt> arvore;
   int id_serie;

    public ArquivoEpisodio() throws Exception {
        super("Episodios", Episodio.class.getConstructor());
        arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");
    }

    public ArquivoEpisodio(int idserie) throws Exception {
        super("Episodios", Episodio.class.getConstructor());
        arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");
        id_serie = idserie;
    }

    @Override
    public int create(Episodio e) throws Exception {
        int id = super.create(e);
        arvore.create(new ParIntInt(this.id_serie, id));
        return id;
    }

    public Episodio[] readSerie(int id)
    {
        try {
            ArrayList<ParIntInt> ptis = arvore.read(new ParIntInt(id, -1));
            if(ptis.size()>0) {
                Episodio[] episodios = new Episodio[ptis.size()];
                int i=0;
                for(ParIntInt pti: ptis)
                    episodios[i++] = read(pti.getNum2());
                return episodios;
            }
            else
                return null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio l = read(id);   // na superclasse
        if(l!=null) {
            if(super.delete(id)) {
                arvore.delete(new ParIntInt(id_serie, id));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEpisodio) throws Exception {
        Episodio l = read(novoEpisodio.getId());    // na superclasse
        if(l!=null) {
            super.update(novoEpisodio);
            return true;
        }
        else return false;
    }
}
