import java.util.ArrayList;
import java.util.Random;

import static java.lang.Long.toUnsignedString;

public class Main {
     public static String toBinaryString(int i) {
          return toUnsignedString(i, 1);
     }
     public static class Population{
          String sign;
          double fitness = 0;
          int sumBit = 0;
          Population(String sign){
               this.sign=sign;
          }
          public void setFitness(Double fitness) {
               this.fitness = fitness;
          }

          public void setSumBit(int sumBit) {
               this.sumBit = sumBit;
          }

          public Double getFitness() {
               return fitness;
          }

          public int getSumBit() {
               return sumBit;
          }
     }
     public static class GeneticEngine {
          String[] phenotype =new String[10];
          String[] codeGray ={"0000","0001","0011","0010","0110","0111","0101","0100","1100","1101","1111","1110","1010","1011","1001","1000"};
          ArrayList<Population> populations = new ArrayList<>();
          ArrayList<Population> selectedPopulations = new ArrayList<>();
          GeneticEngine(){
               Random random = new Random();
               for(int i=0; i<5; i++){
                    int a = random.nextInt(14)+1;
                    phenotype[i]= Integer.toString(a, 2);
                    phenotype[i]= "0000".substring(phenotype[i].length())+phenotype[i];
                    phenotype[i+5]=codeGray[a];
               }
          }
          public void generatePopulation(){
               for(int i=0; i<5; i++){
                    populations.add(new Population(phenotype[0]+phenotype[i+2]));
                    populations.add(new Population(phenotype[1]+phenotype[i+2]));
//                    System.out.println(population[i*2]);
//                    System.out.println(population[(i*2)+1]);
               }
          }

          public ArrayList<Population> getPopulations() {
               return populations;
          }

          public void fitnessFun(){
               int min =8;
               for (Population individ: populations) {
                    int sum = individ.sign.replaceAll("0","").length();
                    individ.setSumBit(sum);
                    if(sum<min){
                         min = sum;
                    }
               }
               for (Population individ: populations) {
                    individ.fitness = min * individ.sumBit;
               }
          }
          public void tournamentSelection(){
               for (int i=0; i<populations.size(); i++){
                    Population selected = new Population("0");
                    Random random = new Random();
                    selected = populations.get(random.ints(3,0,populations.size()).boxed().reduce((x,y) -> {
                         if(populations.get(x).fitness<populations.get(y).fitness){
                              return y;
                         }
                         return x;
                    }).get());
                    selectedPopulations.add(selected);
               }
          }
     }

     public static void main(String[] args) {
          GeneticEngine f = new GeneticEngine();
          f.generatePopulation();
          f.fitnessFun();
          f.tournamentSelection();
          f.getPopulations();
     }
}
