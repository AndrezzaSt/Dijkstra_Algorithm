package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws FileNotFoundException {

        int[][] graph = generateGraph();
        dijkstra(graph, 1);
    }

    public static int minDistance(int[] distance_array, Boolean[] binaryTree, int[][] graph) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int i = 0; i < graph.length; i++)
            if (!binaryTree[i] && distance_array[i] <= min) {
                min = distance_array[i];
                min_index = i;
            }
        return min_index;
    }

    public static void dijkstra(int[][] graph, int src) {
        int[] shortestPath = new int[graph.length];
        // Nosso array resposta ira armazenar a distancia do vértice contido no shortestPath[i] para nosso vertice src (inicial)

        // Usamos uma arvore binaria para, caso nosso vertice i esteja no array de minima distancia, será true (1).
        Boolean[] binaryTree = new Boolean[graph.length];

        // Iniciamos as distancias como infinitas e o primeiro valor da arvore binaria como falso (0)
        for (int i = 0; i < graph.length; i++) {
            shortestPath[i] = Integer.MAX_VALUE;
            binaryTree[i] = false;
        }

        // A distancia do vertice inicial dele mesmo sempre sera 0
        shortestPath[src] = 0;

        // Loop para encontrar nossa distancia minima
        for (int i = 0; i < graph.length - 1; i++) {
            // nosso vertice mais próxima sempre será igual ao destino primeiro...
            int closestVerticeIndex = minDistance(shortestPath, binaryTree, graph);

            // Aqui atualizamos nossa arvore binaria com o nosso vértice mais proximo como true, pois ele está proximo ao destino
            binaryTree[closestVerticeIndex] = true;

            // Aqui vamos atualizar nosso valor de distancia menor para os valores adjacentes ao nosso destino, desde que estejam na
            // nossa arvore binaria como true
            for (int j = 0; j < graph.length; j++)

                // SE nosso valor de distancia proximo NÃO esta na arvore binaria, quer dizer que existe uma aresta do nosso vertice mais proximo
                // para j (valor adjacente do grafo) E o peso total da distancia do destino para j na nossa menor distancia será menor
                // que o valor atual da nossa distancia menor
                if (!binaryTree[j] && graph[closestVerticeIndex][j] != 0
                        && shortestPath[closestVerticeIndex] != Integer.MAX_VALUE
                        && shortestPath[closestVerticeIndex] + graph[closestVerticeIndex][j] < shortestPath[j]) {
                    //adicionamos ao nosso array de distancia menor pelo nosso indice de vertice mais próximo + o valor que esta no grafo
                    shortestPath[j] = shortestPath[closestVerticeIndex] + graph[closestVerticeIndex][j];
                }
        }

        printArray(shortestPath, graph);
    }

    public static void printArray(int[] arr, int[][] graph) {
        // o que tiver valor 0 será nosso destino...
        System.out.println("Vertice // Distancia do vértice para nosso destino:");
        for (int i = 0; i < graph.length; i++)
            System.out.println(i + " \t\t " + arr[i]);
    }

    public static int[][] generateGraph() throws FileNotFoundException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader("INSIRA RESTO DO PATH...\\Dijkstra_Algoritmo\\src\\main\\java\\org\\example\\Graph.txt")));
        int rows = 9;
        int columns = 9;
        int[][] graph = new int[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < graph.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j = 0; j < line.length; j++) {
                    graph[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return graph;
    }

}

