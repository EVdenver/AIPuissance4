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
		//On parcours ensuite le tableau en supprimant élément par élément en fonction de notre heuristique et en choisissant à chaque étape soit le min soit le max
		elagageMinMax();
		return casePossible.get(0).col;
			
	}
	
	public void elagageMinMax() {
		//La première boucle correspond à la boucle testant la suppression des éléments et ne s'arrêtent que lorsque notre tableau de case ne contient qu'un élément soit la case choisie par l'ordinateur.
		//Chaque itération de cette boucle correspond à une strate d'un elagage en min/max, on changera donc la valeur du joueur min/max à chaque changement de strate.
		//Le tableau dans son état initial correspond à la première strate
		//On modifie donc cette strate avec une deuxième boucle parcourant le tableau de case 2 éléments par 2 éléments en ne gardant dans le tableau que le plus petit ou le plus grand de chaque paire(en fonction du joueur)
		//Le critère de sélection est le nombre de case vide ou possédée par l'ordinateur
		//Si la longueur n du tableau est impaire alors on comparera (n-1/2)-1 paire et un trinome
		//Les strates suivantes seront donc forcément paire sauf la dernière qui ne sera pa straité vu qu'elle n'aura qu'un élément
		//Ce cheminement, même si aucun arbre réel n'est crée simulera un parcours d'arbre n-aire
		while (casePossible.size() != 1) {
			if (casePossible.size()%2 == 0) {
				for (int i = 0; i < casePossible.size(); i++) {
					casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
				}
			}else {
				for (int i = 0; i < casePossible.size(); i++) {
					if (i == casePossible.size()-3) {//Traitement du trinome si impaire(On fait juste 2x le traitement au lieu de 1 avant d'itérer)
						casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
					}
					casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));			
				}
			}
			//On change de joueur avant la fin de chaque itération de la première boucle
			jMinMax = !jMinMax;
		}
	}
}
