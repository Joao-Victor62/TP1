package aed3.Interface;



import java.io.IOException;

public interface Registro {
    public void setId(int i);
    public int getId();
    public byte[] toByteArray() throws IOException, Exception;
    public void fromByteArray(byte[] b) throws IOException, Exception;
}
