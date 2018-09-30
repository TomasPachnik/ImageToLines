package sk.tomas.iti.ga;

public abstract class Individual {

    protected double fitness = 0;
    private double runs = 0;

    public double getFitness() {
        return fitness;
    }

    public abstract void mutate();

    public abstract void init();

    public abstract void run();

    public abstract Individual[] cross(Individual parent2);
}
