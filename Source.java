package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Source extends Unit {
	
	private float angle;
	private float maxSpread;
	private int numLasers;
	private ArrayList<Laser> lasers;
	
	public Source(int x, int y, float angle, float maxSpread, int numLasers) {
		super(x, y, new Texture("source.png"));
		this.angle = angle;
		this.maxSpread = maxSpread;
		this.numLasers = numLasers;
		createLasers();
	}
	
	public Source(int x, int y, float angle) {
		this(x, y, angle, 0, 1);
	}
	
	public void render(SpriteBatch batch, ShapeRenderer sr) {
		super.render(batch, sr);
		if (over()) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				maxSpread += 1;
				createLasers();
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				maxSpread -= 1;
				createLasers();
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				angle += 2;
				createLasers();
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				angle -= 2;
				createLasers();
			}
			if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
				numLasers += 1;
				createLasers();
			} else if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
				numLasers -= 1;
				createLasers();
			}
		}
	}
	
	public void createLasers() {
		lasers = new ArrayList<Laser>();
		if (numLasers == 1) {
			lasers.add(new Laser(angle));
		} else {
			for (int i = 0; i < numLasers; i++) {
				lasers.add(new Laser(angle - maxSpread + maxSpread*2*i/(numLasers-1)));
			}
		}
	}
	
	public void renderLaser(float x, float y, ShapeRenderer sr, Source source, ArrayList<Mirror> mirrors, ArrayList<Diffracter> diffracters, ArrayList<Medium> mediums) {
		for (Laser laser : lasers) {
			laser.render(x, y, sr, source, mirrors, diffracters, mediums);
		}
	}
	
	public void renderLaser(ShapeRenderer sr, Source source, ArrayList<Mirror> mirrors, ArrayList<Diffracter> diffracters, ArrayList<Medium> mediums) {
		renderLaser(source.centerX(), source.centerY(), sr, source, mirrors, diffracters, mediums);
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
		createLasers();
	}
	
	
	

}
