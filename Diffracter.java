package com.mygdx.game;

import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.graphics.Texture;

public class Diffracter extends Source {
	
	private int diffractions;
	
	public Diffracter(int x, int y, float maxSpread, int numLasers) {
		super(x, y, 0, maxSpread, numLasers);
		setTexture(new Texture("diffracter.png"));
	}
	
	public boolean touching(float x, float y) {
		return new Rectangle2D.Float(getX(), getY(), getTexture().getWidth(), getTexture().getHeight()).contains(x, y);
	}
	
	public void diffract() {
		diffractions++;
	}
	
	public boolean canDiffract() {
		return diffractions < 100;
	}
	
	public void reset() {
		diffractions = 0;
	}

}
