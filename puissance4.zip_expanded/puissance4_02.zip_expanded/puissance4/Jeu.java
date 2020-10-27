import java.io.*;
import java.util.StringTokenizer;

public class Jeu {
	Grille plateau;		// Juste le graphisme
	byte[][] matJeu;	// La matrice qui contiendra la partie
	int nbCoups;		// Contient le nombre de coups jou�s (d�tection partie nulle)
	boolean enCours;	// Indique si la partie est en cours ou termin�e
	boolean joueur;		// Indique le joueur en cours
	int[] historique;	// Enregistre les colonnes jou�es
	Options opts;		// Une fen�tre d'options	
	
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
	public boolean joueurGagne(boolean joueur, int ligneM, int colM) {  // le M majuscule indique que c'est une ligne correspondant � la position dans la matrice
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
		int nbAlign = 0;  // nombre de pions qui sont align�s les uns � la suite des autres
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
		int nbAlign = 0;  // nombre de pions qui sont align�s les uns � la suite des autres
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
		while (ligneMax + 1 < opts.getGameHeight() && colMax + 1 < opts.getGameWidth() && compteur <= 2) {  //On va en bas � droite du plateau
			ligneMax++;
			colMax++;
			compteur++;   // on ne va que 3 cases en bas � droite au maximum
		}
		
		compteur = 0;
		while (ligneMin >= 1 && colMin >= 1 && compteur <= 2) {  //On va en haut � gauche du plateau de jeu
			ligneMin--;
			colMin--;
			compteur++;   // on ne va que 3 cases en bas � droite au maximum
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
		while (ligneMax + 1 < opts.getGameHeight() && colMin >= 1 && compteur <= 2) {  //On va en bas � gauche du plateau
			ligneMax++;
			colMin--;
			compteur++;   // on ne va que 3 cases en bas � droite au maximum
		}
		
		compteur = 0;
		while (ligneMin >= 1 && colMax + 1 < opts.getGameWidth() && compteur <= 2) {  //On va en haut � droite du plateau de jeu
			ligneMin--;
			colMax++;
			compteur++;   // on ne va que 3 cases en bas � droite au maximum
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
		boolean coupValable;	// Contient la validit� du coup que le jouer veut jouer
		int ligne = 0;		// L� o� sera stock�e la ligne jou�e, le 0 sert pour le compilateur
		
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
			this.matJeu[ligne - 1][col - 1] = 1;  // 1 d�signe dans la matrice un pion du joueur "false"
			Case cc = (Case)plateau.pane.getComponent((opts.getGameWidth()) * (ligne - 1) + (col - 1));
			cc.modifierVal(1);
		} else {
			this.matJeu[ligne - 1][col - 1] = 2;  // 2 d�signe dans la matrice un pion du joueur "true"
			Case cc = (Case)plateau.pane.getComponent((opts.getGameWidth()) * (ligne - 1) + (col - 1));
			cc.modifierVal(2);
		}

		boolean gagne = this.joueurGagne(joueur, ligne - 1, col - 1);
		if (gagne) {
			enCours = false; // La partie est termin�e
			if (!joueur)
				Saisie.infoMsgOk("Le joueur 1 a gagne", "Bravo");
			else
				Saisie.infoMsgOk("Le joueur 2 a gagne", "Bravo");
		}
		
		nbCoups++;  // On sous-entend les this
		if (nbCoups >= opts.getGameHeight() * opts.getGameWidth() && !gagne) {
			Saisie.infoMsgOk("Aucun des 2 joueurs n'a su gagner... : partie nulle", "Partie nulle");
			enCours = false;  // La partie est termin�e
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
	}
	
	// M�thode testant la validit� d'un coup
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
			Saisie.erreurMsgOk("La partie est termin�e, vous ne pouvez plus jouer", "Erreur : partie termin�e");
			return false;
		}
		
		return true;
	}
	
	// M�thode cherchant la 1�re ligne jouable d'une colonne (gravitation...)
	/** Search the row to be played for a given col (gravity law...)
	 * @param col col for which the method searches the line
	 * @return Number of the line corresponding to the col, -1 if the col is full
	 */	
	public int searchLine(int col) {
		for (int i = opts.getGameHeight(); i >= 1; i--) {
			if (matJeu[i - 1][col - 1] == 0)
				return i;
		}
		return -1; // Aucune ligne n'a �t� trouv�e : la colonne est remplie
	}
	
	
	/** Makes a new game */	
	public static void nouveauJeu() {
		Jeu j = new Jeu(true);
	}
	
	/** Saves the game
	 * @param file file in which the game will be saved
	 */	
	public void enregistrer(File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fw);
			out.write("Puissance 4  Kariboo Mikl\n");  // En-t�te de fichier
			out.write(opts.getGameHeight() + " ");
			out.write(opts.getGameWidth() + " ");
			out.write(nbCoups + " ");
			
			out.write("0 ");
			
			out.write("0 \n");
			
			for (int i = 0; i < nbCoups; i++)
				out.write(historique[i] + " ");
			
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	/** Opens a game from a file
	 * @param file file from which the game will be opened
	 */	
	public void ouvrir(File file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader out = new BufferedReader(fr);
			String line = out.readLine();
			if (line.equals("Puissance 4  Kariboo Mikl")) {  // Test de validit� de fichier
				line = out.readLine();
				StringTokenizer s = new StringTokenizer(line);
				int nbR = Integer.parseInt(s.nextToken());
				int nbC = Integer.parseInt(s.nextToken());
				int nbCou = Integer.parseInt(s.nextToken());

				line = out.readLine();
				out.close();
				s = new StringTokenizer(line);

				Jeu j = new Jeu(false);
				j.opts = new Options(nbR, nbC, j);

				for (int i = 0; i < nbCou; i++)
					j.jouer(Integer.parseInt(s.nextToken()));
			}
			else
				Saisie.erreurMsgOk("Le fichier que vous tentez d'ouvrir n'est pas un fichier de Puissance 4 valide.", "Access violation error ;o)");
			
		} catch (IOException e) {
			
		}
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
