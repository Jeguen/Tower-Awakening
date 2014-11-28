package fr.dessin;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.battleField.BattleFieldMatrix;
import fr.battleField.BattleFieldPieceWise;
/**
* 
* @author Corentin Filoche and Lino Tulliez
*
*/
public class Fenetre extends JFrame
{
	// Numéro d'ID
	private static final long serialVersionUID = 1L;
	// Pour pour les instances du programmes
	private static int PORT = 12345; 	
	// Taille Max Matrice
	public static final int TAILLE_MAX=10;
	// Sa liste des trois matrice
	private ArrayList<BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>> plateau = new ArrayList<BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>>();
	// Son Panel
	private Panel content = new Panel(plateau);
	// On crée les matrices de chaque type
	BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> matriceEau = new BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>();
	BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> matricePlaine = new BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>();
	BattleFieldMatrix<BattleFieldPieceWise<Number, Number>> matriceChemin = new BattleFieldMatrix<BattleFieldPieceWise<Number, Number>>();
	
	// Ajoute toutes les matrices à la liste
	public void creation()
	{
		plateau.add(matriceEau);
		plateau.add(matricePlaine);
		plateau.add(matriceChemin);
	}
	// Constructeur
	public Fenetre()
	{
		JOptionPane.showMessageDialog(new JPanel(),"© 2014 - Corentin Filoche & Lino Tulliez", "Application CréerMap", JOptionPane.PLAIN_MESSAGE);
		// On ne peut pas redimentionner
		this.setResizable(false);
		// Titre
		this.setTitle("Créer Map");
		// Taille
		this.setSize(507, 566);
		// Croix Rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Au centre
		this.setLocationRelativeTo(null);
		// On crée un bouton
		// On le nomme
		// On lui fournit une action à faire quand on clique dessus
   		JPanel boutonPane = new JPanel();
     	JButton boutonAjouterEau = new JButton("Add Eau");
     	boutonAjouterEau.addActionListener(new ActionListener()
   		{
  			public void actionPerformed(ActionEvent event)
  			{
  				content.setChoix(1);
      		}
  		});
     	// IDEM
     	JButton boutonAjouterPlaine = new JButton("Add Plaine");
     	boutonAjouterPlaine.addActionListener(new ActionListener()
   		{
  			public void actionPerformed(ActionEvent event)
  			{
  				content.setChoix(2);
      		}
  		});
     	// IDEM
     	JButton boutonAjouterChemin = new JButton("Add Chemin");
     	boutonAjouterChemin.addActionListener(new ActionListener()
   		{
  			public void actionPerformed(ActionEvent event)
  			{
  				content.setChoix(3);
      		}
  		});
        // IDEM
   		JButton boutonSupprimer = new JButton("Supprimer");
   		boutonSupprimer.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent event)
   			{	
   				content.setChoix(-1);  
        	}
     	});
     	// IDEM
   		JButton boutonReset= new JButton("Reset");
   		boutonReset.addActionListener(new ActionListener()
   		{
   			public void actionPerformed(ActionEvent event)
   			{	
   				content.setChoix(0);  
   				content.repaint();
   			}
     	}); 
   		
   		// On ajoute tout cela dans le groupe des boutons
   		boutonPane.add(boutonAjouterEau);
   		boutonPane.add(boutonAjouterPlaine);
   		boutonPane.add(boutonAjouterChemin);
      	boutonPane.add(boutonSupprimer);
      	boutonPane.add(boutonReset);
      	// On crée la liste des matrices
      	creation();
      	// On affiche les éléments dans la fenêtre
       	this.getContentPane().add(boutonPane, BorderLayout.NORTH);
        this.getContentPane().add(content, BorderLayout.CENTER);
		this.addKeyListener(content);
		this.addMouseListener(content);
		// On affiche la fenêtre
		this.setVisible(true);
 	}
	public static void main (String [] arg)
	{
		try
		{ 
			// On crée un nouveau ServerSocket
			ServerSocket server=new ServerSocket(PORT); 
			System.out.println("Instance du programme unique"); 	
			@SuppressWarnings("unused")
			Fenetre f=new Fenetre();
			// On ferme le serverSocket
			server.close();
		}
		catch(Exception e)
		{ 
			// Exception si l'application est déjà lancée
			JOptionPane.showMessageDialog(new JPanel(),"L'application est déjà éxécutée", "CréerMap", JOptionPane.WARNING_MESSAGE);
			System.exit(0); 
		} 
	}
}