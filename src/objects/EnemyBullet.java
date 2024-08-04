package objects;

import update.*;
import render.*;
import core.*;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 敵機子彈
 * @author B11007143
 * @version final
 */
public class EnemyBullet implements Updateable, Renderable {
	private static double width = 10;
	private static double height = 10;
	private double x;
	private double y;

	private final int layer = 1;

	private static BufferedImage enemybullet;
//	private static double speed = 400;
	private static double speed;

	/**
	 * @param x 子彈的x方向位置
	 * @param y 子彈的y方向位置
	 * @param grade 敵人等級
	 * @throws IOException 
	 */
	public EnemyBullet(double x, double y, int grade) throws IOException {
		this.x = x - (getWidth() / 2);
		this.y = y;
		
		speed = 200 * grade;

		enemybullet = ImageIO.read(new File("res/enemybullet2.png"));

		Renderer.addRenderableObject(this);
		Updater.addUpdateableObject(this);
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return enemybullet;
	}

	/**
	 *使子彈向下移動，以及超過視窗消失
	 */
	@Override
	public void update() throws IOException {
		y += speed * FPS.getDeltaTime();

		if (y >= Window.getwinHeight()) {
			Updater.removeUpdateableObject(this);
			Renderer.removeRenderableObject(this);
		}
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "enemybullet";
	}

	@Override
	public Renderable getRenderable() {
		return this;
	}

	@Override
	public boolean drawCollisionBox() {
		// TODO Auto-generated method stub
		return false;
	}

}
