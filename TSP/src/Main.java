import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        /* 5. File ulysses16.csv */
        /* Generate TSP instance */
        TSP tsp = new TSP("ulysses16.csv");
        AntennaArray antenna = new AntennaArray(3, 90);


        /* 4. Random Routes Using Graph */
//        System.out.println("Route " + Arrays.toString(tsp.getRandomRoute()) + ": " + tsp.getCostOfRoute(tsp.getRandomRoute()));

        /* ----- Local Search -----  */
        /* todo Running localsearch and random search together can alter the global variable */
//        tsp.randomSearch(5);
//        tsp.localSearch(2000);

        /* generation can not exceed POPULATION_SIZE */
        tsp.geneticAlgorithm(20, 1000);
    }
}