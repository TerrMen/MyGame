package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.TimeUnit;

public class MyGame extends ApplicationAdapter {
	public static final int SCR_HEIGHT = 720, SCR_WIDTH = 1280;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;

    Sound sndShot;
	Texture imgFon;
	Texture imgFon1;
	Texture imgLandscape[] = new Texture[3];
	Texture imgMan[] = new Texture[29];

	Fon fon[] = new Fon[2];
	Array<Landscape> land = new Array<>();
	Man man;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();
		sndShot = Gdx.audio.newSound(Gdx.files.internal("shag.mp3"));

		imgFon = new Texture("mountains.png");
		for(int i=0; i<imgLandscape.length; i++) imgLandscape[i] = new Texture("landscape"+i+".png");
		for(int i=0; i<imgMan.length; i++) imgMan[i] = new Texture("man00"+(i+1)/10+(i+1)%10+".png");

		fon[0] = new Fon(0, 0);
		fon[1] = new Fon(SCR_WIDTH, 0);
		for(int i=0; i<6; i++) land.add(new Landscape(i*SCR_WIDTH/5, MathUtils.random(0, 1)));
		man = new Man(SCR_WIDTH/10, SCR_HEIGHT/5);
	}

	@Override
	public void render () {
		// обработка нажатий и касаний

		if(Gdx.input.justTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			//if(man.condition==man.GO)
				man.condition=man.JUMP_UP;
		}

		// игровые события (вся логика игры)
		if(man.condition!=man.FAIL) {
			for (int i = 0; i < 2; i++) fon[i].move();
			for (int i = 0; i < land.size; i++) land.get(i).move();
			if (land.get(0).x <= -land.get(0).width) {
				land.removeIndex(0);
				int r = (land.get(land.size - 1).type == 0 || land.get(land.size - 1).type == 1) ? MathUtils.random(0, 2) : MathUtils.random(0, 1);
				land.add(new Landscape(SCR_WIDTH, r));
			}
		}
			try {
				man.move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < land.size; i++) {
				if (man.x > land.get(i).x && man.x < land.get(i).x + land.get(i).width / 2
						&& land.get(i).type == 2 && man.condition == man.GO)
					man.condition = man.FAIL;

		}


		// вывод изображений (отрисовка всей графики)
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (int i = 0; i < 2; i++)
				batch.draw(imgFon, fon[i].x, fon[i].y, fon[i].width + 3, fon[i].height);
			for (int i = 0; i < land.size; i++)
				batch.draw(imgLandscape[land.get(i).type], land.get(i).x, land.get(i).y, land.get(i).width + 2, land.get(i).height);
			batch.draw(imgMan[man.faza], man.x, man.y, man.width, man.height);
			batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgFon.dispose();
		sndShot.dispose();
	}
}
