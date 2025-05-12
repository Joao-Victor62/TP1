package aed3.ArvoreB;

import java.io.*;

public class ParIdSerieIdAtuacao implements RegistroArvoreBMais<ParIdSerieIdAtuacao> {

    private int idSerie;
    private int idAtuacao;
    private static final short TAMANHO = 8;

    public ParIdSerieIdAtuacao() {
        this(-1, -1);
    }

    public ParIdSerieIdAtuacao(int idSerie, int idAtuacao) {
        this.idSerie = idSerie;
        this.idAtuacao = idAtuacao;
    }

    public int getIdSerie() {
        return idSerie;
    }


    public int getIdAtuacao() {
        return idAtuacao;
    }

    @Override
    public ParIdSerieIdAtuacao clone() {
        return new ParIdSerieIdAtuacao(this.idSerie, this.idAtuacao);
    }

    @Override
    public short size() {
        return this.TAMANHO;
    }

    @Override
    public int compareTo(ParIdSerieIdAtuacao par) {
        if (this.idSerie != par.idSerie)
            return this.idSerie - par.idSerie;
        else
            return idAtuacao == -1 ? 0 : this.idAtuacao - par.idAtuacao;
    }

    @Override
    public String toString() {
        return "Série:" + idSerie  + ";Atuação:" + idAtuacao;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idSerie);
        dos.writeInt(this.idAtuacao);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idSerie = dis.readInt();
        this.idAtuacao = dis.readInt();
    }
}