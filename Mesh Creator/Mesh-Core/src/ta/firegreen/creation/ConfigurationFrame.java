package ta.firegreen.creation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import ta.firegreen.creation.ToolsFrame.ColorChangeListener;
import ta.firegreen.creation.ToolsFrame.ColorConfiguration;
import ta.shape3D.Animator;
import ta.shape3D.Triangle3D;
import ta.shape3D.mesh.MeshTA;

@SuppressWarnings("serial")
public class ConfigurationFrame extends JFrame {
	creator c;
	AffichageFrame dispConfig;
	ToolsFrame toolConfig;
	JDesktopPane mainPane;
	static JFileChooser imgDialog;
	static JFileChooser mtaDialog;
	public LinkedList<Object> copyList = new LinkedList<Object>();
	private boolean animating=false;

	public static class TransformFrame extends JInternalFrame{
		private float tX,tY,tZ,rX,rY,rZ,hX,hY,hZ,h;
		public TransformFrame(final ConfigurationFrame cf)
		{
			super("Transformation " + cf.c.mesh,false,true,false,false);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setBounds(0, 50, cf.getWidth()-100, 400);
			cf.getContentPane().add(this);
			this.setContentPane(Box.createVerticalBox());
			this.getContentPane().add(new JLabel("Translation X"));
			final JSlider translationX = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(translationX);
			this.getContentPane().add(new JLabel("Translation Y"));
			final JSlider translationY = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(translationY);
			this.getContentPane().add(new JLabel("Translation Z"));
			final JSlider translationZ = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(translationZ);
			this.getContentPane().add(new JLabel("RotationX"));
			final JSlider rotationX = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(rotationX);
			this.getContentPane().add(new JLabel("RotationY"));
			final JSlider rotationY = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(rotationY);
			this.getContentPane().add(new JLabel("RotationZ"));
			final JSlider rotationZ = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(rotationZ);
			this.getContentPane().add(new JLabel("Hometethie"));
			final JSlider hometethie = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(hometethie);
			this.getContentPane().add(new JLabel("HometethieX"));
			final JSlider hometethieX = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(hometethieX);
			this.getContentPane().add(new JLabel("HometethieY"));;
			final JSlider hometethieY = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(hometethieY);
			this.getContentPane().add(new JLabel("HometethieZ"));
			final JSlider hometethieZ = new JSlider(JSlider.HORIZONTAL,-20,20,0);
			this.getContentPane().add(hometethieZ);
			translationX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0){
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.translate(Math.signum(translationX.getValue()-tX)*0.5f,0,0);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateX(Math.signum(translationX.getValue()-tX)*0.5f);
							
							}
							else
							{
								((MeshTA)o).translate(Math.signum(translationX.getValue()-tX)*0.5f,0,0);
							}
						}
					}
					tX = translationX.getValue();
				}
			});
			translationY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.translate(0,Math.signum(translationY.getValue()-tY)*0.5f,0);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateY(Math.signum(translationY.getValue()-tY)*0.5f);
							
							}
							else
							{
								((MeshTA)o).translate(0,Math.signum(translationY.getValue()-tY)*0.5f,0);
							}
						}
					}
					tY = translationY.getValue();
				}
			});
			translationZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.translate(0,0,Math.signum(translationZ.getValue()-tZ)*0.5f);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateZ(Math.signum(translationZ.getValue()-tZ)*0.5f);
							
							}
							else
							{
								((MeshTA)o).translate(0,0,Math.signum(translationZ.getValue()-tZ)*0.5f);
							}
						}
					}
					tZ = translationZ.getValue();
				}
			});
			rotationZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.rotate(0,0,Math.signum(rotationZ.getValue()-rZ)*0.1f);
					rZ = rotationZ.getValue();
				}
			});
			rotationX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.rotate(Math.signum(rotationX.getValue()-rX)*0.1f,0,0);
					rX = rotationX.getValue();
				}
			});
			rotationY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.rotate(0,Math.signum(rotationY.getValue()-rY)*0.1f,0);
					rY = rotationY.getValue();
				}
			});
			hometethie.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.homethetie((1+Math.signum(hometethie.getValue()-h)*0.2f));
					h= hometethie.getValue();
				}
			});
			hometethieX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.homethetieX((1+Math.signum(hometethieX.getValue()-hX)*0.2f));
					hX= hometethieX.getValue();
				}
			});
			hometethieY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.homethetieY((1+Math.signum(hometethieY.getValue()-hY)*0.2f));
					hY= hometethieY.getValue();
				}
			});
			hometethieZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					cf.c.mesh.homethetieZ((1+Math.signum(hometethieZ.getValue()-hZ)*0.2f));
					hZ= hometethieZ.getValue();
				}
			});
		}
	}
	
	public static class AnimationFrame extends JInternalFrame{
		final JTextField rx = new JTextField();
		final JTextField ry = new JTextField();
		final JTextField rz = new JTextField();
		final JTextField tx = new JTextField();
		final JTextField ty = new JTextField();
		final JTextField tz = new JTextField();
		final JTextField interval = new JTextField();
		
		public AnimationFrame(final ConfigurationFrame cf)
		{
			super("Configuration Animation");
			this.setBounds(50,50,cf.getWidth()/2,cf.getHeight()/2);
			cf.getContentPane().add(this);
			JButton btnValider = new JButton("Valider");
			btnValider.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					cf.c.mesh.animationTX(Float.parseFloat(tx.getText()));
					cf.c.mesh.animationTY(Float.parseFloat(ty.getText()));
					cf.c.mesh.animationTZ(Float.parseFloat(tz.getText()));
					cf.c.mesh.animationRX(Float.parseFloat(rx.getText()));
					cf.c.mesh.animationRY(Float.parseFloat(ry.getText()));
					cf.c.mesh.animationRZ(Float.parseFloat(rz.getText()));
					cf.c.animateur.setInterval(Integer.parseInt(interval.getText()));
					dispose();
				}
			});
			this.getContentPane().add(btnValider, BorderLayout.SOUTH);
			JPanel configuration = new JPanel(new GridBagLayout());
			GridBagConstraints cases = new GridBagConstraints();
			tx.setText(String.valueOf(cf.c.mesh.getAnimationTX()));
			ty.setText(String.valueOf(cf.c.mesh.getAnimationTY()));
			tz.setText(String.valueOf(cf.c.mesh.getAnimationTZ()));
			rx.setText(String.valueOf(cf.c.mesh.getAnimationRX()));
			ry.setText(String.valueOf(cf.c.mesh.getAnimationRY()));
			rz.setText(String.valueOf(cf.c.mesh.getAnimationRZ()));
			interval.setText(String.valueOf(cf.c.animateur.getInterval()));
			cases.fill = GridBagConstraints.BOTH;
			cases.gridy=0;cases.gridx=0;
			configuration.add(new JLabel("Interval"),cases);
			cases.gridy=1;
			configuration.add(new JLabel("Translation X"),cases);
			cases.gridy=2;
			configuration.add(new JLabel("Translation Y"),cases);
			cases.gridy=3;
			configuration.add(new JLabel("Translation Z"),cases);
			cases.gridy=4;
			configuration.add(new JLabel("Rotation X"),cases);
			cases.gridy=5;
			configuration.add(new JLabel("Rotation Y"),cases);
			cases.gridy=6;
			configuration.add(new JLabel("Rotation Z"),cases);
			cases.gridy=0;cases.gridx=1;
			Box interBox = Box.createHorizontalBox();
			interBox.add(interval); interBox.add(new JLabel("millisecondes"));
			configuration.add(interBox,cases);
			cases.gridy=1;
			configuration.add(tx,cases);
			cases.gridy=2;
			configuration.add(ty,cases);
			cases.gridy=3;
			configuration.add(tz,cases);
			cases.gridy=4;
			configuration.add(rx,cases);
			cases.gridy=5;
			configuration.add(ry,cases);
			cases.gridy=6;
			configuration.add(rz,cases);
			this.getContentPane().add(configuration,BorderLayout.CENTER);
		}
	}
	
	public ConfigurationFrame(final creator c){
		super("Configuration");
		this.c = c;
		this.setBounds(0, c.desktopDimension.height/4, 
				c.desktopDimension.width/2, (int)(c.desktopDimension.height/1.5));
		imgDialog = new JFileChooser();
		imgDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		imgDialog.setCurrentDirectory(new java.io.File("."));
		imgDialog.setAcceptAllFileFilterUsed(false);
	    imgDialog.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith("png") || f.getName().endsWith("jpg") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Images";
			}
	    	
	    });
		mtaDialog = new JFileChooser();
		mtaDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		mtaDialog.setCurrentDirectory(new java.io.File("."));
		mtaDialog.setAcceptAllFileFilterUsed(false);
	    mtaDialog.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith("mta") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Mesh TA";
			}
	    	
	    });
		final JMenuBar menu = new JMenuBar();
		final JMenu fichier = new JMenu("Fichier");
		final JMenuItem saveImg = new JMenuItem("Save as image...");
		final JMenuItem saveMesh = new JMenuItem("Save as MeshTA...");
		final JMenuItem addMesh = new JMenuItem("Add MeshTA...");
		final JMenuItem loadMta = new JMenuItem("Load MeshTA...");
		final JMenuItem reinit = new JMenuItem("reinitialize");
		final JMenu edition = new JMenu("Edition");
		final JMenuItem deselectionner = new JMenuItem("Deselect");
		final JMenuItem regroup = new JMenuItem("Regroup ");
		final JMenuItem transform = new JMenuItem("Transformation...");
		final JMenuItem setColor = new JMenuItem("Change Color....");
		final JMenuItem setTexture = new JMenuItem("Set Texture...");
		final JMenuItem copy = new JMenuItem("Copy");
		final JMenuItem paste = new JMenuItem("Paste");
		final JMenu animation = new JMenu("Animation");
		final JMenuItem animationProperties = new JMenuItem("Reglage animation...");
		final JMenuItem animationStart = new JMenuItem("Start animation");
		{
			final ConfigurationFrame moi = this;
			saveImg.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(imgDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION)
						c.saveIMGFile=imgDialog.accept(imgDialog.getSelectedFile()) ? imgDialog.getSelectedFile() :
							new File(imgDialog.getSelectedFile()+".png");
				}
	
		});
		
		saveMesh.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mtaDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION)
					c.mesh.save(mtaDialog.getSelectedFile().getName().endsWith(".mta") ? mtaDialog.getSelectedFile() :
						new File(mtaDialog.getSelectedFile().getName()+".mta"));
			}
			
		});
		reinit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				while(!c.toolFrameQueue.isEmpty())
				{
					c.toolFrameQueue.pop().dispose();
				}
				new ToolsFrame(moi);
				((DefaultTableModel)toolConfig.tableTriangle.getModel()).setRowCount(0);
			}
			
		});
		
		loadMta.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mtaDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION){
					c.fileMTA=mtaDialog.getSelectedFile();
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(c.nouveau!=null) new ToolsFrame(moi,c.nouveau);
					c.nouveau=null;
				}
			}
			
		});
		
	
		regroup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] index = toolConfig.tableTriangle.getSelectedRows();
				if(index.length>0)
				{
					MeshTA m = new MeshTA();
					for(int i=index.length-1;i>=0;i--)
					{
							Object o = toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								Triangle3D t = m.addTriangle();
								t.copy((Triangle3D)o);
								c.mesh.removeTriangle((Triangle3D)o);
							
							}
							else
							{
								MeshTA sousM = m.addSousMesh();
								sousM.copy((MeshTA)o);
								c.mesh.removeSousMesh((MeshTA)o);
							}
							((DefaultTableModel)toolConfig.tableTriangle.getModel()).removeRow(i);

					}
					((DefaultTableModel)toolConfig.tableTriangle.getModel()).addRow(new Object[]{m});
					toolConfig.tableTriangle.getSelectionModel().clearSelection();
					toolConfig.tableTriangle.repaint();

				}
			}
		});
		
		deselectionner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				toolConfig.tableTriangle.getSelectionModel().clearSelection();
			}
		});
		
		transform.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TransformFrame(moi).setVisible(true);
			}
		});
		
		addMesh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mtaDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION){
					c.fileMTA=mtaDialog.getSelectedFile();
					try {
						Thread.sleep(70);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(c.nouveau!=null)
					{
						MeshTA m = toolConfig.mesh.addSousMesh();
						m.copy(c.nouveau);
						m.copyFeatures(c.nouveau);
						((DefaultTableModel)toolConfig.tableTriangle.getModel()).addRow(new Object[]{m});
					}
					c.nouveau=null;
				}
			}
		});
		animationStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(animating)
				{
					c.animateur.arret();
					animationStart.setText("Start animation");
					animating=false;
					c.animateur = new Animator(c.animateur.getInterval());
				}
				else
				{
					c.animateur.addAnimable(c.mesh);
					c.animateur.start();
					animationStart.setText("Stop animation");
					animating=true;
				}
			}
		});
		animationProperties.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AnimationFrame(moi).setVisible(true);
			}
		});
		setColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final ColorConfiguration color = new ColorConfiguration(moi);
				final int[] index = toolConfig.tableTriangle.getSelectedRows();
				if(index.length==0)
					color.setColorChangeListener(new ColorChangeListener() {
						
						@Override
						public void action() {
							float[] rgba = new float[4];
							color.lacouleur.getColorComponents(rgba);
							System.out.println(rgba);
							c.mesh.changeAllColor(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));	
						}
					});
				else
					color.setColorChangeListener(new ColorChangeListener() {
						
						@Override
						public void action() {
							float[] rgba = new float[4];
							color.lacouleur.getColorComponents(rgba);
							for(int i : index)
							{
								Object o = toolConfig.tableTriangle.getModel().getValueAt(i, 0);
								if(o.getClass().equals(Triangle3D.class))
								{
									
									((Triangle3D)o)
									.changeAllColor(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));
								
								}
								else
								{
									((MeshTA)o)
									.changeAllColor(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));
								}
							}
						}
					});
				GrilleCouleur gc = new GrilleCouleur(moi,color, 14);
				gc.setVisible(true);
			}
		});
		setTexture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (ConfigurationFrame.imgDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION) { 
					c.fileTexture = imgDialog.getSelectedFile();
					dispConfig.changeDisplay(true);
			      }
			}
		});
		copy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final int[] index = toolConfig.tableTriangle.getSelectedRows();
				if(index.length>0) copyList.clear();
				for(int i : index)
				{
					copyList.add(toolConfig.tableTriangle.getModel().getValueAt(i, 0));
				}
			}
			
		});
		paste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Object o : copyList)
				{
					if(o.getClass().equals(Triangle3D.class))
					{
						Triangle3D t = c.mesh.addTriangle();
						t.copy((Triangle3D)o);					
						((DefaultTableModel)toolConfig.tableTriangle.getModel()).addRow(new Object[]{t});
					}
					else
					{
						MeshTA sousM = c.mesh.addSousMesh();
						sousM.copy((MeshTA)o);
						((DefaultTableModel)toolConfig.tableTriangle.getModel()).addRow(new Object[]{sousM});
					}
				}
				toolConfig.tableTriangle.repaint();
			}
		});
		}
		edition.add(transform);
		edition.add(deselectionner);
		edition.add(regroup);
		edition.add(setColor);
		edition.add(setTexture);
		edition.add(copy);
		edition.add(paste);
		fichier.add(saveImg);
		fichier.add(saveMesh);
		fichier.add(reinit);
		fichier.add(loadMta);
		fichier.add(addMesh);
		animation.add(animationProperties);
		animation.add(animationStart);
		menu.add(fichier);
		menu.add(edition);
		menu.add(animation);
		this.setJMenuBar(menu);
		mainPane = new JDesktopPane();
		this.setContentPane(mainPane);
		this.setVisible(true);
		dispConfig = new AffichageFrame(this);
		new ToolsFrame(this);
		mainPane.add(dispConfig);
		dispConfig.setVisible(true);
		toolConfig.setVisible(true);
	}

}
