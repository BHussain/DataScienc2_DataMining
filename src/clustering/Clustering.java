package clustering;

import models.Cluster;
import models.Solution;

import java.util.*;

public class Clustering {

    Random rand = new Random();
    private int amountOfCentroids;

    private List<ArrayList<Double>> dataSet;
    private List<ArrayList<Double>> centroids = new ArrayList<>();
    private List<Cluster> clusters = new ArrayList<>();
    private Solution solution;

    public Clustering(int amountOfCentroids, List<ArrayList<Double>> dataSet) {
        this.amountOfCentroids = amountOfCentroids;
        this.dataSet = dataSet;
    }

    public Solution execute(int loopAmount) {
        for (int i = 0; i < loopAmount; i++) {
            centroids.clear();
            clusters.clear();

            for (int j = 0; j < amountOfCentroids; j++) {
                int n = rand.nextInt(3000);
                ArrayList candidate = dataSet.get(n);
                if (!centroids.contains(candidate)) {
                    centroids.add(candidate);
                } else {
                    /**to keep the same amount of iterations*/
                    j--;
                }
            }
            createClusters();
            run();

            Solution newSolution = new Solution(countSSE(), copyClusters(clusters));
            if (solution == null) {
                solution = newSolution;
            } else if (newSolution.getSSE() < solution.getSSE()) {
                solution = newSolution;
            }


        }
        return solution;
    }

    private double countSSE() {
        double SSE = 0.0;
        for (Cluster cluster : clusters) {
            SSE += cluster.getSSE();
        }
        return SSE;
    }

    public void createClusters() {
        for (ArrayList<Double> centroid : centroids) {
            Cluster cluster = new Cluster();
            cluster.setCentroid(centroid);
            cluster.setSSE(1000000);
            clusters.add(cluster);
        }
    }

    public void group() {
        clearClusters();
        for (ArrayList<Double> user : dataSet) {
            Map<Double, Cluster> distances = new HashMap<>();

            for (Cluster cluster : clusters) {
                double distanceToCentroid = calculateDistance(user, cluster.getCentroid());
                distances.put(distanceToCentroid, cluster);
            }

            getLowestDistanceCluster(distances).addMember(user);
            calculateSSE();
        }
    }

    private static Cluster getLowestDistanceCluster(Map<Double, Cluster> distances) {
        boolean first = true;
        double lowestDistance = 0;
        for (Double distance : distances.keySet()) {
            if (first) {
                first = false;
                lowestDistance = distance;
            } else if (distance < lowestDistance) {
                lowestDistance = distance;
            }
        }
        return distances.get(lowestDistance);
    }

    /**
     * Calculates the distance based on the euclidian formula
     *
     * @param first
     * @param second
     * @return
     */
    public double calculateDistance(ArrayList<Double> first, ArrayList<Double> second) {
        double distanceSquared = 0.0;
        for (int i = 0; i < first.size(); i++) {
            distanceSquared += Math.pow(first.get(i) - second.get(i), 2);
        }
        return Math.sqrt(distanceSquared);
    }

    public void calculateCentroid() {
        /**
         * 0,0,1
         * 1,0,1
         * ------ +
         * 1,0,2
         *
         * 1/2 = 0,5
         * 0/2= 0
         * 2/2 = 1
         *
         * 0,0,1
         */

        for (Cluster cluster : clusters) {
            ArrayList<Double> result = new ArrayList<>();
            for (int i = 0; i < cluster.getMembers().get(0).size(); i++) {
                double sum = 0;
                for (ArrayList<Double> member : cluster.getMembers()) {
                    sum += member.get(i);
                }
                result.add(sum / cluster.getMembers().size());
            }
            cluster.setCentroid(result);
        }
    }

    public void run() {
        Boolean loop = true;
        while (loop) {
            List<Cluster> oldClusters = copyClusters(clusters);
            group();
            calculateCentroid();

            int amountCorrect = 0;

            for (int i = 0; i < oldClusters.size(); i++) {
                if (calculateDistance(oldClusters.get(i).getCentroid(), clusters.get(i).getCentroid()) == 0) {
                    amountCorrect++;
                }
                if (amountCorrect == oldClusters.size()) {
                    loop = false;
                }
            }

        }
    }

    public List<Cluster> getClusters() {
        return this.clusters;
    }

    private List<Cluster> copyClusters(List<Cluster> clusters) {
        List<Cluster> oldClusters = new ArrayList<>();
        for (Cluster cluster : clusters) {
            Cluster newCluster = new Cluster();
            newCluster.setCentroid(cluster.getCentroid());
            newCluster.setMembers(cluster.getMembers());
            newCluster.setSSE(cluster.getSSE());
            oldClusters.add(newCluster);
        }
        return oldClusters;
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.getMembers().clear();
        }
    }

    private void calculateSSE() {
        for (Cluster c : clusters) {
            double SSE = 0.0;
            for (ArrayList<Double> member : c.getMembers()) {
                SSE += Math.pow(calculateDistance(member, c.getCentroid()), 2);
            }
            c.setSSE(SSE);
        }
    }
}
