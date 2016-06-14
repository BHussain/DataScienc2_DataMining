package clustering;

import loading.DataLoader;
import models.Cluster;
import org.omg.CORBA.portable.Streamable;

import java.io.IOException;
import java.util.*;

public class Clusterer {
	
	Random rand = new Random();
	private int amountOfCentroids;
	
	private List<Vector<Integer>> centroids = new ArrayList<>();
	private List<Vector<Integer>> dataSet;
	private List<Cluster> clusters = new ArrayList<>();
	
	
	public Clusterer(int amountOfCentroids,List<Vector<Integer>> dataSet){
		this.amountOfCentroids = amountOfCentroids;
		this.dataSet = dataSet;
	}
	
	public void init(){
		for(int i=0;i<amountOfCentroids;i++){
			int  n = rand.nextInt(100);
			Vector candidate = dataSet.get(n);
			if(!centroids.contains(candidate)){
				centroids.add(candidate);
			}else{
				/**to keep the same amount of iterations*/
				i--;
			}	
		}
		createClusters();
	}
	
	public void createClusters(){
		for(Vector<Integer> centroid:centroids){
			Cluster cluster =new Cluster();
			cluster.setCentroid(centroid);
			clusters.add(cluster);
		}
	}
	
	public void group(){
		for(Vector<Integer> user : dataSet){
			Map<Double, Cluster> distances = new HashMap<>();

			for(Cluster cluster: clusters){
				double distanceToCentroid = calculateDistance(user, cluster.getCentroid());
				distances.put(distanceToCentroid, cluster);
			}

			getLowestDistanceCentroid(distances).addMember(user);
		}
	}

	private static Cluster getLowestDistanceCentroid(Map<Double, Cluster> distances){
		boolean first = true;
		double lowestDistance = 0;
		for(Double distance : distances.keySet()){
			if(first) {
				first = false;
				lowestDistance = distance;
			}else if(distance < lowestDistance){
				lowestDistance = distance;
			}
		}
		return distances.get(lowestDistance);
	}
	
	/**
	 * Calculates the distance based on the euclidian formula
	 * @param first
	 * @param second
	 * @return
	 */
	public double calculateDistance(Vector<Integer> first, Vector<Integer> second){
		double distanceSquared = 0.0;
		for(int i =0;i<first.size();i++){
			distanceSquared += Math.pow(first.get(i)-second.get(i),2);
		}
		return Math.sqrt(distanceSquared);
	}

	private void calculateCentroid(){
		//newVector.get(i)+= member.get(i);
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

		for(Cluster cluster : clusters){
			Vector<Integer> result = new Vector<>();
			for(int i=0; i <cluster.getMembers().get(0).size(); i++){
				int sum = 0;
				for(Vector<Integer> member : cluster.getMembers()){
					sum += member.get(i);
				}
				result.add(sum / cluster.getMembers().size());
			}
			cluster.setCentroid(result);
		}
	}

	public static void main(String[] args) throws IOException{
		DataLoader loader = new DataLoader();
		loader.loadData();
		Clusterer cluster = new Clusterer(4,loader.getDataSet());
		cluster.init();
		cluster.group();

		for(Cluster c : cluster.clusters){
			System.out.println(c.getMembers().size());
			System.out.println("centroid before: "+c.getCentroid());
		}

		cluster.calculateCentroid();

		for(Cluster c : cluster.clusters){
			System.out.println(c.getMembers().size());
			System.out.println("centroid after: "+c.getCentroid());
		}

		//int j  = 0;
//		for(Vector<Integer> centroid: cluster.centroids){
//			String output= "";
//			for(int i=0;i<centroid.size();i++){
//				output += centroid.get(i);
//			}
//			j++;
//			System.out.println(j+" : " +output);
//
//		}



	}

}
