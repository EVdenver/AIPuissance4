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
	
	public Case compareCase4(Case C2) {
		t1=OrdiEuristique2.calculerNb4pos(this);
		t2=OrdiEuristique2.calculerNb4pos(this)
		if(C2==null ) {
			return this;
		}
		
		if(calculerNb4pos(this)>=calculerNb4pos(C2)) {
			return this;
		}
		if(calculerNb4pos(C2)>calculerNb4pos(this)) {
			return C2;
		}
	}
	
}
