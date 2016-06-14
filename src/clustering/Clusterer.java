package clustering;

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
	}
	
	public void createClusters(){
		for(Vector<Integer> centroid:centroids){
			Cluster cluster =new Cluster();
			cluster.setCentroid(centroid);
			clusters.add(cluster);
		}
	}
	
	public void group(){
		
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
	
	public static void main(String[] args) throws IOException{
		DataLoader loader = new DataLoader();
		loader.loadData();
		Clusterer cluster = new Clusterer(3,loader.getDataSet());
		cluster.init();
		int j  = 0;
		for(Vector<Integer> centroid: cluster.centroids){
			String output= "";
			for(int i=0;i<centroid.size();i++){
				output += centroid.get(i);
			}
			j++;
			System.out.println(j+" : " +output);
			
		}
	}
	
}
