package sk.tomas.iti.ga.impl;

import sk.tomas.iti.core.IndividualImpl;
import sk.tomas.iti.ga.Genetic;
import sk.tomas.iti.ga.Individual;
import sk.tomas.iti.ga.Population;
import sk.tomas.iti.gui.ImagePanel;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Inject;

import java.io.*;
import java.util.Random;

public class GeneticImpl implements Genetic {

    @Inject
    private ImagePanel imagePanel;

    private double crossRate = 0.7; //crossing probability 0.7 - 1.0
    private double mutationRate = 0.05; //mutation of each gene probability 0.05
    private int populationSize = 50; //number of individuals in population 50
    private int generations = 100; //number of generations
    private int networkRuns = 1; //number of iteration for every network
    private Population population;
    //randoms
    private Random crossingRandom;
    private Random parentRandom;
    private Random selectionRandom;
    private Random mutationRandom;
    private Random gaussianRandom;

    private Individual best;

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
                if (crossRate > Math.random()) {
                    Individual[] children = parent1.cross(parent2);
                    newPopulation.getPopulation().add(children[0]);
                    newPopulation.getPopulation().add(children[1]);
                } else {
                    newPopulation.getPopulation().add(parent1);
                    newPopulation.getPopulation().add(parent2);
                }
            }

            //mutation
            for (Individual individual : newPopulation.getPopulation()) {
                individual.mutate(mutationRate);
            }

            newPopulation.execute();//calculate fitness of each individual

            if (best != null) {
                if (best.getFitness() < population.getBest().getFitness()) {
                    best = (Individual) clone(population.getBest());
                }
                newPopulation.getPopulation().set(0, best); //elitism
            } else {
                best = (Individual) clone(population.getBest());
            }
            population = newPopulation;
            index++;
            System.out.println("population: " + index + ", fitness: " + best.getFitness());
            imagePanel.setBest((IndividualImpl) best);
            imagePanel.repaint();

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

    private Object clone(Object orig) {
        Object obj = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

