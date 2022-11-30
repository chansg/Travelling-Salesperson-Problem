import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /* Generate TSP instance */
        TSP tsp = new TSP("cities63_11.csv");

        /* ----- Cost of route 2,3,0,1,4,5,6,7,8,9,10 ----- */
        /*
        ArrayList<Integer> route = new ArrayList<>() {
            {
                add(3);
                add(4);
                add(1);
                add(2);
                add(5);
                add(6);
                add(7);
                add(8);
                add(9);
                add(10);
                add(11);
            }
        };
        */
//        System.out.println(tsp.getCostOfRoute(route));

        /* ----- Graph ----- */

        char[] route = tsp.getRandomRouteFromGraph();
        int cost = tsp.getCostOfRoute(route);
        System.out.print(route);
        System.out.print(" "+cost);


        /* ----- TSP -----  */
//        tsp.randomSearch(10);
//        tsp.localSearch(10);
//        tsp.geneticAlgorithm(1000, 1520);

        /* ----- Antenna Array -----  */
        /*
        AntennaArray antenna = new AntennaArray(5, 55);

        Swarm particleSwarm = new Swarm(antenna, 20);
        double[] solution = particleSwarm.search(100);

        System.out.println("solution: " + Arrays.toString(solution));
        System.out.println("solution cost: " + antenna.evaluate(solution));
        */
    }
}