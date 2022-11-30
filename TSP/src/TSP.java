import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

public class TSP {
    private final int N = 4; // number of cities
    private int[] id;
    private double[][] coords;

    ArrayList<Character> cities = new ArrayList<>(N) {{
        add('A');
        add('B');
        add('C');
        add('D');
    }};

    ArrayList<Integer> route = new ArrayList<>();

    Random random = new Random();

    int[][] graph = {
        {0, 20, 42, 35},
        {20, 0, 30, 34},
        {42, 30, 0, 12},
        {35, 34, 12, 0}
    };

    public TSP() {}

    public TSP(String file) {
        Loader loader = new Loader(file);

        arrayListToDouble(loader.getData());
        route = getRandomRoute();
//        System.out.println(route);
    }

    public void geneticAlgorithm(int g, int populationSize) {
        final int POPULATION_SIZE = populationSize;
        System.out.println("Genetic Algorithm is running...");
        System.out.println("Population size: " + POPULATION_SIZE);
        int generation = 0; // survivor selection (age-based)
        /* Initialising first route */
        ArrayList<Integer> bestRoute = new ArrayList<Integer>(route);

        /* populate ArrayList with routes */
        ArrayList<ArrayList<Integer>> population = new ArrayList<>(POPULATION_SIZE);
        ArrayList<ArrayList<Integer>> offsprings = new ArrayList<>(POPULATION_SIZE);
        for(int i = 0; i < POPULATION_SIZE; i++) {
            population.add(getRandomRoute());
        }

        /* Parent selection by tournament */
        for(ArrayList<Integer> s : population) {
            double currentCost = getCostOfRoute(s);
            double bestCost = getCostOfRoute(bestRoute);
            if(currentCost < bestCost) {
                bestRoute = s;
            }
        }
        System.out.println("Generation [" + generation + "]: " + bestRoute + ", " + getCostOfRoute(bestRoute));

        for(int i = 0; i < g; i++) {
            offsprings.clear();
            /* parent & survivor selection by tournament */
            ArrayList<Integer> parent1 = parentSelection(populationSize, population);
            ArrayList<Integer> parent2 = population.get(random.nextInt(populationSize));

            for(int j = 0; j < POPULATION_SIZE; j++) {
                /* recombination */
                ArrayList<Integer> offspring = orderOneCrossover(parent1, parent2);
                /* mutate the resulting offspring. */
                offspring = twoOptSwap(offspring);
                /* evaluate new candidates; */
                double currentCost = getCostOfRoute(offspring);
                double bestCost = getCostOfRoute(bestRoute);

                if(currentCost < bestCost) {
                    bestRoute = offspring;
                }

                /* select individuals for the next generation */
                offsprings.add(j, offspring);
            }

            /* Generational model (age-based) */
            population.clear();
            population.addAll(offsprings);
            generation += 1;

            System.out.println("Generation [" + generation + "]: " + bestRoute + ", " + getCostOfRoute(bestRoute));
        }
    }

    public ArrayList<Integer> parentSelection(int populationSize, ArrayList<ArrayList<Integer>> population) {
        ArrayList<ArrayList<Integer>> pop = new ArrayList<ArrayList<Integer>>(populationSize);
        ArrayList<Integer> parent = new ArrayList<Integer>();
        double bestCost = getCostOfRoute(getRandomRoute());

        for(int i = 0; i < populationSize; i++) {
            pop.add(i, getRandomRoute());
        }

        for(ArrayList<Integer> p : population) {
            double currentCost = getCostOfRoute(p);
            if(currentCost < bestCost) {
                bestCost = currentCost;
                parent = p;
            }
        }
        return parent;
    }

    public void randomSearch(int seconds) {
        long timer = System.currentTimeMillis() + seconds * 1000;

        /* Initialising first route */
        ArrayList<Integer> bestRoute = new ArrayList<>(route);
        double bestCost = this.getCostOfRoute(bestRoute);

        /* CPU-time-based termination */
        while(System.currentTimeMillis() < timer) {
            ArrayList<Integer> currentRoute = getRandomRoute();
            double currentCost = this.getCostOfRoute(currentRoute);

            if(currentCost<bestCost) {
                bestCost = currentCost;
                bestRoute = currentRoute;
            }
            System.out.println("Current tour" + currentRoute + " tour cost: " + currentCost);
        }

        System.out.println("Random Search best tour: " + bestRoute + " cost: " + bestCost);
    }

    public void localSearch(int seconds) {
        long timer = System.currentTimeMillis() + seconds * 1000;

        ArrayList<Integer> bestRoute = new ArrayList<>(route);
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

    public ArrayList<Integer> orderOneCrossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
        int size = parent1.size();

        /* Copy randomly selected list from first parent */
        int n1 = random.nextInt(size);
        int n2 = random.nextInt(size);
        int start = Math.min(n1, n2);
        int end = Math.max(n1, n2);

        ArrayList<Integer> child = new ArrayList<>();
        child.addAll(parent1.subList(start, end));

        int index = 0;
        int tour = 0;
        for(int i = 0; i < size; i++) {
            /* get the index of the current city */
            index = (end + i) % size;

            /* get the city at the current index in each of the two parent tours */
            tour = parent2.get(index);

            /* if child does not already contain the current city in tour 2, add it. */
            if(!child.contains(tour)) {
                child.add(tour);
            }
        }

        /* rotate the lists so the original slice is in the same place as the parent tours. */
        Collections.rotate(child, start);

        return child;
    }

    public ArrayList<Integer> twoOptSwap(ArrayList<Integer> route) {
        ArrayList<Integer> neighbourhood = new ArrayList<>(route);
        for(int i = 1; i < route.size()-1; i++) {
            for(int j = i + 1; j < route.size(); j++) {
                ArrayList<Integer> tempRoute = new ArrayList<>(route);
                ArrayList<Integer> tr = new ArrayList<>(tempRoute);
                double previousCost = getCostOfRoute(tempRoute);

                int temp = route.get(i);
                tempRoute.set(i, route.get(j));
                tempRoute.set(j, temp);
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
        Random rnd = new Random();
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
    private void arrayListToDouble(ArrayList<String> temp) {
        /* initialize variables */
        id = new int[temp.size()];
        coords = new double[temp.size()][2];

        /* retrieve values from array and populate coords and id variables */
        for(int i = 0; i < temp.size(); i++) {
            String line = temp.get(i).toString();
            String[] values = line.split(","); // by column

            id[i] = Integer.parseInt(values[0]);

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
