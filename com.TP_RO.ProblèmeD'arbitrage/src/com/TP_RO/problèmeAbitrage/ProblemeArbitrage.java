
package com.TP_RO.problèmeAbitrage;
// Importer les packages necessaires
import java.util.*;

// Definir une classe nomme ArbitrageDetection
public class ProblemeArbitrage {
    
    // Definir une variable constante 'INF' et la definir sur la valeur maximale des entiers
    private static int INF = Integer.MAX_VALUE;

    // Declarer deux variables d'instance, 'n' pour stocker le nombre de sommets et 'adjMatrix' pour stocker la matrice d'adjacence
    private int n; 
    private double[][] adjMatrix; 
    
    // Definir un constructeur pour initialiser les variables d'instance
    public ProblemeArbitrage(int n) {
        this.n = n;
        this.adjMatrix = new double[n][n];
    }
    
    // Une methode pour ajouter des taux de change dans la matrice d'adjacence
    public void addExchangeRate(int i, int j, double rate) {
        adjMatrix[i][j] = -Math.log(rate); // appliquer la transformation logarithmique
    }

    // Une methode pour detecter les opportunites d'arbitrage
    public boolean detectArbitrage() {
        // Initialiser les distances a l'infini
        double[] dist = new double[n];
        Arrays.fill(dist, INF);
        dist[0] = 0.0;

        // Effectuer n-1 iterations de l'algorithme de Bellman-Ford pour trouver la plus courte distance entre les sommets
        for (int k = 1; k < n; k++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (dist[u] != INF && dist[v] > dist[u] + adjMatrix[u][v]) {
                        dist[v] = dist[u] + adjMatrix[u][v];
                    }
                }
            }
        }

        // Verifier les cycles negatifs
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (dist[u] != INF && dist[v] > dist[u] + adjMatrix[u][v]) {
                    return true; // Cycle negatif trouve
                }
            }
        }

        return false; // Aucun cycle negatif trouve
    }

    // La methode principale
    public static void main(String[] args) {
        // Creer un objet Scanner pour lire l'entree de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nombre de devises: ");
        int n = scanner.nextInt(); // Lire le nombre de sommets
        ProblemeArbitrage ad = new ProblemeArbitrage(n); // Creer un objet de la classe ArbitrageDetection
        System.out.println("Entrez les taux de change:");
        // Boucle pour lire les taux de change de l'utilisateur et les ajouter  la matrice d'adjacence
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double rate = scanner.nextDouble();
                ad.addExchangeRate(i, j, rate);
            }
        }
        // Verifier les opportunites d'arbitrage et afficher le resultat en consequence
        if (ad.detectArbitrage()) {
            System.out.println("Opportunite d'Arbitrage detecte.");
        } else {
            System.out.println("Aucun Opportunite d'Arbitrage detecte.");
        }
        scanner.close(); // Close the scanner
    }
}