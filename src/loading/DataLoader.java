package loading;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DataLoader {
	private BufferedReader inputStream = null;
    private String splitter = ",";
	private String line;
	private List<Vector<Double>> vectorList = new ArrayList<>();
	
	/**Create a dataset */
	public void initDataSet(int columns){
		for(int i=0;i<columns;i++){
			Vector user = new Vector<Double>();
			this.vectorList.add(user);
		}
	}
		
	public void loadData() throws IOException{	
	    try {
	    	inputStream = new BufferedReader(new FileReader("WineData.csv"));
	        while ((line = inputStream.readLine()) != null) {
	        	String[] data = line.split(splitter);
	        	
	        	/**Init the dataset if needed*/
	        	if(vectorList.isEmpty()){
	        		initDataSet(data.length);
	        	}
	        	
	        	for(int i=0;i<data.length;i++){
	        		vectorList.get(i).add(Double.parseDouble(data[i]));
	        	}	            
	        }	
	    } finally {
	        if (inputStream != null) {
	            inputStream.close();
	        }
	    }

		}
	
	public List<Vector<Double>> getDataSet(){
		return this.vectorList;
	}
	
	public static void main(String[] args) throws IOException{
		DataLoader loader = new DataLoader();
		loader.loadData();
		for(Vector<Double> user: loader.vectorList){
			String output = "";
			for(int i=0;i<user.size();i++){
				output += user.get(i);
			}
			System.out.println(output);
		}
		
		
	}
}
