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
	//private List<Vector<Double>> vectorList = new ArrayList<>();
	private List<List<Double>> matrix = new ArrayList<>();
	
	/**Create a dataset */
	public void initDataSet(int columns){
		for(int i=0;i<columns;i++){
			/*
			Vector<Double> user = new Vector<Double>();
			this.vectorList.add(user);
			*/
			List<Double> user = new ArrayList<>();
			this.matrix.add(user);
		}
	}
		
	public void loadData() throws IOException{	
	    try {
	    	//inputStream = new BufferedReader(new FileReader("WineData.csv"));
	    	inputStream = new BufferedReader(new FileReader("a1.csv"));
	        while ((line = inputStream.readLine()) != null) {
	        	//String[] data = line.split(splitter);
	        	String[] data = line.split(";");
	        	
	        	/**Init the dataset if needed*/
	        	/*
	        	if(vectorList.isEmpty()){
	        		initDataSet(data.length);
	        	}
	        	*//*
	        	if(matrix.isEmpty()){
	        		initDataSet(data.length);
	        	}
	        	
	        	
	        	for(int i=0;i<data.length;i++){
	        		matrix.get(i).add(Double.parseDouble(data[i]));
	        	}
	        	*/
	        	List<Double> row = new ArrayList<>();
	        	row.add(Double.parseDouble(data[0]));
	        	row.add(Double.parseDouble(data[1]));
	        	matrix.add(row);
	        	
	        }	
	    } finally {
	        if (inputStream != null) {
	            inputStream.close();
	        }
	    }

	}
	/*
	public List<Vector<Double>> getDataSet(){
		return this.vectorList;
	}
	*/
	public List<List<Double>> getDataMatrix(){
		return this.matrix;
	}
	
	
	public static void main(String[] args) throws IOException{
		DataLoader loader = new DataLoader();
		loader.loadData();
		/*
		for(Vector<Double> user: loader.vectorList){
			String output = "";
			for(int i=0;i<user.size();i++){
				output += user.get(i);
			}
			System.out.println(output);
		}
		*/
		for(List<Double> user: loader.matrix){
			String output = "";
			for(int i=0;i<user.size();i++){
				output += user.get(i)+",";
			}
			System.out.println(output);
		}
		
	}
}
