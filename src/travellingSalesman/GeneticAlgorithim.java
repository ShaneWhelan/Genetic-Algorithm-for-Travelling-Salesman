package travellingSalesman;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author Shane
 *
 */
public class GeneticAlgorithim {
    private static Population currentPopulation = new Population();
    public static Population matingPool = new Population();
    static DecimalFormat df = new DecimalFormat("#.##");
    //Best Fitness Statistics
    public static double bestFitness = 20000.00;
    public static int bestFitnessInd = 0;
    public static String actualFittestTour;
    

    @SuppressWarnings("unchecked")
	public static void processGeneration(Population population, BufferedWriter outputBuffer) throws IOException{
        // Each loop represents a generation
        currentPopulation = population;
        for(int genCount = 0; genCount < is09005763.generations; genCount++){
            matingPool.clearPopulation();
            // Generate a mating Pool as big as the Population 
            while(matingPool.getSize() != currentPopulation.getSize()) {
                
                // Tournament select which tour to operate on
                Tour currentTour = tournamentSelection(currentPopulation, currentPopulation.getSize()/3);
                
                // Generate random number to apply rates
                double operatorSelection = Math.random();
                double mutationUpper = is09005763.crossoverRate + is09005763.mutationRate;
                if(operatorSelection <= is09005763.crossoverRate && matingPool.getSize() < is09005763.populationSize-1){
                    // Crossover
                    Tour secondTour = tournamentSelection(currentPopulation, currentPopulation.getSize()/3);
                    if(is09005763.crossoverType.equals("a") == true) {
                        altPosCrossover(currentTour, secondTour);
                    }else if (is09005763.crossoverType.equals("o") == true) {
                        orderCrossover(currentTour, secondTour);
                    }else if (is09005763.crossoverType.equals("m") == true) {
                        maxCrossover(currentTour, secondTour);
                    }
                }else if(operatorSelection > is09005763.crossoverRate &&  operatorSelection <= mutationUpper) {
                    // Mutation
                    if(is09005763.mutationType.equals("e")){
                        exchangeMutation(currentTour);
                    }else if(is09005763.mutationType.equals("i")){
                        simpleInversionMutation(currentTour);
                    }else if(is09005763.mutationType.equals("v")){
                        inversionMutation(currentTour);
                    }
                }else if(operatorSelection > mutationUpper){
                    // Reproduce
                    matingPool.addIndividual(currentTour);
                }
            }        
            
            // Clone Array List of matingPool so you can clear matingPool Array List next generation
            currentPopulation = new Population((ArrayList<Tour>) matingPool.getPopulation().clone());
            // Recalculate the fitness of all tours with new population
            Tour currentFittestTour = currentPopulation.calculateFittest();
            // Save best results
            if(currentFittestTour.getFitness() < bestFitness){
                bestFitness = currentFittestTour.getFitness();
                bestFitnessInd = genCount;
                actualFittestTour = currentFittestTour.toString();
                // Goal State Reached
                if(currentFittestTour.getFitness() == 2089.00){
                	break;
                }
            }
            // Print out generation statistics
            outputBuffer.write(genCount + " " + df.format(currentPopulation.getAverageFitness()) + " " + df.format(currentFittestTour.getFitness()) + " " + currentFittestTour.toString() + "\n");
            // End generation
        }
        // 2089 is optimal
        outputBuffer.write("\nBest Individual: " + bestFitnessInd + " " + df.format(bestFitness) + " " + actualFittestTour + "\n");
        // DEV
        //System.out.println(validateTour(actualFittestTour.getTour()));
    }
    
    public static Tour tournamentSelection(Population population, int k){
        ArrayList<Tour> tournamentPool = new ArrayList<Tour>();
        // Randomly pick Individuals to add to tournament pool of size k
        Random rand = new Random();
        for(int i = 0; i < k; i++){
            tournamentPool.add(population.getPopulation().get(rand.nextInt(population.getPopulation().size())));
        }
        double bestFitnessSoFar = 20000.0;
        double currentFitness = 0;
        int bestIndividual = 0;
        for(int i = 0; i < tournamentPool.size(); i++){
            currentFitness = tournamentPool.get(i).calculateFitness();
            if(bestFitnessSoFar >= currentFitness){
                bestFitnessSoFar = currentFitness;
                bestIndividual = i;
            }
        }    
        return tournamentPool.get(bestIndividual);
    }
    
