import java.util.Arrays;

public class Swarm {
    private AntennaArray antennaArray;
    private Particle[] particles;
    private double[] gbest;
    private double gbestCost;

    public Swarm(AntennaArray ar, int pn) {
        antennaArray = ar;
        particles = new Particle[pn];
        gbest = antennaArray.generateRandomSolution();
        gbestCost = antennaArray.evaluate(gbest);

        // Create particles
        for (int i = 0; i < pn; i++) {
            particles[i] = new Particle(antennaArray);
        }
    }

    public double[] search(int iterations) {
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < particles.length; j++) {
                double[] pbest = particles[j].updateParticle(gbest);
                double pbestCost = antennaArray.evaluate(pbest);

                if (pbestCost < gbestCost) {
                    gbest = pbest;
                    gbestCost = pbestCost;
                }
            }
            System.out.println("gbest, gbestcost: " + Arrays.toString(gbest) + " " + gbestCost);
        }
        return gbest;
    }
}