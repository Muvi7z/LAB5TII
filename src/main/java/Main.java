
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.Random;

import static java.lang.Long.toUnsignedString;

public class Main {
     public static String toBinaryString(int i) {
          return toUnsignedString(i, 1);
     }
     public static class GeneticEngene {
          String[] phenotype ={};
          String[] population = {};
          GeneticEngene(){
               for(int i=0; i<5; i++){
                    Random random = new Random();
                    int a = random.nextInt(15);
                    phenotype[i]= Integer.toString(a, 2);
               }

               System.out.println();
          }
     }

     public static void main(String[] args) {
          GeneticEngene f = new GeneticEngene();
     }
}
