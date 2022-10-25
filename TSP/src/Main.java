public class Main {

    public static void main(String[] args) {
        /* 5. File ulysses16.csv */
        Loader loader = new Loader();
        TSP tsp = new TSP(loader.getData());

        /* No parameters will use the default graph and cities. */
        //TSP tsp = new TSP();
        /* 4. Random Routes */
        //tsp.getRandomRouteCost();

        /* Random Search & CPU-time-based termination */
        tsp.randomSearch(1000);
    }
}