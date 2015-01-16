package awakening.view.GameWidget;

import java.util.ArrayList;

import awakening.modele.partie.Partie;
import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.monster.MonsterEarth;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ToolShopWindow extends Window{

	ArrayList<BoutonShop> btnsTours;
	BoutonShop openButton;
	Partie partie;
	
	public ToolShopWindow(ToolShop shop, final Partie partie, float height, float width)
	{
		super("Tools Shop", new Skin(Gdx.files.internal("uiskin.json")));
		setModal(true);
		setMovable(true);
		btnsTours = new ArrayList<BoutonShop>(shop.tours.size());
		float btnHeight = height/4;
		float btnWidth = btnHeight;
		this.partie = partie;
		int x=0;
		this.setSize(width, height);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		for(Tower t : shop.tours)
		{
			System.out.println(t.canBeUpgrade());
			if(t.getModeleImage()!=null)
			{
				BoutonShop btn = new BoutonShop(t.getModeleImage(), btnWidth, btnHeight);
				this.addActor(btn);
				btnsTours.add(btn);
				btn.setPosition(10,	this.getHeight() - (50 + x*(btnWidth+30)) - btn.getHeight());
				Label lblTower = new Label(t.toString() + "  price : " + t.getBuildCost() + " $ ", skin);
				Label lblFeature = new Label("speed : " + t.getSpeedAttack() + "   range : " + t.getRange(), skin);
				lblTower.setPosition(btnWidth + 30,  btn.getY() + btn.getHeight()/2 - lblTower.getHeight()/2);
				lblFeature.setPosition(lblTower.getX(), lblTower.getY() - lblTower.getHeight() - 10);
				this.addActor(lblFeature);
				this.addActor(lblTower);
			}
			x++;
		}
		for(final Monster m : shop.monstres)
		{
			if(m.getModeleImage()!=null)
			{
				BoutonShop btn = new BoutonShop(m.getModeleImage(), btnWidth, btnHeight);
				this.addActor(btn);
				btnsTours.add(btn);
				btn.setPosition(10,	this.getHeight() - (50 + x*(btnWidth+30)) - btn.getHeight());
				btn.addListener(new ClickListener()
				{
					public void clicked(InputEvent event, float x, float y) {
						Monster newMonster = new MonsterEarth(m);
						partie.getTerrain().addMonster(newMonster);
						setVisible(false);
						Gdx.input.setInputProcessor(partie.getInputManager());
					};
				});
				Label lblTower = new Label(m.toString() + "  price : " + m.getBuildCost() + " $ ", skin);
				lblTower.setPosition(btnWidth + 30,  btn.getY() + btn.getHeight()/2 - lblTower.getHeight()/2);
				this.addActor(lblTower);
			}
			x++;
		}
		openButton = new BoutonShop(((TextureRegionDrawable)btnsTours.get(0).getStyle().up)
				.getRegion().getTexture(), btnWidth, btnHeight);
		
		TextButton valider = new TextButton("Acheter",skin);
		valider.setSize(120,btnHeight/4);
		valider.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setVisible(false);
				}
			});
		this.addActor(valider);
	}

	public BoutonShop getTheOpenButton()
	{
		return openButton;
	}
}
