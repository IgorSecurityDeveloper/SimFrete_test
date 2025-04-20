package simfretemain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program1 {

    static class City {
        String cityName;
        int cepStart;
        int cepEnd;

        City(String cityName, int cepStart, int cepEnd) {
            this.cityName = cityName;
            this.cepStart = cepStart;
            this.cepEnd = cepEnd;
        }

        boolean contain(int cep) {
            return cep >= cepStart && cep <= cepEnd;
        }
    }

    public static void main(String[] args) {
        List<City> cityLines = new ArrayList<>();
        int cepSearch = -1;

        System.out.print("Informe o caminho exato do arquivo de texto: ");
        Scanner filePath = new Scanner(System.in);
        String path = filePath.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            filePath.close();
            String docLine;
            boolean readingCep = false;

            while ((docLine = reader.readLine()) != null) {
                if (docLine.equals("--")) {
                    readingCep = true;
                    continue;
                }

                if (!readingCep) {
                    String[] parts = docLine.split(",");
                    cityLines.add(new City(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                } else {
                    cepSearch = Integer.parseInt(docLine);
                }
            }

            for (City lines : cityLines) {
                if (lines.contain(cepSearch)) {
                    System.out.println("Cidade: " + lines.cityName);
                    return;
                }
            }

            System.out.println("CEP não encontrado em nenhuma cityName.");

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, por favor verifique se o caminho está correto.");
            System.out.println("AVISO: " + e.getMessage());
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}