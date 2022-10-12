public class Main {

    public static void main(String[] args) {
        Reader r = new Reader();
        TSP tsp = new TSP();

        /* 3. Evaluation Function */
        char[] testRoute = {'A','C','B','D'};
        //int cost = tsp.getCostOfRoute(testRoute);
        //System.out.println(cost);

        /* 4. Random Routes */
        char[] route = tsp.getRandomRoute();
        System.out.println(route);
        System.out.println(tsp.getCostOfRoute(route));
    }
}