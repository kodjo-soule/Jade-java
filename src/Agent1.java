import jade.core.Agent ;
//un simple agent qui affiche un texte
public class Agent1 extends Agent{
	//Initialisation de l'agent
	@Override
	protected void setup() {
		System.out.println("Creation d'Agent  : "+ this.getName());
	}
	
	//Fin de traitement 
	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		super.takeDown();
		System.out.println("C'est fini !");
	}
}
