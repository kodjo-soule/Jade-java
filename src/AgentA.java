import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentA extends Agent {
	Scanner sc = new Scanner(System.in) ;
  
protected void setup(){
  
  System.out.println("----------------agent A----------------");
  
  FSMBehaviour agentA_beh= new FSMBehaviour();
  //definiton des etats
  agentA_beh.registerFirstState(new attendreAgentB(), "attendreAgentB");
  agentA_beh.registerState(new envoiChiffre(), "envoiChiffre");
  agentA_beh.registerState(new afficherReponse(), "afficherReponse");
  agentA_beh.registerLastState(new fin(), "fin");
  
  agentA_beh.registerDefaultTransition("attendreAgentB", "envoiChiffre");
  agentA_beh.registerTransition("envoiChiffre", "attendreAgentB",0);
  agentA_beh.registerTransition("envoiChiffre", "afficherReponse",0);
  agentA_beh.registerTransition("afficherReponse", "fin",0);
  
  addBehaviour(agentA_beh);  
}

private class attendreAgentB extends OneShotBehaviour{

  @Override
  public void action() {
  System.out.println("en attente de l agent B");
  block();
  }
}
/*****************************************************************/
private class envoiChiffre extends OneShotBehaviour{

  int valeurRetour = 0;
  @Override
  public void action() {
  
  ACLMessage messageRecu = receive();
  if (messageRecu.getContent().equalsIgnoreCase("pret") ) {
	  valeurRetour=0;
  }else{
	  valeurRetour=1;
  }
  
  
  System.out.print("Entrez un nombre  : ");
  int nombre = sc.nextInt() ;
  System.out.println("Envoi du nombre :"+ nombre);
  ACLMessage messageEnvoie = new ACLMessage(ACLMessage.INFORM);
  //messageEnvoie.addReceiver(messageRecu.getSender()); OLD 58
  messageEnvoie.addReceiver(new AID("AgentB", AID.ISLOCALNAME));
  messageEnvoie.setContent(nombre+"");  
  send(messageEnvoie);
  block() ;
  }
  
  public int onEnd(){
  return valeurRetour;
  }  
}
private class afficherReponse extends OneShotBehaviour{

	  @Override
	  public void action() {
	  
	  ACLMessage reponseRecu = receive();
	  String reponse = reponseRecu.getContent() ;
	  System.out.println("LA reponse est : "+ reponse);
	  }  
}
/*****************************************************************/
private class fin extends OneShotBehaviour{

  @Override
  public void action() {
  System.out.println("Fin de Communication");
  myAgent.doDelete();  
  }  
}
}