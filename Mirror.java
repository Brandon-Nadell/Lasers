package com.mygdx.game;

import java.awt.geom.Line2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Mirror extends Unit {
	
	private float angle;
	private int radius;
	private boolean lens;
	private Color color;
	private Line2D line;
	
	public Mirror(int x, int y, float angle, boolean lens, int radius) {
		super(x, y, new Texture("mirror.png"));
		this.angle = angle;
		this.radius = radius;
		this.lens = lens;
		createLine();
	}
	
	public Mirror(int x, int y, float angle, boolean lens) {
		this(x, y, angle, lens, 30);
	}
	
	public Mirror setColor(Color color) {
		this.color = color;
		return this;
	}
	
	public void createLine() {
		line = new Line2D.Float(centerX() - radius*(float)Math.cos(Math.toRadians(angle)), centerY() - radius*(float)Math.sin(Math.toRadians(angle)), centerX() + radius*(float)Math.cos(Math.toRadians(angle)), centerY() + radius*(float)Math.sin(Math.toRadians(angle)));
//		setWidth((float)line.getBounds().getWidth());
//		setHeight((float)line.getBounds().getHeight());
	}
	
	public void render(SpriteBatch batch, ShapeRenderer sr) {
		super.render(batch, sr);
		if (over()) {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				radius += 2;
				createLine();
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				radius -= 2;
				createLine();
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				angle += 2;
				createLine();
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				angle -= 2;
				createLine();
			}
		}
	}
	
	public boolean over() {
		return Gdx.input.getX() > centerX() - radius && Gdx.input.getX() < centerX() + radius && 790 - Gdx.input.getY() > centerY() - radius && 790 - Gdx.input.getY() < centerY() + radius;
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer sr) {
//		batch.begin();
//		batch.draw(new TextureRegion(getTexture()), getX(), getY(), getTexture().getWidth()/2, getTexture().getHeight()/2, getTexture().getWidth(), getTexture().getHeight(), 1, 1, angle);
//		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(lens ? Color.WHITE : Color.BLACK);
		sr.rectLine((float)line.getX1(), (float)line.getY1(), (float)line.getX2(), (float)line.getY2(), 5);
		sr.end();
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			sr.begin(ShapeType.Line);
			sr.circle(centerX(), centerY(), radius);
			sr.end();
		}
		
	}
	
	public void move() {
		super.move();
		createLine();
	}
	
//	public boolean touching(float x, float y) {
//		return x > getX() && x < getX2() && y > getY() && y < getY2();
//	}
	
	public boolean touching(float x, float y) {
		return line.ptSegDist(x, y) <= .5;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public boolean getLens() {
		return lens;
	}
	
	public Color getColor() {
		return color;
	}

}
