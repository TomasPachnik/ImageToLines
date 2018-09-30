package sk.tomas.iti;


import sk.tomas.iti.core.IndividualImpl;
import sk.tomas.iti.ga.Genetic;
import sk.tomas.iti.ga.impl.GeneticImpl;
import sk.tomas.servant.core.Servant;

public class Main {

    public static void main(String[] args) {
        //Servant.scanPackage("sk.tomas.iti");

        Genetic genetic = new GeneticImpl(IndividualImpl.class);
        genetic.run();
    }
}
