package sk.tomas.iti.ga.impl;

import sk.tomas.iti.ga.Genetic;
import sk.tomas.iti.ga.Individual;
import sk.tomas.iti.ga.Population;

import java.util.Random;

public class GeneticImpl implements Genetic {

    private double crossRate = 0.7; //crossing probability 0.7 - 1.0
    private double mutationRate = 0.05; //mutation of each gene probability 0.05
    private int populationSize = 50; //number of individuals in population 50
    private int generations = 30; //number of generations
    private int networkRuns = 1; //number of iteration for every network
    private Population population;
    //randoms
    private Random crossingRandom;
    private Random parentRandom;
    private Random selectionRandom;
    private Random mutationRandom;
    private Random gaussianRandom;

    private Class<? extends Individual> clazz;

    public GeneticImpl(Class<? extends Individual> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void run() {
        crossingRandom = new Random();
        parentRandom = new Random();
        selectionRandom = new Random();
        mutationRandom = new Random();
        gaussianRandom = new Random();

        int index = 0;
        Population newPopulation;
        while (index < generations) {

            newPopulation = new Population(networkRuns);

            if (population == null) { //population zero
                population = new Population(populationSize, networkRuns, clazz);
                System.out.println("population zero performed");
            }

            //crossing
            for (int i = 0; i < population.getPopulation().size() / 2; i++) {
                Individual parent1 = selection(population); //selection
                Individual parent2 = selection(population); //selection
                Individual[] children = parent1.cross(parent2);
                newPopulation.getPopulation().add(children[0]);
                newPopulation.getPopulation().add(children[1]);
            }

            //mutation
            for (Individual individual : newPopulation.getPopulation()) {
                individual.mutate();
            }

            newPopulation.execute();//calculate fitness of each individual

            newPopulation.getPopulation().set(0, population.getBest()); //elitism
            population = newPopulation;
            index++;
            System.out.println("population: " + index + ", fitness: " + population.getBest().getFitness());

        }
    }

    //roulette selection algorithm
    private Individual selection(Population population) {
        double sum = 0;
        double dd = selectionRandom.nextDouble();
        double fitnessPoint = dd * population.getSumFitness();
        for (Individual individual : population.getPopulation()) {
            if (individual.getFitness() > 0) {
                sum += individual.getFitness();
                if (sum > fitnessPoint) {
                    return individual;
                }
            }
        }
        throw new RuntimeException("wrong selection -> this should not happen at all");
    }

}
