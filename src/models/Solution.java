package models;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public Solution(double SSE, List<Cluster> clusters){
        this.SSE = SSE;
        this.clusters = clusters;
    }

    public double getSSE() {
        return SSE;
    }

    public void setSSE(double SSE) {
        this.SSE = SSE;
    }

    private double SSE;

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    private List<Cluster> clusters = new ArrayList<>();
}