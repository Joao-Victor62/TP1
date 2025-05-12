package aed3.ArvoreB;

import java.io.*;

public class ParIdAtorIdAtuacao implements RegistroArvoreBMais<ParIdAtorIdAtuacao> {

    private int idAtor;
    private int idAtuacao;
    private short TAMANHO = 8;

    public ParIdAtorIdAtuacao() {
        this(-1, -1);
    }


    public ParIdAtorIdAtuacao(int idAtor, int idAtuacao) {
        this.idAtor = idAtor;
        this.idAtuacao = idAtuacao;
    }

    public int getIdAtor() {
        return idAtor;
    }


    public int getIdAtuacao() {
        return idAtuacao;
    }

    @Override
    public ParIdAtorIdAtuacao clone() {
        return new ParIdAtorIdAtuacao(this.idAtor, this.idAtuacao);
    }

    @Override
    public short size() {
        return this.TAMANHO;
    }

    @Override
    public int compareTo(ParIdAtorIdAtuacao par) {
        if (this.idAtor != par.idAtor)
            return this.idAtor - par.idAtor;
        else
            return idAtuacao == -1 ? 0 : this.idAtuacao - par.idAtuacao;
    }

    @Override
    public String toString() {
        return "Ator:" + idAtor  + ";Atuação:" + idAtuacao;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idAtor);
        dos.writeInt(this.idAtuacao);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idAtor = dis.readInt();
        this.idAtuacao = dis.readInt();
    }
}