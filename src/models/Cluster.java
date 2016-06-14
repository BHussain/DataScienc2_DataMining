package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Cluster {
	private Vector<Integer> centroid;
	private List<Vector<Integer>> members = new ArrayList<>();
	
	public Vector<Integer> getCentroid() {
		return centroid;
	}
	
	public void setCentroid(Vector<Integer> centroid) {
		this.centroid = centroid;
	}

	public List<Vector<Integer>> getMembers() {
		return members;
	}

	public void setMembers(List<Vector<Integer>> members) {
		this.members = members;
	}
	
	public void addMember(Vector<Integer> member){
		members.add(member);
	}
}
