import java.util.ArrayList;

public class HeuristiqueCaseVide {
	private Jeu j;
	private ArrayList<Case> casePossible = new ArrayList<Case>();
	private boolean jMinMax = true; //booleen pour l'algo min max, true = min; false = max
	private boolean alpha; //Pour la méthode d'elagage
	
	public HeuristiqueCaseVide(Jeu j,boolean alpha) {
		this.j = j;
		this.alpha = alpha;
	}
	
	public int ordiJoue(boolean joueur) {
		//on vide d'abord le tableau pour éviter d'avoir des cases en plus
		casePossible.clear();
		// On recherche toute les cases vides possible
		for (int col = j.opts.getGameWidth(); col >= 1; col--) {
			int ligne = j.searchLine(col);
			if (ligne != -1) {
				Case caseVide = (Case)j.plateau.pane.getComponent((j.opts.getGameWidth()) * (ligne - 1) + (col - 1));
				casePossible.add(caseVide);
			}
		}
		//On parcours ensuite le tableau en supprimant élément par élément en fonction de notre heuristique et en choisissant à chaque étape soit le min soit le max
		if (alpha) {
			return elagageAlpha(joueur);
		}else {
			return elagageMinMax(joueur);
		}	
	}
	
	public int elagageMinMax(boolean joueur) {
		//La première boucle correspond à la boucle testant la suppression des éléments et ne s'arrêtent que lorsque notre tableau de case ne contient qu'un élément soit la case choisie par l'ordinateur.
		//Chaque itération de cette boucle correspond à une strate d'un elagage en min/max, on changera donc la valeur du joueur min/max à chaque changement de strate.
		//Le tableau dans son état initial correspond à la première strate
		//On modifie donc cette strate avec une deuxième boucle parcourant le tableau de case 2 éléments par 2 éléments en ne gardant dans le tableau que le plus petit ou le plus grand de chaque paire(en fonction du joueur)
		//Le critère de sélection est le nombre de case vide ou possédée par l'ordinateur
		//Si la longueur n du tableau est impaire alors on comparera (n-1/2)-1 paire et un trinome
		//Les strates suivantes seront donc forcément paire sauf la dernière qui ne sera pa straité vu qu'elle n'aura qu'un élément
		//Ce cheminement, même si aucun arbre réel n'est crée simulera un parcours d'arbre n-aire
		while (casePossible.size() !=1) {
			int res;
			if (casePossible.size()%2 == 0) {
				res =  filtrePair(joueur);
				if(res != -1) {
					return res;
				}
			}else {
				res =  filtreImpair(joueur);
				if(res != -1) {
					return res;
				}
			}
			//On change de joueur avant la fin de chaque itération de la première boucle
			jMinMax = !jMinMax;
		}
		return casePossible.get(0).col;
	}
	
	public int elagageAlpha(boolean joueur) {
		//Même que pour Min/Max sauf qu'une partie sera ignoré
		//TODO
		return 0;
	}
	
	public int filtrePair(boolean joueur) {
		for (int i = 0; i < casePossible.size(); i++) {
			if (testSiGagnante(casePossible.get(i),joueur)) {
				return casePossible.get(i).col;
			}
			if (testSiGagnante(casePossible.get(i+1), joueur)) {
				return casePossible.get(i+1).col;
			}
			if (testSiGagnante(casePossible.get(i), !joueur)) {
				return casePossible.get(i).col;
			}
			if (testSiGagnante(casePossible.get(i+1),!joueur)) {
				return casePossible.get(i+1).col;
			}
			casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
		}
		return -1;
	}
	
	public int filtreImpair(boolean joueur) {
		for (int i = 0; i < casePossible.size(); i++) {
			if (i == casePossible.size()-3) {//Traitement du trinome si impaire(On fait juste 2x le traitement au lieu de 1 avant d'itérer)
				if (testSiGagnante(casePossible.get(i),joueur)) {
					return casePossible.get(i).col;
				}
				if (testSiGagnante(casePossible.get(i+1), joueur)) {
					return casePossible.get(i+1).col;
				}
				if (testSiGagnante(casePossible.get(i), !joueur)) {
					return casePossible.get(i).col;
				}
				if (testSiGagnante(casePossible.get(i+1),!joueur)) {
					return casePossible.get(i+1).col;
				}
				casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
			}
			if (testSiGagnante(casePossible.get(i),joueur)) {
				return casePossible.get(i).col;
			}
			if (testSiGagnante(casePossible.get(i+1), joueur)) {
				return casePossible.get(i+1).col;
			}
			if (testSiGagnante(casePossible.get(i), !joueur)) {
				return casePossible.get(i).col;
			}
			if (testSiGagnante(casePossible.get(i+1),!joueur)) {
				return casePossible.get(i+1).col;
			}
			casePossible.remove(casePossible.get(i).compareCaseMinMax(casePossible.get(i+1), jMinMax));
		}
		return -1;
	}
	
	public boolean testSiGagnante(Case c, boolean joueur) {
		byte jval = 1;
		if (joueur) {
			jval = 2;
		}
		j.matJeu[c.ligne-1][c.col-1] = jval;
		boolean res = j.joueurGagne(joueur, c.ligne-1, c.col-1);
		j.matJeu[c.ligne-1][c.col-1] = 0;
		return res;
	}
}
