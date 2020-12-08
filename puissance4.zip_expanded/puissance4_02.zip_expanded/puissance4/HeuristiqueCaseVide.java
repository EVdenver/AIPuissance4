import java.util.ArrayList;

public class HeuristiqueCaseVide {
	private Jeu j;
	private ArrayList<Case> casePossible = new ArrayList<Case>();
	private boolean jMinMax = true; //booleen pour l'algo min max, true = min; false = max
	private boolean alpha; //Pour la m�thode d'elagage
	
	public HeuristiqueCaseVide(Jeu j,boolean alpha) {
		this.j = j;
		this.alpha = alpha;
	}
	
	public int ordiJoue(boolean joueur) {
		//on vide d'abord le tableau pour �viter d'avoir des cases en plus
		casePossible.clear();
		// On recherche toute les cases vides possible
		for (int col = j.opts.getGameWidth(); col >= 1; col--) {
			int ligne = j.searchLine(col);
			if (ligne != -1) {
				Case caseVide = (Case)j.plateau.pane.getComponent((j.opts.getGameWidth()) * (ligne - 1) + (col - 1));
				casePossible.add(caseVide);
			}
		}
		//On parcours ensuite le tableau en supprimant �l�ment par �l�ment en fonction de notre heuristique et en choisissant � chaque �tape soit le min soit le max
		if (alpha) {
			return elagageAlpha(joueur);
		}else {
			return elagageMinMax(joueur);
		}	
	}
	
	public int elagageMinMax(boolean joueur) {
		//La premi�re boucle correspond � la boucle testant la suppression des �l�ments et ne s'arr�tent que lorsque notre tableau de case ne contient qu'un �l�ment soit la case choisie par l'ordinateur.
		//Chaque it�ration de cette boucle correspond � une strate d'un elagage en min/max, on changera donc la valeur du joueur min/max � chaque changement de strate.
		//Le tableau dans son �tat initial correspond � la premi�re strate
		//On modifie donc cette strate avec une deuxi�me boucle parcourant le tableau de case 2 �l�ments par 2 �l�ments en ne gardant dans le tableau que le plus petit ou le plus grand de chaque paire(en fonction du joueur)
		//Le crit�re de s�lection est le nombre de case vide ou poss�d�e par l'ordinateur
		//Si la longueur n du tableau est impaire alors on comparera (n-1/2)-1 paire et un trinome
		//Les strates suivantes seront donc forc�ment paire sauf la derni�re qui ne sera pa strait� vu qu'elle n'aura qu'un �l�ment
		//Ce cheminement, m�me si aucun arbre r�el n'est cr�e simulera un parcours d'arbre n-aire
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
			//On change de joueur avant la fin de chaque it�ration de la premi�re boucle
			jMinMax = !jMinMax;
		}
		return casePossible.get(0).col;
	}
	
	public int elagageAlpha(boolean joueur) {
		//M�me que pour Min/Max sauf qu'une partie sera ignor�
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
			if (i == casePossible.size()-3) {//Traitement du trinome si impaire(On fait juste 2x le traitement au lieu de 1 avant d'it�rer)
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
