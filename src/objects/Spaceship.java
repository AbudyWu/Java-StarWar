package objects;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.FPS;
import core.Input;
import core.Sound;
import core.Timer;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * 飛船類
 */
public class Spaceship implements Renderable, Updateable {
	public boolean alive;

	public static double width = 75;
	private static double height = 75;

	private double x;
	private double y;

	private int layer = 2;
	private static BufferedImage spaceShip;

	private double speed = 200;

	private static int shootTimerMills = 500;

	public static Polygon polygon;
	private Shield shield;

	Timer timer = new Timer(shootTimerMills);

	/**
	 * @param x 飛船的初始x位置
	 * @param y 飛船的初始y位置
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public Spaceship(double x, double y) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.x = x;
		this.y = y;

		spaceShip = ImageIO.read(new File("res/Spaceship01.png"));
		Sound.playSound("res/Sound/hub.wav");
		Renderer.addRenderableObject(this);
		Updater.addUpdateableObject(this);
		
		shield = new Shield(this);
        core.emo.shieldalive = true;

	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getLayer() {
		return layer;
	}
	
	/**
     * 更新飛船的位置、發射子彈和處理碰撞。
     */
	@Override
	public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		if (Input.keys[Input.LEFT] && x >= 0) {
			x -= speed * FPS.getDeltaTime();
		}
		if (Input.keys[Input.RIGHT] && x <= Window.getwinWidth() - width) {
			x += speed * FPS.getDeltaTime();
		}
		if (Input.keys[Input.UP] && y >= 0) {
			y -= speed * FPS.getDeltaTime();
		}
		if (Input.keys[Input.DOWN] && y <= Window.getwinHeight() - height) {
			y += speed * FPS.getDeltaTime();
		}

		if (Input.keys[Input.SPACE] && timer.isRinging()) {
			new Bullet(x + (getWidth() / 2), y);
//			new Bullet(x + (getWidth()/4),y);
//			new Bullet(x + (getWidth()*3/4),y);
			Sound.playSound("res/Sound/shoot.wav");
			timer.resetTimer();
		}
		
		if (core.emo.shieldalive) {
            shield.update();
        } else {
            Updateable collidingObject00 = isColliding(this, "asteroid");
            Updateable collidingObject01 = isColliding(this, "enemybullet");

            if (objects.Text.HP <= 0) {
            	Sound.playSound("res/Sound/loser.wav");
                Updater.removeUpdateableObject(this);
                Renderer.removeRenderableObject(this);
                core.emo.spaceshipIsHit = true;
            }

            if (collidingObject00 != null) {
                Updater.removeUpdateableObject(collidingObject00);
                Renderer.removeRenderableObject(collidingObject00.getRenderable());
                new Explosion(x, y, 0.3);
                Sound.playSound("res/Sound/enemyexplosion.wav");
                objects.Text.HP -= 20;
            }
            if (collidingObject01 != null) {
                Updater.removeUpdateableObject(collidingObject01);
                Renderer.removeRenderableObject(collidingObject01.getRenderable());
                new Explosion(x, y, 0.3);
                Sound.playSound("res/Sound/enemyexplosion.wav");
                objects.Text.HP -= 10;
            }
        }
	}

	@Override
	public BufferedImage getBufferedImage() {
		return spaceShip;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "spaceship";
	}

	@Override
	public Renderable getRenderable() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean drawCollisionBox() {
		// TODO Auto-generated method stub
		return false;
	}

}