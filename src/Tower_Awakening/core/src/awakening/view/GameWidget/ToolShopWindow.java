package awakening.view.GameWidget;

import java.util.ArrayList;

import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ToolShopWindow extends Window{

	ArrayList<BoutonShop> btnsTours;
	BoutonShop openButton;
	
	public ToolShopWindow(ToolShop shop, float height, float width)
	{
		super("Tools Shop", new Skin(Gdx.files.internal("uiskin.json")));
		btnsTours = new ArrayList<BoutonShop>(shop.tours.size());
		float btnHeight = height/4;
		float btnWidth = btnHeight;
		int x=0;
		for(Tower t : shop.tours)
		{
			System.out.println(t.canBeUpgrade());
			if(t.getModeleImage()!=null)
			{
				BoutonShop btn = new BoutonShop(t.getModeleImage(), btnWidth, btnHeight);
				this.addActor(btn);
				btnsTours.add(btn);
				btn.setPosition(10 + x * (btnHeight+5), 10 + (int)(x/3)*(btnWidth+5));
			}
			x++;
		}
		openButton = new BoutonShop(((TextureRegionDrawable)btnsTours.get(0).getStyle().up)
				.getRegion().getTexture(), btnWidth, btnHeight);
		openButton.addAction(new Action() {
			
			@Override
			public boolean act(float delta) {
				setVisible(true);
				return false;
			}
		});
	}

	public BoutonShop getTheOpenButton()
	{
		return openButton;
	}
}
