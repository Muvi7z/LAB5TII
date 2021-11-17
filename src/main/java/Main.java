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
          public static void printPopulations(ArrayList<Population> populations){
               populations.stream().map(x->x.sign).forEach(System.out::println);
          }
          public int getSumBit() {
               return sumBit;
          }
     }
     public static class GeneticEngine {
          ArrayList<String> phenotype =new ArrayList<>();
          ArrayList<String> phenotypeCodeGrey =new ArrayList<>();
          ArrayList<Population> mutationPopulations = new ArrayList<>();
          Population maxPopulation = new Population("");
          String[] codeGray ={"0000","0001","0011","0010","0110","0111","0101","0100","1100","1101","1111","1110","1010","1011","1001","1000"};
          ArrayList<Population> populations = new ArrayList<>();
          ArrayList<Population> selectedPopulations = new ArrayList<>();
          ArrayList<Population> newPopulations = new ArrayList<>();
          GeneticEngine(){
               Random random = new Random();
               System.out.println("Признаки:");
               while (phenotype.size() < 5) {
                    int a = random.nextInt(14)+1;
                    String phen = Integer.toString(a, 2);
                    phen="0000".substring(phen.length())+phen;
                    if (!phenotype.contains(phen)) {
                         phenotype.add(phen);
                         phenotypeCodeGrey.add(codeGray[a]);
                         System.out.println(a+" - "+ phen +" - "+ codeGray[a]);

                    }
               }
               phenotype.addAll(phenotypeCodeGrey);

          }
          public void generatePopulation(){
               System.out.println("Сформированная популяция:");
               for(int i=0; i<5; i++){
                    populations.add(new Population(phenotype.get(0)+phenotype.get(i+2)));
                    populations.add(new Population(phenotype.get(1)+phenotype.get(i+2)));
               }
          }

          public ArrayList<Population> getPopulations() {
               return populations;
          }

          public void fitnessFun(){
               int min =8;
               int max =0;
               for (Population individ: populations) {
                    int sum = individ.sign.replaceAll("0","").length();
                    individ.setSumBit(sum);
                    if(sum<min){
                         min = sum;
                    }
                    if(sum>max){
                         max = sum;
                         maxPopulation = individ;
                    }
               }
               System.out.println("Фитнес функция:");
               for (Population individ: populations) {
                    individ.fitness = min * individ.sumBit;
                    System.out.println(individ.sign+" - Fitness: "+individ.fitness +" - Sum: " + individ.sumBit);
               }
          }
          public void tournamentSelection(){
               System.out.println("Особи прошедшие отбор:");
               for (int i=0; i<8; i++){
                    Population selected = new Population("0");
                    Random random = new Random();
                    selected = populations.get(random.ints(2,0,populations.size()).boxed().reduce((x,y) -> {
                         if(populations.get(x).fitness<populations.get(y).fitness){
                              return y;
                         }
                         return x;
                    }).get());
                    selectedPopulations.add(selected);
                    int j = selectedPopulations.indexOf(selected);
                    System.out.println(j +1+") "+ selected.sign);
               }
          }
          public void twoPointCrossover(){
               Random random = new Random();
               int fPoint = random.nextInt(4)+2;
               int sPoint = random.nextInt(4-(fPoint-2))+4+(fPoint-2);
               System.out.println("Популяция после скрещивания");
               for(int i=0; i<selectedPopulations.size()/2; i++){
                    String pathF= selectedPopulations.get(i*2).sign.substring(0,fPoint)+selectedPopulations.get((i*2)+1).sign.substring(fPoint, sPoint)+selectedPopulations.get(i*2).sign.substring(sPoint);
                    selectedPopulations.get(i*2).sign = selectedPopulations.get((i*2)+1).sign.substring(0,fPoint)+
                            selectedPopulations.get((i*2)).sign.substring(fPoint,sPoint)+
                            selectedPopulations.get((i*2)+1).sign.substring(sPoint);
                    selectedPopulations.get((i*2)+1).sign=pathF;
                    System.out.println(selectedPopulations.get(i*2).sign);
                    System.out.println(pathF);
               }
          }
          public void translocationMutation(){

               for(Population ind: selectedPopulations){
                    Random random = new Random();
                    int percent =random.nextInt(101);
                    if(percent<=30){
                         mutationPopulations.add(new Population(ind.sign.substring(0,2)+ind.sign.substring(5,7)+ind.sign.substring(2,5)+
                                 ind.sign.substring(7)));
                         //ind.sign = temp;
                    }
               }
          }
          public void generateNewPop(){
               System.out.println("Популяция наследников:");
               Population.printPopulations(selectedPopulations);
               newPopulations.addAll(selectedPopulations);
               System.out.println("Мутировавшие особи:");
               Population.printPopulations(mutationPopulations);
               newPopulations.addAll(mutationPopulations);
               System.out.println("Особь по принципу Элитизма:");
               System.out.println(maxPopulation.sign);
          }
     }

     public static void main(String[] args) {
          GeneticEngine f = new GeneticEngine();
          f.generatePopulation();
          Population.printPopulations(f.populations);
          f.fitnessFun();
          f.tournamentSelection();

          f.twoPointCrossover();
          f.translocationMutation();
          f.generateNewPop();
          f.getPopulations();
     }
}
