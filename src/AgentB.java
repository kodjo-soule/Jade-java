
import jade.core.Agent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentB extends Agent {
  
  String reponse ;
  boolean stop = false;
  
  // function verifie 
  public static String verifie(int nbre) {
		int reste;
		boolean flag = true ;	        
		for(int i=2; i <= nbre/2; i++){
			//nombre est divisible par lui-meme
		    reste = nbre%i;
		            
		    //si le reste est 0, alors arrete la boucle. Sinon continuer la boucle
		    if(reste == 0){
		    	flag = false;
		        break;
		     }
		}
		//si flag est true, alors nombre est premier, sinon non premier
		if(flag)
			return nbre + " est un nombre premier" ;
		 else
		    return nbre + " n'est pas un nombre premier" ;
	}
  //end function verifie
  
  
  protected void setup(){
    System.out.println("----------------agent B----------------");
    FSMBehaviour agentB_beh= new FSMBehaviour();
    
    
    //definiton des etats
    agentB_beh.registerFirstState(new attendrechiffre(), "attendrechiffre");
    agentB_beh.registerState(new VerifieNombre(), "VerifieNombre");
    agentB_beh.registerState(new envoieReponse(), "envoieReponse");
    agentB_beh.registerLastState(new fin(), "fin");
    
    agentB_beh.registerDefaultTransition("attendrechiffre", "afficher");
    agentB_beh.registerTransition("attendrechiffre", "VerifieNombre",0);
    agentB_beh.registerTransition("VerifieNombre", "envoieReponse",0);
    agentB_beh.registerTransition("envoieReponse", "fin",0);
    
    addBehaviour(agentB_beh);
  }
  /**********************************************************************/
  private class attendrechiffre extends OneShotBehaviour{

	    int valeurRetour = 0;
	    
	    public void action() {
	    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
	    message.addReceiver(new AID("AgentA", AID.ISLOCALNAME));  
	        
	      if(!stop){
	        message.setContent("pret");
	        send(message);
	        valeurRetour=0;
	        block();
	      }else{
	        message.setContent("arret");
	        send(message);
	        valeurRetour=1;  
	      }    
	    }
	    public int onEnd(){
	      return valeurRetour;
	    }  
	  }
  /**********************************************************************/
  private class VerifieNombre extends OneShotBehaviour{
	  int valeurReTour = 0 ;
    @Override
    public void action() {
 
      ACLMessage messageRecu = receive();
      int nombre = Integer.parseInt(messageRecu.getContent());
      System.out.println("Nombre recue "+ nombre);
      reponse = verifie(nombre) ;
    }  
  }
  /**********************************************************************/
  private class envoieReponse extends OneShotBehaviour{  
	    @Override
	    public void action() {
	    	ACLMessage msg = new ACLMessage(ACLMessage.INFORM) ;
	    	msg.setContent(reponse);
	    	msg.addReceiver(new AID("AgentA", AID.ISLOCALNAME));
	    	send(msg);
	    	//stop = true ;
	    }
  }
  /**********************************************************************/
  private class fin extends OneShotBehaviour{
    @Override
    public void action() {
      System.out.println("fin de l'agent");
      myAgent.doDelete();
    }  
  }
}