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
	
	JCheckBox computerOnCheckBox = new JCheckBox("Jouer contre l'ordinateur", false);
	JCheckBox computerStartsCheckBox = new JCheckBox("L'ordinateur commence", false);
	JLabel label4 = new JLabel("Difficulté");
	JSlider slider1 = new JSlider(1, 9, 1);
        
	JPanel networkPane = new JPanel();
	JCheckBox networkOn = new JCheckBox("Jouer en réseau", false);
	ButtonGroup serveurClient = new ButtonGroup();
	JRadioButton server = new JRadioButton("Etre serveur", false);
	JRadioButton client = new JRadioButton("Etre client", true);
	JTextField ipTextField = new JTextField("Hostname or IP number", 15);

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
		/*JPanel computerPane = new JPanel(new GridLayout(4, 1));
		computerPane.add(computerOnCheckBox);
		computerPane.add(computerStartsCheckBox);
		computerPane.add(label4);
		
		initSlider(); // règle les paramètres de slider1
		
		computerOnCheckBox.addActionListener(this);
		
		buildConstraints(constraints, 0, 1, 2, 1, 0, 20);
		gbl.setConstraints(computerPane, constraints);
		
		computerPane.add(slider1);
		
		pane.add(computerPane);*/
		
		// Réseau
		/*JPanel serveurClientPane = new JPanel(new BorderLayout());
		serveurClient.add(server);
		serveurClient.add(client);
		serveurClientPane.add(networkOn, "North");
		serveurClientPane.add(server, "West");
		serveurClientPane.add(client, "East");
		serveurClientPane.add(ipTextField, "South");
		networkPane.add(serveurClientPane);
		
		networkOn.addActionListener(this);
		client.addActionListener(this);
		server.addActionListener(this);
		
		buildConstraints(constraints, 0, 2, 2, 1, 0, 10);
		gbl.setConstraints(networkPane, constraints);
		
		pane.add(networkPane);*/
                
		// Bouton Ok
		ok.addActionListener(this);
		
		buildConstraints(constraints, 1, 3, 1, 1, 0, 10);
		gbl.setConstraints(ok, constraints);
		pane.add(ok);
		
		//setComputerOptionsEnabled(computerOnCheckBox.isSelected());
		//setNetworkOptionsEnabled(networkOn.isSelected());
		
		setContentPane(pane);
		
		setVisible(true);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.opts = opts;

	}
	
	/** Sets the good settings for the computer difficulty slider */	
	/*public void initSlider() {
		slider1.setMajorTickSpacing(4);
		slider1.setMinorTickSpacing(1);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		slider1.setSnapToTicks(true);
	}*/
	
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
				if (!networkOn.isSelected() || !client.isSelected())
					opts.setSize(nbRow, nbCol, true);
				
				setVisible(false);
				
				//opts.initComputer(computerOnCheckBox.isSelected(), computerStartsCheckBox.isSelected(), slider1.getValue());
				
				/*if (networkOn.isSelected()) {
					if (client.isSelected())
						opts.initNetwork(false, ipTextField.getText());
					else
						opts.initNetwork(true, new String());
				}*/
				
			} catch (NumberFormatException e) {
				Saisie.erreurMsgOk("Erreur : le nombre de ligne et le nombre de colonnes doivent être des entiers", "Erreur");
			}
			
		}
		
		/*else if (src == networkOn || src == client || src == server)
			setNetworkOptionsEnabled(networkOn.isSelected()); // enables or disables the network options
		
		else if (src == computerOnCheckBox)
			setComputerOptionsEnabled(computerOnCheckBox.isSelected()); // disables or enables the computer options*/
		
	}
	
	/** Makes computer options enabled or not whether the user enabled the network option or not
	 * @param b if true, makes computer options available (enabled), if false makes them disabled
	 */	
	/*public void setComputerOptionsEnabled(boolean b) {
		computerStartsCheckBox.setEnabled(b);
		slider1.setEnabled(b);
		// if computer enable, disable network options
		networkOn.setEnabled(!b);
	}*/
	
	/** Makes network options enabled or not whether the user enabled the network option or not
	 * @param b if true, makes network options available (enabled), if false makes them disabled
	 */	
	/*public void setNetworkOptionsEnabled(boolean b) {
		server.setEnabled(b);
		client.setEnabled(b);
		if (client.isSelected())
			ipTextField.setEnabled(b);
		else
			ipTextField.setEnabled(false);
		
		computerOnCheckBox.setEnabled(!b);
		
	}*/
	
}
