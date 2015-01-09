package awakening.moi_affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import awakening.field.Barrier;
import awakening.field.Box;

public class Panel extends JPanel implements KeyListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int clicX;
	private int clicY;
	private boolean afficherQuadrillage = true;
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.white);
		if (afficherQuadrillage && Main.terrain.getBox().size() != 0)
		{
			for (Box b : Main.terrain.getBox())
			{
				g.drawPolygon(b.getTabCoordX(), b.getTabCoordY(), Main.terrain.getNbSidePolygon());
			}
		}
		g.setColor(Color.red);
		if (Main.terrain.getFinishBox() != null)
		{
			g.fillOval(Main.terrain.getFinishBox().getCoordX() - 10, Main.terrain.getFinishBox().getCoordY() - 10, 20, 20);
			Main.terrain.numeroterDistance(Main.terrain.getFinishBox());
		}
		for (Box b : Main.terrain.getBox())
		{
			if (b.getRange() == 0)
			{
				g.setColor(Color.yellow);
			}
			if (b.getRange() == 1)
			{
				g.setColor(Color.orange);
			}
			if (b.getRange() % 2 == 0)
			{
				g.setColor(Color.blue);
			}
			if (b.getRange() % 3 == 0)
			{
				g.setColor(Color.green);
			}
			if (b.getRange() % 4 == 0)
			{
				g.setColor(Color.orange);
			}
			if (b.getRange() % 5 == 0)
			{
				g.setColor(Color.pink);
			}
			if (b.getRange() % 6 == 0)
			{
				g.setColor(Color.gray);
			}
			g.setFont(new Font("BOLD", 10, 10));
			if (b.getRange() == -2)
			{
				g.setColor(Color.red);
				g.setFont(new Font("BOLD", 10, 10));
			}
			g.drawString(Integer.toString(b.getRange()), b.getCoordX(), b.getCoordY());
		}
		g.setColor(Color.white);
		if (Main.terrain.getBarriers().size() != 0)
		{
			for (Barrier o : Main.terrain.getBarriers())
			{
				g.fillPolygon(o.getBox().getTabCoordX(), o.getBox().getTabCoordY(), Main.terrain.getNbSidePolygon());
			}
		}
		/*
		 * // Dessine l'arrivée si celle ci existe g.setColor(Color.red); if
		 * (Main.arrivee.getSaCase() != null) {
		 * g.fillOval(Main.arrivee.getSaCase().getCoordX() - 10,
		 * Main.arrivee.getSaCase().getCoordY() - 10, 20, 20); }
		 * 
		 * // Numérote les cases en fonction de l'arrivée cliquée if
		 * (Main.arrivee.getSaCase() != null) { System.out.println("56");
		 * Main.numeroterDistance(Main.arrivee); }
		 * 
		 * // Trouve le chemin s'il existe des monstres et que l'arrivée
		 * existe if (Main.lesMonstres.size() != 0 &&
		 * Main.arrivee.getSaCase() != null) { //Main.trouverChemin(); }
		 * 
		 * 
		 * 
		 * g.setColor(Color.green); if (Main.lesMonstres.size() != 0 &&
		 * Main.arrivee.getSaCase() != null) { //dessinerMonstre(g); }
		 * 
		 * // Dessine le spawn si celui ci existe g.setColor(Color.yellow);
		 * if (Main.spawn.getSaCase() != null) {
		 * g.fillPolygon(Main.spawn.getSaCase().getTabX(),
		 * Main.spawn.getSaCase().getTabY(), Main.NB_COTE_POLYGONE); }
		 * 
		 * 
		 * 
		 * g.setColor(Color.ORANGE); { for (Case c : Main.tabCase) { if
		 * (c.getTerrain() == "vinchon") { g.fillPolygon(c.getTabX(),
		 * c.getTabY(), Main.NB_COTE_POLYGONE); } }
		 * 
		 * }
		 */
	}
	public void mousePressed(MouseEvent e)
	{
		System.out.println("ok");
		clicX = e.getX();
		clicY = e.getY();
		// Clic bouton gauche
		// Monstres
		/*
		 * if (e.getButton() == MouseEvent.BUTTON1) { int i = 0; boolean
		 * trouve = false; while (i < Main.tabCase.size() && !trouve) { Case
		 * c = Main.tabCase.get(i); if (Math.sqrt(Math.pow(clicX -
		 * c.getCoordX(), 2) + Math.pow(clicY - c.getCoordY(), 2)) < 2 *
		 * Main.DEMI_RAYON_POLYGONE) {
		 * 
		 * if(Main.lesObstacles.size()!=0) {
		 * if(c.getTerrain()==Case.TERRAIN_OBSTACLE) {
		 * System.out.println(Main.lesObstacles.size());
		 * Main.supprimerObstacle(c); } }
		 * 
		 * c.setTerrain(Case.TERRAIN_TERRE); Main.spawn.setSaCase(c); trouve
		 * = true; } i++; } }
		 */
		// Clic bouton milieu
		// Arrivée
		if (e.getButton() == MouseEvent.BUTTON2)
		{
			System.out.println("ok");
			int i = 0;
			boolean trouve = false;
			while (i < Main.terrain.getBox().size() && !trouve)
			{
				Box b = Main.terrain.getBox().get(i);
				if (Math.sqrt(Math.pow(clicX - b.getCoordX(), 2) + Math.pow(clicY - b.getCoordY(), 2)) < 2 * Main.terrain
				            .getHalfRadiusPolygon())
				{
					Main.terrain.setFinishBox(b);
					trouve = true;
				}
				i++;
			}
		}
		// Clic bouton droit
		// Obstacles
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			int i = 0;
			boolean trouve = false;
			while (i < Main.terrain.getBox().size() && !trouve)
			{
				Box b = Main.terrain.getBox().get(i);
				if (Math.sqrt(Math.pow(clicX - b.getCoordX(), 2) + Math.pow(clicY - b.getCoordY(), 2)) < 2 * Main.terrain
				            .getHalfRadiusPolygon())
				{
					b.setFieldType(Box.FIELD_BARRIER);
					Main.terrain.getBarriers().add(new Barrier(b));
					trouve = true;
				}
				i++;
			}
		}
		repaint();
	}
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			if (Main.terrain.getBarriers().size() != 0)
			{
				Main.terrain.getBarriers().get(Main.terrain.getBarriers().size() - 1).getBox().setFieldType(Box.FIELD_EARTH);
				Main.terrain.getBarriers().remove(Main.terrain.getBarriers().size() - 1);
			}
		}
		/*
		 * if (e.getKeyCode() == KeyEvent.VK_M) { Main.lesMonstres.add(new
		 * Monstre(Main.spawn.getSaCase())); }
		 */
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			afficherQuadrillage = !afficherQuadrillage;
		}
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
