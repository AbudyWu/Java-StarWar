package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import core.FPS;
import core.Window;
import update.Updateable;
import update.Updater;
import render.Renderable;
import render.Renderer;

/**
 * HealthItem 類
 * 此類表示遊戲中的補血道具，實現了 Updateable 和 Renderable 接口。
 */
public class HealthItem implements Updateable, Renderable {
    
    private double x, y;           // 補血道具的 x 和 y 座標
    private double width, height;  // 補血道具的寬度和高度
    private BufferedImage healthitem;  // 補血道具的圖像
    private double speed = 100;    // 補血道具的移動速度

    /**
     * 構造函數
     * 初始化補血道具的座標、尺寸和圖像，並將其添加到渲染和更新列表中。
     * @param x 補血道具的初始 x 座標
     * @param y 補血道具的初始 y 座標
     * @throws IOException 當讀取圖像文件時發生 I/O 錯誤時拋出
     */
    public HealthItem(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        this.width = 30; // 設置寬度
        this.height = 30; // 設置高度
        healthitem = ImageIO.read(new File("res/healthitem.png")); // 補血道具的圖片
        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    /**
     * 更新方法
     * 更新補血道具的位置並檢測與飛船的碰撞。
     */
    @Override
    public void update() {
        // 補血道具的移動邏輯
        y += speed * FPS.getDeltaTime();

        // 如果補血道具超出窗口底部，則將其移除
        if (y >= Window.getwinHeight()) {
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
        }

        // 檢測與飛船的碰撞
        Updateable collidingObject = isColliding(this, "spaceship");
        if (collidingObject != null) {
            // 移除補血道具
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);

            // 根據護盾狀態增加相應的生命值或護盾值
            if (core.emo.shieldalive) {
                objects.Text.Shield += 5;
                if (objects.Text.Shield >= 100) {
                    objects.Text.Shield = 100;
                }
            } else {
                objects.Text.HP += 10;
                if (objects.Text.HP >= 100) {
                    objects.Text.HP = 100;
                }
            }
        }
    }

    /**
     * 獲取補血道具的 ID
     * @return 補血道具的 ID
     */
    @Override
    public String getID() {
        return "healthitem";
    }

    /**
     * 獲取補血道具的 Renderable 對象
     * @return 補血道具的 Renderable 對象
     */
    @Override
    public Renderable getRenderable() {
        return this;
    }

    /**
     * 獲取渲染層次
     * @return 渲染層次
     */
    @Override
    public int getLayer() {
        return 1; // 設置渲染層次
    }

    /**
     * 獲取補血道具的 x 座標
     * @return 補血道具的 x 座標
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * 獲取補血道具的 y 座標
     * @return 補血道具的 y 座標
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * 獲取補血道具的寬度
     * @return 補血道具的寬度
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * 獲取補血道具的高度
     * @return 補血道具的高度
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * 確定是否繪製碰撞框
     * @return false，表示不繪製碰撞框
     */
    @Override
    public boolean drawCollisionBox() {
        return false;
    }

    /**
     * 獲取補血道具的圖像
     * @return 補血道具的圖像
     */
    @Override
    public BufferedImage getBufferedImage() {
        return healthitem;
    }
}
