class Computer {

	int p;
	int p2;
	byte[][] matJeu;
	byte[][] matJeu2;
	//int colResult;
	int nbCoups;
	boolean joueurBase; // cf commentaire de PerdGagne
	boolean[] forbiddenCols = new boolean[7];

	// constructeur à refaire si joueurBase en attribut
	public Computer(int niveau) {
		this.p = 2 * niveau;
		this.p2 = niveau;
	}
	
	/**
	 * Fait jouer l'ordinateur
	 * @return Colonne que l'ordinateur joue
	 */
	public int ordiJoue(boolean joueur) {
		
	
		forbiddenCols = new boolean[7];
		int ligne;
		int col;
		int max = -5001;
		int res = 0;
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (joueur)
			jVal = 2;
		
		nbCoups++;
		for (col = 0; col < matJeu[0].length; col++) {
			ligne = searchLine(col);
			if (ligne != -1) {
				if (nbCoups == (matJeu.length * matJeu[0].length) - 1)
					return col + 1; // c'est la seule colonne que l'on peut jouer
				
				matJeu[ligne][col] = jVal;
				
				res = perdGagne(!joueurBase, p - 1);
				if (res == 1) {
					Saisie.infoMsgOk("Beeeee", "titr");
					return col + 1;					
				}
				if (res == -1) //ENREGISTRER LE COUP
					forbiddenCols[col] = true;
				
				matJeu[ligne][col] = 0;  // UNDO
				
			}
		}
		nbCoups--;
		
		
		/*for (int i = 0; i < matJeu.length; i++) {
			for (int j = 0; j < matJeu[0].length; j++) {
				System.out.print(matJeu[i][j] + " ");
			}
		}*/
		
		int colResult = -2; // POUR TESTER
		for (col = 0; col < matJeu[0].length; col++) {
			ligne = searchLine(col);
			if (ligne != -1) {
				
				matJeu[ligne][col] = jVal;
				nbCoups++;
				res = miniMax(!joueurBase, p2 - 1);
				System.out.println(res);
				if (res > max) {
					max = res;
					colResult = col;
				}
				matJeu[ligne][col] = 0;  // UNDO
				nbCoups--;
				
			}
		}
		
		System.out.println("-----");
		return colResult + 1;
		
	}

	public int perdGagne(boolean joueur, int profondeur) {
		
		int ligne;
		int col;
		int minMax;
		if (joueur == joueurBase)
			minMax = -1;  // on part du minimum
		else
			minMax = 1;
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (joueur)
			jVal = 2;
		
		if (profondeur == 0 || nbCoups == (matJeu.length * matJeu[0].length) - 1) {
		
			for (col = 0; col < matJeu[0].length; col++) {
				ligne = searchLine(col);
				if (ligne != -1) {
					matJeu[ligne][col] = jVal;
					nbCoups++;
					if (joueurGagne(jVal, ligne, col)) {
						
						matJeu[ligne][col] = 0;  // UNDO
						nbCoups--;
						if (joueur == joueurBase)
							return 1;
						else
							return -1;
						
					}
					matJeu[ligne][col] = 0;  // UNDO
					nbCoups--;
					
				}
			} // fin du for
			return 0; // pas de situation gagnante
		
		} else {
		
			for (col = 0; col < matJeu[0].length; col++) {
				ligne = searchLine(col);
				if (ligne != -1) {
					matJeu[ligne][col] = jVal;
					nbCoups++;
					if (joueurGagne(jVal, ligne, col)) {
						
						matJeu[ligne][col] = 0;  // UNDO
						nbCoups--;
						if (joueur == joueurBase)
							return 1;
						else
							return -1;  //peut-être enregistrer ce coup-là
						
					}
					int v = perdGagne(!joueur, profondeur - 1);
					/*if (joueur == joueurBase && v >= minMax)
						minMax = v;
					if (joueur != joueurBase && v <= minMax)
						minMax = v;*/
					
					matJeu[ligne][col] = 0;  // UNDO
					nbCoups--;
					
					if (joueur == joueurBase && v == 1)
						return 1;
					if (joueur != joueurBase && v == -1)
						return -1;
					
				}
			} // fin du for
			//return minMax;
			return 0;
			
		}

	}
	
