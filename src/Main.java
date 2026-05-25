import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int qtdVertex = scanner.nextInt();
        int e = scanner.nextInt();
        int qtdEdges = scanner.nextInt();

        ArrayList<Vertex> vertex = new ArrayList<>();
        for (int i = 0; i < qtdVertex; i++) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            vertex.add(new Vertex(x, y, i));
        }

        UnionFind uf = new UnionFind(qtdVertex);

        for (int i = 0; i < e - 1; i++) {
            uf.union(i, i + 1);
        }

        if (qtdEdges > 0) {
            for (int j = 0; j < qtdEdges; j++) {
                int id1 = scanner.nextInt() - 1;
                int id2 = scanner.nextInt() - 1;
                uf.union(id1, id2);
            }
        }
        scanner.close();

        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertex.size() - 1; i++) {
            for (int j = i + 1; j < vertex.size(); j++) {
                if (uf.find(i) != uf.find(j)) {
                    double euclidiano = Math.hypot(vertex.get(i).x - vertex.get(j).x,
                            vertex.get(i).y - vertex.get(j).y);
                    edges.add(new Edge(vertex.get(i), vertex.get(j), euclidiano));
                }
            }
        }


        edges.sort(Comparator.comparingDouble(a -> a.weight));

        double totalWeight = 0;
        for (Edge edge : edges) {
            if (uf.union(edge.v1.id, edge.v2.id)) {
                totalWeight += edge.weight;
            }
        }
        System.out.printf(Locale.US, "%.6f\n", totalWeight);
    }
}

class Vertex {
    double x;
    double y;
    int id;

    Vertex(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}

class Edge {
    Vertex v1;
    Vertex v2;
    double weight;

    Edge(Vertex v1, Vertex v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}

class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            parent[rootI] = rootJ;
            return true;
        }
        return false;
    }
}