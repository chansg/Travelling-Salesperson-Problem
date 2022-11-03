import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

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

    public TSP(ArrayList<String> data) {
        arrayListToDouble(data);
    }

    public void randomSearch(int seconds) {
        long timer = System.currentTimeMillis() + seconds * 1000;

        /* Initialising first route */
        ArrayList<Integer> bestRoute = getRandomRoute();
        double bestCost = this.getCostOfRoute(bestRoute);

        /* CPU-time-based termination */
        while(System.currentTimeMillis() < timer) {
            ArrayList<Integer> currentRoute = getRandomRoute();
            double currentCost = this.getCostOfRoute(currentRoute);

            if(currentCost<bestCost) {
                bestCost = currentCost;
                bestRoute = currentRoute;
            }
//            System.out.println(currentRoute + " tour cost: " + currentCost);
        }

        System.out.println("Random Search: " + bestRoute + " cost: " + bestCost);
    }

    public void localSearch(int seconds) {
        long timer = System.currentTimeMillis() + seconds * 1000;

        ArrayList<Integer> bestRoute = getRandomRoute();
        double bestCost = this.getCostOfRoute(bestRoute);

        /* CPU-time-based termination */
        while(System.currentTimeMillis() < timer) {
            ArrayList<Integer> tempRoute = twoOptSwap(bestRoute);
            if(!tempRoute.isEmpty()) {
                double currentCost = getCostOfRoute(tempRoute);

            System.out.println(bestRoute + " cost: " + getCostOfRoute(bestRoute));
                /* if statement is the mistake */
                if(currentCost < bestCost && tempRoute.size() != 0) {
                    bestRoute = tempRoute;
                    bestCost = currentCost;
                }
            } else {
                break;
            }
        }
        System.out.println("Local Search: " + bestRoute + " cost: " + getCostOfRoute(bestRoute));
    }

    public ArrayList<Integer> twoOptSwap(ArrayList<Integer> route) {
        ArrayList<Integer> neighbourhood = new ArrayList<>();

        // swapping the edges
        for(int i = 1; i < route.size()-1; i++) {
            for(int j = i + 1; j < route.size(); j++) {
                ArrayList<Integer> tempRoute = new ArrayList<>(route);
                ArrayList<Integer> tr = new ArrayList<>(tempRoute);
                double previousCost = getCostOfRoute(tempRoute);

                int temp = route.get(i);
                tempRoute.set(i, route.get(j));
                tempRoute.set(j, temp);
//                neighbourhood = tempRoute;

                double newCost = getCostOfRoute(tempRoute);
                if(newCost < previousCost) {
                    neighbourhood = tempRoute;
                    tempRoute = tr;
                }

//                System.out.println(tempRoute + " route cost: " + getCostOfRoute(tempRoute));
            }
        }
//        System.out.println(neighbourhood + " shortest route cost: " + getCostOfRoute(neighbourhood));
        return neighbourhood;
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

    public double getCostOfRoute(ArrayList<Integer> route) {
        double cost = 0;

        for(int i = 0; i < id.length-1; i++) {
            cost += getDistance(route.get(i), route.get(i+1));
        }
        return cost;
    }

    public char[] getRandomRouteFromGraph() {
        char[] randomRoute = new char[N];
        List<Character> c = new ArrayList<Character>(cities);

        for(int i = 0; i < N; i++) {
            int randomCharIndex = rnd.nextInt(N-i);
            randomRoute[i] = c.get(randomCharIndex);
            c.remove(randomCharIndex);
        }

        return randomRoute;
    }

    public ArrayList<Integer> getRandomRoute() {
        List<Integer> cities = Arrays.stream(id).boxed().toList();
        ArrayList<Integer> shuffledCities = new ArrayList<Integer>(cities);
        Collections.shuffle(shuffledCities);

        return shuffledCities;
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

    public double getDistance(int id1, int id2) {
        double tempX = Math.pow(this.coords[id2-1][0] - this.coords[id1-1][0], 2);
        double tempY = Math.pow(this.coords[id2-1][1] - this.coords[id1-1][1], 2);
        return Math.sqrt(tempX + tempY);
    }
}
