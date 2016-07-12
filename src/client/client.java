package client;

import clustering.Clustering;
import loading.DataLoader;
import models.Cluster;
import models.Solution;

import java.io.IOException;
import java.util.ArrayList;

public class client {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        loader.loadData();

        Clustering cluster = new Clustering(20, loader.getDataSet());
        Solution solution = cluster.execute(30);

        System.out.println("Total SSE: " + solution.getSSE());
        for (Cluster c : solution.getClusters()) {
            System.out.println("---------------");
            for (ArrayList<Double> member : c.getMembers()) {
                if (member.size() == 2) {
                    double first = member.get(0);
                    double second = member.get(1);
                    System.out.println(first + ", " + second);
                }

            }

        }
    }
}
