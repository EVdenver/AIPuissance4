
public class OrdiEuristique2 {
	private Jeu j;
	private boolean joueur;
	
	public OrdiEuristique2(Boolean joueur, Jeu j){
		this.j=j;
	}
	
	
	
	public int calculerNb4pos(Case c) {
		byte jVal = 1; // Variable contenant la valeur du joueur, un byte suffit
		if (j.joueur) {
			jVal = 2;
		}
	
	
	int nbd4p=0;
	
	int nbhori=0;
	int nbverti=0;
	int nbdiag=0;
	int nbadiag=0;
	
	boolean stopg=false;
	boolean stopd=false;
	
	boolean stoph=false;
	boolean stopb=false;
	
	boolean stopdiagg=false;
	boolean stopdiagd=false;
	
	boolean stopadiagg=false;
	boolean stopadiagd=false;
	
		for(int i=1; i<=3;i++) {
			
			// observe les case horizontal
			// verifie les cases a gauche
			if(c.col-i>=0 && !stopg) {
				if(j.matJeu[c.ligne][c.col-i]==0 || j.matJeu[c.ligne][c.col-i]==jVal) {
					nbhori++;
				}
				else {
					stopg=true;
				}
			}
			else {
				stopg=true;
			}
			// verifie les cases a gauche
			if(c.col+i<j.matJeu[0].length && !stopd) {
				if(j.matJeu[c.ligne][c.col+i]==0 || j.matJeu[c.ligne][c.col+i]==jVal) {
					nbhori++;
				}
				else {
					stopd=true;
				}
			}
			else {
				stopd=true;
			}
			
			//observe les cases vertical
			if(c.ligne-i>=0/* faut voir les limites de la matrice*/ && !stopb) {
				if(j.matJeu[c.ligne-i][c.col]==jVal) {
					nbverti++;
				}
				else {
					stopb=true;
				}
			}
			else {
				stopb=true;
			}
			if(c.ligne+i<j.matJeu.length&&!stoph) {
				if(j.matJeu[c.ligne+i][c.col]==0 ) {
					nbverti++;
				}
				else {
					stoph=true;
				}
			}
			else {
				stoph=true;
			}
			// anti diag
			if(c.ligne-i>=0 && c.col+i<j.matJeu[0].length && !stopadiagd) {
				if(j.matJeu[c.ligne-i][c.col+i]==0 |j.matJeu[c.ligne-i][c.col+i]==jVal) {
					nbadiag++;
				}
				else {
					stopadiagd=true;
				}
			}
			else {
				stopadiagd=true;
			}
			
			if(c.ligne+i<j.matJeu.length && c.col-i>=0 && !stopadiagg) {
				if(j.matJeu[c.ligne+i][c.col-i]==0 |j.matJeu[c.ligne+i][c.col-i]==jVal) {
					nbadiag++;
				}
				else {
					stopadiagg=true;
				}
				
			}
			else {
				stopadiagg=true;
			}
			
			// diagonal
			if(c.ligne-i>=0 && c.col-i>=0 && !stopdiagg) {
				if(j.matJeu[c.ligne-i][c.col-i]==0 |j.matJeu[c.ligne-i][c.col-i]==jVal) {
					nbdiag++;
				}
				else {
					stopdiagg=true;
				}
			}
			else {
				stopdiagg=true;
			}
			
			if(c.ligne+i<j.matJeu.length && c.col-i<j.matJeu[0].length && !stopdiagd) {
				if(j.matJeu[c.ligne+i][c.col+i]==0 |j.matJeu[c.ligne+i][c.col+i]==jVal) {
					nbdiag++;
				}
				else {
					stopdiagd=true;
				}
			}
			else {
				stopdiagd=true;
			}
			
		}
		// on fait le décompte des possibilité de 4
		
		if (nbhori<=2){
			
		}
		else {
			nbd4p=nbd4p+(nbhori-2);
		}
		if(nbverti<=2) {
			
		}
		else {
			nbd4p=nbd4p+(nbverti-2);
		}
		if(nbadiag<=2) {
			
		}
		else {
			nbd4p=nbd4p+(nbadiag-2);
		}
		if(nbdiag<=2) {
			
		}
		else {
			nbd4p=nbd4p+(nbdiag-2);
		}
		
		
		return nbd4p;
 }

}
