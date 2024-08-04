package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import core.FPS;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * 得分50分以上開始生成的小行星。
 */
public class Asteroid implements Updateable, Renderable {
    private double width;
    private double height;
    private double x;
    private double y;

    private final int layer = 2;

    private static BufferedImage asteroidImage;

    private double speed = 150;

    private Random rand = new Random();

    /**
     * 構造函數
     * 初始化小行星的屬性並將其添加到渲染和更新列表中。
     */
    public Asteroid() throws IOException {
        // 隨機設置小行星的大小，最小為35
        int dimensions = rand.nextInt(75 + 1);
        if (dimensions < 35) {
            dimensions = 35;
        }

        width = dimensions;
        height = dimensions;

        // 隨機設置小行星的初始橫向位置
        int posX = rand.nextInt((int) Window.getwinWidth() - (int) getWidth() + 1);
        this.x = posX;
        this.y = -getHeight(); // 小行星從窗口頂部開始

        // 加載小行星圖片
        asteroidImage = ImageIO.read(new File("res/Asterioid.png"));

        // 將小行星對象添加到渲染和更新列表中
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
        return asteroidImage;
    }

    /**
     * 更新小行星的位置。
     */
    @Override
    public void update() throws IOException {
        // 根據遊戲幀率更新小行星的縱向位置
        y += speed * FPS.getDeltaTime();

        // 如果小行星超出了窗口高度，將其從更新和渲染列表中移除
        if (y >= Window.getwinHeight()) {
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
        }
    }

    @Override
    public String getID() {
        return "asteroid";
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
