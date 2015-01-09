package awakening.moi_affichage;

import javax.swing.JFrame;

public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private Panel pan=new Panel();
	public Fenetre(int x, int y)
	{
		int width = x;
		int height = y;
		this.setSize(width, height);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(pan);
		this.setVisible(true);
		this.addKeyListener(pan);
		this.addMouseListener(pan);
	}
}
