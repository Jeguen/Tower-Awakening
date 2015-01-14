 // Copyright © 2014, 2015 VINCENT Steeve, steeve.vincent@gmail.com
 
 // Licensed under the Apache License, Version 2.0 (the "License");
 // you may not use this file except in compliance with the License.
 // You may obtain a copy of the License at
 // 
 // http://www.apache.org/licenses/LICENSE-2.0
 // 
 // Unless required by applicable law or agreed to in writing, software
 // distributed under the License is distributed on an "AS IS" BASIS,
 // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 // See the License for the specific language governing permissions and
 // limitations under the License.

package ta.firegreen.creation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import awakening.modele.toolshop.tower.OffensivTower;

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
						cf.c.mesh.translate(Math.signum(translationX.getValue()-tX)*0.2f,0,0);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateX(Math.signum(translationX.getValue()-tX)*0.2f);
							
							}
							else
							{
								((MeshTA)o).translate(Math.signum(translationX.getValue()-tX)*0.2f,0,0);
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
						cf.c.mesh.translate(0,Math.signum(translationY.getValue()-tY)*0.2f,0);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateY(Math.signum(translationY.getValue()-tY)*0.2f);
							
							}
							else
							{
								((MeshTA)o).translate(0,Math.signum(translationY.getValue()-tY)*0.2f,0);
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
						cf.c.mesh.translate(0,0,Math.signum(translationZ.getValue()-tZ)*0.2f);
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								((Triangle3D)o).translateZ(Math.signum(translationZ.getValue()-tZ)*0.2f);
							
							}
							else
							{
								((MeshTA)o).translate(0,0,Math.signum(translationZ.getValue()-tZ)*0.2f);
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
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.homethetie((1+Math.signum(hometethie.getValue()-h)*0.2f));
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								Triangle3D t = ((Triangle3D)o);
								t.getPoint1().x *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint2().x *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint3().x *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint1().y *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint2().y *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint3().y *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint1().z *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint2().z *= (1+Math.signum(hometethie.getValue()-h)*0.2f);
								t.getPoint3().z *= (1+Math.signum(hometethie.getValue()-h)*0.2f);

							}
							else
							{
								((MeshTA)o).homethetie((1+Math.signum(hometethie.getValue()-h)*0.2f));
							}
							cf.toolConfig.tableTriangle.repaint();
						}
					}
					h= hometethie.getValue();
					
				}
			});
			hometethieX.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.homethetieX((1+Math.signum(hometethieX.getValue()-hX)*0.2f));
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								Triangle3D t = ((Triangle3D)o);
								t.getPoint1().x *= (1+Math.signum(hometethieX.getValue()-hX)*0.2f);
								t.getPoint2().x *= (1+Math.signum(hometethieX.getValue()-hX)*0.2f);
								t.getPoint3().x *= (1+Math.signum(hometethieX.getValue()-hX)*0.2f);

							}
							else
							{
								((MeshTA)o).homethetieX((1+Math.signum(hometethieX.getValue()-hX)*0.2f));
							}
							cf.toolConfig.tableTriangle.repaint();
						}
					}
					hX= hometethieX.getValue();
				}
			});
			hometethieY.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.homethetieY((1+Math.signum(hometethieY.getValue()-hY)*0.2f));
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								Triangle3D t = ((Triangle3D)o);
								t.getPoint1().y *= (1+Math.signum(hometethieY.getValue()-hY)*0.2f);
								t.getPoint2().y *= (1+Math.signum(hometethieY.getValue()-hY)*0.2f);
								t.getPoint3().y *= (1+Math.signum(hometethieY.getValue()-hY)*0.2f);

							}
							else
							{
								((MeshTA)o).homethetieY((1+Math.signum(hometethieY.getValue()-hY)*0.2f));
							}
							cf.toolConfig.tableTriangle.repaint();
						}
					}				
					hY= hometethieY.getValue();
				}
			});
			hometethieZ.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					int[] index = cf.toolConfig.tableTriangle.getSelectedRows();
					if(index.length==0)
					{
						cf.c.mesh.homethetieZ((1+Math.signum(hometethieZ.getValue()-hZ)*0.2f));
					}
					else
					{
						for(int i : index)
						{
							Object o = cf.toolConfig.tableTriangle.getModel().getValueAt(i, 0);
							if(o.getClass().equals(Triangle3D.class))
							{
								
								Triangle3D t = ((Triangle3D)o);
								t.getPoint1().z *= (1+Math.signum(hometethieZ.getValue()-hZ)*0.2f);
								t.getPoint2().z *= (1+Math.signum(hometethieZ.getValue()-hZ)*0.2f);
								t.getPoint3().z *= (1+Math.signum(hometethieZ.getValue()-hZ)*0.2f);

							}
							else
							{
								((MeshTA)o).homethetieZ((1+Math.signum(hometethieZ.getValue()-hZ)*0.2f));
							}
							cf.toolConfig.tableTriangle.repaint();
						}
					}			
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
	
	public static class SaveTowerFrame extends JInternalFrame{
		
		public SaveTowerFrame(final ConfigurationFrame cf)
		{
			super("Save Tower",false,true,false,false);
			this.setBounds(50,50,(int)(cf.getWidth()/1.2f),(int)(cf.getHeight()/1.7f));
			cf.getContentPane().add(this);
			JButton btnValider = new JButton("Enregistrer");
			final MeshTA nothing = new MeshTA();
			nothing.setName("Nothing");
			final JComboBox<MeshTA> level1List = new JComboBox<MeshTA>();
			final JComboBox<MeshTA> level2List = new JComboBox<MeshTA>();
			final JComboBox<MeshTA> level3List = new JComboBox<MeshTA>();
			level2List.addItem(nothing);
			level3List.addItem(nothing);
			for(ToolsFrame tf : cf.c.toolFrameQueue)
			{
				level1List.addItem(tf.mesh);
				level2List.addItem(tf.mesh);
				level3List.addItem(tf.mesh);
			}
			final ItemListener ActionListenerList1 = new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					cf.c.mesh = cf.c.toolFrameQueue.get(level1List.getSelectedIndex()).mesh;
				}
			};
			final ItemListener ActionListenerList2 = new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					cf.c.mesh = cf.c.toolFrameQueue.get(level2List.getSelectedIndex()-1).mesh;
				}
			};
			final ItemListener ActionListenerList3 = new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					cf.c.mesh = cf.c.toolFrameQueue.get(level3List.getSelectedIndex()-1).mesh;
				}
			};
			level1List.addItemListener(ActionListenerList1);
			level2List.addItemListener(ActionListenerList2);
			level3List.addItemListener(ActionListenerList3);

			final JTextField buildCost1 = new JTextField();
			final JTextField range1 = new JTextField();
			final JTextField speedA1 = new JTextField();
			final JTextField feature1 = new JTextField();
			final JTextField buildCost2 = new JTextField();
			final JTextField range2 = new JTextField();
			final JTextField speedA2 = new JTextField();
			final JTextField feature2 = new JTextField();
			final JTextField buildCost3 = new JTextField();
			final JTextField range3 = new JTextField();
			final JTextField speedA3 = new JTextField();
			final JTextField feature3 = new JTextField();
			
			
			this.getContentPane().add(btnValider, BorderLayout.SOUTH);
			JPanel mainPane = new JPanel(new GridBagLayout());
			GridBagConstraints cases = new GridBagConstraints();
			cases.fill = GridBagConstraints.BOTH;
			cases.gridy=0;cases.gridx=0;
			mainPane.add(new JLabel("Meshs"),cases);
			cases.gridy=1;
			mainPane.add(new JLabel("BuildCost"),cases);
			cases.gridy=2;
			mainPane.add(new JLabel("Range"),cases);
			cases.gridy=3;
			mainPane.add(new JLabel("Attaque frequency"),cases);
			cases.gridy=4;
			mainPane.add(new JLabel("Damage"),cases);
			cases.gridy=0;cases.gridx=1;
			mainPane.add(level1List,cases);
			cases.gridy=1;
			mainPane.add(buildCost1,cases);
			cases.gridy=2;
			mainPane.add(range1,cases);
			cases.gridy=3;
			mainPane.add(speedA1,cases);
			cases.gridy=4;
			mainPane.add(feature1,cases);
			cases.gridy=0;cases.gridx=2;
			mainPane.add(level2List,cases);
			cases.gridy=1;
			mainPane.add(buildCost2,cases);
			cases.gridy=2;
			mainPane.add(range2,cases);
			cases.gridy=3;
			mainPane.add(speedA2,cases);
			cases.gridy=4;
			mainPane.add(feature2,cases);
			cases.gridy=0;cases.gridx=3;
			mainPane.add(level3List,cases);
			cases.gridy=1;
			mainPane.add(buildCost3,cases);
			cases.gridy=2;
			mainPane.add(range3,cases);
			cases.gridy=3;
			mainPane.add(speedA3,cases);
			cases.gridy=4;
			mainPane.add(feature3,cases);
			
			int w = this.getWidth()/5;

			for (Component c :mainPane.getComponents())
				c.setPreferredSize(new Dimension(w,25));

			this.getContentPane().add(mainPane,BorderLayout.CENTER);
			
			Box titles = Box.createHorizontalBox();
			final JComboBox<String> type = new JComboBox<String>();
			type.addItem(OffensivTower.class.getName());
			titles.add(type);
			titles.add(new JLabel("Niveau 1"));
			titles.add(new JLabel("Niveau 2"));
			titles.add(new JLabel("Niveau 3"));
			w = this.getWidth()/4;
			for (Component c :titles.getComponents())
				c.setPreferredSize(new Dimension(w,25));
			this.getContentPane().add(titles,BorderLayout.NORTH);
			btnValider.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					cf.c.posX = 2.5f;
					cf.c.posY = 3f;
					cf.c.posZ = -0.5f;
					cf.c.cibleX = cf.c.cibleZ = 0;
					cf.c.cibleY = 1;
					cf.c.cam.update();
					if(type.getSelectedItem().equals(OffensivTower.class.getName()))
					{
						if(mtaDialog.showOpenDialog(cf) == JFileChooser.APPROVE_OPTION)
						{
							String file = mtaDialog.getSelectedFile().getName();
							if(!file.startsWith(
									mtaDialog.getCurrentDirectory().getAbsolutePath()))
								file = mtaDialog.getCurrentDirectory().getAbsolutePath()+"\\"+mtaDialog.getCurrentDirectory().getName()
								+"\\"+file;
							file = mtaDialog.getSelectedFile().getName().endsWith(".mta") ? 
									mtaDialog.getSelectedFile().getAbsolutePath() :
								mtaDialog.getSelectedFile().getAbsolutePath()+".mta";
							float buildCost = Float.parseFloat(ToolsFrame.corrigeFloatString(buildCost1.getText()));
							int range = (int)Float.parseFloat(ToolsFrame.corrigeFloatString(range1.getText()));
							int level = 1;
							float speedAttaque = Float.parseFloat(ToolsFrame.corrigeFloatString(speedA1.getText()));
							float damage = Float.parseFloat(ToolsFrame.corrigeFloatString(feature1.getText()));
							OffensivTower t = new OffensivTower((MeshTA)level1List.getSelectedItem(),
									0, buildCost, range, speedAttaque, level, damage);
							ActionListenerList1.itemStateChanged(new ItemEvent(level1List, 0, null, 0));
							cf.c.saveIMGFile = new File(
									mtaDialog.getSelectedFile().getParent()+"\\"+cf.c.mesh.toString() + "ImageModele.png");
							try {
								Thread.sleep(60);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(!((MeshTA)level2List.getSelectedItem()).toString().equals("Nothing"))
							{
								buildCost = Float.parseFloat(ToolsFrame.corrigeFloatString(buildCost2.getText()));
								range = (int)Float.parseFloat(ToolsFrame.corrigeFloatString(range2.getText()));
								level = 2;
								speedAttaque = Float.parseFloat(ToolsFrame.corrigeFloatString(speedA2.getText()));
								damage = Float.parseFloat(ToolsFrame.corrigeFloatString(feature2.getText()));
								OffensivTower t2 = new OffensivTower((MeshTA)level2List.getSelectedItem(),
										0, buildCost, range, speedAttaque, level, damage);
								ActionListenerList2.itemStateChanged(new ItemEvent(level2List, 0, null, 0));
								cf.c.saveIMGFile = new File(
										mtaDialog.getSelectedFile().getParent()+"\\"+cf.c.mesh.toString() + "ImageModele.png");
								try {
									Thread.sleep(60);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(!((MeshTA)level3List.getSelectedItem()).toString().equals("Nothing"))
								{
									buildCost = Float.parseFloat(ToolsFrame.corrigeFloatString(buildCost3.getText()));
									range = (int)Float.parseFloat(ToolsFrame.corrigeFloatString(range3.getText()));
									level = 3;
									speedAttaque = Float.parseFloat(ToolsFrame.corrigeFloatString(speedA3.getText()));
									damage = Float.parseFloat(ToolsFrame.corrigeFloatString(feature3.getText()));
									OffensivTower t3 = new OffensivTower((MeshTA)level3List.getSelectedItem(),
											0, buildCost, range, speedAttaque, level, damage);
									ActionListenerList3.itemStateChanged(new ItemEvent(level3List, 0, null, 0));
									cf.c.saveIMGFile = new File(
											mtaDialog.getSelectedFile().getParent()+"\\"+cf.c.mesh.toString() + "ImageModele.png");
									try {
										Thread.sleep(60);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									t2.setNextLevel(t3);
								}
								t.setNextLevel(t);
							}
							t.save(new File(file));
							dispose();
						}
					}
				}
			});

		}
	}
	
