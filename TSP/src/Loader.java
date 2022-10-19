import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
    private ArrayList<String> data = new ArrayList<>();

    public Loader() {
        load();
    }

    public void load() {
        /* read from the file and store values in the array */
        try {
            BufferedReader br = new BufferedReader(new FileReader("ulysses16.csv"));
            String line;

            while((line = br.readLine()) != null) {
                data.add(line);
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getData() {
        return data;
    }
}
