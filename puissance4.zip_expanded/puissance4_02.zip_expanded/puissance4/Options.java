public class Options {
	private int nbRow;			// Nombre de lignes
	private int nbCol;			// Nombre de colonnes

	boolean heuristique1On = false; //Heuristique 1 activé ou non
	boolean alpha = false; //Heuristique 1 en alpha activé ou non
	boolean computerStarts = false; //Ordinateur commence ou non
	boolean heuristique2On=false; // Heuristique 2 activée ou non
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
	
	public void initComputerHeuristique1(boolean computerOn, boolean computerStarts,boolean alpha) {
		this.heuristique1On = computerOn;
		this.computerStarts = computerStarts;
		this.alpha = alpha;
		jeu.heuristique1 = new HeuristiqueCaseVide(jeu, alpha); // on crée tout le temps l'ordinateur, au cas où l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.heuristique1Joue();
	}
  
	public void initComputerHeuristique2(boolean computerOn, boolean computerStarts) {
		this.heuristique2On = computerOn;
		this.computerStarts = computerStarts;
		jeu.heuristique2 = new Heuristiques(jeu); // on crée tout le temps l'ordinateur, au cas où l'utilisateur clique sur Jouer...
		if (computerStarts)
			jeu.heuristique2Joue();
	}
	
}
