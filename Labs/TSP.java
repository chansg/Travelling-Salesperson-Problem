package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class TSP {

	private int N = 4;

	private double[][] graph = {
			{0, 20, 42, 35},
			{20, 0, 30, 34},
			{42, 30, 0, 12},
			{35, 34, 12, 0}
	};

	private char[] route = new char[N];

	public TSP(int N, double[][] graph) {
		this.N = N;
		this.graph = graph;
	}

	public TSP() {

	}

	public void randomSearch(int a) {
		double bestC = 0, curr;
		char[] bestR = {};
		int counter = 0;
		for (int i = 0; i < a; i++) {
			this.route = randomRoute();
			curr = getRouteCost(route);
			System.out.println(counter + ": " + Arrays.toString(route) + ": " + curr);
			if (curr < bestC || bestC == 0) {
				bestC = curr;
				bestR = route;
			}
			counter++;
		}
		System.out.println("Best route: " + Arrays.toString(bestR) + " Cost: " + bestC);
	}

	public void localSearch(int t) {
		long timer = System.currentTimeMillis() + t;
		this.route = randomRoute();
		double bestC = 0, curr;
		char[] bestR = route.clone();
		ArrayList<char[]> neighbourhood = new ArrayList<char[]>();
		int counter = 0;
		while (System.currentTimeMillis() < timer) {
			neighbourhood = twoOpt(bestR);
			for (char[] route : neighbourhood) {
				curr = getRouteCost(route);
				System.out.println(counter + ": " + Arrays.toString(route) + ": " + curr);
				if (curr <= bestC || bestC == 0) {
					bestC = curr;
					bestR = route.clone();
				}
				counter++;
			}
		}
		System.out.println("Best route: " + Arrays.toString(bestR) + " Cost: " + bestC);
		System.out.println("Total routes: " + counter);
	}
	
	public void geneticAlgorithm(int a) {
		double bestC = 0, curr;
		char[] bestR = route.clone();
		ArrayList<char[]> population = new ArrayList<char[]>();
		ArrayList<char[]> offSprings = new ArrayList<char[]>();
		for (int i = 0; i < 100; i++) {
			population.add(randomRoute());
		}
		System.out.println("Generation: 0");
		for (char[] row : population) {
			curr = getRouteCost(row);
			if (curr <= bestC || bestC == 0) {
				bestC = curr;
				bestR = row.clone();
			}
			//System.out.println(Arrays.toString(row) + " Cost: " + curr);
		}
		System.out.println("Current best route: " + Arrays.toString(bestR) + " Cost: " + bestC);
		for (int i = 1; i <= a; i++) {
			offSprings.clear();
			for (int j = 0; j < 100; j++) {
				ArrayList<char[]> parents = selectionP(population);
				offSprings.add(oOneCross(parents));
			}
			population = mutate((ArrayList<char[]>) offSprings.clone());
			System.out.println();
			System.out.println("Generation: " + i);
			for (char[] row : population) {
				curr = getRouteCost(row);
				if (curr <= bestC || bestC == 0) {
					bestC = curr;
					bestR = row.clone();
				}
				//System.out.println(Arrays.toString(row) + " Cost: " + curr);
			}
			System.out.println("Best route: " + Arrays.toString(bestR) + " Cost: " + bestC);
		}
	}

	public double getRouteCost(char[] route) {
		int f, t;
		double cost = 0;
		for (int i = 0; i < N - 1; i++) {
			f = (int) route[i] - 97;
			t = (int) route[i+1] - 97;
			cost += graph[f][t];
		}
		f = (int) route[N - 1] - 97;
		t = (int) route[0] - 97;
		cost += graph[f][t];
		return cost;
	}

	private char[] randomRoute() {
		char[] newRoute = new char[N];
		ArrayList<Integer> cities = new ArrayList<>();
		Random rng = new Random();
		for (int i = 0; i < N; i++) {
			cities.add(i);
		}
		newRoute[0] = 0 + 97;
		cities.remove(0);
		for (int i = 1; i < N; i++) {
			int rand = rng.nextInt(cities.size());
			newRoute[i] = (char) (cities.get(rand).intValue() + 97);
			cities.remove(rand);
		}
		return newRoute;
	}

	private ArrayList<char[]> twoOpt(char[] route) {
		ArrayList<char[]> neighbourhood = new ArrayList<char[]>();
		for (int i = 1; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				char[] tempRoute = route.clone();
				char temp;
				temp = route[i];
				tempRoute[i] = route[j];
				tempRoute[j] = temp;
				neighbourhood.add(tempRoute.clone());
			}
		}
		return neighbourhood;
	}
	
	private ArrayList<char[]> selectionP(ArrayList<char[]> p) {
		ArrayList<char[]> parents = new ArrayList<char[]>();
		int pA, pB, tempBest = -1, curr;
		Random rng = new Random();
		for (int i = 0; i < Math.floor(100 / 33); i++) {
			do {
				curr = rng.nextInt(N);
			} while (curr == tempBest);
			if (tempBest == -1 || getRouteCost(p.get(curr)) < getRouteCost(p.get(tempBest))) {
				tempBest = curr;
			}
		}
		pA = tempBest;
		tempBest = -1;
		for (int i = 0; i < Math.floor(100 / 33); i++) {
			do {
				curr = rng.nextInt(N);
			} while (curr == tempBest || curr == pA);
			if (tempBest == -1 || getRouteCost(p.get(curr)) < getRouteCost(p.get(tempBest))) {
				tempBest = curr;
			}
		}
		pB = tempBest;
		parents.add(p.get(pA));
		parents.add(p.get(pB));
		return parents;
	}
	
	private char[] oOneCross(ArrayList<char[]> parents) {
		char[] offSpring = new char[N];
		Random rng = new Random();
		int point = rng.nextInt(N);
		for (int i = point; i < N / 2 + point; i++) {
			offSpring[i % N] = parents.get(0).clone()[i % N];
		}
		int offset = 0;
		for (int i = (int) (point + Math.floor(N/2)); i < (int) (point + N + Math.floor(N/2)); i++) {
			boolean duplicate = false;
			for (char chara : offSpring) {
				if (chara == parents.get(1)[i % N]) {
					duplicate = true;
					offset--;
					break;
				}
			}
			if (!duplicate) {
				offSpring[(i + offset) % N] = parents.get(1)[i % N];

			}
		}
		return offSpring;
	}
	
	private ArrayList<char[]> mutate(ArrayList<char[]> pop) {
		Random rng = new Random();
		for (int i = 0; i < Math.floor(100 / 3); i++) {
			int rand = rng.nextInt(N);
			char[] mutated = pop.get(rand);
			double bestC = 0, curr;
			char[] bestR = route;
			ArrayList<char[]> mutations = twoOpt(mutated);
			for (char[] row : mutations) {
				curr = getRouteCost(row);
				if (curr <= bestC || bestC == 0) {
					bestC = curr;
					bestR = row.clone();
				}
			}
			pop.remove(rand);
			pop.add(bestR);
		}
		return pop;
	}

}
