package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.stream.Stream;

public class Cluster {
	//private Vector<Double> centroid;
	private List<Double> centroid;
	//private List<Vector<Double>> members = new ArrayList<>();
	private List<List<Double>> members = new ArrayList<>();
	private double SSE = 0.0;

	public double getSSE() { return SSE; }

	public void setSSE(double SSE) { this.SSE = SSE; }
	
	/*
	public Vector<Double> getCentroid() {
		return centroid;
	}
	
	public void setCentroid(Vector<Double> centroid) {
		this.centroid = centroid;
	}

	public List<Vector<Double>> getMembers() {
		return members;
	}

	public void setMembers(List<Vector<Double>> members) {
		this.members = members;
	}
	
	public void addMember(Vector<Double> member){
		members.add(member);
	}
	*/
	
	
	public List<Double> getCentroid() {
		return centroid;
	}
	
	public void setCentroid(List<Double> centroid) {
		this.centroid = centroid;
	}

	public List<List<Double>> getMembers() {
		return members;
	}

	public void setMembers(List<List<Double>> members) {
		this.members = members;
	}
	
	public void addMember(List<Double> member){
		members.add(member);
	}
	
	
	/**
	 * Determine the most popular items in the cluster.
	 * Return how many times these items were bought
	 * 
	 * I.E
	 * 
	 * Offer 3 -> bought 10 times
	 * Offer 2 -> bought 8 times
	 * .
	 * .
	 * .
	 * Offer n -> bought n times
	 * 
	 * @return
	 */
	public String getPrintData(){		
		/**Determine for each wine how often it was sold */
		Map<Integer,Double> winesSold = new HashMap<>();
		for(List<Double> member: members){
			for(int i =0; i<member.size(); i++){
				if(!winesSold.containsKey(i)){
					winesSold.put(i, member.get(i));
				}else{
					winesSold.put(i, winesSold.get(i)+member.get(i));
				}
			}
			
		}
		Map<Integer, Double> result = new LinkedHashMap<>();
		Stream<Map.Entry<Integer,Double>> st = winesSold.entrySet().stream();

		st.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(e->result.put(e.getKey(), e.getValue()));
		
		/**Build the output string*/
		String output = "";
		for(Integer wine:result.keySet()){
			output+= "Offer "+ (wine+1)+" was sold "+ winesSold.get(wine).intValue()+ " times." + "\n";
		}
		return output;
		
	}
	
	

}