	public int miniMax(boolean joueur, int profondeur) {
		int val = -5001;
		if (joueur != joueurBase)
			val = - val;
		int note;
		int ligne;
		int col;
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (joueur)
			jVal = 2;
		
		if (profondeur == 0 || nbCoups == matJeu.length * matJeu[0].length) {
			return evalGrille(jVal);
		} else {  // profondeur = 0, on est arrivé au bout de l'arbre
			
			for (col = 0; col < matJeu[0].length; col++) {
				ligne = searchLine(col);
				if (ligne != -1 && !forbiddenCols[col]) {
					matJeu[ligne][col] = jVal;
					nbCoups++;
					boolean g = joueurGagne(jVal, ligne, col);
					if (!g)
						note = miniMax(!joueur, profondeur - 1);
					else {
						if (joueur == joueurBase)
							note = 5000;
						else
							note = -5000;
					}
					matJeu[ligne][col] = 0;
					nbCoups--;
					if (joueur == joueurBase && note > val)
						val = note;
					if (joueur != joueurBase && note < val)
						val = note;
					
				}
			}
			return val;
			
		}
		
	}
	
	// Méthode renvoyant la valeur d'une position
	public int evalGrille(byte joueur) {
		int valeur = 0;
		for (int row = 0; row < matJeu.length; row++) {
			for (int col = 0; col < matJeu[0].length; col++) {
				if (matJeu[row][col] == 0)
					valeur += evalCase(row, col, joueur);
			}
		}
		return valeur;
	}
	
	public int evalCase(int row, int col, byte joueur) {
		int valeur;
		valeur = nbAlignVertic(row, col, joueur)
			+ nbAlignHoriz(row, col, joueur)
			+ nbAlignSlash(row, col, joueur)
			+ nbAlignAntiSlash(row, col, joueur);
		
		int oth = (- joueur) + 3; // changement de joueur : (-1)*(joueur - 2) + 1
		byte otherJ = (byte)oth;
		valeur = valeur
			- nbAlignVertic(row, col, otherJ)
			- nbAlignHoriz(row, col, otherJ)
			- nbAlignSlash(row, col, otherJ)
			- nbAlignAntiSlash(row, col, otherJ);
		
		double d = Math.pow(2, matJeu.length - row);
		valeur = valeur * (int)Math.floor(d);
		
		return valeur;
	}
	
	public int nbAlignVertic(int row, int col, byte joueur) {
		int val = 0;
		boolean b = true;
		while (row + 1 < matJeu.length && b) {
			row++;
			if (matJeu[row][col] == joueur)
				val++;
			else
				b = false;
		}
		val = modifierVal(val);
		return val;
	}
	
	public int nbAlignHoriz(int row, int col, byte joueur) {
		int val = 0;
		int c = col;
		boolean b = true;
		while (c - 1 >= 0 && b) {
			c--;
			if (matJeu[row][c] == joueur)
				val++;
			else
				b = false;
		}
		
		b = true;
		c = col;
		while (c + 1 < matJeu[0].length && b) {
			c++;
			if (matJeu[row][c] == joueur)
				val++;
			else
				b = false;
		}
		val = modifierVal(val);
		return val;
		
	}
	
	public int nbAlignSlash(int row, int col, byte joueur) {
		int val = 0;
		int c = col;
		int r = row;
		boolean b = true;
		while (c - 1 >= 0 && r + 1 < matJeu.length && b) {
			c--;
			r++;
			if (matJeu[r][c] == joueur)
				val++;
			else
				b = false;
		}
		
		b = true;
		c = col;
		r = row;
		while (c + 1 < matJeu[0].length && r - 1 >= 0 && b) {
			c++;
			r--;
			if (matJeu[r][c] == joueur)
				val++;
			else
				b = false;
		}
		val = modifierVal(val);
		return val;
	}
	
