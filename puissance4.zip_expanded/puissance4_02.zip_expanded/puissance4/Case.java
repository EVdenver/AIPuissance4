import javax.swing.*;
import java.awt.*;

public class Case extends JPanel {
	int val;
	int ligne;
	int col;
	Jeu jeu;
	Color bgCol;
	
	public Case(int ligne, int col, int a, Jeu j) {
		super();
		this.val = a;
		this.ligne = ligne;
		this.col = col;
		this.jeu = j;
		this.bgCol = Color.white;
	}
	
	public void modifierVal(int a) {
		this.val = a;
		this.bgCol = Color.white; // Pour bien confirmer le fond blanc de la case
		repaint();
	}
	
	public void modifierBg(Color c) {
		this.bgCol = c;
		repaint();
	}
	
	public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D)comp;
		comp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON); // Pour l'antialias
		
		comp2D.setColor(bgCol);
		comp2D.fillRect(0, 0, getWidth(), getHeight());
		comp2D.setColor(Color.black);
		comp2D.drawRect(0, 0, getWidth(), getHeight());
                
		if (val != 0) {
			if (val == 1)
				comp2D.setColor(Color.red);
			else
				comp2D.setColor(Color.green);
			comp2D.fillOval(3, 3, getWidth() - 4, getHeight() - 4);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////
	
	public Case compareCase4(Case C2) {
		int i1=this.calculerNb4pos();
		if(C2==null ) {
			return this;
		}
		int i2=C2.calculerNb4pos();
		
		
		if(i1>=i2) {
			return this;
		}
		
			return C2;
		
	}
	
	
	
	
	
	
	
	public int calculerNb4pos() {
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
	
		for(int i=1; i<=3;i++) {
			
			// observe les case horizontal
			// verifie les cases a gauche
			if(c.col-i>=0 && !stopg) {
				if(jeu.matJeu[c.ligne][c.col-i]==0 || jeu.matJeu[c.ligne][c.col-i]==jVal) {
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
			if(c.col+i<jeu.matJeu[0].length && !stopd) {
				if(jeu.matJeu[c.ligne][c.col+i]==0 || jeu.matJeu[c.ligne][c.col+i]==jVal) {
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
				if(jeu.matJeu[c.ligne-i][c.col]==jVal||jeu.matJeu[c.ligne-i][c.col]==0) {
					nbverti++;
				}
				else {
					stopb=true;
				}
			}
			else {
				stopb=true;
			}
			if(c.ligne+i<jeu.matJeu.length&&!stoph) {
				if(jeu.matJeu[c.ligne+i][c.col]==0||jeu.matJeu[c.ligne+i][c.col]==jVal) {
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
			if(c.ligne-i>=0 && c.col+i<jeu.matJeu[0].length && !stopadiagd) {
				if(jeu.matJeu[c.ligne-i][c.col+i]==0 |jeu.matJeu[c.ligne-i][c.col+i]==jVal) {
					nbadiag++;
				}
				else {
					stopadiagd=true;
				}
			}
			else {
				stopadiagd=true;
			}
			
			if(c.ligne+i<jeu.matJeu.length && c.col-i>=0 && !stopadiagg) {
				if(jeu.matJeu[c.ligne+i][c.col-i]==0 |jeu.matJeu[c.ligne+i][c.col-i]==jVal) {
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
				if(jeu.matJeu[c.ligne-i][c.col-i]==0 |jeu.matJeu[c.ligne-i][c.col-i]==jVal) {
					nbdiag++;
				}
				else {
					stopdiagg=true;
				}
			}
			else {
				stopdiagg=true;
			}
			
			if(c.ligne+i<jeu.matJeu.length && c.col-i<jeu.matJeu[0].length && !stopdiagd) {
				if(jeu.matJeu[c.ligne+i][c.col+i]==0 |jeu.matJeu[c.ligne+i][c.col+i]==jVal) {
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
	
	
	
	
	
	

