package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * Explosion 類別
 * 此類別用於表示爆炸效果，並實現了 Updateable 和 Renderable 接口。
 */
public class Explosion implements Renderable, Updateable {
    private double x, y; // 爆炸的 x 和 y 坐標
    private ArrayList<BufferedImage> explosionImages; // 爆炸動畫的每一幀圖片
    private long startTime; // 爆炸開始時間
    private long duration; // 爆炸持續時間
    private int currentFrame; // 當前幀索引
    private int totalFrames; // 總幀數
    private double scale; // 圖像縮放比例

    /**
     * 構造函數
     * 初始化爆炸效果並將其添加到渲染和更新列表中。
     * @param x 爆炸的 x 坐標
     * @param y 爆炸的 y 坐標
     * @param scale 圖像縮放比例
     * @throws IOException 當讀取圖像失敗時拋出
     */
    public Explosion(double x, double y, double scale) throws IOException {
        this.x = x;
        this.y = y;
        this.duration = 500; // 爆炸持續時間（毫秒）
        this.startTime = System.currentTimeMillis(); // 獲取當前時間作為開始時間
        this.explosionImages = new ArrayList<>(); // 初始化圖片列表
        this.currentFrame = 0; // 初始化當前幀為 0
        this.scale = scale; // 設置縮放比例

        // 加載爆炸動畫的每一幀圖片
        for (int i = 1; i <= 5; i++) {
            explosionImages.add(ImageIO.read(new File("res/explosion" + i + ".png")));
        }
        this.totalFrames = explosionImages.size(); // 獲取總幀數

        // 將爆炸效果添加到渲染和更新列表中
        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    /**
     * 更新爆炸的狀態
     * @throws IOException 當更新過程中出現 I/O 錯誤時拋出
     */
    @Override
    public void update() throws IOException {
        long currentTime = System.currentTimeMillis(); // 獲取當前時間
        if (currentTime - startTime > duration) {
            // 移除爆炸效果
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
        } else {
            // 更新當前幀
            int frameDuration = (int) (duration / totalFrames); // 計算每幀持續時間
            currentFrame = (int) ((currentTime - startTime) / frameDuration); // 計算當前幀索引
            if (currentFrame >= totalFrames) {
                currentFrame = totalFrames - 1; // 確保不超出幀數範圍
            }
        }
    }

    @Override
    public int getLayer() {
        return 3; // 爆炸的渲染層級
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
        return explosionImages.get(currentFrame).getWidth() * scale; // 獲取當前幀的寬度並進行縮放
    }

    @Override
    public double getHeight() {
        return explosionImages.get(currentFrame).getHeight() * scale; // 獲取當前幀的高度並進行縮放
    }

    @Override
    public BufferedImage getBufferedImage() {
        return explosionImages.get(currentFrame); // 獲取當前幀的圖像
    }

    @Override
    public String getID() {
        return "explosion"; // 返回 ID
    }

    @Override
    public Renderable getRenderable() {
        return this; // 返回當前對象
    }

    @Override
    public boolean drawCollisionBox() {
        return false; // 不繪製碰撞框
    }
}
