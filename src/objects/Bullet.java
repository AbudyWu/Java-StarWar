package objects;

import update.*;
import render.*;
import core.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Bullet 類
 * 表示遊戲中的一顆子彈。
 */
public class Bullet implements Updateable, Renderable {
    private static double width = 10;
    private static double height = 10;
    private double x;
    private double y;

    private final int layer = 1;

    private static BufferedImage bulletImage;
    private static double speed = 800;

    /**
     * 構造函數
     * 初始化子彈的屬性並將其添加到渲染和更新列表中。
     * 
     * @param x 子彈的初始橫向位置
     * @param y 子彈的初始縱向位置
     * @throws IOException 如果讀取子彈圖片失敗
     */
    public Bullet(double x, double y) throws IOException {
        this.x = x - (getWidth() / 2);
        this.y = y;

        // 加載子彈圖片
        bulletImage = ImageIO.read(new File("res/bullet.png"));

        // 將子彈對象添加到渲染和更新列表中
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
        return bulletImage;
    }

    /**
     * 更新子彈的位置並檢查碰撞。
     * @throws LineUnavailableException 
     * @throws UnsupportedAudioFileException 
     */
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // 根據遊戲幀率更新子彈的縱向位置
        y -= speed * FPS.getDeltaTime();

        // 如果子彈超出了窗口高度，將其從更新和渲染列表中移除
        if (y < -Window.getwinHeight()) {
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
        }

        // 檢查子彈與小行星的碰撞
        Updateable collidingObject = isColliding(this, "asteroid");
        if (collidingObject != null) {
            // 移除碰撞的小行星和子彈對象
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdateableObject(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());
            new Explosion(x, y, 0.4);
            Sound.playSound("res/Sound/enemyexplosion.wav");
            // 更新分數
            objects.Text.Count += 1;
        }
    }

    @Override
    public String getID() {
        return "bullet";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }

    @Override
    public boolean drawCollisionBox() {
        return false;
    }
}
