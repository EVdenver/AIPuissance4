package snippet;

public class Snippet {
	// renvoie le nombre de ligne de 4 possible pour une case
		public int calculerNb4pos(byte[][] matJeuSim) {
			
			Case c=this;
			byte jVal = 1; // Variable contenant la valeur du joueur, un byte suffit
			if (jeu.joueur) {
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
		//System.out.println("la case: "+ this);
			for(int i=1; i<=3;i++) {
									
				// observe les case horizontal
				// verifie les cases a gauche
				if(c.col-i>0 && !stopg) {
					
					if(matJeuSim[c.ligne-1][c.col-i-1]==0 || matJeuSim[c.ligne-1][c.col-i-1]==jVal) {
						nbhori++;
					}
					else {
						stopg=true;
					}
				}
				else {
					stopg=true;
				}
				
				// verifie les cases a droite
				
				
				
				
				if(c.col+i<matJeuSim[0].length && !stopd) {
					if(matJeuSim[c.ligne-1][c.col+i-1]==0 || matJeuSim[c.ligne-1][c.col+i-1]==jVal) {
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
				if(c.ligne-i>0/* faut voir les limites de la matrice*/ && !stopb) {
					if(matJeuSim[c.ligne-i-1][c.col-1]==jVal||matJeuSim[c.ligne-i-1][c.col-1]==0) {
						nbverti++;
					}
					else {
						stopb=true;
					}
				}
				else {
					stopb=true;
				}
				if(c.ligne+i<jeu.matJeu.length &&!stoph) {
					if(matJeuSim[c.ligne+i-1][c.col-1]==0||matJeuSim[c.ligne+i-1][c.col-1]==jVal) {
						nbverti++;
					}
					else {
						stoph=true;
					}
				}
				else {
					stoph=true;
				}
				//diagonal
				if(c.ligne-i>0 && c.col+i<jeu.matJeu[0].length && !stopdiagd) {
					if(matJeuSim[c.ligne-i-1][c.col+i-1]==0 ||matJeuSim[c.ligne-i-1][c.col+i-1]==jVal) {
						nbdiag++;
					}
					else {
						stopdiagd=true;
					}
				}
				else {
					stopdiagd=true;
				}
				
				if(c.ligne+i<jeu.matJeu.length && c.col-i>=1 && !stopdiagg) {
					if(matJeuSim[c.ligne+i-1][c.col-i-1]==0 ||matJeuSim[c.ligne+i-1][c.col-i-1]==jVal) {
						nbdiag++;
					}
					else {
						stopdiagg=true;
					}
					
				}
				else {
					stopdiagg=true;
				}
				
				// anti diagonal
				if(c.ligne-i>0 && c.col-i>0 && !stopadiagg) {
					if(matJeuSim[c.ligne-i-1][c.col-i-1]==0 ||matJeuSim[c.ligne-i-1][c.col-i-1]==jVal) {
						nbadiag++;
					}
					else {
						stopadiagg=true;
					}
				}
				else {
					stopadiagg=true;
				}
				
				if(c.ligne+i<jeu.matJeu.length && c.col+i<jeu.matJeu[0].length && !stopadiagd) {
					//System.out.println("limite max des ligne:"+jeu.matJeu.length);
					//System.out.println("c.ligne+i-1:"+(c.ligne+i-1));
					//System.out.println("limite max des colones:"+jeu.matJeu[0].length);
					//System.out.println("c.col+i-1:"+ (c.col+i-1));// bugait dans le depassement des colones et ne donne pas les bon résultat (le resultat attendue -1)
					if(jeu.matJeu[c.ligne+i-1][c.col+i-1]==0 ||jeu.matJeu[c.ligne+i-1][c.col+i-1]==jVal) {
						nbadiag++;
					}
					else {
						stopadiagd=true;
					}
				}
				else {
					stopadiagd=true;
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
			System.out.println("nbhori:"+nbhori);
			System.out.println("nbverti:"+nbverti);
			System.out.println("nbadiag:"+nbadiag);
			System.out.println("nbdiag:"+nbdiag);
			System.out.println("resultat nbd4p :"+nbd4p);
			
			return nbd4p;
			
	 }
	
}

