import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TSP tsp = new TSP();

        char[] testRoute = {'A','C','B','D'};
        //int cost = tsp.getCostOfRoute(testRoute);
        //System.out.println(cost);

        char[] route = tsp.getRandomRoute();
        System.out.println(route);
        System.out.println(tsp.getCostOfRoute(route));
    }
}