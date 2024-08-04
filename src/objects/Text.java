package objects;


import java.awt.image.BufferedImage;
import java.io.IOException;

import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * 其實這個沒幹麻，只是放參數在這邊，呵呵
 * @author B11007143
 * @version final
 */
public class Text implements Renderable, Updateable {

	private static double width = Window.getwinWidth();
	private static double height = Window.getwinHeight();
	private int x;
	private int y;

	private final int layer = 3;

	public static int Count = 0;
	public static int HP = 100;
	public static int Shield = 100;
	
//	private static Graphics count;

	public Text(int x, int y) throws IOException {
		this.x = x;
		this.y = y;
		
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
	public String getID() {
		return null;
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

	@Override
	public void update() throws IOException {
		
	}
	
	

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
