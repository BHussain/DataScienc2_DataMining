import clustering.Clustering;
import loading.DataLoader;
import models.Cluster;
import models.Solution;

import java.io.IOException;

public class client {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        loader.loadData();

        Clustering cluster = new Clustering(3, loader.getDataSet());
        Solution solution = cluster.execute(50);

        System.out.println("Total SSE: "+solution.getSSE());
        for(Cluster c : solution.getClusters()){
            System.out.println("-------------------------------------");
        	System.out.println("The Sum of Squared Errors in this cluster: "+c.getSSE()+"\n");
            System.out.println(c.getPrintData());
        }
    }
}
