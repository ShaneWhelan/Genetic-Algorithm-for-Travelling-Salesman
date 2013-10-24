package travellingSalesman;
import java.util.ArrayList;

/**
 * @author Shane
 *
 */
public class Tour {
    private ArrayList<Integer> tour = new ArrayList<Integer>();
    private double fitness = 0;
    private int townID = 0;
    
    public Tour(){
        for(int i = 0; i < 100; i++){
            tour.add(-1);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Tour(ArrayList<Integer> tour){
        this.tour = (ArrayList<Integer>) tour.clone();
    }
    
    @SuppressWarnings("unchecked")
    public Tour(ArrayList<Integer> tour,  int townID){
        this.tour = (ArrayList<Integer>) tour.clone();
        this.townID = townID;
    }

    public ArrayList<Integer> getTour() {
        return tour;
    }

    @SuppressWarnings("unchecked")
    public void setTour(ArrayList<Integer> tour) {
        this.tour = (ArrayList<Integer>) tour.clone();
    }

    public double getFitness() {
        return fitness;
    }
    
    public void setTownPosition(int townNum, int townValue){
        tour.set(townNum, townValue);
        fitness = 0;
    }
    
    public int getTownPosition(int townNum){
        return tour.get(townNum);
    }
    
    public boolean townExists(int town){
        return tour.contains(town);
    }
    
    public double calculateFitness(){
        double fitness = 0;
        for(int i = 0; i < 100; i++){
            if(i != 99){
                fitness += TownDatabase.distance(tour.get(i),  tour.get(i+1));
            }else{
                fitness += TownDatabase.distance(tour.get(i), tour.get(0));
            }
        }
        this.fitness = fitness;
        return fitness;
    }
    
    @Override
    public String toString(){
        String printTour = "(";
        for (int i = 0; i < tour.size(); i++) {
        	// Make sure output file prints evenly
            int town = tour.get(i);
            if (town > 9 && town < 100) {
                printTour += town + "  ";
            }else if (town == 100) {
                printTour += town + " ";
            }else if(town >= 0){
                printTour += town + "   ";
            }else{
                printTour += town + "  ";
            }
        }
        printTour += ")";
        return printTour;
    }

    public int getTownID() {
        return townID;
    }

    public void setTownID(int townID) {
        this.townID = townID;
    }
    
    
}
 