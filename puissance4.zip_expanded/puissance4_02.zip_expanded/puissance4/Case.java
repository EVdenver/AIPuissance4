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
	
	public int nbCaseVideAround() {
		int res = 0;
		byte jVal = 1; // Variable contenant la valeur du joueur
		if (jeu.joueur)
			jVal = 2;
		int nbCol = jeu.opts.getGameWidth();
		int nbRow = jeu.opts.getGameHeight();
		Case cc; 
		if (ligne+1 <= nbRow && col-1 >= 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne+1) - 1) + ((col-1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (col-1 >= 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * (ligne - 1) + ((col-1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (ligne-1 >= 0 && col-1 >= 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + ((col-1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (ligne-1 >= 0) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + (col - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (ligne-1 >= 0 && col+1 <= nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne-1) - 1) + ((col+1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (col+1 <= nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * (ligne - 1) + ((col+1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
				res++;
			}
		}else if (ligne+1 <= nbRow && col+1 <= nbCol) {
			cc = (Case)jeu.plateau.pane.getComponent((jeu.opts.getGameWidth()) * ((ligne+1) - 1) + ((col+1) - 1));
			if (cc.val == 0 || cc.val == jVal) {
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
	
}
