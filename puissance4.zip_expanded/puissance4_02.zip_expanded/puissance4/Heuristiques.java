import java.util.ArrayList;
import java.util.Scanner;


public class Heuristiques {
	private static int nbRow;
	//private static Jeu jeu;
	private static final int PRONFONDEUR_MAX=5;

	public Heuristiques(Jeu jeu) {
		Noeud.jeu=jeu; // tout les noeud concerneront ce jeu
		Heuristiques.nbRow = jeu.matJeu.length;
	}

	static public ArrayList<Noeud> couPossible(byte[][] matJeuSim) {
		
		ArrayList<Noeud> resultat = new ArrayList<>();
		
		for(int i=1;i<=matJeuSim[0].length;i++)// commence a 1 parsque dans la methode searchLine sa va de 1 a length
		{	
			int l=searchLine(i,matJeuSim);
			if(l!=-1) {
			Noeud n= new Noeud(l,i,0,null,new ArrayList<Noeud>(),0,matJeuSim);
			resultat.add(n);
			}
		}

		return resultat;
	}

	// renvoie la colone du choix avec le plus ou le moins de ligne possible en fonction du parametre max pour le coups prochain uniquement
	static public Noeud HeuriMinMax(boolean max, byte[][] matJeu) {
		
		ArrayList<Noeud> couPossible=couPossible(matJeu);
		if(couPossible.isEmpty()) return null;
		Noeud choix=couPossible.get(0);
		couPossible.remove(0);

		while(!couPossible.isEmpty()) {
			choix=(max)?((choix).comparePossibilite(couPossible.get(0))>=0)?choix:couPossible.get(0)
					:((choix).comparePossibilite(couPossible.get(0))<0)?choix:couPossible.get(0);
			couPossible.remove(0);
			
		}
		
		return choix;
	}

	// renvoie la colone qui lorsque ce cera le tour de l'adversaire lui laissera le moins de possibilite
	static public int HeuriBloqueur(Jeu jeu) {
		// 1 : trouver tout les choix possible de jeu
		// 2 : pour chacun de ses choix possible evaluer le nombre de possiblit� de du meilleur coup qe peut faire l'adversaire ( est-qu'il peut jouer un coup a 3 possibilit� � 4 possibilit�)
		// 3 : choisir le coup pour lequel de possibilit� du meilleur coup adverse sera le plus faible
		return 0;
	}



	// nbCoupMax correspondant au nombre de coup restant � jouer � ce moment de la partie
	static public Noeud HeuriMinMaxProfonde(boolean max, byte[][] matJeu, int nbCoupMax) {
		//Noeud choix = HeuriMinMax( max,  matJeu); // on initialise pour une profondeur de 0
		//Noeud coupTrouve;
		Noeud choix;
		//int poidMax=choix.poid;
		nbCoupMax=(nbCoupMax>PRONFONDEUR_MAX)? PRONFONDEUR_MAX:nbCoupMax;

		/*if (poidMax != Noeud.MAX_POID) { // si le premier trouv� n'est pas le noeud optimale
			ArrayList<Noeud> choixPossible = new ArrayList<Noeud>();

			for (int i=1; i<nbCoupMax;i++) {
				coupTrouve =minMaxProfondeArbreRecherche(couPossible(matJeu), i,  max);
				if (coupTrouve.poid>poidMax) {
					choix = coupTrouve;
					poidMax=coupTrouve.poid;
					System.out.println("choix :"+coupTrouve);
					System.out.println("poid de celui-ci :"+coupTrouve.poid);
					System.out.println("pronfondeur de celui-ci:"+ i);
				}
				if (choix.poid == Noeud.MAX_POID) break;
			}*/
		choix=minMaxProfondeArbreRecherche(couPossible(matJeu), nbCoupMax,  max);
		// remont� les parents de choix sur profondeur niveau
			
		int profondeurAREmonter = choix.profondeurARemonter;
		for (int i=0;i<profondeurAREmonter;i++) {
			choix=choix.parent;
		}


		return choix;


	}




