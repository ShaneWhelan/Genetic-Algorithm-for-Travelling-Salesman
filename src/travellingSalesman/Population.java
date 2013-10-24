package travellingSalesman;
import java.util.ArrayList;

/**
 * @author Shane
 *
 */
public class Population{
    private ArrayList<Tour> population = new ArrayList<Tour>();
    private Tour bestFitnessTour;
    private double bestFitnessNumber = 20000.0;
    private double averageFitness = 0;
    
    Population(){
        bestFitnessNumber = 20000.0;
    }
    
    Population(ArrayList<Tour> population){
        this.population = population;
        bestFitnessNumber = 20000.0;    
    }
    
    public Tour calculateFittest(){
        double currentFitness = 0;
        bestFitnessNumber = 20000.00;
        for(int i = 0; i < population.size(); i++){
            currentFitness = population.get(i).calculateFitness();
            averageFitness += currentFitness;
            if(currentFitness < bestFitnessNumber){
                bestFitnessTour = population.get(i);
                bestFitnessNumber = currentFitness;
            }
        }
        averageFitness = averageFitness / population.size();
        return bestFitnessTour;
    }
    
    public double getAverageFitness(){
        return averageFitness;
    }
    
    public void addIndividual(Tour addTour){
        population.add(addTour);
    }

    public ArrayList<Tour> getPopulation() {
        return population;
    }

    public void clearPopulation(){
        population.clear();
    }
    
    public int getSize(){
        return population.size();
    }
}
