package loading;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private BufferedReader inputStream = null;
    private String splitter = "   ";
    private String line;
    private List<ArrayList<Double>> dataList = new ArrayList<>();

    public void loadData() throws IOException {
        try {
            inputStream = new BufferedReader(new FileReader("a11.txt"));
            while ((line = inputStream.readLine()) != null) {
                String[] data = line.split(splitter);

                /**Init the dataset if needed*/

                ArrayList<Double> t = new ArrayList<>();
                t.add(Double.parseDouble(data[0]));
                t.add(Double.parseDouble(data[1]));

                dataList.add(t);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    public List<ArrayList<Double>> getDataSet() {
        return this.dataList;
    }

    public static void main(String[] args) throws IOException {
        DataLoader loader = new DataLoader();
        loader.loadData();
        for (ArrayList<Double> user : loader.dataList) {
            String output = "";
            for (int i = 0; i < user.size(); i++) {
                output += user.get(i);
            }
            System.out.println(output);
        }
    }
}
