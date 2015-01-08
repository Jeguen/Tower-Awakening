package ta.firegreen.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;

import ta.shape3D.Triangle3D;
import ta.shape3D.mesh.MeshTA;

public final class ToolsFrame extends JInternalFrame {

	
	private static final long serialVersionUID = 1L;
	final ConfigurationFrame mainFrame;
	final JTable tableTriangle;
	final EditeTriangleFrame editeur;
	final static String modifierTriangle = "modifier Triangle";
	final static String modifierMesh = "modifier Mesh";
	MeshTA mesh;
	static interface ColorChangeListener{
		public void action();
	}
	@SuppressWarnings("serial")
	public static class ColorConfiguration extends JPanel implements MouseListener{
		Color lacouleur;
		private ColorChangeListener listener;
		private ConfigurationFrame cfParent;
				
		public ColorConfiguration(ConfigurationFrame cfParent)
		{
			this.lacouleur = Color.WHITE;
			this.addMouseListener(this);
			this.cfParent = cfParent;
		}
		void setColorChangeListener(ColorChangeListener listener)
		{
			this.listener = listener;
		}
		public void paintComponent(Graphics g)
		{
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(lacouleur);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		public void setColor(Color c)
		{
			this.lacouleur=c;
			if(listener!=null) listener.action();
			this.repaint();
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			GrilleCouleur gc = new GrilleCouleur(cfParent,this, 14);
			gc.setVisible(true);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			Graphics g = this.getGraphics();
			g.setColor(Color.BLUE);
			g.drawRect(1, 1, getWidth()-2, getHeight()-2);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			this.repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@SuppressWarnings("serial")
	public static class EditeTriangleFrame extends JInternalFrame{

		final ToolsFrame tf;
		final JTextField x1 = new JTextField();
		final JTextField y1 = new JTextField();
		final JTextField z1 = new JTextField();
		final JTextField x2 = new JTextField();
		final JTextField y2 = new JTextField();
		final JTextField z2 = new JTextField();
		final JTextField x3 = new JTextField();
		final JTextField y3 = new JTextField();
		final JTextField z3 = new JTextField();
		final JSlider xT1 =  new JSlider(JSlider.HORIZONTAL,0,20,0);
		final JSlider yT1 =  new JSlider(JSlider.HORIZONTAL,0,20,0);
		final JSlider xT2 =  new JSlider(JSlider.HORIZONTAL,0,20,10);
		final JSlider yT2 =  new JSlider(JSlider.HORIZONTAL,0,20,0);
		final JSlider xT3 =  new JSlider(JSlider.HORIZONTAL,0,20,0);
		final JSlider yT3 =  new JSlider(JSlider.HORIZONTAL,0,20,10);
		final ColorConfiguration c1;
		final ColorConfiguration c2;
		final ColorConfiguration c3;

		public static abstract class docListnener implements DocumentListener
		{
			@Override
			public void removeUpdate(DocumentEvent e) {}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				insertUpdate(e);
			}
		}
		
		public EditeTriangleFrame(final ToolsFrame tf)
		{
			super("Triangle Editeur");
			this.tf=tf;
			tf.mainFrame.getContentPane().add(this);
			c1 = new ColorConfiguration(tf.mainFrame);
			c2 = new ColorConfiguration(tf.mainFrame);
			c3 = new ColorConfiguration(tf.mainFrame);

			this.setBounds((int)tf.mainFrame.getWidth()/2-300, 20,500,tf.mainFrame.getHeight()-80);
			final JLabel 
				  lblp1 = new JLabel("Points 1 "), lblp2 = new JLabel("Points 2 "), lblp3 = new JLabel("Points 3"),
				  lblpT1 = new JLabel("Points Texture 1 "), lblpT2 = new JLabel("Points Texture 2 "),
				  lblpT3 = new JLabel("Points Texture 3");
			int w = this.getWidth()/5;
			x1.setPreferredSize(new Dimension(w,25));
			y1.setPreferredSize(new Dimension(w,25));
			z1.setPreferredSize(new Dimension(w,25));
			x2.setPreferredSize(new Dimension(w,25));
			y2.setPreferredSize(new Dimension(w,25));
			z2.setPreferredSize(new Dimension(w,25));
			x3.setPreferredSize(new Dimension(w,25));
			y3.setPreferredSize(new Dimension(w,25));
			z3.setPreferredSize(new Dimension(w,25));
			w/=2;
			c1.setPreferredSize(new Dimension(w,25));
			c2.setPreferredSize(new Dimension(w,25));
			c3.setPreferredSize(new Dimension(w,25));

			Box point1 = Box.createHorizontalBox();
			point1.add(new JLabel("X : "));point1.add(x1);
			point1.add(new JLabel("Y : "));point1.add(y1);
			point1.add(new JLabel("Z : "));point1.add(z1);
			
			Box point2 = Box.createHorizontalBox();
			point2.add(new JLabel("X : "));point2.add(x2);
			point2.add(new JLabel("Y : "));point2.add(y2);
			point2.add(new JLabel("Z : "));point2.add(z2);
			
			Box point3 = Box.createHorizontalBox();
			point3.add(new JLabel("X : "));point3.add(x3);
			point3.add(new JLabel("Y : "));point3.add(y3);
			point3.add(new JLabel("Z : "));point3.add(z3);
			
			Box pointT1 = Box.createHorizontalBox();
			pointT1.add(new JLabel("X : "));pointT1.add(xT1);
			pointT1.add(new JLabel("Y : "));pointT1.add(yT1);
			
			Box pointT2 = Box.createHorizontalBox();
			pointT2.add(new JLabel("X : "));pointT2.add(xT2);
			pointT2.add(new JLabel("Y : "));pointT2.add(yT2);
			
			Box pointT3 = Box.createHorizontalBox();
			pointT3.add(new JLabel("X : "));pointT3.add(xT3);
			pointT3.add(new JLabel("Y : "));pointT3.add(yT3);
			
			JButton btnValider = new JButton("Valider");
			btnValider.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					tf.tableTriangle.repaint();
					setVisible(false);
				}
			});
			this.getContentPane().add(btnValider, BorderLayout.SOUTH);
			
