package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Cluster {
    private ArrayList<Double> centroid;
    private List<ArrayList<Double>> members = new ArrayList<>();
    private double SSE = 0.0;

    public double getSSE() {
        return SSE;
    }

    public void setSSE(double SSE) {
        this.SSE = SSE;
    }

    public ArrayList<Double> getCentroid() {
        return centroid;
    }

    public void setCentroid(ArrayList<Double> centroid) {
        this.centroid = centroid;
    }

    public List<ArrayList<Double>> getMembers() {
        return members;
    }

    public void setMembers(List<ArrayList<Double>> members) {
        this.members = members;
    }

    public void addMember(ArrayList<Double> member) {
        members.add(member);
    }

    /**
     * Determine the most popular items in the cluster.
     * Return how many times these items were bought
     * <p>
     * I.E
     * <p>
     * Offer 3 -> bought 10 times
     * Offer 2 -> bought 8 times
     * .
     * .
     * .
     * Offer n -> bought n times
     *
     * @return
     */
    public String getPrintData() {
        /**Determine for each wine how often it was sold */
        Map<Integer, Double> winesSold = new HashMap<>();
        for (ArrayList<Double> member : members) {
            for (int i = 0; i < member.size(); i++) {
                if (!winesSold.containsKey(i)) {
                    winesSold.put(i, member.get(i));
                } else {
                    winesSold.put(i, winesSold.get(i) + member.get(i));
                }
            }

        }
        Map<Integer, Double> result = new LinkedHashMap<>();
        Stream<Map.Entry<Integer, Double>> st = winesSold.entrySet().stream();

        st.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(e -> result.put(e.getKey(), e.getValue()));

        /**Build the output string*/
        String output = "";
        for (Integer wine : result.keySet()) {
            output += "Offer " + (wine + 1) + " was sold " + winesSold.get(wine).intValue() + " times." + "\n";
        }
        return output;

    }


}
