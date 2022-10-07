package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Loader {

	private int N;
	private ArrayList<String> lines;
	private double[][] coords;
	private double[][] graph;
	
	public Loader() {
		load();
		plotGraph();
	}

	private void load() {
		lines = new ArrayList<String>();
		Scanner sc;
		try {
			sc = new Scanner(new File("src/cities18_9.csv"));
			while (sc.hasNext()) {  
				lines.add(sc.next());
			}
			sc.close();
			this.N = lines.size() - 8;
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	private void plotGraph() {
		this.coords = new double[N][2];
		this.graph = new double [N][N];
		String[] tempArray = new String[3];
		double[] intArray = new double[3];
		for (int i = 8; i < lines.size(); i++) {
			tempArray = lines.get(i).split(",");
			intArray = Arrays.stream(tempArray).mapToDouble(Double::parseDouble).toArray();
			for (int j = 0; j < 2; j++) {
				this.coords[i - 8][j] = intArray[j + 1];
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				graph[i][j] = getDistance(coords[i], coords[j]);
			}
		}
	}
	
	private double getDistance(double[] a, double[] b) {
		double x = a[0] - b[0], y = a[1] - b[1];
		double d = Math.sqrt((x*x)+(y*y));
		return d;
	}
	
	public void printCoords() {
		for (double[] coord : coords) {
			System.out.println(Arrays.toString(coord));
		}
	}
	
	public void printGraph() {
		for (double[] row : graph) {
			System.out.println(Arrays.toString(row));
		}
	}
	
	public double[][] getGraph() { return this.graph;}
	public int getN() {return this.N;}
}
