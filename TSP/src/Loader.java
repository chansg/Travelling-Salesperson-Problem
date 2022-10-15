import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {

    private int[] id = new int[16];
    private double[][] coords;

    public Loader() {
        load();
    }

    public void load() {
        ArrayList<String> temp = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("ulysses16.csv"));
            String line;

            while((line = br.readLine()) != null) {
                temp.add(line);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < temp.size(); i++) {
            String line = temp.get(i);
            String[] values = line.split(",");
            id[i] = Integer.valueOf(values[0]);
            Double.valueOf(values[1]);
            Double.valueOf(values[2]);
     
        }
    }
}
