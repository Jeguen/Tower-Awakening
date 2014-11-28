package fr.dessin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.battleField.BattleFieldLandscape;
import fr.battleField.BattleFieldMatrix;
import fr.battleField.BattleFieldPieceWise;
import fr.battleField.BattleFieldRoad;
import fr.battleField.BattleFieldWater;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public class Panel extends JPanel implements MouseListener, KeyListener
{
	// Numéro d'ID
	private static final long serialVersionUID = 1L;
	// Le plateau total
	private ArrayList<BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>> plateau;
	// Stockage des derniers clics de la souris
	private int clicX;
	private int clicY;
	// 1 = eau
	// 2 = plaine
	// 3 = chemin
	//-1 = supprimer
	// 0 = reset
	// Initialisation autre que les valeurs possibles
	private int choix=10;
	// Getter du plateau pour les dessins
	Panel(ArrayList<BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>> plateau)
	{
		this.plateau=plateau;		
	}	
	// Setter du choix
	public void setChoix(int i)
	{
		this.choix=i;
	}
	// Fonction pour dessiner
	public void paintComponent(Graphics g)
	{
		// Si le choix est RESET
		if(choix==0)
		{
			// On parcourt les trois matrices
			for(BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b: plateau)
			{
				// Pour toute la matrice on met à null
				for(int i=0; i<Fenetre.TAILLE_MAX; i++)
				{
					for(int j=0; j<Fenetre.TAILLE_MAX; j++)
					{
						try
                                    {
	                                    b.suppElement(i, j);
                                    }
                                    catch (Exception e)
						{
	                                    e.printStackTrace();
                                    }
					}	
				}
			}
		}
		// On met le fond noir
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 500);	
		// On dessine le quadrillage		
		g.setColor(Color.GRAY);
		for(int i=0; i<=500; i+=50)
		{
			g.drawLine(i, 0, i, 500);
		}
		for(int i=0; i<=500; i+=50)
		{
			g.drawLine(0, i, 500, i);
		}
		// On dessine les cases
		dessinerCase(g);	
	}
	// Pour dessiner les cases
	public void dessinerCase(Graphics g)
	{
		// On définit une matrice qui contientdra les trois matrices à la suite
		BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b;
		// Eau
		b=plateau.get(0);
		for(int i=0; i<Fenetre.TAILLE_MAX; i++)
		{
			for(int j=0; j<Fenetre.TAILLE_MAX; j++)
			{
				// Si l'élément existe on le dessine
				if(b.elementExists(i,j))
				{
					g.setColor(b.getElement(i, j).getCouleur());
					g.fillRect(i*50+1, j*50+1, 49, 49);
				}
			}		
		}
		// Plaine
		b=plateau.get(1);
		for(int i=0; i<Fenetre.TAILLE_MAX; i++)
		{
			for(int j=0; j<Fenetre.TAILLE_MAX; j++)
			{
				// Si l'élément existe on le dessine
				if(b.elementExists(i,j))
				{
					g.setColor(b.getElement(i, j).getCouleur());
					g.fillRect(i*50+1, j*50+1, 49, 49);
				}
			}		
		}
		// Chemin
		b=plateau.get(2);
		for(int i=0; i<Fenetre.TAILLE_MAX; i++)
		{
			for(int j=0; j<Fenetre.TAILLE_MAX; j++)
			{
				// Si l'élément existe on le dessine
				if(b.elementExists(i,j))
				{
					g.setColor(b.getElement(i, j).getCouleur());
					g.fillRect(i*50+9, j*50+9, 33, 33);
				}
			}		
		}
	}
	public void mouseClicked(MouseEvent arg0)
	{
	}
	public void mouseEntered(MouseEvent arg0)
	{
	}
	public void mouseExited(MouseEvent arg0)
	{
	}
	public void mousePressed(MouseEvent e)
	{
		// On récupère les coordonnées du clic de l'user
		try
		{
			clicX=e.getX();
			clicY=e.getY();
			
			// Si c'est le clic gauche
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				// Mise en forme avec la fenêtre
				clicX+=-8;
				clicY+=-67;
				// On met les coordonnées sous forme de tableau du quadrillage
				clicX=(clicX-clicX%50)/50;
				clicY=(clicY-clicY%50)/50;

				// Si on veut supprimer
				if(choix<0)
				{
					for(BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b: plateau)
					{
						// Si l'élément est différent de nul
						if(b.getElement(clicX, clicY)!=null)
						{
							// Si il est destructible
							if(b.getElement(clicX, clicY).isDestroyable())
							{
								// On le supprime
								try
	                                          {
		                                          b.suppElement(-2,  clicY);
	                                          }
	                                          catch (Exception e1)
	                                          {
		                                          e1.printStackTrace();
	                                          }
							}
							else
							{
								// Si c'est pas destructible c'est que c'est de l'eau
								JOptionPane.showMessageDialog(new JFrame(), "L'élément 'eau' est indestrutible. Pas moi qui choisit ...", "Indestrutible !", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				}
				// Si on veut ajouter
				else if(choix>0)
				{
					// booléen de test
					boolean vide=true;
					// Pour ajouter de l'eau
					if(choix==1)
					{
						// Pour les trois matrices on regarde s'il existe un objet dans l'une d'elles
						for(BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b: plateau)
						{
							if(b.getElement(clicX, clicY)!=null)
							{
								vide=false;
							}
						}
						// S'il n'y a rien, alors il n'y a pas de contrainte particulière
						// Donc on ajoute tout simplement
						if(vide)
						{
							plateau.get(0).setElement(clicX, clicY, new BattleFieldWater());						
						}
						else
						{
							// Si l'élement qui existe est de l'eau, alors on affiche une erreur
							if(plateau.get(0).elementExists(clicX, clicY))
							{
								JOptionPane.showMessageDialog(new JFrame(), "La case est déjà de l'eau.");
							}
							// Sinon, de toute façon l'eau est imcompatible donc affichage de l'erreur
							else
							{
								JOptionPane.showMessageDialog(new JFrame(), "Tu ne peux pas mettre de l'eau ici.", "Eléments Incompatibles", JOptionPane.WARNING_MESSAGE);	
							}	
						}
					}
					// Pour ajouter un paysage
					else if(choix==2)
					{
						for(BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b: plateau)
						{
							// Pareil, on regarde si c'est vide
							if(b.getElement(clicX, clicY)!=null)
							{
								vide=false;
							}
						}
						// Si c'est vide ajout simple
						if(vide)
						{
							plateau.get(1).setElement(clicX, clicY, new BattleFieldLandscape());						
						}
						else
						{
							// Si l'élément est une plaine, erreur
							if(plateau.get(1).elementExists(clicX, clicY))
							{
								JOptionPane.showMessageDialog(new JFrame(), "Cette case est déjà une plaine.");
							}
							// Si l'élement actuel est de l'eau, erreur
							else if(plateau.get(0).elementExists(clicX, clicY))
							{
								JOptionPane.showMessageDialog(new JFrame(), "Tu veux mettre de la plaine sur de l'eau. Impossible.", "Eléments Incompatibles", JOptionPane.WARNING_MESSAGE);	
							}
							// Si l'élement actuel est un chemin, on peut ajouter
							else
							{
								plateau.get(1).setElement(clicX, clicY, new BattleFieldLandscape());	
							}
						}
					}
					// Pour ajouter une route
					else if(choix==3)
					{
						// Idem, on regarde si c'est vide
						for(BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> b: plateau)
						{
							if(b.getElement(clicX, clicY)!=null)
							{
								vide=false;
							}
						}
						// Si c'est vide, ajout simple
						if(vide)
						{
							plateau.get(2).setElement(clicX, clicY, new BattleFieldRoad());						
						}
						else
						{
							// Si c'est déjà une route, erreur
							if(plateau.get(2).elementExists(clicX, clicY))
							{
								JOptionPane.showMessageDialog(new JFrame(), "Cette case est déjà une route");
							}
							// Si c'est de l'eau, erreur
							else if(plateau.get(0).elementExists(clicX, clicY))
							{
								JOptionPane.showMessageDialog(new JFrame(), "Tu veux mettre une route sur de l'eau. Impossible. Tu peux marcher sur l'eau ?", "Eléments Incompatibles", JOptionPane.WARNING_MESSAGE);	
							}
							else
							// Sinon ajout simple
							{
								plateau.get(2).setElement(clicX, clicY, new BattleFieldRoad());	
							}
						}
					}
				}
			}
			// On actualise
			repaint();
		}
            catch (Exception e2)
		{
                  e2.printStackTrace();
            }
	}
	public void mouseReleased(MouseEvent arg0)
	{
	}
	public void keyPressed(KeyEvent e)
	{
	}
	public void keyReleased(KeyEvent arg0)
	{
	}
	public void keyTyped(KeyEvent arg0)
	{
	}
}