			Box mainPane = Box.createVerticalBox();
			this.getContentPane().add(mainPane,BorderLayout.CENTER);

			JPanel configuration = new JPanel(new GridBagLayout());
			GridBagConstraints cases = new GridBagConstraints();
			cases.fill = GridBagConstraints.BOTH;
			cases.gridy=0;cases.gridx=0;
			configuration.add(lblp1,cases);
			cases.gridy=1;
			configuration.add(point1,cases);
			cases.gridy=2;
			configuration.add(lblp2,cases);
			cases.gridy=3;
			configuration.add(point2,cases);
			cases.gridy=4;
			configuration.add(lblp3,cases);
			cases.gridy=5;
			configuration.add(point3,cases);
			cases.gridy=1;cases.gridx=1;
			configuration.add(c1,cases);
			cases.gridy=3;
			configuration.add(c2,cases);
			cases.gridy=5;
			configuration.add(c3,cases);
			
			JPanel configurationTex = new JPanel(new GridBagLayout());
			cases.gridx=0;
			cases.gridy=1;
			configurationTex.add(lblpT1,cases);
			cases.gridy=2;
			configurationTex.add(pointT1,cases);
			cases.gridy=3;
			configurationTex.add(lblpT2,cases);
			cases.gridy=4;
			configurationTex.add(pointT2,cases);
			cases.gridy=5;
			configurationTex.add(lblpT3,cases);
			cases.gridy=6;
			configurationTex.add(pointT3,cases);
			mainPane.add(configuration);
			mainPane.add(configurationTex);
		}
		
