package LayeredBattleField;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Terrain composé de BattleFieldPieceWise
 * @author S Firegreen
 *
 */
public class BattleFieldMatrixPW extends
		BattleFieldMatrix<BattleFieldPieceWise> {

	public BattleFieldMatrixPW() {
		super();
	}
	
	/**
	 * Affiche la carte sur une JFrame
	 */
	public void afficheCarte()
	{
		JFrame afficheur = new JFrame("Carte");
		
		//afficheur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		afficheur.setSize(900, 600);
		int maxI=0,maxJ=0;
		Set<Index> cles = matrice.keySet();
		Iterator<Index> cptCle = cles.iterator();
		final BattleFieldPieceWise[] pieces = new BattleFieldPieceWise[cles.size()]; 
		for(int cpt = 0; cpt < cles.size() ; cpt++)
		{
			pieces[cpt] = matrice.get(cptCle.next());
			if(pieces[cpt].getX()>maxI) maxI=pieces[cpt].getX();
			if(pieces[cpt].getY()>maxJ) maxJ=pieces[cpt].getY();
		}
		JPanel ta = new JPanel(){
			@Override
			public void paint(Graphics g)
			{
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
				for(BattleFieldPieceWise c : pieces)
				{
					if(c.getClass().equals(BattleFieldPieceWise.BattleFieldWater.class))
					{
						g.setColor(Color.blue);
					}
					else
						if(c.getClass().equals(BattleFieldPieceWise.BattleFieldLandScape.class))
					{
						g.setColor(Color.green);
					}
					else
					{
						g.setColor(Color.gray);
					}
					g.fillRect(10+50*c.getX(), 10+50*c.getY(),50,50);
					g.setColor(Color.red);
					((Graphics2D)g).setStroke(new BasicStroke(3));
					g.drawRect(10+50*c.getX()-1, 10+50*c.getY()-1, 51, 51);
				}
				
			}
		};
		ta.setMinimumSize(new Dimension(maxI*100+10,maxJ*100+10));
		ta.setPreferredSize(ta.getMinimumSize());
		JScrollPane jp = new JScrollPane(ta);
		afficheur.setContentPane(jp);
		afficheur.setVisible(true);
	}
	
	@Override
	public void set(int numeroLigne, int numeroColonne, BattleFieldPieceWise data)
	{
		super.set(numeroLigne, numeroColonne, data);
		data.setX(numeroLigne);
		data.setY(numeroColonne);
	}

}
