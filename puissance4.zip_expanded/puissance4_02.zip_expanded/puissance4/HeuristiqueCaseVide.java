import java.util.ArrayList;

public class HeuristiqueCaseVide {
	private Jeu j;
	private ArrayList<Case> casePossible = new ArrayList<Case>();
	private boolean jMinMax = true; //booleen pour l'algo min max, true = min; false = max
	
	public HeuristiqueCaseVide(Jeu j) {
		this.j = j;
	}
	
	public int ordiJoue(boolean joueur) {
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (joueur)
			jVal = 2;
		// On recherche toute les cases vides possible
		for (int col = 0; col < j.matJeu[0].length; col++) {
			int ligne = j.searchLine(col);
			if (ligne != -1) {
				Case caseVide = (Case)j.plateau.pane.getComponent((j.opts.getGameWidth()) * (ligne - 1) + (col - 1));
				casePossible.add(caseVide);
			}
		}
		//On parcours ensuite le tableau en supprimant �l�ment par �l�ment en fonction de notre heuristique et en choisissant � chaque �tape soit le min soit le max
		elagageMinMax();
		return casePossible.get(0).col;
			
	}
	
	public void elagageMinMax() {
		//La premi�re boucle correspond � la boucle testant la suppression des �l�ments et ne s'arr�tent que lorsque notre tableau de case ne contient qu'un �l�ment soit la case choisie par l'ordinateur.
		//Chaque it�ration de cette boucle correspond � une strate d'un elagage en min/max, on changera donc la valeur du joueur min/max � chaque changement de strate.
		//Le tableau dans son �tat initial correspond � la premi�re strate
		//On modifie donc cette strate avec une deuxi�me boucle parcourant le tableau de case 2 �l�ments par 2 �l�ments en ne gardant dans le tableau que le plus petit ou le plus grand de chaque paire(en fonction du joueur)
		//Le crit�re de s�lection est le nombre de case vide ou poss�d�e par l'ordinateur
		//Si la longueur n du tableau est impaire alors on comparera (n-1/2)-1 paire et un trinome
		//Les strates suivantes seront donc forc�ment paire sauf la derni�re qui ne sera pa strait� vu qu'elle n'aura qu'un �l�ment
		//Ce cheminement, m�me si aucun arbre r�el n'est cr�e simulera un parcours d'arbre n-aire
		while (casePossible.size() != 1) {
			if (casePossible.size()%2 == 0) {
				for (int i = 0; i < casePossible.size(); i++) {
					casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
				}
			}else {
				for (int i = 0; i < casePossible.size(); i++) {
					if (i == casePossible.size()-3) {//Traitement du trinome si impaire(On fait juste 2x le traitement au lieu de 1 avant d'it�rer)
						casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
					}
					casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));			
				}
			}
			//On change de joueur avant la fin de chaque it�ration de la premi�re boucle
			jMinMax = !jMinMax;
		}
	}
}
