package ta.firegreen.creation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public final class AffichageFrame extends JInternalFrame {
	private final static String BTNTEXTURE = "Affichage Texture";
	private final static String BTNCOLOR = "Affichage Couleur";

	final ConfigurationFrame mainFrame;
	final JButton btnChangeDisplay;
	int rotX,rotY,rotZ,tX,tY,tZ=10;
	
	public AffichageFrame(final ConfigurationFrame mainFrame){
		super("Configuration affichage",true,false,false,false);
		this.mainFrame = mainFrame;
		this.setBounds(0, 0, 
				mainFrame.getContentPane().getWidth()/2, mainFrame.getContentPane().getHeight());
		Box mainPane = Box.createVerticalBox();
		this.setContentPane(mainPane);
		mainPane.setSize(500, 500);
		btnChangeDisplay = new JButton(BTNTEXTURE);
		btnChangeDisplay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeDisplay(btnChangeDisplay.getText().equals(BTNTEXTURE));
			}
			
		});
		mainPane.add(btnChangeDisplay);
		
		final JSlider slideX = new JSlider(JSlider.HORIZONTAL,-10,10,
				(int) mainFrame.c.posX);
		final JSlider slideY = new JSlider(JSlider.HORIZONTAL,-10,10,
				(int) mainFrame.c.posY);
		final JSlider slideZ = new JSlider(JSlider.HORIZONTAL,-10,10,
				(int) mainFrame.c.posZ);
		JLabel lblX = new JLabel("Translation X");
		JLabel lblY = new JLabel("Translation Y");
		JLabel lblZ = new JLabel("Translation Z");

		mainPane.add(lblX);mainPane.add(slideX);
		mainPane.add(lblY);mainPane.add(slideY);
		mainPane.add(lblZ);mainPane.add(slideZ);

		
		final JSlider slideRotX = new JSlider(JSlider.HORIZONTAL,-16,16,0);
		final JSlider slideRotY = new JSlider(JSlider.HORIZONTAL,-16,16,0);
		final JSlider slideRotZ = new JSlider(JSlider.HORIZONTAL,-16,16,0);

		slideRotX.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double d = distance(mainFrame.c.posZ,mainFrame.c.posY);
				if(d>0){
					double angle = Math.acos(mainFrame.c.posZ/d);
					angle += 0.1f * Math.signum(slideRotX.getValue()-rotX);
					mainFrame.c.posZ = (float) (Math.cos(angle)*d);
					mainFrame.c.posY = (float) (Math.sin(angle)*d);
					rotX=slideRotX.getValue();
				}
			}
			
		});
		slideRotY.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double d = distance(mainFrame.c.posX,mainFrame.c.posZ);
				if(d>0){
					double angle = Math.atan(mainFrame.c.posZ/mainFrame.c.posX);
					angle += 0.1f * Math.signum(slideRotY.getValue()-rotY);
					mainFrame.c.posX = (float) (Math.cos(angle)*d);
					mainFrame.c.posZ = (float) (Math.sin(angle)*d);
					rotY=slideRotY.getValue();
				}
			}
			
		});
		slideRotZ.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				double d = distance(mainFrame.c.posX,mainFrame.c.posY);
				if(d>0){
					double angle = Math.acos(mainFrame.c.posX/d);
					angle += 0.1f * Math.signum(slideRotZ.getValue()-rotZ);
					mainFrame.c.posY = (float) (Math.sin(angle)*d);
					mainFrame.c.posX = (float) (Math.cos(angle)*d);
					rotZ=slideRotZ.getValue();
				}
			}
			
		});
		
		slideX.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				mainFrame.c.posX+=Math.signum(slideX.getValue()-tX);
				mainFrame.c.cibleX=mainFrame.c.posX;
				tX=slideX.getValue();
			}
			
		});
		slideY.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				mainFrame.c.posY+=Math.signum(slideY.getValue()-tY);
				mainFrame.c.cibleY=mainFrame.c.posY;
				tY=slideY.getValue();
			}
			
		});
		slideZ.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				mainFrame.c.posZ+=Math.signum(slideZ.getValue()-tZ);
				tZ=slideZ.getValue();
			}
			
		});
		JLabel lblRotX = new JLabel("Rotation X");
		JLabel lblRotY = new JLabel("Rotation Y");
		JLabel lblRotZ = new JLabel("Rotation Z");

		mainPane.add(lblRotX);mainPane.add(slideRotX);
		mainPane.add(lblRotY);mainPane.add(slideRotY);
		mainPane.add(lblRotZ);mainPane.add(slideRotZ);
		
		for(Component c : getContentPane().getComponents())
		{
			c.setMaximumSize(new Dimension(getWidth(), 30));
			((JComponent)c).setAlignmentX(CENTER_ALIGNMENT);
		}

	
	}
	
	public void changeDisplay(boolean texture)
	{
		if(texture)
		{
			mainFrame.c.afficheTexture=true;
			btnChangeDisplay.setText(BTNCOLOR);
		}
		else
		{
			mainFrame.c.afficheTexture=false;
			btnChangeDisplay.setText(BTNTEXTURE);
		}
	}
	double distance(double x, double y) {
		  return Math.sqrt(x*x+y*y);
		}

}
