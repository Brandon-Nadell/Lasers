package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Unit {
	
	private int x;
	private int y;
	private float width;
	private float height;
	private Texture texture;
	private boolean clicked;
	
	public Unit(int x, int y, Texture texture) {
		this.x = x;
		this.y = y;
		width = texture.getWidth();
		height = texture.getHeight();
		this.texture = texture;
	}
	
	public void render(SpriteBatch batch, ShapeRenderer sr) {
		draw(batch, sr);
		
		if (!LaserDemo.clicked && Gdx.input.isButtonPressed(Buttons.LEFT) && over()) {
			LaserDemo.clicked = clicked = true;
		}
		if (clicked && LaserDemo.clicked) {
			move();
		}
		if (!Gdx.input.isButtonPressed(Buttons.LEFT)) {
			LaserDemo.clicked = clicked = false;
		}
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer sr) {
		batch.begin();
		batch.draw(texture, x, y);
		batch.end();
	}
	
	public void move() {
		x = Gdx.input.getX() - (int)width/2;
		y = 790 - Gdx.input.getY() - (int)height/2;
	}
	
	public boolean over() {
		return Gdx.input.getX() > x && Gdx.input.getX() < getX2() && 790 - Gdx.input.getY() > y && 790 - Gdx.input.getY() < getY2();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getX2() {
		return x + width;
	}
	
	public float getY2() {
		return y + height;
	}
	
	public float centerX() {
		return x + width/2;
	}
	
	public float centerY() {
		return y + height/2;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}
