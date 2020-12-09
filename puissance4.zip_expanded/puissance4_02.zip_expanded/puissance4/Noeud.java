import java.util.ArrayList;

public class Noeud extends Case {
	Noeud parent; 
	ArrayList<Noeud> enfants;
	public int profondeurARemonter;
	int poid ; // plus un noeud à des possibilite de ligne4 importante, plus son poid est important
	byte[][] matJeuSim; //si le noeud a une profondeur de zero, alors c'est le matJeu de Jeu. Sinon c'est un tableau virtuel simulant un jeu plus avancé
	
	static Jeu jeu; // tout les noeuds appartiennent au même jeu
	public static final int MAX_POID = 16; // nombre de ligne de4 maximum

	public Noeud(int ligne, int col, int a, Noeud parent, ArrayList<Noeud> enfants, int profondeur,byte[][] matJeuSim) {
		super(ligne, col, a, jeu);
		this.parent=parent;
		this.enfants=enfants;
		this.profondeurARemonter=profondeur;
		this.matJeuSim=matJeuSim;
	}
	
	public Noeud(Case c, Noeud parent, ArrayList<Noeud> enfants ) {
		super(c.ligne, c.col, c.val, c.jeu);
		this.parent=parent;
		this.enfants=enfants;
	}
	
	public int comparePoid(Noeud N2) {
		if(N2==null ) {
			throw new NullPointerException() ;
		}
		if(this.poid>N2.poid) {
			return 1;
		}
		else if(this.poid==N2.poid) {
			return 0;
		}
		
		return -1;
		
	}


}
