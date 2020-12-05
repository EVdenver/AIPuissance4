import java.util.ArrayList;

public class Heuristiques {
	
	
	 	
	static public ArrayList<Case> couPossible(Jeu jeu) {
		
		ArrayList<Case> resultat = new ArrayList<>();
		for(int i=1;i<=jeu.matJeu[0].length;i++)// commence a 1 parsque dans la methode jeu.searchLine sa va de 1 a length
		{
			int l=jeu.searchLine(i);
			if(l!=-1) {
			Case n= new Case(l,i,0,jeu);
			
			
			resultat.add(n); 
			}
		}
		
		 
		return resultat;	
	}
	
	// renvoie la colone du choix avec le plus ou le moins de ligne possible en fonction du paramètre max pour le coups prochain uniquement
	static public int HeuriMinMax(Jeu jeu, boolean max) {

		ArrayList<Case> couPossible=couPossible(jeu);
		if(couPossible.isEmpty()) return -1;
		Case choix=couPossible.get(0);
		couPossible.remove(0);

		while(!couPossible.isEmpty()) {
			choix=(max)?((choix).comparePossibilite((Case)couPossible.get(1))>=0)?choix:couPossible.get(1)
					:((choix).comparePossibilite((Case)couPossible.get(1))<0)?choix:couPossible.get(1);

			couPossible.remove(0);
		}
		return choix.col;
	}
	
	// renvoie la colone qui lorsque ce cera le tour de l'adversaire lui laissera le moins de possibilité
	static public int HeuriBloqueur(Jeu jeu) {
		// 1 : trouver tout les choix possible de jeu 
		// 2 : pour chacun de ses choix possible evaluer le nombre de possiblité de du meilleur coup qe peut faire l'adversaire ( est-qu'il peut jouer un coup a 3 possibilité à 4 possibilité)
		// 3 : choissir le coup pour lequel de possibilité du meilleur coup adverse sera le plus faible
		
		return 0;
		
	}

}


