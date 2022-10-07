package Main;

public class Main {

	public static void main(String[] args) {
		
		Loader loader = new Loader();
		
		
		TSP tsp = new TSP(loader.getN(), loader.getGraph());
		//TSP tsp = new TSP();
		//tsp.randomSearch(2000);
		//tsp.localSearch(2000);
		tsp.geneticAlgorithm(10);
		
		char[] route = {
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'
		};
		//System.out.println("Cost: " + tsp.getRouteCost(route));
		
		
		//AntennaArray aa = new AntennaArray(4, 70);
		//aa.randomSearch(1000);
		
	}
	
}
