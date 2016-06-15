package clustering;

import models.Cluster;
import java.util.*;

public class Clusterer {
	
	Random rand = new Random();
	private int amountOfCentroids;

    private List<Vector<Double>> dataSet;
	private List<Vector<Double>> centroids = new ArrayList<>();
	private List<Cluster> clusters = new ArrayList<>();
	
	public Clusterer(int amountOfCentroids,List<Vector<Double>> dataSet){
		this.amountOfCentroids = amountOfCentroids;
		this.dataSet = dataSet;
	}
	
	public void execute(){
		for(int i=0;i < amountOfCentroids; i++){
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
        run();
	}
	
	public void createClusters(){
		for(Vector<Double> centroid:centroids){
			Cluster cluster =new Cluster();
			cluster.setCentroid(centroid);
			cluster.setSSE(1000000);
			clusters.add(cluster);
		}
	}
	
	public void group(){
        clearClusters();
		for(Vector<Double> user : dataSet){
			Map<Double, Cluster> distances = new HashMap<>();

			for(Cluster cluster: clusters){
				double distanceToCentroid = calculateDistance(user, cluster.getCentroid());
				distances.put(distanceToCentroid, cluster);
			}

			getLowestDistanceCluster(distances).addMember(user);
            calculateSSE();
		}
	}

	private static Cluster getLowestDistanceCluster(Map<Double, Cluster> distances){
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
	public double calculateDistance(Vector<Double> first, Vector<Double> second){
		double distanceSquared = 0.0;
		for(int i =0;i<first.size();i++){
			distanceSquared += Math.pow(first.get(i)-second.get(i),2);
		}
		return Math.sqrt(distanceSquared);
	}

	public void calculateCentroid(){
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
			Vector<Double> result = new Vector<>();
			for(int i=0; i <cluster.getMembers().get(0).size(); i++){
				double sum = 0;
				for(Vector<Double> member : cluster.getMembers()){
					sum += member.get(i);
				}
				result.add(sum / cluster.getMembers().size());
			}
			cluster.setCentroid(result);
		}
	}

    public void run(){
    	Boolean loop = true;
    	int count = 0;
    	while(loop==true){
    		count++;
    		System.out.println("looping");
    		List<Cluster> oldClusters = new ArrayList<>();
    		for(Cluster cluster:clusters){
    			Cluster newCluster = new Cluster();
    			newCluster.setCentroid(cluster.getCentroid());
    			newCluster.setMembers(cluster.getMembers());
    			newCluster.setSSE(cluster.getSSE());
    			oldClusters.add(newCluster);
    		}
            group();
            calculateCentroid();
            List<Cluster> newClusters = clusters;
            
            int amountCorrect=0;
            
            for(int i=0;i<oldClusters.size();i++){
            	/*
            	System.out.println(oldClusters.get(i).getSSE()-newClusters.get(i).getSSE());
            	if((oldClusters.get(i).getSSE()-newClusters.get(i).getSSE())==0){
                	amountCorrect++;
                }
            	if(amountCorrect==oldClusters.size()){
            		loop = false;
            	}
            	*/
            	if(calculateDistance(oldClusters.get(i).getCentroid(),newClusters.get(i).getCentroid())==0){
                	amountCorrect++;
                }
            	if(amountCorrect==oldClusters.size()){
            		loop = false;
            	}
            }
            
    	}
    	System.out.println(count);
    }

    public List<Cluster> getClusters(){
        return this.clusters;
    }

    private void clearClusters(){
        for(Cluster cluster: clusters){
            cluster.getMembers().clear();
        }
    }

    private void calculateSSE(){
        for(Cluster c : clusters){
            double SSE = 0.0;
            for(Vector<Double> member : c.getMembers()){
                SSE += Math.sqrt(calculateDistance(member, c.getCentroid()));
            }
            c.setSSE(SSE);
        }
    }
}
