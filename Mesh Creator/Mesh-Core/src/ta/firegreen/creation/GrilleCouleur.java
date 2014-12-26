package ta.firegreen.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ta.firegreen.creation.ToolsFrame.ColorConfiguration;

@SuppressWarnings("serial")
public class GrilleCouleur extends JInternalFrame {

		private int nbcouleur;
		private Couleur couleurs[][];
		private Couleur colorSelected;
		private GridBagLayout grilleCouleur;
		private BarreCouleur barre;
		private ColorConfiguration colorToDefine;
		private float value;
		
		public GrilleCouleur(ConfigurationFrame cfParent, ColorConfiguration colorToDefine, int nbcouleur)
		{
			super("Palette");
			cfParent.getContentPane().add(this);
			this.setLocation(new Point(50,50));
			this.setSize(new Dimension(400,400));
			this.nbcouleur=nbcouleur;
			couleurs= new Couleur[(int)nbcouleur][(int)nbcouleur]; 
			this.colorToDefine=colorToDefine;
			this.value=1f;
			grilleCouleur=new GridBagLayout();
			GridBagConstraints cases = new GridBagConstraints(); 
			JPanel grille = new JPanel(grilleCouleur){
				
				public void paintComponent(Graphics g)
				{
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
				}
			};

			this.getContentPane().add(grille, BorderLayout.CENTER);
			cases.fill = GridBagConstraints.BOTH;
			int w = (int) (this.getWidth()/nbcouleur);
			int h = (int) (this.getHeight()/nbcouleur);
			cases.weightx=w;
			cases.weighty=h+1;
			float nbC = (float) nbcouleur-1;
			for (int cptt=0; cptt<nbcouleur;cptt++)
				for(int cpt=0; cpt<nbcouleur;cpt++)
				{
					cases.gridx=cpt;
					cases.gridy=cptt;
					couleurs[cpt][cptt]=new Couleur(Color.getHSBColor(this.value/100, (float)cpt/nbC, (float)cptt/nbC),this);
					grille.add(couleurs[cpt][cptt], cases);
				}
			colorSelected = couleurs[0][0];
			colorSelected.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			colorSelected.isSelected = true;
			barre = new BarreCouleur(this);
			this.getContentPane().add(barre, BorderLayout.EAST);
			
		}
		
		public void setCouleur(float valeur)
		{
			value=valeur;
			float nbC = (float) nbcouleur-1;
			for (int cptt=0; cptt<nbcouleur;cptt++)
				for(int cpt=0; cpt<nbcouleur;cpt++)
				{
					couleurs[cpt][cptt].lacouleur = java.awt.Color.getHSBColor(this.value/100, cpt/nbC, cptt/nbC);
					couleurs[cpt][cptt].repaint();
				}
			this.repaint();
		}

	
	
	
	
	
	public Color getColor() { return(colorSelected.lacouleur);}
	

	
	private static class BarreCouleur extends JPanel implements MouseListener
	{
		private GrilleCouleur grille;
		public BarreCouleur(GrilleCouleur grille)
		{
			this.addMouseListener(this);
			this.grille=grille;
			this.setMaximumSize(new Dimension(30,grille.getHeight()));
			this.setMinimumSize(new Dimension(30,60));

		}
		public void  paintComponent(Graphics g)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, 36, getHeight());
			for(int cpt=0;cpt<this.getHeight();cpt++)
			{
				g.setColor(Color.getHSBColor((float)cpt/this.getHeight(),(float) 0.8,(float)1));
				g.fillRect(0, cpt, 30, 1);
			}
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			float valeur=((float)e.getY()*100/(float)this.getHeight());
			grille.setCouleur(valeur);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	private static class Couleur extends JPanel implements MouseListener 
	{

		private Color lacouleur;
		private boolean isSelected;
		private GrilleCouleur parent;
		
		
		
		public Couleur(Color lacouleur, GrilleCouleur parent)
		{
			this.lacouleur=lacouleur;
			this.parent=parent;
			this.addMouseListener(this);
		}
		public void paintComponent(Graphics g)
		{
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(lacouleur);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(!isSelected)
			{
				parent.colorSelected.isSelected=false;
				parent.colorSelected.setBorder(this.getBorder());
				parent.colorSelected.repaint();
				isSelected=true;
				parent.colorSelected=this;
				setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			else{
				parent.colorToDefine.setColor(this.lacouleur);
				parent.dispose();
				
			}			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			this.repaint();
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			Graphics g = this.getGraphics();
			g.setColor(Color.BLUE);
			g.drawRect(0, 0, getWidth(), getHeight());
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
		
}