	public int nbAlignAntiSlash(int row, int col, byte joueur) {
		int val = 0;
		int c = col;
		int r = row;
		boolean b = true;
		while (c + 1 < matJeu[0].length && r + 1 < matJeu.length && b) {
			c++;
			r++;
			if (matJeu[r][c] == joueur)
				val++;
			else
				b = false;
		}
		
		b = true;
		c = col;
		r = row;
		while (c - 1 >= 0 && r - 1 >= 0 && b) {
			c--;
			r--;
			if (matJeu[r][c] == joueur)
				val++;
			else
				b = false;
		}
		val = modifierVal(val);
		return val;
	}
	
	public static int modifierVal(int a) {
		switch(a) {
			case 0: return 0;
			case 1: return 4;
			case 2: return 10;
			case 3: return 50;
			default: return 60;
		}
	}
	
	
	
	public boolean joueurGagne(byte joueur, int ligneM, int colM) {  // le M majuscule indique que c'est une ligne correspondant à la position dans la matrice
		if (horiGagne(joueur, ligneM, colM) || vertGagne(joueur, ligneM, colM) || diag1Gagne(joueur, ligneM, colM) || diag2Gagne(joueur, ligneM, colM))
			return true;
		
		return false;
	}
	
	public boolean horiGagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;  // nombre de pions qui sont alignés les uns à la suite des autres
		int colMin = colM - 3;
		if (colMin <= 0)
			colMin = 0;
		
		int colMax = colM + 3;
		if (colMax >= matJeu[0].length)
			colMax = matJeu[0].length - 1;
		
		for (int i = colMin; i <= colMax; i++) {
			if (matJeu[ligneM][i] == jVal)
				nbAlign++;
			else
				nbAlign = 0;

			if (nbAlign == 4)
				return true;
		}
		return false;
	}
	
	public boolean vertGagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;  // nombre de pions qui sont alignés les uns à la suite des autres
		int ligneMin = ligneM - 3;
		if (ligneMin <= 0)
			ligneMin = 0;
		
		int ligneMax = ligneM + 3;
		if (ligneMax >= matJeu.length)
			ligneMax = matJeu.length - 1;
		
		
		for (int i = ligneMin; i <= ligneMax; i++) {
			if (matJeu[i][colM] == jVal)
				nbAlign++;
			else
				nbAlign = 0;
		
			if (nbAlign == 4)
				return true;
		}
		return false;
	}
	
	// Test de diagonale en forme d'anti-slash
	public boolean diag1Gagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;
		int ligneMin = ligneM;
		int ligneMax = ligneM;
		int colMin = colM;
		int colMax = colM;
		
		int compteur = 0;
		while (ligneMax + 1 < matJeu.length && colMax + 1 < matJeu[0].length && compteur <= 2) {  //On va en bas à droite du plateau
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
			if (matJeu[ligneM][colM] == jVal)
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
	
	// test de diagonale en forme de slash
	public boolean diag2Gagne(byte jVal, int ligneM, int colM) {
		int nbAlign = 0;
		int ligneMin = ligneM;
		int ligneMax = ligneM;
		int colMin = colM;
		int colMax = colM;
		
		int compteur = 0;
		while (ligneMax + 1 < matJeu.length && colMin >= 1 && compteur <= 2) {  //On va en bas à gauche du plateau
			ligneMax++;
			colMin--;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		compteur = 0;
		while (ligneMin >= 1 && colMax + 1 < matJeu[0].length && compteur <= 2) {  //On va en haut à droite du plateau de jeu
			ligneMin--;
			colMax++;
			compteur++;   // on ne va que 3 cases en bas à droite au maximum
		}
		
		ligneM = ligneMax;
		colM = colMin;
		do {
			if (matJeu[ligneM][colM] == jVal)
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
	
	public int searchLine(int col) {
		for (int i = matJeu.length - 1; i >= 0; i--) {
			if (matJeu[i][col] == 0)
				return i;
		}
		return -1; // Aucune ligne n'a été trouvée : la colonne est remplie
		
	}

}
