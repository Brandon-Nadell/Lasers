package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Laser {
	
	private float angleInitial;
	private float angle;
	
	public Laser(float angleInitial) {
		this.angleInitial = angleInitial;
	}
	
	public void render(ShapeRenderer sr, Source source, ArrayList<Mirror> mirrors, ArrayList<Diffracter> diffracters, ArrayList<Medium> mediums) {
		render(source.centerX(), source.centerY(), sr, source, mirrors, diffracters, mediums);
	}
	
	public void render(float x1, float y1, ShapeRenderer sr, Source source, ArrayList<Mirror> mirrors, ArrayList<Diffracter> diffracters, ArrayList<Medium> mediums) {
		float x = x1;
		float y = y1;
		angle = angleInitial;
		Unit lastTouched = source;
		Diffracter hit = null;
		float lastMediumIndex = 1;
		Medium lastMediumOfIndex = null;
		sr.setColor(Color.RED);
		int attempts = 0;
		while (x > 0 && x < 1440 && y > 0 && y < 790) {
			x += Math.cos(Math.toRadians(angle));
			y += Math.sin(Math.toRadians(angle));
			for (Mirror mirror : mirrors) {
				if (mirror != lastTouched && mirror.touching(x, y)) {
					//draw line
					drawLaser(sr, x1, y1, x, y);
					x1 = x;
					y1 = y;
					//change vector
					angle = 2*mirror.getAngle() - angle + (mirror.getLens() ? 180 : 0);
					if (mirror.getColor() != null) {
						sr.setColor(mirror.getColor());
					}
					lastTouched = mirror;
				}
			}
			for (Diffracter diffracter : diffracters) {
				if (diffracter != lastTouched && diffracter.canDiffract() && diffracter.touching(x, y)) {
					hit = diffracter;
					hit.diffract();
//					diffracter.setAngle(angle);
//					diffracter.renderLaser(x, y, sr, diffracter, mirrors, diffracters);
//					lastTouched = diffracter;
					attempts = 100000;
				}
			}
			if (++attempts > 100000) {
				break;
			}
			float mediumIndex = 1;
			Medium mediumOfIndex = null;
			for (Medium medium : mediums) {
				if (medium.touching(x, y)) {
					mediumIndex = medium.getRefraction();
					mediumOfIndex = medium;
				}
			}
			if (mediumIndex != lastMediumIndex) {
				drawLaser(sr, x1, y1, x, y);
				x1 = x;
				y1 = y;
//				System.out.println(angle);
				if (mediumOfIndex != null) {
					angle = mediumOfIndex.refract(x, y, lastMediumIndex, mediumIndex, terminal(angle)%360);
//					System.out.println(lastMediumIndex + " " + mediumIndex);
					lastMediumIndex = mediumIndex;
					lastMediumOfIndex = mediumOfIndex;
				} else {
					AngleType at = lastMediumOfIndex.refract2(x, y, lastMediumIndex, mediumIndex, terminal(angle)%360);
					angle = at.angle;
					if (!at.reflected) {
						lastMediumIndex = mediumIndex;
						lastMediumOfIndex = mediumOfIndex;
					} else {
//						lastMediumIndex = 1.5f;
//						lastMediumOfIndex = mediumOfIndex;
					}

//					System.out.println(lastMediumIndex + " " + mediumIndex);
				}
//				System.out.println();
//				angle = Medium.refract(lastMediumIndex, mediumIndex, terminal(angle)%360);
			}
 		}
		drawLaser(sr, x1, y1, x, y);
		
		if (hit != null) {
			hit.setAngle(angle);
			hit.renderLaser(x, y, sr, hit, mirrors, diffracters, mediums);
		}
	}
	
	public void drawLaser(ShapeRenderer sr, float x1, float y1, float x, float y) {
		sr.begin(ShapeType.Filled);
//		sr.line(x1, y1, x, y);
		sr.rectLine(x1, y1, x, y, 4);
		sr.end();
	}
	
	public static float terminal(float angle) {
		while (angle < 0) {
			angle += 360;
		}
		return angle;
	}

}
