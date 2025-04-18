package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program {

    public static void main(String[] args) throws IOException {

        String path = "C:\\Users\\Igor Amaral Silva\\Documents\\GitHub\\SimFrete_test\\simpleResolution\\src\\part1\\CEP.txt";
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            System.out.println("Lendo o arquivo: " + path);
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
       
            
        