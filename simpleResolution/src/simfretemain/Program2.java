package simfretemain;
// importação unica de cada classe necessária para o funcionamento do código, assim faz economia de memória;
// single import of each class required for the code to work, thus saving memory
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Program2 {

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

    static class freightCalc {
        String destiny;
        double cost;

        freightCalc(String destiny, double cost) {
            this.destiny = destiny;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        Map<String, List<freightCalc>> grafo = new HashMap<>();
        List<City> citys = new ArrayList<>();
        String OriginCity = null, destinyCity = null;

        // Optei por utilizar o Scanner para ler o arquivo, pois futuramente pode haver alterações e o sistema deve estar preparado para atualizações nos dados;
        // I chose to use the Scanner to read the file, as there may be changes in the future and the system must be prepared for data updates;
        System.out.print("Insira o caminho completo do arquivo: ");
        Scanner filePath = new Scanner(System.in);
        String path = filePath.nextLine();

        // Optei por usarar o FileReader e BufferedReader, pois o bufferedReader contém um buffer que armazena os dados lidos e deixa o código mais limpo e rápido;
        // I chose to use FileReader and BufferedReader, as bufferedReader contains a buffer that stores the read data and makes the code cleaner and faster;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            filePath.close();
            String travelLine;
            int step = 0;

            // Estrutura de repetição com while que le o arquivo linha por linha e armazena os dados em uma lista;
            // Repetition structure with while that reads the file line by line and stores the data in a list;
            while ((travelLine = reader.readLine()) != null) {
                if (travelLine.equals("--")) {
                    step++;
                    continue;
                }

                switch (step) {
                    case 0 -> {
                        String[] parts = travelLine.split(",");
                        citys.add(new City(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                    }
                    case 1 -> {
                        String[] datas = travelLine.split(",");
                        grafo.putIfAbsent(datas[0], new ArrayList<>());
                        grafo.get(datas[0]).add(new freightCalc(datas[1], Double.parseDouble(datas[2])));
                    }
                    case 2 -> {
                        String[] ceps = travelLine.split(",");
                        int cepStart = Integer.parseInt(ceps[0]);
                        int cepEnd = Integer.parseInt(ceps[1]);

                        for (City city : citys) {
                            if (OriginCity == null && city.contain(cepStart)) OriginCity = city.cityName;
                            if (destinyCity == null && city.contain(cepEnd)) destinyCity = city.cityName;
                        }
                    }
                }
            }

            if (OriginCity == null || destinyCity == null) {
                System.out.println("Uma ou ambas as citys não foram encontradas.");
                return;
            }

            Map<String, Double> distance = new HashMap<>();
            Map<String, String> previous = new HashMap<>();
            PriorityQueue<String> query = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

            for (String city : grafo.keySet()) {
                distance.put(city, Double.POSITIVE_INFINITY);
            }
            distance.put(OriginCity, 0.0);
            query.add(OriginCity);

            while (!query.isEmpty()) {
                String current = query.poll();

                for (freightCalc neighbor : grafo.getOrDefault(current, Collections.emptyList())) {
                    double newDist = distance.get(current) + neighbor.cost;
                    if (newDist < distance.getOrDefault(neighbor.destiny, Double.POSITIVE_INFINITY)) {
                        distance.put(neighbor.destiny, newDist);
                        previous.put(neighbor.destiny, current);
                        query.add(neighbor.destiny);
                    }
                }
            }

            if (!distance.containsKey(destinyCity)) {
                System.out.println("Não foi possível encontrar uma rota.");
            } else {
                double totalCost = distance.get(destinyCity);
                LinkedList<String> caminho = new LinkedList<>();
                for (String at = destinyCity; at != null; at = previous.get(at)) {
                    caminho.addFirst(at);
                }

                System.out.println("Rota mais barata: " + String.join(" -> ", caminho));
                System.out.printf("Custo total: R$ %.2f%n", totalCost);
            }

        } 
        // StackTrace usado para mostrar o erro caso ocorra algum, fica mais simples para debugar o código, mas se eu fosse criar uma aplicação real, utilizaria log4j na produção pois é mais seguro;
        //StackTrace used to show the error if any occurs, it is simpler to debug the code, but if I were to create a real application, I would use log4j in production as it is safer;
        catch (FileNotFoundException e){
            System.out.println("Reinicie o programa e verifique o caminho completo do arquivo. AVISO:" + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            
        } 
    }
}
