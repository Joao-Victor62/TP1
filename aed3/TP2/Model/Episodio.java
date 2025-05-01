package aed3.TP2.Model;

import aed3.Interface.Registro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

public class Episodio implements Registro
{
    private int id;
    private byte temporada;
    private String nome;
    private LocalDate data;
    private byte duracao;
    private int id_serie;

    public Episodio() 
    {
        this(-1, " ", 0, LocalDate.now(), 0, -1);
    }

    public Episodio(String nome, int temporada, LocalDate data, int duracao) 
    {
        this(-1, nome, temporada, data, duracao, -1);
    }

    public Episodio(String nome, int temporada, LocalDate data, int duracao, int id_serie)
    {
        this(-1, nome, temporada, data, duracao, id_serie);
    }

    public Episodio(int id, String nome, int temporada, LocalDate data, int duracao, int id_serie) 
    {
        this.id = id;
        this.nome = nome;
        this.temporada = (byte)temporada;
        this.data = data;
        this.duracao = (byte)duracao;
        this.id_serie = id_serie;

    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeByte(temporada);
        dos.writeInt((int)data.toEpochDay());
        dos.writeByte(duracao);
        dos.writeInt(id_serie);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        nome = dis.readUTF();
        temporada = dis.readByte();
        data = LocalDate.ofEpochDay(dis.readInt());
        duracao = dis.readByte();
        id_serie = dis.readInt();
    }

    public byte getTemporada() {
        return temporada;
    }

    public void setTemporada(byte temporada) {
        this.temporada = temporada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public byte getDuracao() {
        return duracao;
    }

    public void setDuracao(byte duracao) {
        this.duracao = duracao;
    }

    public int getId_serie() {
        return id_serie;
    }

    public void setId_serie(int id_serie) {
        this.id_serie = id_serie;
    }
}
