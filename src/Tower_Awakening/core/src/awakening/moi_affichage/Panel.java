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
import awakening.toolshop.monster.FlyingMonster;
import awakening.toolshop.monster.Monster;

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
		

		g.setColor(Color.red);
		if (Main.terrain.getFinishBox() != null)
		{
			g.fillOval(Main.terrain.getFinishBox().getCoordX() - 10, Main.terrain.getFinishBox().getCoordY() - 10, 20, 20);
			Main.terrain.numeroterDistance(Main.terrain.getFinishBox());
		}
		

		g.setColor(Color.yellow);
		if (Main.terrain.getSpawns().size() != 0)
		{
			for (Box b : Main.terrain.getSpawns())
			{
				g.fillPolygon(b.getTabCoordX(), b.getTabCoordY(), Main.terrain.getNbSidePolygon());
			}
		}
		g.setColor(Color.green);
		if (Main.terrain.getMonsters() != null)
		{
			for (Monster m : Main.terrain.getMonsters())
			{
				g.fillOval(m.getBox().getCoordX() - 10, m.getBox().getCoordY() - 10, 20, 20);
			}
		}

		if (Main.terrain.getMonsters().size() != 0 && Main.terrain.getFinishBox() != null && Main.terrain.getSpawns().size()!=0)
		{
			Main.terrain.findPathMonster();
			for (Monster m : Main.terrain.getMonsters())
			{		g.setColor(Color.black);
				for (Box b : Main.terrain.getBox())
				{
					g.fillPolygon(b.getTabCoordX(), b.getTabCoordY(), Main.terrain.getNbSidePolygon());
				}		g.setColor(Color.orange);
				for (Box b : m.getPath())
				{
					g.fillPolygon(b.getTabCoordX(), b.getTabCoordY(), Main.terrain.getNbSidePolygon());
				}
			}
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
		if (afficherQuadrillage && Main.terrain.getBox().size() != 0)
		{
			for (Box b : Main.terrain.getBox())
			{
				g.drawPolygon(b.getTabCoordX(), b.getTabCoordY(), Main.terrain.getNbSidePolygon());
			}
		}
		g.setColor(Color.white);
		if (Main.terrain.getBarriers().size() != 0)
		{
			for (Barrier o : Main.terrain.getBarriers())
			{
				g.fillPolygon(o.getBox().getTabCoordX(), o.getBox().getTabCoordY(), Main.terrain.getNbSidePolygon());
			}
		}
	}
	public void mousePressed(MouseEvent e)
	{
		clicX = e.getX();
		clicY = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			int i = 0;
			boolean trouve = false;
			while (i < Main.terrain.getBox().size() && !trouve)
			{
				Box b = Main.terrain.getBox().get(i);
				if (Math.sqrt(Math.pow(clicX - b.getCoordX(), 2) + Math.pow(clicY - b.getCoordY(), 2)) < 2 * Main.terrain
				            .getHalfRadiusPolygon())
				{
					b.setFieldType(Box.FIELD_SPAWNS);
					Main.terrain.getSpawns().add(b);
					trouve = true;
				}
				i++;
			}
		}
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
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if (Main.terrain.getSpawns().size() != 0)
			{
				Main.terrain.getSpawns().get(Main.terrain.getSpawns().size() - 1).setFieldType(Box.FIELD_EARTH);
				Main.terrain.getSpawns().remove(Main.terrain.getSpawns().size() - 1);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_M)
		{
			Main.terrain.addMonster(new FlyingMonster());
		}
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			afficherQuadrillage = !afficherQuadrillage;
		}
		repaint();
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
}
