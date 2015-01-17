package awakening.view.GameWidget;

import java.util.ArrayList;

import awakening.modele.partie.Partie;
import awakening.modele.toolshop.ToolShop;
import awakening.modele.toolshop.monster.Monster;
import awakening.modele.toolshop.monster.MonsterEarth;
import awakening.modele.toolshop.tower.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ToolShopWindow extends Window {

	ArrayList<BoutonShop> btnsTours;
	BoutonShop openButton;
	Partie partie;

	public ToolShopWindow(final ToolShop shop, final Partie partie,
			float height, float width) {
		super("Tools Shop", new Skin(Gdx.files.internal("uiskin.json")));
		setModal(true);
		setMovable(true);
		btnsTours = new ArrayList<BoutonShop>(shop.tours.size());
		float btnHeight = height / 4;
		float btnWidth = btnHeight;
		this.partie = partie;
		int x = 0;
		this.setSize(width, height);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Table toolTable = new Table();
		final ScrollPane scroll = new ScrollPane(toolTable, skin);
		scroll.setSize(this.getWidth(), this.getHeight() - 30);
		scroll.setPosition(0, 0);
		for (final Tower t : shop.tours) {
			System.out.println(t.canBeUpgrade());
			if (t.getModeleImage() != null) {
				Group liste = new Group();
				BoutonShop btn = new BoutonShop(t.getModeleImage(), btnWidth,
						btnHeight);
				liste.addActor(btn);
				btnsTours.add(btn);
				btn.setPosition(10, this.getHeight()
						- (50 + x * (btnWidth + 30)) - btn.getHeight());
				btn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						shop.setSelectedTowerIndex(t.getId());
						openButton.getStyle().up = new TextureRegionDrawable(
								new TextureRegion(t.getModeleImage()));
						setVisible(false);
						Gdx.input.setInputProcessor(partie.getInputManager());
					};
				});

				Label lblTower = new Label(t.toString() + "  price : "
						+ t.getBuildCost() + " $ ", skin);
				Label lblFeature = new Label("speed : " + t.getSpeedAttack()
						+ "   range : " + t.getRange(), skin);
				lblTower.setPosition(btnWidth + 30,
						btn.getY() + btn.getHeight() / 2 - lblTower.getHeight()
								/ 2);
				lblFeature.setPosition(lblTower.getX(), lblTower.getY()
						- lblTower.getHeight() - 10);
				liste.addActor(lblFeature);
				liste.addActor(lblTower);
				toolTable.addActor(liste);
				toolTable.row();
			}
			x++;
		}
		for (final Monster m : shop.monstres) {
			if (m.getModeleImage() != null) {
				Group liste = new Group();
				BoutonShop btn = new BoutonShop(m.getModeleImage(), btnWidth,
						btnHeight);
				liste.addActor(btn);
				btnsTours.add(btn);
				btn.setPosition(10, this.getHeight()
						- (50 + x * (btnWidth + 30)) - btn.getHeight());
				btn.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						Monster newMonster = new MonsterEarth(m);
						newMonster.homethetie(3);
						newMonster.setLifePoint(300);
						partie.getTerrain().addMonster(newMonster);
						setVisible(false);
						Gdx.input.setInputProcessor(partie.getInputManager());
					};
				});
				Label lblTower = new Label(m.toString() + "  price : "
						+ m.getBuildCost() + " $ ", skin);
				lblTower.setPosition(btnWidth + 30,
						btn.getY() + btn.getHeight() / 2 - lblTower.getHeight()
								/ 2);
				liste.addActor(lblTower);
				toolTable.addActor(liste);
				toolTable.row();
			}
			x++;
		}
		toolTable.row();
		openButton = new BoutonShop(((TextureRegionDrawable) btnsTours.get(0)
				.getStyle().up).getRegion().getTexture(), btnWidth, btnHeight);

		TextButton annuler = new TextButton("Annuler", skin);
		annuler.setSize(this.getWidth(), 30);
		annuler.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setVisible(false);
				Gdx.input.setInputProcessor(partie.getInputManager());
			}
		});
		this.addActor(scroll);
		this.addActor(annuler);
		this.act(30);
	}

	public BoutonShop getTheOpenButton() {
		return openButton;
	}
}
