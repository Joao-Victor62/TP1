package aed3.TP2.Model;

import aed3.Interface.Registro;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Atuacao implements Registro {

    private int id;
    private int idAtor;
    private int idSerie;
    private String papel; // campo adicional exemplo

    public Atuacao() {
        this(-1, -1, -1, "");
    }

    public Atuacao(int idAtor, int idSerie, String papel) {
        this(-1, idAtor, idSerie, papel);
    }

    public Atuacao(int id, int idAtor, int idSerie, String papel) {
        this.id = id;
        this.idAtor = idAtor;
        this.idSerie = idSerie;
        this.papel = papel;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getIdAtor() {
        return idAtor;
    }

    public void setIdAtor(int idAtor) {
        this.idAtor = idAtor;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "\nID.........: " + this.id +
                "\nID Ator....: " + this.idAtor +
                "\nID Série...: " + this.idSerie +
                "\nPapel......: " + this.papel;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.idAtor);
        dos.writeInt(this.idSerie);
        dos.writeUTF(this.papel);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idAtor = dis.readInt();
        this.idSerie = dis.readInt();
        this.papel = dis.readUTF();
    }
}