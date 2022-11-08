import java.util.Random;

public class Particle {
    /*
    The coefficients control the distance moved by the particle in iteration.
     */
    public static final double INERTIAL_COEFFICIENT = 0.721;
    public static final double COGNITIVE_COEFFICIENT = 1.1193;
    public static final double SOCIAL_COEFFICIENT = 1.1193;

    private AntennaArray antennaArray;

    private double[] position;
    private double[] velocity;
    private double[] pbest;
    private double pbestCost;

    public Particle(AntennaArray antArr) {
        antennaArray = antArr;

        position = antennaArray.generateRandomSolution();
        pbest = position;
        pbestCost = antennaArray.evaluate(pbest);

        velocity = new double[position.length];

        double[] pRandom = antennaArray.generateRandomSolution();

        for (int i = 0; i < position.length - 1; i++) {
            velocity[i] = (pRandom[i] - position[i]) / 2;
        }
    }

    /* todo error handling using invisible wall */
    public double[] calculateNewVelocity(double[] gbest) {
        Random rand = new Random();
        double[] newVelocity = new double[velocity.length];

        for (int i = 0; i < velocity.length-1; i++) {
            newVelocity[i] = (INERTIAL_COEFFICIENT * velocity[i]) + COGNITIVE_COEFFICIENT * rand.nextDouble() * (pbest[i] - position[i])
                    + SOCIAL_COEFFICIENT * rand.nextDouble() * (gbest[i] - position[i]);
        }

        newVelocity[velocity.length-1] = 0;

        return newVelocity;
    }

    public double[] calculateNewPosition() {
        double[] newPosition = new double[position.length];

        for (int i = 0; i < position.length; i++) {
            newPosition[i] = position[i] + velocity[i];
        }

        return newPosition;
    }


    public double[] updateParticle(double[] gbest) {
        velocity = calculateNewVelocity(gbest);
        position = calculateNewPosition();
        double newCost = antennaArray.evaluate(position);
        boolean isValidPosition = antennaArray.is_valid(position);

        if (newCost < pbestCost && isValidPosition) {
            pbest = position;
            pbestCost = newCost;
        }

        return position;
    }

    public double getPbestCost() {
        return pbestCost;
    }

    public double[] getPbest() {
        return pbest;
    }
}