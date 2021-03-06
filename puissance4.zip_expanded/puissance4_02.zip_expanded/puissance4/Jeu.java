import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Jeu {
	Grille plateau;		// Juste le graphisme
	byte[][] matJeu;	// La matrice qui contiendra la partie
	int nbCoups;		// Contient le nombre de coups joués (détection partie nulle)
	boolean enCours;	// Indique si la partie est en cours ou terminée
	boolean joueur;		// Indique le joueur en cours
	int[] historique;	// Enregistre les colonnes jouées
	Options opts;		// Une fenêtre d'options
	HeuristiqueCaseVide heuristique1;
	static Heuristiques heuristique2;
	
	public Jeu(boolean optTrue) {
		if (optTrue)
			opts = new Options(this);
		nbCoups = 0;
		enCours = true;
	}

	/** Test if a player has won
	 * @param joueur search winning position for the player joueur
	 * @param ligneM row just played by joueur
	 * @param colM col line just played by joueur
	 * @return true if there is a winning position, false if not
	 */	
	public boolean joueurGagne(boolean joueur, int ligneM, int colM) {  // le M majuscule indique que c'est une ligne correspondant à la position dans la matrice
		byte jVal = 1; // Variable contenant la valeur du joueur, un byte suffit
		if (joueur)
			jVal = 2;
		
		if (horiGagne(jVal, ligneM, colM) || vertGagne(jVal, ligneM, colM) || diag1Gagne(jVal, ligneM, colM) || diag2Gagne(jVal, ligneM, colM))
			return true;
		
		return false;
	}
	
	/** Test if a player has won horizontally
	 * @param jVal search winning position for the player jVal
	 * @param ligneM row just played by jVal
	 * @param colM col line just played by jVal
	 * @return true if there is a horizontal winning position, false if not
	 */	
	public boolean horiGagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;  // nombre de pions qui sont alignés les uns à la suite des autres
		int colMin = colM - 3;
		if (colMin <= 0)
			colMin = 0;
		
		int colMax = colM + 3;
		if (colMax >= opts.getGameWidth())
			colMax = opts.getGameWidth() - 1;
		
		for (int i = colMin; i <= colMax; i++) {
			if (this.matJeu[ligneM][i] == jVal)
				nbAlign++;
			else
				nbAlign = 0;

			if (nbAlign == 4)
				return true;
		}
		return false;
	}
	
	/** Test if a player has won vertically
	 * @param jVal search winning position for the player jVal
	 * @param ligneM row just played by jVal
	 * @param colM col line just played by jVal
	 * @return true if there is a vertical winning position, false if not
	 */	
	public boolean vertGagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;  // nombre de pions qui sont alignés les uns à la suite des autres
		int ligneMin = ligneM - 3;
		if (ligneMin <= 0)
			ligneMin = 0;
		
		int ligneMax = ligneM + 3;
		if (ligneMax >= opts.getGameHeight())
			ligneMax = opts.getGameHeight() - 1;
		
		
		for (int i = ligneMin; i <= ligneMax; i++) {
			if (this.matJeu[i][colM] == jVal)
				nbAlign++;
			else
				nbAlign = 0;
		
			if (nbAlign == 4)
				return true;
		}
		return false;
	}
	
	/** Test if a player has won "antislashly"
	 * @param jVal search winning position for the player jVal
	 * @param ligneM row just played by jVal
	 * @param colM col line just played by jVal
	 * @return true if there is an "antislash" winning position, false if not
	 */	
	public boolean diag1Gagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;
		int ligneMin = ligneM;
		int ligneMax = ligneM;
		int colMin = colM;
		int colMax = colM;
		
		int compteur = 0;
		while (ligneMax + 1 < opts.getGameHeight() && colMax + 1 < opts.getGameWidth() && compteur <= 2) {  //On va en bas à droite du plateau
			ligneMax++;
			colMax++;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		compteur = 0;
		while (ligneMin >= 1 && colMin >= 1 && compteur <= 2) {  //On va en haut à gauche du plateau de jeu
			ligneMin--;
			colMin--;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		ligneM = ligneMin;
		colM = colMin;
		do {
			if (this.matJeu[ligneM][colM] == jVal)
				nbAlign++;
			else
				nbAlign = 0;
			
			if (nbAlign == 4)
				return true;
				
			ligneM++;
			colM++;
			
		} while (ligneM <= ligneMax && colM <= colMax);
		
		return false;
	}
	
	/** Test if a player has won "slashly"
	 * @param jVal search winning position for the player jVal
	 * @param ligneM row just played by jVal
	 * @param colM col line just played by jVal
	 * @return true if there is a "slash" winning position, false if not
	 */	
	public boolean diag2Gagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;
		int ligneMin = ligneM;
		int ligneMax = ligneM;
		int colMin = colM;
		int colMax = colM;
		
		int compteur = 0;
		while (ligneMax + 1 < opts.getGameHeight() && colMin >= 1 && compteur <= 2) {  //On va en bas à gauche du plateau
			ligneMax++;
			colMin--;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		compteur = 0;
		while (ligneMin >= 1 && colMax + 1 < opts.getGameWidth() && compteur <= 2) {  //On va en haut à droite du plateau de jeu
			ligneMin--;
			colMax++;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		ligneM = ligneMax;
		colM = colMin;
		do {
			if (this.matJeu[ligneM][colM] == jVal)
				nbAlign++;
			else
				nbAlign = 0;
			
			if (nbAlign == 4)
				return true;
			
			ligneM--;
			colM++;
					
		} while (ligneM >= ligneMin && colM <= colMax);
		
		return false;
	}
	
	/** Tries to make a col played
	 * @param col col to be played
	 */	
	public void jouer(int col) {
		boolean coupValable;	// Contient la validité du coup que le jouer veut jouer
		int ligne = 0;		// Là où sera stockée la ligne jouée, le 0 sert pour le compilateur
		
		coupValable = false;
			
		ligne = this.searchLine(col);
		coupValable = this.coupValable(ligne, col);
		
		if (coupValable)
			validerCoup(ligne, col);
		
	}

	/** Makes a cell played (generally after having been authorized by the method jouer(int)
	 * )
	 * @param ligne row to be played
	 * @param col col to be played
	 */	
	public void validerCoup(int ligne, int col) {
		
		if (!joueur) {
			this.matJeu[ligne - 1][col - 1] = 1;  // 1 désigne dans la matrice un pion du joueur "false"
			Case cc = (Case)plateau.pane.getComponent((opts.getGameWidth()) * (ligne - 1) + (col - 1));
			cc.modifierVal(1);
		} else {
			this.matJeu[ligne - 1][col - 1] = 2;  // 2 désigne dans la matrice un pion du joueur "true"
			Case cc = (Case)plateau.pane.getComponent((opts.getGameWidth()) * (ligne - 1) + (col - 1));
			cc.modifierVal(2);
		}

		boolean gagne = this.joueurGagne(joueur, ligne - 1, col - 1);
		if (gagne) {
			enCours = false; // La partie est terminée
			if (!joueur)
				Saisie.infoMsgOk("Le joueur 1 a gagne", "Bravo");
			else
				Saisie.infoMsgOk("Le joueur 2 a gagne", "Bravo");
		}
		
		nbCoups++;  // On sous-entend les this
		if (nbCoups >= opts.getGameHeight() * opts.getGameWidth() && !gagne) {
			Saisie.infoMsgOk("Aucun des 2 joueurs n'a su gagner... : partie nulle", "Partie nulle");
			enCours = false;  // La partie est terminée
		}
		
		historique[nbCoups - 1] = col;
		joueur = !joueur;
		if (joueur) {
			plateau.statusBar.setText("Le joueur 2 joue");
			plateau.statusBar.setIcon(plateau.pionV);
		}
		else {
			plateau.statusBar.setText("Le joueur 1 joue");
			plateau.statusBar.setIcon(plateau.pionR);
		}
		if (!enCours) {		// Proposition de faire une nouvelle partie
			int ok = Saisie.question_ouinon("La partie est terminee, voulez-vous en faire une nouvelle ?", "Nouvelle partie");
			if (ok == 0)
				nouveauJeu();
		}
		else {
			if (opts.heuristique1On && joueur != opts.computerStarts)
				this.heuristique1Joue();
			if (opts.heuristique2On && joueur != opts.computerStarts)
				this.heuristique2Joue();			
		}
			
				
	}
	
	// Méthode testant la validité d'un coup
	/** Verifies if you are allowed to play the chosen cell
	 * @param ligne row to be verified
	 * @param col col to be verified
	 * @return true if you are allowed to play the cell false if not
	 */	
	public boolean coupValable(int ligne, int col) {
		
		if (ligne == -1) {
			Saisie.erreurMsgOk("Vous ne pouvez pas jouer ce coup : la colonne est remplie", "Coup invalide");
			return false;
		}
		
		if (!enCours) {
			Saisie.erreurMsgOk("La partie est terminée, vous ne pouvez plus jouer", "Erreur : partie terminée");
			return false;
		}
		
		return true;
	}
	
	// Méthode cherchant la 1ère ligne jouable d'une colonne (gravitation...)
	/** Search the row to be played for a given col (gravity law...)
	 * @param col col for which the method searches the line
	 * @return Number of the line corresponding to the col, -1 if the col is full
	 */	
	public int searchLine(int col) {
		for (int i = opts.getGameHeight(); i >= 1; i--) {
			if (matJeu[i - 1][col - 1] == 0)
				return i;
		}
		return -1; // Aucune ligne n'a été trouvée : la colonne est remplie
	}
	
	
	public void heuristique1Joue() {
		plateau.statusBar.setText("L'ordinateur réfléchit : patientez");
		plateau.repaint();
		jouer(heuristique1.ordiJoue(joueur));
	}

	public void heuristique2Joue() {
		plateau.statusBar.setText("L'ordinateur réfléchit : patientez");
		plateau.repaint();
		jouer(Heuristiques.HeuriMinMaxProfonde(true, matJeu, 0).col);
	}
	
	/** Makes a new game */	
	public static void nouveauJeu() {
		Jeu j = new Jeu(true);
	}
	
	
	/** Undo the last move */	
	public void undo() {
		if (nbCoups != 0) {
			int col = historique[nbCoups - 1];
			int ligne = searchLine(col);
			if (ligne == -1)
				ligne = 1;
			else
				ligne++;
			matJeu[ligne - 1][col - 1] = 0;
			joueur = !joueur;
			nbCoups--;
			if (!enCours)
				enCours = true;
			Case cc = (Case)plateau.pane.getComponent((opts.getGameWidth()) * (ligne - 1) + (col - 1));
			cc.modifierVal(0);
		}
	}
	
	public static void main(String[] args) {
		
		Jeu partie = new Jeu(true);
		
	}
}