	// une profondeur de 0 correspond � une recherche sur les coups directement suivants pour le jeu j
	static public Noeud minMaxProfondeArbreRecherche(ArrayList<Noeud> choixPossibles, int profondeur, boolean max) {
			
		System.out.println("entrer dans la methode arbre");
		ArrayList<Noeud> nouveauChoixPossibles = new ArrayList<Noeud>();
		
			if (profondeur == 0) { // on prend celui qui sur la liste � le meilleur r�sultats
			for (Noeud noeud : choixPossibles) {
				
				noeud.poid=noeud.calculerNb4pos(noeud.matJeuSim);
			}
			Noeud choix=choixPossibles.get(0);// erreur finit par renvoyer un arraylist vide
			choixPossibles.remove(0);
			while(!choixPossibles.isEmpty()) {
				
				 byte jVal = 1; // Variable contenant la valeur du joueur,
			        if (choix.jeu.joueur) {
			            jVal = 2;
			        }
			        choix.jeu.matJeu[choix.ligne-1][choix.col-1]=jVal;
			        if (choix.jeu.joueurGagne(choix.jeu.joueur, choix.ligne-1, choix.col-1)) {
			        	choix.jeu.matJeu[choix.ligne-1][choix.col-1]=0;
			            return choix;
			        }
			        choix.jeu.matJeu[choix.ligne-1][choix.col-1]=0;
			        
			        
			        choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=jVal;
			        if(choixPossibles.get(0).jeu.joueurGagne(choixPossibles.get(0).jeu.joueur, choixPossibles.get(0).ligne-1, choixPossibles.get(0).col-1)) {
			        	choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=0;
			        	return choixPossibles.get(0);
			        }
			        choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=0;
			        
			         jVal = 1; // Variable contenant la valeur du joueur,
			        if (!choix.jeu.joueur) {
			            jVal = 2;
			        }
			        
			        choix.jeu.matJeu[choix.ligne-1][choix.col-1]=jVal;
			        if (choix.jeu.joueurGagne(!choix.jeu.joueur, choix.ligne-1, choix.col-1)) {
			        	choix.jeu.matJeu[choix.ligne-1][choix.col-1]=0;
			            return choix;
			        }
			        choix.jeu.matJeu[choix.ligne-1][choix.col-1]=0;
			        
			        
			        choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=jVal;
			        if(choixPossibles.get(0).jeu.joueurGagne(!choixPossibles.get(0).jeu.joueur, choixPossibles.get(0).ligne-1, choixPossibles.get(0).col-1)) {
			        	choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=0;
			        	return choixPossibles.get(0);
			        }
			        choixPossibles.get(0).jeu.matJeu[choixPossibles.get(0).ligne-1][choixPossibles.get(0).col-1]=0;
			        
			        
				
				
				choix=(max)?((choix).comparePoid(choixPossibles.get(0))>=0)?choix:choixPossibles.get(0) // attention, il faut adapter les m�thodes pour qu'elle calculent les choix possibles sur le matJeuSim contenu dans le Noeud et non le matJeu de Jeu
						   :((choix).comparePoid(choixPossibles.get(0))<0)?choix:choixPossibles.get(0);
				choixPossibles.remove(0);
				// de plus, il faut instancier le param�tres "poid" de Noeud avec le nombre de ligne4
			}
			choix.poid=choix.calculerNb4pos(choix.matJeuSim);
			System.out.println("le choix "+ choix+" a un poid de : "+ choix.poid);
			return choix;
		}
		else{
			for (Noeud noeud : choixPossibles) { // pour chaque noeud de ce niveau de profondeur
				byte[][] matJeuSimSuivant = cloneByte(noeud.matJeuSim);  // on clone le tableau de jeu
				if(noeud.jeu.joueur) {
					if(profondeur%2==0) {
						matJeuSimSuivant[noeud.ligne-1][noeud.col-1]=(byte)2; //  et on joue la case correspondante joueur.jVal
					}
					else {
						matJeuSimSuivant[noeud.ligne-1][noeud.col-1]=(byte)1;
					}
				}
				else {
					if(profondeur%2==0) {
						matJeuSimSuivant[noeud.ligne-1][noeud.col-1]=(byte)1; //  et on joue la case correspondante joueur.jVal
					}
					else {
						matJeuSimSuivant[noeud.ligne-1][noeud.col-1]=(byte)2;
						
					}
					
				}
				ArrayList<Noeud> enfants = couPossible(matJeuSimSuivant);  // on produit les nouveaux coups possibles � partir de cet �tat
				    for (int i=0;i<enfants.size();i++) {
					enfants.get(i).parent=noeud;  // chacun de ses nouveaux noeuds ont pour parent le noeud courant
					enfants.get(i).profondeurARemonter=noeud.profondeurARemonter+1; // chaque enfant a la profondeur de son parent +1
					enfants.get(i).matJeuSim=matJeuSimSuivant;
					if(profondeur%2==0) {
						enfants.get(i).poid=enfants.get(i).parent.poid+enfants.get(i).calculerNb4pos(matJeuSimSuivant); // si on est a une profondeur pair on ajoute le nouveau poid, 
					}
					else {
						
						enfants.get(i).poid=enfants.get(i).parent.poid-enfants.get(i).calculerNb4pos(matJeuSimSuivant);// si on est a une profondeur pair on ajoute le nouveau poid, 
					}
					
				}
				noeud.enfants=enfants; // et le noeud courant � pour enfants tout ces nouveaux choix possibles
				nouveauChoixPossibles.addAll(enfants); // on ajoute ses noeuds possibles � ce que l'on va parcourir lors de la prochaine couche
				
			}
			
		}
		System.out.println("profondeur :" +profondeur);
		System.out.println("nombre de nouveau choix:" + nouveauChoixPossibles.size());
		return minMaxProfondeArbreRecherche(nouveauChoixPossibles,  profondeur-1,  max);	// on fait une recherche sur les choix de la nouvelle couche en pr�cisant que la profonder � �voluer
	}







	// M�thode cherchant la 1�re ligne jouable d'une colonne (gravitation...)
	/** Search the row to be played for a given col (gravity law...)
	 * @param col col for which the method searches the line
	 * @return Number of the line corresponding to the col, -1 if the col is full
	 */
	public static int searchLine(int col, byte[][] matJeu) {

		for (int i = matJeu.length; i >= 1; i--) {
			//System.out.println("la ligne:"+(i-1));
			//System.out.println("la col:"+(col-1));
			//System.out.println("matJeu[i - 1][col - 1]="+matJeu[i - 1][col - 1]);

			if (matJeu[i - 1][col - 1] == 0)return i;
		}
		return -1; // Aucune ligne n'a �t� trouv�e : la colonne est remplie
	}


    // renvoie une nouvelle instance de la matrice mis en facteur
	public static byte[][] cloneByte(byte[][] b){
		byte[][] newb= new byte[b.length][b[0].length];
		for(int i=0;i<b.length-1;i++) {
			for(int j=0; i<b[0].length-1;i++) {
				newb[i][j]=b[i][j];
			}
		}
		return newb;

	}
}