import java.util.ArrayList;
import java.util.List;
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
}
