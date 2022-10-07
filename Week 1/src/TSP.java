import java.util.ArrayList;
import java.util.Random;

public class TSP {
    private final int N = 4;

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

    public TSP() {
    }

    public int getCostOfRoute(char[] route){
        int r, c; // rows and column
        int cost = 0;
        // refer to the graph[r][c] to += to cost for each occurring character in route
        // correspond index of chars to graph
        // remember to go back to the start
        for (int i = 0; i < N-1; i++) {
            r = cities.indexOf(route[i]);
            c = cities.indexOf(route[i + 1]);

            cost += graph[r][c];
        }
        return cost;
    }

    public char[] getRandomRoute() {
        char[] randomRoute = new char[4];
        ArrayList<Character> c = this.cities;

        /*
            instead of removing element from the arraylist shuffle it to the last index and -1
            to the index we are searching for: [0, 2, 4, 5]
            i = 2, value = 4
            new array = [0, 2, 5, 4]

            dave - david20000613@gmail.com

         */
        for(int i = 0; i < N; i++) {
            int randomCharIndex = rnd.nextInt(N-i);
            randomRoute[i] = c.get(randomCharIndex);
            c.remove(randomCharIndex);
        }

        return randomRoute;
    }
}
