package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.FPS;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * Background 類別
 * 此類別負責渲染和更新背景圖像，並實現了 Renderable 和 Updateable 接口。
 */
public class Background implements Renderable, Updateable {

    // 背景圖像的寬度和高度，寬度為窗口寬度，高度為窗口高度的兩倍
    private static double width = Window.getwinWidth();
    private static double height = Window.getwinHeight() * 2;
    
    // 背景圖像的初始位置
    private static double x;
    private double y;

    // 背景的渲染層級
    private final int layer = 0;

    // 背景圖像
    private static BufferedImage background;

    // 背景的移動速度
    private double speed = 300;

    /**
     * 構造函數
     * 初始化背景圖像並將其添加到渲染和更新列表中。
     * @param y 背景的初始 y 位置
     * @throws IOException 當讀取背景圖像時發生 I/O 錯誤時拋出
     */
    public Background(double y) throws IOException {
        this.y = y;
        background = ImageIO.read(new File("res/Space2.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    /**
     * 獲取背景的渲染層級
     * @return 渲染層級
     */
    @Override
    public int getLayer() {
        return layer;
    }

    /**
     * 獲取背景的 x 位置
     * @return x 位置
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * 獲取背景的 y 位置
     * @return y 位置
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * 獲取背景的寬度
     * @return 寬度
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * 獲取背景的高度
     * @return 高度
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * 獲取背景的圖像
     * @return 背景圖像
     */
    @Override
    public BufferedImage getBufferedImage() {
        return background;
    }

    /**
     * 更新背景的位置，使其產生滾動效果
     * @throws IOException 當更新過程中出現 I/O 錯誤時拋出
     */
    @Override
    public void update() throws IOException {
        y += speed * FPS.getDeltaTime();
        
        // 當背景圖像的 y 位置超過窗口高度時，重置其位置以實現循環滾動效果
        if (y >= 0) {
            y = -Window.getwinHeight();
        }
    }

    /**
     * 獲取背景的 ID
     * @return 背景的 ID，這裡返回 null，因為沒有特定的 ID
     */
    @Override
    public String getID() {
        return null;
    }

    /**
     * 獲取背景的 Renderable 對象
     * @return 背景的 Renderable 對象，這裡返回 null，因為不需要
     */
    @Override
    public Renderable getRenderable() {
        return null;
    }

    /**
     * 確定是否繪製碰撞框
     * @return false，因為背景不需要碰撞框
     */
    @Override
    public boolean drawCollisionBox() {
        return false;
    }
}
