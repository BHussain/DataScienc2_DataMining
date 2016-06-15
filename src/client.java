import clustering.Clusterer;
import loading.DataLoader;
import models.Cluster;

import java.io.IOException;

public class client {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        loader.loadData();

        Clusterer cluster = new Clusterer(3, loader.getDataSet());
        cluster.init(100);

        for(Cluster c : cluster.getClusters()){
        	System.out.println("The Sum of Squared Errors in this cluster: "+c.getSSE()+"\n");
            System.out.println(c.getPrintData());
            System.out.println("-------------------------------------");
        	//System.out.println("SSE: "+c.getSSE()+", size: "+c.getMembers().size()+", "+"\n"+c.getCentroid());
        }
        
        
    }
}
