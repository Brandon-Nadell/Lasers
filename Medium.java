package com.mygdx.game;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Medium extends Unit {
	
	private float refraction;
	
	public Medium(int x, int y, int width, int height, float refraction) {
		super(x, y, new Texture("medium.png"));
		setWidth(width);
		setHeight(height);
		this.refraction = refraction;
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer sr) {
		batch.begin();
		batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
		batch.end();
	}
	
	public boolean touching(float x, float y) {
		return new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight()).contains(x, y);
	}
	
	public static float refract(float index1, float index2, float angle) {
		if (Math.abs(index1*Math.sin((Math.toRadians(angle))/index2)) > 1) {
			return 1000;
		}
		return (float)Math.toDegrees(Math.asin(index1*Math.sin((Math.toRadians(angle))/index2)));
	}
	
	public float refract(float x, float y, float index1, float index2, float angle) {
//		Polygon left = new Polygon(new int[] { getX(), getX(), (int)centerX() }, new int[] { getY(), (int)getY2(), (int)centerY() }, 3);
//		Polygon right = new Polygon(new int[] { (int)getX2(), (int)getX2(), (int)centerX() }, new int[] { getY(), (int)getY2(), (int)centerY() }, 3);
//		Polygon bottom = new Polygon(new int[] { getX(), (int)getX2(), (int)centerX() }, new int[] { getY(), getY(), (int)centerY() }, 3);
//		Polygon top = new Polygon(new int[] { getX(), (int)getX2(), (int)centerX() }, new int[] { (int)getY2(), (int)getY2(), (int)centerY() }, 3);
		
		Polygon left = new Polygon(new int[] { getX(), getX() - (int)getWidth()/2, getX(), (int)centerX() }, new int[] { getY(), (int)centerY(), (int)getY2(), (int)centerY() }, 4);
		Polygon right = new Polygon(new int[] { (int)getX2(), (int)getX2() + (int)getWidth()/2, (int)getX2(), (int)centerX() }, new int[] { getY(), (int)centerY(), (int)getY2(), (int)centerY() }, 4);
		Polygon bottom = new Polygon(new int[] { getX(), (int)centerX(), (int)getX2(), (int)centerX() }, new int[] { getY(), getY() - (int)getHeight()/2, getY(), (int)centerY() }, 4);
		Polygon top = new Polygon(new int[] { getX(), (int)centerX(), (int)getX2(), (int)centerX() }, new int[] { (int)getY2(), (int)getY2() + (int)getHeight()/2, (int)getY2(), (int)centerY() }, 4);
	
		
		if (left.contains(x, y)) {
//			System.out.println("left");
			return refract(index1, index2, Laser.terminal(angle)%360);
		} else if (right.contains(x, y)) {
//			System.out.println("right");
			return refract(index1, index2, angle-180) + 180;
		} else if (top.contains(x, y)) {
//			System.out.println("top");
			return refract(index1, index2, angle-270) + 270;
		} else if (bottom.contains(x, y)) {
//			System.out.println("bottom");
			return refract(index1, index2, angle-90) + 90;
		}
		return 0;
	}
	
	public AngleType refract2(float x, float y, float index1, float index2, float angle) {
//		Polygon left = new Polygon(new int[] { getX(), getX(), (int)centerX() }, new int[] { getY(), (int)getY2(), (int)centerY() }, 3);
//		Polygon right = new Polygon(new int[] { (int)getX2(), (int)getX2(), (int)centerX() }, new int[] { getY(), (int)getY2(), (int)centerY() }, 3);
//		Polygon bottom = new Polygon(new int[] { getX(), (int)getX2(), (int)centerX() }, new int[] { getY(), getY(), (int)centerY() }, 3);
//		Polygon top = new Polygon(new int[] { getX(), (int)getX2(), (int)centerX() }, new int[] { (int)getY2(), (int)getY2(), (int)centerY() }, 3);
		
		Polygon left = new Polygon(new int[] { getX(), getX() - (int)getWidth()/2, getX(), (int)centerX() }, new int[] { getY(), (int)centerY(), (int)getY2(), (int)centerY() }, 4);
		Polygon right = new Polygon(new int[] { (int)getX2(), (int)getX2() + (int)getWidth()/2, (int)getX2(), (int)centerX() }, new int[] { getY(), (int)centerY(), (int)getY2(), (int)centerY() }, 4);
		Polygon bottom = new Polygon(new int[] { getX(), (int)centerX(), (int)getX2(), (int)centerX() }, new int[] { getY(), getY() - (int)getHeight()/2, getY(), (int)centerY() }, 4);
		Polygon top = new Polygon(new int[] { getX(), (int)centerX(), (int)getX2(), (int)centerX() }, new int[] { (int)getY2(), (int)getY2() + (int)getHeight()/2, (int)getY2(), (int)centerY() }, 4);
	
		float resultantAngle = 0;
		boolean reflected = false;
		float newAngle = 0;
		if (left.contains(x, y)) {
//			System.out.println("left");
			newAngle = refract(index1, index2, angle-180) + 180;
			if (newAngle == 1180) {
				resultantAngle = 2*90 - angle;
				reflected = true;
			} else {
				resultantAngle = newAngle;
			}
		} else if (right.contains(x, y)) {
//			System.out.println("right");
			newAngle = refract(index1, index2, angle-0) + 0;
			if (newAngle == 1000) {
				resultantAngle = 2*90 - angle;
				reflected = true;
			} else {
				resultantAngle = newAngle;
			}
		} else if (top.contains(x, y)) {
//			System.out.println("top");
			newAngle = refract(index1, index2, angle-90) + 90;
			if (newAngle == 1090) {
				resultantAngle = 2*180 - angle;
				reflected = true;
			} else {
				resultantAngle = newAngle;
			}
		} else if (bottom.contains(x, y)) {
//			System.out.println("bottom");
			newAngle = refract(index1, index2, angle-270) + 270;
			if (newAngle == 1270) {
				resultantAngle = 2*180 - angle;
				reflected = true;
			} else {
				resultantAngle = newAngle;
			}
		}
//		System.out.println(resultantAngle + " " + reflected);
		return new AngleType(resultantAngle, reflected);
	}
	
	public float getRefraction() {
		return refraction;
	}

}
