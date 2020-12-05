import java.util.ArrayList;

public class OrdiEuristique2 {
	private Jeu j;
	private boolean joueur;
	byte[][] matJeu;
	
	
	public OrdiEuristique2(Boolean joueur, Jeu j, byte[][] matJeu){
		this.j=j;
		this.joueur=joueur;
		this.matJeu=matJeu;
		
	}
	
	public <Case> ArrayList<Case> couPossible() {
		
		ArrayList<Case> resultat = new ArrayList<>();
		for(int i=0;i<matJeu[0].length-1;i++)
		{
			int c=i;
			int l=j.searchLine(i);
			if(l!=-1) {
			int val= matJeu[l][c];
			Case n= new Case(l,c,val,j);
			resultat.add(n);
			}
		}
		
		 
		return resultat;	
	}
	public int HeuriChoix(ArrayList couPossible) {
		
		
		Case choix=new Case(0,0,0,j);
		if (matJeu[0].length%2==1) {
			
			choix=((Case) couPossible.get(0)).compareCase4((Case)couPossible.get(1),joueur);
			couPossible.remove(0);
			couPossible.remove(0);
			joueur=!joueur;
			while(!couPossible.isEmpty()) {
				choix=choix.compareCase4((Case) couPossible.get(0),joueur);
				couPossible.remove(0);
				joueur=!joueur;
			}
		}
		if (matJeu[0].length%2==0) {
			
		}
		
		
		
		return choix.col;
	}
	
}
