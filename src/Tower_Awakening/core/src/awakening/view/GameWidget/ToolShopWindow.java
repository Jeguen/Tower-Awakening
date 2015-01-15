package awakening.view.GameWidget;

import java.util.ArrayList;

import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ToolShopWindow extends Window{

	ArrayList<BoutonShop> btnsTours;
	BoutonShop openButton;
	
	public ToolShopWindow(ToolShop shop, float height, float width)
	{
		super("Tools Shop", new Skin(Gdx.files.internal("uiskin.json")));
		setModal(true);
		setMovable(true);
		btnsTours = new ArrayList<BoutonShop>(shop.tours.size());
		float btnHeight = height/4;
		float btnWidth = btnHeight;
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
				btn.setPosition(10,	this.getHeight() - (50 + x/3*(btnWidth+30)) - btn.getHeight());
				Label lblTower = new Label(t.toString() + " " + t.getBuildCost() + " $ ", skin);
				lblTower.setPosition(btnWidth + 30,  btn.getY() + btn.getHeight()/2 - lblTower.getHeight()/2);
				this.addActor(lblTower);
			}
			x++;
		}
		openButton = new BoutonShop(((TextureRegionDrawable)btnsTours.get(0).getStyle().up)
				.getRegion().getTexture(), btnWidth, btnHeight);
	}

	public BoutonShop getTheOpenButton()
	{
		return openButton;
	}
}
