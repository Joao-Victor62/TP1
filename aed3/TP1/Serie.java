
import aed3.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Serie implements Registro {

    public int id;
    public String nome;
    public String sinopse;
    public String streaming; 
    public int anoLancamento;

    public Serie() {
        this(-1, "", "", "", 0);
    }
    public Serie(String nome, String sinopse, String streaming, int ano) {
        this(-1, nome, sinopse, streaming, ano);
    }

    public Serie(int id, String no, String si, String stre, int ano) {
        this.id = id;
        this.nome = no;
        this.sinopse = si;
        this.streaming = stre;
        this.anoLancamento = ano;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "\nID...............: " + this.id +
               "\nNome.............: " + this.nome +
               "\nSinopse..........: " + this.sinopse +
               "\nStreaming........: " + this.streaming +
               "\nAno de lancamento: " + this.anoLancamento;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.sinopse);
        dos.writeUTF(this.streaming);
        dos.writeInt(this.anoLancamento);
        return baos.toByteArray();
    }

   public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.sinopse = dis.readUTF();
        this.streaming = dis.readUTF();
        this.anoLancamento = dis.readInt();
    }
}
