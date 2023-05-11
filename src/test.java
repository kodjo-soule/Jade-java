import java.util.Scanner;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class test extends Agent {
	int nombrex ;
  protected void setup() {
	  Scanner sc = new Scanner(System.in);
	  
    // l'ajout d'un one-shot behaviour pour afficher un Hello world :D
      addBehaviour(new OneShotBehaviour(this){
            public void action(){
                System.out.println("Bonjour tout le monde, je suis l'agent "+getLocalName());
          }
      });

    // l'ajout d'un  CyclicBehaviour pour afficher un message à chaque fois qu'il s'execute  
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {         
                System.out.println("cyclique... ");
                System.out.print("Entrez un nombre : ");
                nombrex = sc.nextInt() ;
      } 
    });

    // l'ajout d'un generic behaviour
    // le Behaviour s'arrête quand aléatoire reçoit la valeur 7
    addBehaviour(new RandomBehaviour());
  } 

  /**
   * Inner class RandomBehaviour
   */
  private class RandomBehaviour extends Behaviour {
    private int aleatoire ;

    public void action() {
        aleatoire = (int) (Math.random()*10);
        System.out.println("aleatoire ="+ aleatoire);
        System.out.println("Nombre :"+ nombrex );
        
    } 

    public boolean done() {
      return aleatoire == 7;
    } 

    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }    
}