		public void setTriangle(final Triangle3D t)
		{
			if(t==null) System.out.println("ouech! mainFrame.c'est nul");
			x1.setDocument(new PlainDocument());
			x1.setText(String.valueOf(t.getPoint1().x));
			x1.getDocument().addDocumentListener(new docListnener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if(!x1.getText().isEmpty()){
						t.getPoint1().x = Float.parseFloat(corrigeFloatString(x1.getText()));
						tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint1().x = t.getPoint1().x;
					}
				}
				
			});
			y1.setDocument(new PlainDocument());
			y1.setText(String.valueOf(t.getPoint1().y));
			y1.getDocument().addDocumentListener(new docListnener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if(!y1.getText().isEmpty()){
						t.getPoint1().y = Float.parseFloat(corrigeFloatString(y1.getText()));
						tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint1().y = t.getPoint1().y;
					}
				}
			});
			z1.setDocument(new PlainDocument());
			z1.setText(String.valueOf(t.getPoint1().z));
			z1.getDocument().addDocumentListener(new docListnener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if(!z1.getText().isEmpty()){
						t.getPoint1().z = Float.parseFloat(corrigeFloatString(z1.getText()));
						tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint1().z = t.getPoint1().z;
					}
				}
			});
			x2.setDocument(new PlainDocument());
			x2.setText(String.valueOf(t.getPoint2().x));
			x2.getDocument().addDocumentListener(new docListnener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if(!x2.getText().isEmpty()){
						t.getPoint2().x = Float.parseFloat(corrigeFloatString(x2.getText()));
						tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint2().x = t.getPoint2().x;
					}
				}
			});
			y2.setDocument(new PlainDocument());
			y2.setText(String.valueOf(t.getPoint2().y));
			y2.getDocument().addDocumentListener(new docListnener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!y2.getText().isEmpty()){
							t.getPoint2().y = Float.parseFloat(corrigeFloatString(y2.getText()));
							tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint2().y = t.getPoint2().y;
						}
					}
				});
			z2.setDocument(new PlainDocument());
			z2.setText(String.valueOf(t.getPoint2().z));
			z2.getDocument().addDocumentListener(new docListnener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!z2.getText().isEmpty()){
							t.getPoint2().z = Float.parseFloat(corrigeFloatString(z2.getText()));
							tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint2().z = t.getPoint2().z;
						}
					}
				});
			x3.setDocument(new PlainDocument());
			x3.setText(String.valueOf(t.getPoint3().x));
			x3.getDocument().addDocumentListener(new docListnener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!x3.getText().isEmpty()){
							t.getPoint3().x = Float.parseFloat(corrigeFloatString(x3.getText()));
							tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint3().x = t.getPoint3().x;
						}
					}
				});
			y3.setDocument(new PlainDocument());
			y3.setText(String.valueOf(t.getPoint3().y));
			y3.getDocument().addDocumentListener(new docListnener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!y3.getText().isEmpty()){
							t.getPoint3().y = Float.parseFloat(corrigeFloatString(y3.getText()));
							tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint3().y = t.getPoint3().y;
						}

					}
				});
			z3.setDocument(new PlainDocument());
			z3.setText(String.valueOf(t.getPoint3().z));
			z3.getDocument().addDocumentListener(new docListnener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						if(!z3.getText().isEmpty()){
							t.getPoint3().z = Float.parseFloat(corrigeFloatString(z3.getText()));
							tf.mainFrame.c.trianglesSelected.getTriangle(0).getPoint3().z = t.getPoint3().z;
						}
					}
				});
			c1.setColorChangeListener(new ColorChangeListener() {
				
				@Override
				public void action() {
					float[] rgba = new float[4];
					c1.lacouleur.getColorComponents(rgba);
					t.setColor1(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));
				}
			});
			c1.setColor(new Color(t.getColor1().r,t.getColor1().g,t.getColor1().b,t.getColor1().a));
			c2.setColorChangeListener(new ColorChangeListener() {
				
				@Override
				public void action() {
					float[] rgba = new float[4];
					c2.lacouleur.getColorComponents(rgba);
					t.setColor2(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));	
				}
			});	
			c2.setColor(new Color(t.getColor2().r,t.getColor2().g,t.getColor2().b,t.getColor2().a));
			c3.setColorChangeListener(new ColorChangeListener() {
				
				@Override
				public void action() {
					float[] rgba = new float[4];
					c3.lacouleur.getColorComponents(rgba);
					t.setColor3(new com.badlogic.gdx.graphics.Color(rgba[0],rgba[1],rgba[2],1));
				}
			});
			c3.setColor(new Color(t.getColor3().r,t.getColor3().g,t.getColor3().b,t.getColor3().a));
			if(xT1.getChangeListeners().length>0)
				xT1.removeChangeListener(xT1.getChangeListeners()[0]);
			xT1.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture1().x = xT1.getValue()/10f;
				}
			});
			xT1.setValue((int) (t.getPointTexture1().x*10));
			if(yT1.getChangeListeners().length>0)
				yT1.removeChangeListener(yT1.getChangeListeners()[0]);
			yT1.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture1().y = yT1.getValue()/10f;
				}
			});
			yT1.setValue((int) (t.getPointTexture1().y*10));
			if(xT2.getChangeListeners().length>0)
				xT2.removeChangeListener(xT2.getChangeListeners()[0]);
			xT2.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture2().x = xT2.getValue()/10f;
				}
			});
			xT2.setValue((int) (t.getPointTexture2().x*10));
			if(yT2.getChangeListeners().length>0)
				yT2.removeChangeListener(yT2.getChangeListeners()[0]);
			yT2.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture2().y = yT2.getValue()/10f;
				}
			});
			yT2.setValue((int) (t.getPointTexture2().y*10));
			if(xT3.getChangeListeners().length>0)
				xT3.removeChangeListener(xT3.getChangeListeners()[0]);
			xT3.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture3().x = xT3.getValue()/10f;
				}
			});
			xT3.setValue((int) (t.getPointTexture3().x*10));
			if(yT3.getChangeListeners().length>0)
				yT3.removeChangeListener(yT3.getChangeListeners()[0]);
			yT3.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					t.getPointTexture3().y = yT3.getValue()/10f;
				}
			});
			yT3.setValue((int) (t.getPointTexture3().y*10));
			this.repaint();
		}
	}

	
	public ToolsFrame(final ConfigurationFrame mainFrame) {
		
		this(mainFrame,new MeshTA());
	}

	@SuppressWarnings("serial")
	public ToolsFrame(final ConfigurationFrame mainFrame, final MeshTA meshToEdit)
	{
		super("Mesh Creator (" + meshToEdit +")",false,true,false,false);
		this.mesh = meshToEdit;
		this.mainFrame = mainFrame;
		mainFrame.getContentPane().add(this);
		mainFrame.c.toolFrameQueue.add(this);
		System.out.println(mainFrame.c.toolFrameQueue);
		mainFrame.toolConfig=this;
		mainFrame.c.mesh = mesh;
		editeur = new EditeTriangleFrame(this);
		this.setBounds(mainFrame.getContentPane().getWidth()/2, 0, 
				mainFrame.getContentPane().getWidth()/2, mainFrame.getContentPane().getHeight());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		tableTriangle = new JTable();
		tableTriangle.setModel(new DefaultTableModel(new Object[][]{},new String[]{"Triangles"}){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		for(Triangle3D t : mesh.getTriangles())
		{
			((DefaultTableModel)tableTriangle.getModel()).addRow(new Object[]{t});;
		}
		for(MeshTA m : mesh.getSousMesh())
		{
			((DefaultTableModel)tableTriangle.getModel()).addRow(new Object[]{m});;
		}
		this.getContentPane().add(tableTriangle, BorderLayout.CENTER);
		
		final JButton btnajouter = new JButton("Ajouter Triangle");
		btnajouter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final Triangle3D t = mesh.addTriangle();
				((DefaultTableModel)tableTriangle.getModel()).addRow(new Object[]{t});
			}
		});
		final JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] index = tableTriangle.getSelectedRows();
				if(index.length>0)
				{
					for(int i=index.length-1;i>=0;i--)
					{
							Object o = tableTriangle.getModel().getValueAt(index[i], 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								mesh.removeTriangle((Triangle3D)o);
							
							}
							else
							{
								mesh.removeSousMesh((MeshTA)o);
							}
							((DefaultTableModel)tableTriangle.getModel()).removeRow(index[i]);
					}
					tableTriangle.repaint();

				}
			}
		});
		final JButton btnmodifier = new JButton(modifierTriangle);
		ActionListener modifyAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tableTriangle.getSelectedRow()>=0)
				{
					Object o = tableTriangle.getModel().getValueAt(tableTriangle.getSelectedRow(), 0);
					if(o.getClass().equals(Triangle3D.class))
					{
						mainFrame.c.setSelectedTriangles(new Triangle3D[]{(Triangle3D)o});
						editeur.setTriangle((Triangle3D)o);
						editeur.setVisible(true);
					}
					else
					{
						MeshTA mesh = (MeshTA)o;
						openSubFrame(mesh);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Selectionnez un triangle plz");
				}
			}
		};
		btnmodifier.addActionListener(modifyAction);
		tableTriangle.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] index = tableTriangle.getSelectedRows();
				LinkedList<Triangle3D> trianglesSelectionnes = new LinkedList<Triangle3D>();
				if(index.length>0)
				{
					for(int i : index)
					{
							Object o = tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								trianglesSelectionnes.add((Triangle3D)o);
							}
							else
							{
								MeshTA m = (MeshTA)o;
								for(Triangle3D t : m.getTriangles())
								{
									trianglesSelectionnes.add(t);
								}
							}
					}
					mainFrame.c.setSelectedTriangles(trianglesSelectionnes);
					if(index.length==1)
					{
						Object o = tableTriangle.getModel().getValueAt(index[0], 0);
						if(o.getClass().equals(Triangle3D.class))
						{
							btnmodifier.setText(modifierTriangle);
						}
						else
						{
							btnmodifier.setText(modifierMesh);
						}
					}
				}
				
				else
					mainFrame.c.unsetSelectedTriangle();
			}
		});
		Box bbox = Box.createVerticalBox();
		bbox.add(btnajouter);
		bbox.add(btnmodifier);
		bbox.add(btnSupprimer);

		btnmodifier.setMaximumSize(new Dimension(getWidth(), 30));
		btnajouter.setMaximumSize(new Dimension(getWidth(), 30));
		btnSupprimer.setMaximumSize(new Dimension(getWidth(), 30));

		this.getContentPane().add(bbox, BorderLayout.SOUTH);
		
		{
			final ToolsFrame moi = this;
			this.addInternalFrameListener(new InternalFrameListener() {
				
				@Override
				public void internalFrameOpened(InternalFrameEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void internalFrameIconified(InternalFrameEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void internalFrameDeiconified(InternalFrameEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void internalFrameDeactivated(InternalFrameEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void internalFrameClosing(InternalFrameEvent arg0) {		
				}
				
				@Override
				public void internalFrameClosed(InternalFrameEvent arg0) {
					if(!moi.isClosed)
					{
						moi.isClosed=true;
						System.out.println(mainFrame.c.toolFrameQueue.size());
						System.out.println(mainFrame.c.toolFrameQueue.peek().getTitle() +" - "+ moi.getTitle());
						if(mainFrame.c.toolFrameQueue.size()>2){
							while(!mainFrame.c.toolFrameQueue.peek().equals(moi) && mainFrame.c.toolFrameQueue.size()>2)
							{
								mainFrame.c.toolFrameQueue.peek().dispose();
								mainFrame.c.toolFrameQueue.pop();
							}
							mainFrame.c.toolFrameQueue.peek().dispose();
							mainFrame.c.toolFrameQueue.pop();
							mainFrame.c.toolFrameQueue.peek().setVisible(true);
						}
					}
				}
				
				@Override
				public void internalFrameActivated(InternalFrameEvent arg0) {
					setEnabled(true);
					mainFrame.toolConfig=moi;
					mainFrame.c.mesh = moi.mesh;
				}
			});
		}
		this.setVisible(true);
	}
	
	public void openSubFrame(MeshTA m)
	{
		this.setEnabled(false);
		ToolsFrame tf = new ToolsFrame(mainFrame,m);
		tf.setLocation(this.getX()-40, 0);
		tf.setVisible(true);
	}
	
	private static String corrigeFloatString(String s)
	{
		if(s.length()<=1)
			if(s.charAt(0)<48 || s.charAt(0)>57)
				return "0";
		for(int cpt=0; cpt<s.length();cpt++)
		{
			if(s.charAt(cpt)!= '-' && s.charAt(cpt)!='+' && s.charAt(cpt)!='.' && (s.charAt(cpt)<48 || s.charAt(cpt)>57))
				s.replace(s.charAt(cpt), '0');
		}
		return s;
	}

}
