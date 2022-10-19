import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TSP {
    private final int N = 4;
    private int[] id;
    private double[][] coords;

    Random rnd = new Random();

    ArrayList<Character> cities = new ArrayList<>(N) {{
        add('A');
        add('B');
        add('C');
        add('D');
    }};

    int[][] graph = {
        {0, 20, 42, 35},
        {20, 0, 30, 34},
        {42, 30, 0, 12},
        {35, 34, 12, 0}
    };

    public TSP() {}

    public TSP(ArrayList temp) {
        arrayListToDouble(temp);
    }

    public int getCostOfRoute(char[] route){
        int r, c;
        int cost = 0;

        for (int i = 0; i < N-1; i++) {
            r = cities.indexOf(route[i]);
            c = cities.indexOf(route[i + 1]);

            cost += graph[r][c];
        }
        return cost;
    }

    public char[] getRandomRoute() {
        char[] randomRoute = new char[N];
        List<Character> c = new ArrayList<>(cities);

        for(int i = 0; i < N; i++) {
            int randomCharIndex = rnd.nextInt(N-i);
            randomRoute[i] = c.get(randomCharIndex);
            c.remove(randomCharIndex);
        }

        return randomRoute;
    }

    public void getRandomRouteCost() {
        char[] randomRoute = this.getRandomRoute();
        int cost = getCostOfRoute(randomRoute);
        System.out.println("Route " + Arrays.toString(randomRoute) + ": " + cost);
    }

    /* convert input to usable format */
    private void arrayListToDouble(ArrayList temp) {
        /* initialize variables */
        id = new int[temp.size()];
        coords = new double[temp.size()][2];

        /* retrieve values from array and populate coords and id variables */
        for(int i = 0; i < temp.size(); i++) {
            String line = temp.get(i).toString();
            String[] values = line.split(","); // by column

            id[i] = Integer.valueOf(values[0]);

            for(int j = 0; j < temp.size(); j++) {
                coords[i][0] = Double.parseDouble(values[1]);
                coords[i][1] = Double.parseDouble(values[2]);
            }
        }
    }
}
