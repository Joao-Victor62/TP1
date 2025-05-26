package aed3.ListaInvertida;

import aed3.Interface.Registro;

import java.nio.file.Files;
import java.util.stream.Collectors;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LI<T extends Registro> {
    ListaInvertida listaInvertida;

    public LI(String classe)
    {
        try {
            listaInvertida = new ListaInvertida(4, "dados/" + classe + "/dados.listainv.db", "dados/" + classe + "/blocos.listainv.db");
        }
        catch (Exception e) {
            System.out.println("Erro ao instanciar ListaInvertida");
            e.printStackTrace();
        }
    }

    public void insert (String s,  T objeto)
    {
        String[] palavras = s.split(" ");
        List<String> palavrasLista = new ArrayList<>();
        try {
            List<String> stopWords = Files.readAllLines(Paths.get("aed3/ListaInvertida/stopwords.txt"));
            for (int i = 0; i < palavras.length; i++) {
                boolean sw = false;
                palavras[i] = formatarPalavra(palavras[i]);
                for (String word : stopWords) {
                    if (palavras[i].equals(word.trim()))
                    {
                        sw = true;
                        break;
                    }
                }
                if (!sw) palavrasLista.add(palavras[i]);
            }
            for (String palavra : palavrasLista) {
                float freq = (float)1.0/palavrasLista.size();
                //System.out.println(freq);
                listaInvertida.create(palavra, new ElementoLista(objeto.getId(), freq));
            }
        }
        catch (Exception e) {
            System.out.println("Erro ao inserir em lista invertida");
            e.printStackTrace();
        }
    }


    public List<Integer> buscar(String consulta) {
    List<Integer> resultadosOrdenados = new ArrayList<>();
    try {
        // 1. Ler stop words
        List<String> stopWords = Files.readAllLines(Paths.get("aed3/ListaInvertida/stopwords.txt"));

        // 2. Pré-processar a consulta
        String[] palavras = consulta.split(" ");
        List<String> termosValidos = new ArrayList<>();
        for (String p : palavras) {
            String palavraFormatada = formatarPalavra(p);
            if (!palavraFormatada.isEmpty() && !stopWords.contains(palavraFormatada)) {
                termosValidos.add(palavraFormatada);
            }
        }
        
        // 3. Para cada termo, buscar lista invertida
        int totalDocumentos = listaInvertida.numeroEntidades(); 
        Map<Integer, Float> acumuladorScores = new HashMap<>(); 
        
        for (String termo : termosValidos) {
            ElementoLista[] listaTermo = listaInvertida.read(termo); 
            
            // Calcular IDF
            int docsComTermo = listaTermo.length;
            if (docsComTermo == 0) continue;
            double idf = Math.log((double) totalDocumentos / docsComTermo) + 1;
            
            for (ElementoLista el : listaTermo) {
                int idDoc = el.getId();
                float tf = el.getFrequencia();
                float score = (float)(tf * idf);
                
                acumuladorScores.put(idDoc, acumuladorScores.getOrDefault(idDoc, 0f) + score);
            }
        }
        
        // 4. Ordenar por score decrescente
        resultadosOrdenados = acumuladorScores.entrySet().stream()
            .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
    } catch (Exception e) {
        System.out.println("Erro na busca:");
        e.printStackTrace();
    }
    return resultadosOrdenados;
}


    public String formatarPalavra (String s)
    {
        s = s.toLowerCase();
        char[] c = new char[s.length()];
        for (int i = 0; i < s.length(); i++)
        {
            char c1 = s.charAt(i);
            if (c1 == 'ã' || c1 == 'â' || c1 == 'á' || c1 == 'à') c[i] = 'a';
            else if (c1 == 'ô' || c1 == 'ó' || c1 == 'õ') c[i] = 'o';
            else if (c1 == 'é' || c1 == 'ê') c[i] = 'e';
            else if (c1 == 'í') c[i] = 'i';
            else if (c1 == 'ú' || c1 == 'ü') c[i] = 'u';
            else if (c1 == 'ç') c[i] = 'c';
            else if (c1 == '.' || c1 == ',' || c1 == ':' || c1 == ';') c[i] = ' ';
            else c[i] = c1;
        }
        String r = new String(c);
        r = r.trim();
        return r;
    }
}