public static class SaveMonsterFrame extends JInternalFrame{
		
		
		public SaveMonsterFrame(final ConfigurationFrame cf)
		{
			super("Save Monster",false,true,false,false);
			this.setBounds(50,50,cf.getWidth()/2,cf.getHeight()/2);
			cf.getContentPane().add(this);
			JButton btnValider = new JButton("Enregistrer");
			btnValider.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					dispose();
				}
			});
			final JComboBox<MeshTA> level1List = new JComboBox<MeshTA>();
			final JTextField buildCost1 = new JTextField();
			final JTextField range1 = new JTextField();
			final JTextField speedA1 = new JTextField();
			
			
			this.getContentPane().add(btnValider, BorderLayout.SOUTH);
			JPanel mainPane = new JPanel(new GridBagLayout());
			GridBagConstraints cases = new GridBagConstraints();
			cases.fill = GridBagConstraints.BOTH;
			
			cases.gridy=0;cases.gridx=0;
			mainPane.add(new JLabel("Meshs"),cases);
			cases.gridy=1;
			mainPane.add(new JLabel("BuildCost"),cases);
			cases.gridy=2;
			mainPane.add(new JLabel("Range"),cases);
			cases.gridy=3;
			mainPane.add(new JLabel("Attaque frequency"),cases);
			cases.gridy=0;cases.gridx=1;
			mainPane.add(level1List,cases);
			cases.gridy=1;
			mainPane.add(buildCost1,cases);
			cases.gridy=2;
			mainPane.add(range1,cases);
			cases.gridy=3;
			mainPane.add(speedA1,cases);
			int w = this.getWidth()/4;
			for (Component c :mainPane.getComponents())
				c.setPreferredSize(new Dimension(w,25));
			this.getContentPane().add(mainPane,BorderLayout.CENTER);
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
		final JMenuItem setName = new JMenuItem("Set name...");
		final JMenuItem saveImg = new JMenuItem("Save as image...");
		final JMenuItem saveMesh = new JMenuItem("Save as MeshTA...");
		final JMenuItem saveMonster = new JMenuItem("Save as Monster...");
		final JMenuItem saveTower = new JMenuItem("Save as Tower...");
		final JMenuItem saveField = new JMenuItem("Save a Field...");

		final JMenuItem addMesh = new JMenuItem("Add MeshTA...");
		final JMenuItem loadMta = new JMenuItem("Load MeshTA...");
		final JMenuItem reinit = new JMenuItem("reinitialize");
		final JMenu edition = new JMenu("Edition");
		final JMenuItem deselectionner = new JMenuItem("Deselect");
		final JMenuItem regroup = new JMenuItem("Regroup ");
		final JMenuItem transform = new JMenuItem("Transformation...");
		final JMenuItem setColor = new JMenuItem("Change Color....");
		final JMenuItem setTexture = new JMenuItem("Set Texture...");
		final JMenuItem reset = new JMenuItem("Reset Features");
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
					{
						String file = mtaDialog.getSelectedFile().getName();
						if(!file.startsWith(
								mtaDialog.getCurrentDirectory().getAbsolutePath()))
							file = mtaDialog.getCurrentDirectory().getAbsolutePath()+"\\"+mtaDialog.getCurrentDirectory().getName()
							+"\\"+file;
						file = mtaDialog.getSelectedFile().getName().endsWith(".png") ? 
								mtaDialog.getSelectedFile().getAbsolutePath() :
							mtaDialog.getSelectedFile().getAbsolutePath()+".png";
						c.saveIMGFile = new File(file);
					}
				}
	
			});
		
			saveMesh.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(mtaDialog.showOpenDialog(moi) == JFileChooser.APPROVE_OPTION)
					{
						String file = mtaDialog.getSelectedFile().getName();
						if(!file.startsWith(
								mtaDialog.getCurrentDirectory().getAbsolutePath()))
							file = mtaDialog.getCurrentDirectory().getAbsolutePath()+"\\"+mtaDialog.getCurrentDirectory().getName()
							+"\\"+file;
						file = mtaDialog.getSelectedFile().getName().endsWith(".mta") ? 
								mtaDialog.getSelectedFile().getAbsolutePath() :
							mtaDialog.getSelectedFile().getAbsolutePath()+".mta";
						c.mesh.save(new File(file));
							
					}
	
				}
				
			});
			reinit.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					c.unsetSelectedTriangle();
					c.posX = c.posY = 0; c.posZ = 10;
					c.cibleX = c.cibleY = c.cibleZ = 0;
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
							Thread.sleep(250);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(c.nouveau!=null) new ToolsFrame(moi,c.nouveau);
						System.out.println(c.nouveau);
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
						MeshTA m = c.mesh.addSousMesh();
						for(int i=index.length-1;i>=0;i--)
						{
								Object o = toolConfig.tableTriangle.getModel().getValueAt(index[i], 0);
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
								((DefaultTableModel)toolConfig.tableTriangle.getModel()).removeRow(index[i]);
	
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
			
			setName.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					final JInternalFrame edit = new JInternalFrame("Nom Mesh");
					edit.setBounds(100,50,200,70);
					moi.getContentPane().add(edit);
					final JTextField editTxt = new JTextField(toolConfig.mesh.toString());
					final JButton btnValider = new JButton("Valider");
					btnValider.addActionListener(new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent arg0) {
							toolConfig.mesh.setName(editTxt.getText());
							toolConfig.setName(toolConfig.mesh.toString());
							edit.dispose();
						}
						
					});
					edit.getContentPane().add(btnValider,BorderLayout.EAST);
					edit.getContentPane().add(editTxt,BorderLayout.CENTER);
					edit.setVisible(true);
				}
			});
			reset.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					toolConfig.mesh.setAbsoluteFeature(0, 0, 0, 0, 0, 0, 1, 1, 1);
				}
			});
			
			saveTower.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new SaveTowerFrame(moi).setVisible(true);
				}
			});
			saveMonster.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new SaveMonsterFrame(moi).setVisible(true);
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
		edition.add(reset);
		fichier.add(setName);
		fichier.add(saveImg);
		fichier.add(saveMesh);
		fichier.add(saveTower);
		fichier.add(saveMonster);
		fichier.add(saveField);
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
