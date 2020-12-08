public class Options {
	private int nbRow;			// Nombre de lignes
	private int nbCol;			// Nombre de colonnes
	boolean alpha = false;
	boolean heuri2=false; // Heuristique 2 activée ou non
	boolean computerOn = false;	// Ordinateur activé ou non
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
	
	
	/** Règle les dimensions du jeu
	 * @param initSize Définit si le plateau de jeu doit être initalisé avec la taille entrée
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
	
	public void initComputer(boolean computerOn, boolean computerStarts, int diff) {
		this.computerOn = computerOn;
		this.computerStarts = computerStarts;
		jeu.deep = new Computer(diff); // on crée tout le temps l'ordinateur, au cas où l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.ordiJoue();
	}
	public void initComputerHeuristique2(boolean computerOn, boolean computerStarts) {
		this.heuri2 = computerOn;
		this.computerStarts = computerStarts;
		jeu.euri = new Heuristiques(jeu); // on crée tout le temps l'ordinateur, au cas où l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.heuristique2Joue();
	}
	
}
