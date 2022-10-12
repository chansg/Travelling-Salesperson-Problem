import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public int[] city_id;
    public double[][] coords;
    public int size;

    public Reader() {
    }

    /* todo parameter of String loadFile */
    protected void loadFile() {
        try {
            ArrayList<List<String>> data = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader("ulysses16.csv"));
            String line;

            System.out.println(data.toString());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Reader r = new Reader();
        r.loadFile();
    }
}
