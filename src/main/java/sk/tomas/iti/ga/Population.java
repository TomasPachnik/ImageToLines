package sk.tomas.iti.ga;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Individual> population;
    private final int networkRuns;

    public Population(int networkRuns) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
    }

    public Population(int populationSize, int networkRuns, Class<? extends Individual> clazz) {
        this.networkRuns = networkRuns;
        this.population = new ArrayList<>();
        try {
            initPopulation(populationSize, clazz);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void initPopulation(int populationSize, Class<? extends Individual> clazz) throws IllegalAccessException, InstantiationException {
        int i = 0;
        while (i < populationSize) {
            Individual individual = clazz.newInstance();
            individual.init();
            population.add(individual);
            i++;
        }
    }


    public void execute() {
        for (Individual individual : population) {
            for (int i = 0; i < networkRuns; i++) {
                individual.run();
            }
        }
    }

    public Individual getBest() {
        double bestScore = -999;
        Individual bestNetwork = null;
        for (Individual individual : population) {
            if (individual.getFitness() > bestScore) {
                bestScore = individual.getFitness();
                bestNetwork = individual;
            }
        }
        return bestNetwork;
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public double getSumFitness() {
        double sum = 0;
        for (Individual individual : population) {
            if (individual.getFitness() > 0) {
                sum += individual.getFitness();
            }
        }
        return sum;
    }

}
