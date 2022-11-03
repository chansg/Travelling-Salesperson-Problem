import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        /* 5. File ulysses16.csv */
        Loader loader = new Loader();

        /* Generate TSP instance */
        TSP tsp = new TSP(loader.getData());

        /* 4. Random Routes Using Graph */
//        System.out.println("Route " + Arrays.toString(tsp.getRandomRoute()) + ": " + tsp.getCostOfRoute(tsp.getRandomRoute()));

        /* ----- Local Search -----  */
        /* todo Running localsearch and random search together can alter the global variable */
//        tsp.randomSearch(5);
        tsp.localSearch(2);

    }
}