    // Alternate position crossover - Select every second town from parents and form offspring
    public static void altPosCrossover(Tour tour1, Tour tour2){
        ArrayList<Integer> childPath = new ArrayList<Integer>();
        // Loop over parents to form children
        for (int i = 0; i < 100; i++) {
            // If childTour doesn't have the tour add it to child
            if (!childPath.contains(tour1.getTownPosition(i))) {
                childPath.add(tour1.getTownPosition(i));           
            }

            if (!childPath.contains(tour2.getTownPosition(i))) {
                childPath.add(tour2.getTownPosition(i));
            }
        }
        Tour childTour = new Tour(childPath);
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
        
        // Reverse parents for second offspring
        ArrayList<Integer> childPath2 = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            if (!childPath2.contains(tour2.getTownPosition(i))) {
                childPath2.add(tour2.getTownPosition(i));           
            }

            if (!childPath2.contains(tour1.getTownPosition(i))) {
                childPath2.add(tour1.getTownPosition(i));
            }
        }
        // Create new child, recalculate fitness and add to matingPool
        Tour childTour2 = new Tour(childPath2);
        childTour2.calculateFitness();
        matingPool.addIndividual(childTour2);
    }
    
    // Order Crossover (OX1)
    public static void orderCrossover(Tour tour1, Tour tour2){
        Tour childTour = new Tour();
        int startPos = 0;
        int endPos = 0;
        // Loop forever until we have suitable cut point numbers
        while (true){
            startPos = (int) (Math.random() * 100);
            endPos = (int) (Math.random() * 100);
            if(startPos < endPos){
                break;
            }
        }
        
        // Loop and add the sub tour from tour1 to childTour 
        for (int i = startPos; i < endPos; i++) {
            childTour.setTownPosition(i, tour1.getTownPosition(i));
        }                
        // Loop from endPos to 100 in tour2 and check if town exists in child. If it doesn't find an empty position between endPos and 100 and add to child.
        for (int i = endPos; i < 100; i++) {
            if (!childTour.townExists(tour2.getTownPosition(i))) {
                for (int j = endPos; j < 100; j++) {
                    if (childTour.getTownPosition(j) == -1) {
                        childTour.setTownPosition(j, tour2.getTownPosition(i));
                        break;
                    }
                }
            }
        }
        // Loop from 0 to endPos and check if town exists. If it doesn't find an empty position between endPos and 100 ELSE if no empty position between endPos and 100 loop from 0 to 100 for an empty position
        for (int i = 0; i < endPos; i++) {
            if (!childTour.townExists(tour2.getTownPosition(i))) {
                for (int j = endPos; j < 100; j++) {
                    if (childTour.getTownPosition(j) == -1) {
                        childTour.setTownPosition(j, tour2.getTownPosition(i));
                        break;
                    }
                }
                if(childTour.getTownPosition(99) != -1 && childTour.getTownPosition(99) != tour2.getTownPosition(i)){
                    for (int j = 0; j < 100; j++) {
                        if (childTour.getTownPosition(j) == -1) {
                            childTour.setTownPosition(j, tour2.getTownPosition(i));
                            break;
                        }
                    }
                }
            }
        }
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
        
        // Reverse parents for second child
        Tour childTour2 = new Tour();
        
        for (int i = startPos; i < endPos; i++) {
            childTour2.setTownPosition(i, tour2.getTownPosition(i));
        }                
        
        for (int i = endPos; i < 100; i++) {
            if (!childTour2.townExists(tour1.getTownPosition(i))) {
                for (int j = endPos; j < 100; j++) {
                    if (childTour2.getTownPosition(j) == -1) {
                        childTour2.setTownPosition(j, tour1.getTownPosition(i));
                        break;
                    }
                }
            }
        }
    
        for (int i = 0; i < endPos; i++) {
            if (!childTour2.townExists(tour1.getTownPosition(i))) {
                for (int j = endPos; j < 100; j++) {
                    if (childTour2.getTownPosition(j) == -1) {
                        childTour2.setTownPosition(j, tour1.getTownPosition(i));
                        break;
                    }
                }
                if(childTour2.getTownPosition(99) != -1 && childTour2.getTownPosition(99) != tour1.getTownPosition(i)){
                    for (int j = 0; j < 100; j++) {
                        if (childTour2.getTownPosition(j) == -1) {
                            childTour2.setTownPosition(j, tour1.getTownPosition(i));
                            break;
                        }
                    }
                }
            }
        }
        childTour2.calculateFitness();
        matingPool.addIndividual(childTour2);
    }
    
    // Maximal Preservative Crossover (MPX)
    public static void maxCrossover(Tour tour1, Tour tour2) {        
        int startPos = 0;
        int endPos = 0;
        // Loop forever until we have suitable cut point numbers
        while (true){
            startPos = (int) (Math.random() * 100);
            endPos = (int) (Math.random() * 100);
            if(endPos-startPos >= 10 && endPos-startPos <= 50 && startPos < endPos){
                break;
            }
        }
        
        ArrayList<Integer> childPath = new ArrayList<Integer>();
        //Loop through sub tour add to child
        for (int i = startPos; i <= endPos; i++){
        	childPath.add(tour1.getTownPosition(i));
        }
    
        // Add childPath to the end of above sub tour
        for (int i = 0; i < 100; i++){
            if(childPath.contains(tour2.getTownPosition(i)) == false){
            	childPath.add(tour2.getTownPosition(i));
            }
        }
        
        Tour childTour = new Tour(childPath);
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
        
        ArrayList<Integer> childPath2 = new ArrayList<Integer>();
        for (int i = startPos; i <= endPos; i++){
        	childPath2.add(tour2.getTownPosition(i));
        }
    
        for (int i = 0; i < 100; i++){
            if(childPath2.contains(tour1.getTownPosition(i)) == false){
            	childPath2.add(tour1.getTownPosition(i));
            }
        }
        
        Tour childTour2 = new Tour(childPath2);
        childTour2.calculateFitness();
        matingPool.addIndividual(childTour2);
    }
    
    // Exchange Mutation (EM)
    public static void exchangeMutation(Tour tour){
    	Tour childTour = new Tour(tour.getTour());
        int swapPos1 = 0;
        int swapPos2 = 0;
        // Loop forever until we have two distinct swap numbers
        while (true){
            swapPos1 = (int) (Math.random() * 100);
            swapPos2 = (int) (Math.random() * 100);
            if(swapPos1 != swapPos2){
                break;
            }
        }

        // Put towns at swap indices in temporary variables
        int tempTown1 = childTour.getTownPosition(swapPos1);
        int tempTown2 = childTour.getTownPosition(swapPos2);
        // Do actual swap
        childTour.setTownPosition(swapPos1, tempTown2);
        childTour.setTownPosition(swapPos2, tempTown1);
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
    }
    
    // Simple Inversion Mutation
    public static void simpleInversionMutation(Tour tour){
    	Tour childTour = new Tour(tour.getTour());
        int startPos = 0;
        int endPos = 0;
        // Loop forever until we have suitable cut point numbers
        while (true){
            startPos = (int) (Math.random() * 100);
            endPos = (int) (Math.random() * 100);
            if(startPos < endPos){
                break;
            }
        }
        // Reverse the numbers between cut off points
        int reverse = endPos;
        int tempTown = 0;
        for(int i = startPos; i < reverse; i++){
            tempTown = childTour.getTownPosition(i);
            childTour.setTownPosition(i, childTour.getTownPosition(reverse));
            childTour.setTownPosition(reverse, tempTown);
            reverse--;
        }
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
    }

    // Inversion Mutation
    public static void inversionMutation(Tour tour){
    	Tour childTour = new Tour(tour.getTour());
        int startPos = 0;
        int endPos = 0;
        // Loop forever until we have suitable cut point numbers
        while (true){
            startPos = (int) (Math.random() * 100);
            endPos = (int) (Math.random() * 100);
            if(startPos < endPos){
                break;
            }
        }
        
        ArrayList<Integer> childPath = new ArrayList<Integer>();
        
        for(int i = 0; i < tour.getTour().size(); i++){
            if(i < startPos || i > endPos){
            	childPath.add(tour.getTownPosition(i));
            }
        }
        // Reverse the numbers between cut off points
        int reverse = endPos;
        int tempTown = 0;
        for(int i = startPos; i < reverse; i++){
            tempTown = childTour.getTownPosition(i);
            childTour.setTownPosition(i, childTour.getTownPosition(reverse));
            childTour.setTownPosition(reverse, tempTown);
            reverse--;

        }
        for(int i = startPos; i <= endPos; i++){
        	childPath.add(childTour.getTownPosition(i));
        }
        
        childTour.setTour(childPath);
        childTour.calculateFitness();
        matingPool.addIndividual(childTour);
    }
    
    public static boolean validateTour(ArrayList<Integer> tour){
        boolean[] tourTest = new boolean[100];
        int sum = 0; 
        for(int i = 0; i < tour.size(); i++){
            tourTest[tour.get(i)-1] = true;
            sum = sum + tour.get(i);
        }  
        for(int i = 0; i < tourTest.length; i++){
            if(tourTest[i] != true){
                return false;
            }
        }
        // Double check that numbers add up to 1+2+3...100
        if(sum != 5050){
             return false;
        }
        return true;
    }
    
    public static void printPopulation(Population population, BufferedWriter outputBuffer) throws NumberFormatException, IOException{
        ArrayList<Tour> populationList = population.getPopulation();
        String populationToPrint = "Generation\n";
        for(int i = 0; i < populationList.size(); i++){    
            Tour tour = populationList.get(i);
            populationToPrint += tour.getTownID() + " " + tour.toString() + " " + df.format(tour.getFitness()) + "\n"; 
        }
        outputBuffer.write(populationToPrint);
    }
}
