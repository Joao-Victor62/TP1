package aed3;

import aed3.ArvoreB.ArvoreBMais;
import aed3.ArvoreB.ParIntInt;
import aed3.TP1.Episodio;
import java.util.ArrayList;
//import TP1.ArvoreBMais;
import aed3.RegistroHashExtensivel;
import aed3.Arquivo;

public class ArquivoEpisodio extends Arquivo<Episodio>
{
    Arquivo<Episodio> arqEpisodios;
   // HashExtensivel<ParISBNID> indiceISBN;
   ArvoreBMais<ParIntInt> arvore;
   int id_serie;

    public ArquivoEpisodio() throws Exception {
        super("Episodios", Episodio.class.getConstructor());
        arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");
        /*
            indiceISBN = new HashExtensivel<>(
            ParISBNID.class.getConstructor(),
            4,
            "./dados/"+nomeEntidade+"/indiceISBN.d.db",
            "./dados/"+nomeEntidade+"/indiceISBN.c.db"
            );
     */
    }

    public ArquivoEpisodio(int idserie) throws Exception {
        super("Episodios", Episodio.class.getConstructor());
        arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");
        id_serie = idserie;
    }

    @Override
    public int create(Episodio e) throws Exception {
        int id = super.create(e);

       // indiceISBN.create(new ParISBNID(e.getISBN(), id));
        arvore.create(new ParIntInt(this.id_serie, id));

        return id;
    }
    /* 
    public Episodio readISBN(String isbn) throws Exception {
        if(!Episodio.isValidISBN13(isbn))
            throw new Exception("ISBN inválido");
        ParISBNID pii = indiceISBN.read(ParISBNID.hash(isbn));
        if(pii != null)
            return read(pii.getId());    // na superclasse
        else 
            return null;
    }
    
    public Episodio[] readTitulo(String titulo) throws Exception {
        if(titulo.length()==0)
            return null;
        ArrayList<ParTituloId> ptis = indiceTitulo.read(new ParTituloId(titulo, -1));
        if(ptis.size()>0) {
            Episodio[] Episodios = new Episodio[ptis.size()];
            int i=0;
            for(ParTituloId pti: ptis) 
                Episodios[i++] = read(pti.getId());
            return Episodios;
        }
        else 
            return null;
    }
    */
    @Override
    public boolean delete(int id) throws Exception {
        Episodio l = read(id);   // na superclasse
        if(l!=null) {
            if(super.delete(id)) {
                arvore.delete(new ParIntInt(id_serie, id));
                return true;
            }
            /*
            if(super.delete(id))
                return indiceISBN.delete(ParISBNID.hash(l.getISBN()))
                    && indiceTitulo.delete(new ParTituloId(l.getTitulo(), id));
            */
        }
        return false;
    }

    /* 
    public boolean delete(String isbn) throws Exception {
        ParISBNID pii =  indiceISBN.read(ParISBNID.hash(isbn));
        if(pii!=null) {
            return delete(pii.getId());
        }
        return false;
    }
    */

    @Override
    public boolean update(Episodio novoEpisodio) throws Exception {
        Episodio l = read(novoEpisodio.getId());    // na superclasse
        if(l!=null) {
            //if(super.update(novoEpisodio))

            /* 
            if(super.update(novoEpisodio)) {
                if(!l.getISBN().equals(novoEpisodio.getISBN())) {
                    indiceISBN.delete(ParISBNID.hash(l.getISBN()));
                    indiceISBN.create(new ParISBNID(novoEpisodio.getISBN(), l.getID()));
                }
                if(!l.getTitulo().equals(novoEpisodio.getTitulo())) {
                    indiceTitulo.delete(new ParTituloId(l.getTitulo(), l.getID()));
                    indiceTitulo.create(new ParTituloId(novoEpisodio.getTitulo(), novoEpisodio.getID()));
                }
                return true;
            }
            */
        }
        return false;
    }
}
