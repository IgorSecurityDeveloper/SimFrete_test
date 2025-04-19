package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Program {

    static class cepList {

    String city;
    int cepBegin;
    int cepEnd;
    
    cepList(String city, int cepBegin, int cepEnd) {
         this.city = city;
         this.cepBegin = cepBegin;
         this.cepEnd = cepEnd;
    }
    
    boolean cepExist(int cep) {
        return cepEnd >= cep && cepBegin <= cep;
    }
    }
    public static void main(String[] args) throws IOException {

        ArrayList<cepList> list = new ArrayList<>();
        String path = "C:\\Users\\Igor Amaral Silva\\Documents\\GitHub\\SimFrete_test\\simpleResolution\\src\\part1\\CEP.txt";
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            
            String line;
            boolean readingLines = true;
            int cepSearch = -1;
            while(line(br.readLine()) != null){

            }


        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        } finally {
            try {
                   
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            } 
        }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
       
            
        