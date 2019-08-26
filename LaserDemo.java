package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LaserDemo extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private ArrayList<Source> sources;
	private ArrayList<Mirror> mirrors;
	private ArrayList<Diffracter> diffracters;
	private ArrayList<Medium> mediums;
	
	public static boolean clicked;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		mirrors = new ArrayList<Mirror>();
		mirrors.add(new Mirror(900, 300, 45, false));
		mirrors.add(new Mirror(900, 100, 30, true, 100).setColor(Color.GREEN));
//		mirrors.add(new Mirror(900, 500, 105, false));
		
		sources = new ArrayList<Source>();
		sources.add(new Source(300, 300, 0, 10, 4));
//		sources.add(new Source(300, 100, 10));
		sources.add(new Source(300, 500, 135));
		
		diffracters = new ArrayList<Diffracter>();
		diffracters.add(new Diffracter(300, 700, 10, 4));
		
		mediums = new ArrayList<Medium>();
		mediums.add(new Medium(900, 700, 100, 100, 1.5f));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.25f, .25f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		
		//draw stuff
		for (Source source : sources) {
			source.render(batch, sr);
		}
		for (Diffracter diffracter : diffracters) {
			diffracter.render(batch, sr);
			diffracter.reset();
		}
		for (Mirror mirror : mirrors) {
			mirror.render(batch, sr);
		}
		for (Medium medium : mediums) {
			medium.render(batch, sr);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.M)) {
			mirrors.add(new Mirror(Gdx.input.getX(), 790 - Gdx.input.getY(), 0, false));
		}
		if (Gdx.input.isKeyJustPressed(Keys.L)) {
			mirrors.add(new Mirror(Gdx.input.getX(), 790 - Gdx.input.getY(), 0, true).setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f)));
		}
		
		
		//laser!...
		for (Source source : sources) {
			source.renderLaser(sr, source, mirrors, diffracters, mediums);
		}
		
	}
}
