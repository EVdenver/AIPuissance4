import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
 * OptionsGUI.java
 *
 * Created on 12 mars 2004, 18:16
 */

/**
 *
 * @author  Michaël Perrin 
 * @modifieur Vincent Marechal, Garnier Nicolas
 */

public class OptionsGUI extends JFrame implements ActionListener {
	
	Options opts;
	
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	JPanel pane = new JPanel();
	
	JPanel lignesColPane = new JPanel(new GridLayout(2, 2, 10, 10));
	JLabel nbRowLabel = new JLabel("Nombre de lignes du tableau :");
	JTextField text1 = new JTextField("6", 2);
	JLabel nbColLabel = new JLabel("Nombre de colonnes du tableau :");
	JTextField text2 = new JTextField("7", 2);
	
	JCheckBox computerHeuristique1 = new JCheckBox("Jouer contre l'heuristique 1", false);

	JCheckBox computerHeuristique2 = new JCheckBox("Jouer contre l'heuristique 2", false);
	//JCheckBox computerHeuristiquefight = new JCheckBox("Heuristique 1 contre l'heuristique 2", false);

	JCheckBox computerHeuristique1Alpha = new JCheckBox("Jouer contre l'heuristique 1(alpha)", false);
	JCheckBox computerStartsCheckBox = new JCheckBox("L'ordinateur commence", false);

	JButton ok = new JButton("Ok");
	
	/** Creates a new instance of OptionsGUI for initializing an instance of Options */
	public OptionsGUI(Options opts) {
		
		super("Options de jeu");
		setSize(600, 400);
		setLocation(50, 50);
		
		pane.setLayout(gbl);
		
		// Nb de lignes, colonnes
		buildConstraints(constraints, 0, 0, 2, 1, 80, 30);
		lignesColPane.add(nbRowLabel);
		lignesColPane.add(text1);
		lignesColPane.add(nbColLabel);
		lignesColPane.add(text2);
		gbl.setConstraints(lignesColPane, constraints);
		
		pane.add(lignesColPane);
		
		// L'ordinateur joue ?
		JPanel computerPane = new JPanel(new GridLayout(4, 1));

		//Pour jouer avec l'heuristique 1
		computerPane.add(computerHeuristique1);
		//Pour jouer avec l'heuristique 1 avec élagage alpha(non fonctionnel)
		computerPane.add(computerHeuristique1Alpha);
    computerPane.add(computerHeuristique2);
		//Booleen pour savoir si l'heuristique commence
		computerPane.add(computerStartsCheckBox);
		computerHeuristique1Alpha.setEnabled(false);
		
		
		computerHeuristique1.addActionListener(this);
		computerHeuristique1Alpha.addActionListener(this);
		computerHeuristique2.addActionListener(this);
		
		
		buildConstraints(constraints, 0, 1, 2, 1, 0, 20);
		gbl.setConstraints(computerPane, constraints);
		
		pane.add(computerPane);
		
		// Bouton Ok
		ok.addActionListener(this);
		
		buildConstraints(constraints, 1, 3, 1, 1, 0, 10);
		gbl.setConstraints(ok, constraints);
		pane.add(ok);
		
		setComputerHeuristique1OptionsEnabled(computerHeuristique1.isSelected());
		//setComputerHeuristiqueAlphaOptionsEnabled(computerHeuristique1Alpha.isSelected());
		setComputerHeuristique2OptionsEnabled(computerHeuristique2.isSelected());

		
		setContentPane(pane);
		
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.opts = opts;

	}
	

	public void buildConstraints (GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
		gbc.gridx = gx;			// Coordonnées dans la "grille"
		gbc.gridy = gy;
		gbc.gridwidth = gw;		// Nombre de cellules sur lesquelles s'étend l'objet
		gbc.gridheight = gh; 
		gbc.weightx = wx;		// "Largeur", en proportion
		gbc.weighty = wy;
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		
		if (src == ok) {
			try {
				int nbRow = Integer.parseInt(text1.getText());
				int nbCol = Integer.parseInt(text2.getText());
				if (nbRow <= 0 || nbCol <= 0)
					throw new NumberFormatException();
					opts.setSize(nbRow, nbCol, true);
				
				setVisible(false);
				
				if (computerHeuristique1.isSelected()) {
					opts.initComputerHeuristique1(computerHeuristique1Alpha.isSelected()?computerHeuristique1Alpha.isSelected():computerHeuristique1.isSelected(), computerStartsCheckBox.isSelected(),computerHeuristique1Alpha.isSelected());
				}
				if (computerHeuristique2.isSelected()) {
					opts.initComputerHeuristique2(computerHeuristique2.isSelected(), computerStartsCheckBox.isSelected());	
				}
				
				
				
			} catch (NumberFormatException e) {
				Saisie.erreurMsgOk("Erreur : le nombre de ligne et le nombre de colonnes doivent être des entiers", "Erreur");
			}
			
		}
		

		else if (src == computerHeuristique1)
			setComputerHeuristique1OptionsEnabled(computerHeuristique1.isSelected()); // disables or enables the computer options

		else if (src == computerHeuristique2)
			setComputerHeuristique2OptionsEnabled(computerHeuristique2.isSelected()); // disables or enables the computer options
		
	}
	
	/** Makes computer options enabled or not whether the user enabled the network option or not
	 * @param b if true, makes computer options available (enabled), if false makes them disabled
	 */	
	public void setComputerHeuristique2OptionsEnabled(boolean b) {
		computerStartsCheckBox.setEnabled(b);
		computerHeuristique1.setEnabled(!b);
	}
  
  	public void setComputerHeuristique1OptionsEnabled(boolean b) {
		computerStartsCheckBox.setEnabled(b);
		computerHeuristique2.setEnabled(!b);
	}
	
}
