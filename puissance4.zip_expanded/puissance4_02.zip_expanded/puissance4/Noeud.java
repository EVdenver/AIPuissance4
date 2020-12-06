import java.util.ArrayList;

public class Noeud extends Case {
	Noeud parent; 
	ArrayList<Noeud> enfants;
	public int profondeur;
	int poid ; // plus un noeud � des possibilite de ligne4 importante, plus son poid est important
	byte[][] matJeuSim; //si le noeud a une profondeur de zero, alors c'est le matJeu de Jeu. Sinon c'est un tableau virtuel simulant un jeu plus avanc�
	
	static Jeu jeu; // tout les noeuds appartiennent au m�me jeu
	public static final int MAX_POID = 16; // nombre de ligne de4 maximum

	public Noeud(int ligne, int col, int a, Noeud parent, ArrayList<Noeud> enfants, int profondeur,byte[][] matJeuSim) {
		super(ligne, col, a, jeu);
		this.parent=parent;
		this.enfants=enfants;
		this.profondeur=profondeur;
		this.matJeuSim=matJeuSim;
	}
	
	public Noeud(Case c, Noeud parent, ArrayList<Noeud> enfants ) {
		super(c.ligne, c.col, c.val, c.jeu);
		this.parent=parent;
		this.enfants=enfants;
	}
	
			


}
