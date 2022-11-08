import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /* 5. File ulysses16.csv */
        /* Generate TSP instance */
        TSP tsp = new TSP("ulysses16.csv");

        /* ----- Graph ----- */
//        System.out.println("Route " + Arrays.toString(tsp.getRandomRoute()) + ": " + tsp.getCostOfRoute(tsp.getRandomRoute()));

        /* ----- TSP -----  */
//        tsp.randomSearch(5);
//        tsp.localSearch(2000);
//        tsp.geneticAlgorithm(10, 50); // generation can not exceed POPULATION_SIZE

        /* ----- Antenna Array -----  */
        AntennaArray antenna = new AntennaArray(5, 90);

        Swarm particleSwarm = new Swarm(antenna, 20);
        double[] solution = particleSwarm.search(100);

        System.out.println("solution: " + Arrays.toString(solution));
        System.out.println("solution cost: " + antenna.evaluate(solution));

    }
}