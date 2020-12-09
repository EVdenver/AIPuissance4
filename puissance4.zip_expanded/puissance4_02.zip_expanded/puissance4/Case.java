import javax.swing.*;
import java.awt.*;

public class Case extends JPanel {
	int val;
	int ligne; // ligne et colone commence a 1
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
	
	public int nbCaseVideAround() {
		int res = 0;
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (jeu.joueur)
			jVal = 2;
		int nbCol = jeu.opts.getGameWidth();
		int nbRow = jeu.opts.getGameHeight();
		Case cc; 
		if(ligne+1 < nbRow && col-1 > 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne+1) - 1) + ((col-1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (col-1 > 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * (ligne - 1) + ((col-1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (ligne-1 > 0 && col-1 > 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + ((col-1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (ligne-1 > 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + (col - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (ligne-1 > 0 && col+1 < nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + ((col+1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (col+1 < nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * (ligne - 1) + ((col+1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		if (ligne+1 < nbRow && col+1 < nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne+1) - 1) + ((col+1) - 1));
			if (jeu.matJeu[cc.ligne-1][cc.col-1] == 0 || jeu.matJeu[cc.ligne-1][cc.col-1] == jVal) {
				res++;
			}
		}
		return res;
	}
	
	public Case compareCaseMinMax(Case c, boolean joueur) {
		if (joueur) {//Le joueur est min
			if (this.nbCaseVideAround() < c.nbCaseVideAround()) {
				return c;
			}else if (this.nbCaseVideAround() > c.nbCaseVideAround()) {
				return this;
			}else {//Les 2 cases ont le même nombre de cases vides donc on garde la première
				return c;
			}
		}else {//Le joueur est max
			if (this.nbCaseVideAround() < c.nbCaseVideAround()) {
				return this;
			}else if (this.nbCaseVideAround() > c.nbCaseVideAround()) {
				return c;
			}else {//Les 2 cases ont le même nombre de cases vides donc on garde la première
				return c;
			}
		}
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
	
	@Override
	public String toString() {
		return "Case [ligne=" + ligne + ", col=" + col + "]";
	}
	// elle renvoie 1 si la case courante a plus de possibilité, 0 si il ont la même quantité de possibilité, et -1 si c'est Cé qui a plus de possibilité
	public int comparePossibilite(Case C2) {
		int possibiliteCaseCourante=this.calculerNb4pos();
		if(C2==null ) {
			throw new NullPointerException() ;
		}
		int possibiliteC2=C2.calculerNb4pos();


		if(possibiliteCaseCourante>possibiliteC2) {
			return 1;
		}
		
		if(possibiliteCaseCourante==possibiliteC2) {
			return 0;
		}
		return-1;

		
	}
	
	public int calculerNb4pos() {
		//System.out.println("jeu:"+jeu);
		return this.calculerNb4pos(jeu.matJeu);
		
	}
	
	
	
	
	
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
	System.out.println("la case: "+ this);
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
			if(c.col+i<=matJeuSim[0].length && !stopd) {
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
			if(c.ligne+i<=jeu.matJeu.length &&!stoph) {
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
			if(c.ligne-i>0 && c.col+i<=jeu.matJeu[0].length && !stopdiagd) {
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
			
			if(c.ligne+i<=jeu.matJeu.length && c.col-i>0 && !stopdiagg) {
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
			
			if(c.ligne+i<=jeu.matJeu.length && c.col+i<=jeu.matJeu[0].length && !stopadiagd) {
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
		
		return nbd4p;
		
 }

}
	
	
	
	
	
	

