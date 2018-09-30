package sk.tomas.iti;


import sk.tomas.iti.core.IndividualImpl;
import sk.tomas.iti.ga.Genetic;
import sk.tomas.iti.ga.impl.GeneticImpl;
import sk.tomas.servant.annotation.Bean;
import sk.tomas.servant.annotation.Config;
import sk.tomas.servant.core.Servant;

@Config
public class Main {

    @Bean
    public Genetic genetic() {
        return new GeneticImpl(IndividualImpl.class);
    }

    public static void main(String[] args) {
        Servant.scanPackage("sk.tomas.iti");
        Servant.addConfiguration(Main.class);

    }
}
