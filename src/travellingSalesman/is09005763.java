package travellingSalesman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class is09005763 {
    static DecimalFormat df = new DecimalFormat("#.##");
    public static int populationSize;
    public static int generations;
    public static String crossoverType;
    public static double crossoverRate;
    public static String mutationType;
    public static double mutationRate;
    
    public static void main(String [] args) throws IOException{    
        long startTime = System.nanoTime();
        // Process command line arguments
        if(args.length < 7) {
            System.out.println("Usage: is09005763.java populationSize generatons crossoverType crossoverRate mutationType mutationRate outputFile");
            System.exit(0);
        }

        populationSize = Integer.parseInt(args[0]);
        generations = Integer.parseInt(args[1]);
        crossoverType = args[2];
        crossoverRate = Double.parseDouble(args[3]);
        mutationType = args[4];
        mutationRate = Double.parseDouble(args[5]);
        String outputFileName = args[6];
        
        crossoverType = crossoverType.toLowerCase();
        mutationType = mutationType.toLowerCase();
        
        // Check command line arguments
        if(populationSize < 3){
            System.out.println("Population Size too small");
            System.exit(0);
        }else if(generations < 1){
            System.out.println("Generation number should be > 0");
            System.exit(0);
        }else if(!(crossoverType.equals("a") == true || crossoverType.equals("o") == true || crossoverType.equals("m") == true)){
            System.out.println("Invalid paramter entered for crossoverType");
        }else if(!(mutationType.equals("e") == true || mutationType.equals("i") == true || mutationType.equals("v") == true)){
            System.out.println("Invalid paramter entered for mutationType");
        }else if(crossoverRate >= 1){
            System.out.println("Crossover rate too large.");
        }else if(mutationRate >= 1){
            System.out.println("Mutation rate too large.");
        }else if((mutationRate + crossoverRate) > 1){
        	System.out.println("Mutation rate + Crossover rate > 1");
        }
        
        // Set up seed
        long seed = 9005772;
        //long seed = System.nanoTime();
        Random rand = new Random(seed);
        
        // Set up File I/O
        File outputFile = new File(outputFileName);
        FileWriter outputFileWriter = new FileWriter(outputFile, false);
        BufferedWriter outputBuffer = new BufferedWriter(outputFileWriter) ;
        System.out.println("Output File found at " + outputFile.getAbsolutePath());
        
        // Write out command line arguments
        outputBuffer.write(populationSize + " " + generations + " " + crossoverType + " " + 
        crossoverRate + " " + mutationType     + " " + mutationRate + " " + outputFileName + " " + seed + "\n\n");
        
        // Populate an array of numbers 1-100. These numbers represent each town
        int[] pathNumbers = new int[100];
        for(int i = 1; i <= 100; i++){
            pathNumbers[i-1] = i;
        }
        
        // Create initial population
        ArrayList<Tour> populationList = new ArrayList<Tour>();
        for(int pop = 0; pop < populationSize; pop++){
            // Copy numbers 1 - 100 to preserve original array
            int[] individual = pathNumbers;
            ArrayList<Integer> tour = new ArrayList<Integer>();
            // Do Fisher-Yates Shuffle (or Knuth shuffle)
            for(int i = 0; i < 100; i++){
                int randVal = i + (int) (rand.nextDouble() * (100 - i));
                int shuffleVal = individual[randVal];
                individual[randVal] = individual[i];
                individual[i] = shuffleVal;
            }

            for(int i = 0; i < 100; i++){
                tour.add(individual[i]);
            }
            Tour newTour = new Tour(tour, pop);
            populationList.add(newTour);
        }
        
        Population populationForProcessing = new Population(populationList);
        populationForProcessing.calculateFittest();
        // Enter genetic algorithm
        GeneticAlgorithim.processGeneration(populationForProcessing, outputBuffer);
        outputBuffer.close();
        
        // Performance Test
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double seconds = (double)duration / 1000000000.0;
        System.out.println("Execution Time in Seconds: " + seconds);
    }
}
