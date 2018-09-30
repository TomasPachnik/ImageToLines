package sk.tomas.iti.ga;

import java.io.Serializable;

public abstract class Individual implements Serializable {

    protected double fitness = 0;
    private double runs = 0;

    public double getFitness() {
        return fitness;
    }

    public abstract void mutate(double mutationRate);

    public abstract void init();

    public abstract void run();

    public abstract Individual[] cross(Individual parent2);
}
