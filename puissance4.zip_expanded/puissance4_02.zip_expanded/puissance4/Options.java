public class Options {
	private int nbRow;			// Nombre de lignes
	private int nbCol;			// Nombre de colonnes
	boolean alpha = false;
	boolean heuri2=false; // Heuristique 2 activ�e ou non
	boolean computerOn = false;	// Ordinateur activ� ou non
	boolean computerStarts = false;
	Jeu jeu;
	
	public Options(Jeu j) {
		
		this.jeu = j;
		OptionsGUI optionsGUI = new OptionsGUI(this);
		
	}
	
	public Options(int nbRow, int nbCol, Jeu jeu) {
		this.jeu = jeu;
		setSize(nbRow, nbCol, true);
	}
	
	
	/** R�gle les dimensions du jeu
	 * @param initSize D�finit si le plateau de jeu doit �tre initalis� avec la taille entr�e
	 */
	public void setSize(int nbRow, int nbCol, boolean initSize) {
		this.nbRow = nbRow;
		this.nbCol = nbCol;
		if (initSize)
			initSize(nbRow, nbCol);
	}
	
	/** Initialise le plateau de jeu */
	public void initSize(int nbRow, int nbCol) {
		jeu.plateau = new Grille(nbRow, nbCol, jeu);
		jeu.plateau.setVisible(true);
		jeu.matJeu = new byte[nbRow][nbCol];
		jeu.historique = new int[nbRow * nbCol];
	}
	
	/** Retourne le nombre de colonnes du jeu */
	public int getGameWidth() {
		return nbCol;
	}
	
	/** Retourne le nombre de lignes du jeu */
	public int getGameHeight() {
		return nbRow;
	}
	
	/*public void initComputer(boolean computerOn, boolean computerStarts, int diff) {
		this.computerOn = computerOn;
		this.computerStarts = computerStarts;
		jeu.deep = new Computer(diff); // on cr�e tout le temps l'ordinateur, au cas o� l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.ordiJoue();
	}*/
	public void initComputerHeuristique2(boolean computerOn, boolean computerStarts) {
		this.heuri2 = computerOn;
		this.computerStarts = computerStarts;
		jeu.euri = new Heuristiques(jeu); // on cr�e tout le temps l'ordinateur, au cas o� l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.heuristique2Joue();
	}
	
}
