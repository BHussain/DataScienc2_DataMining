import clustering.Clusterer;
import loading.DataLoader;
import models.Cluster;

import java.io.IOException;

public class client {
    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        loader.loadData();

        Clusterer cluster = new Clusterer(10, loader.getDataSet());
        cluster.init(100);

        for(Cluster c : cluster.getClusters()){
            System.out.println("SSE: "+c.getSSE()+", size: "+c.getMembers().size()+", "+c.getCentroid());
        }
    }
}
