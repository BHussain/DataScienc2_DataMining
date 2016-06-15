package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Cluster {
	private Vector<Double> centroid;
	private List<Vector<Double>> members = new ArrayList<>();
	private double SSE = 0.0;

	public double getSSE() { return SSE; }

	public void setSSE(double SSE) { this.SSE = SSE; }
	
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

}
