package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.Sound;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * Shield 類
 * 此類負責渲染和更新護盾，並實現了 Renderable 和 Updateable 接口。
 */
public class Shield implements Renderable, Updateable {

    // 護盾的寬度和高度
    public static double width = 75;
    private static double height = 75;

    // 護盾的 x 和 y 座標
    private double x;
    private double y;

    // 渲染層級
    private int layer = 1;

    // 關聯的 Spaceship 對象
    private Spaceship spaceship;

    // 護盾的圖像
    private static BufferedImage shieldImage;

    /**
     * 構造函數
     * 初始化護盾圖像並將其添加到渲染和更新列表中。
     * @param spaceship 關聯的 Spaceship 對象
     * @throws IOException 當讀取護盾圖像時發生 I/O 錯誤時拋出
     * @throws UnsupportedAudioFileException 當音頻文件格式不受支持時拋出
     * @throws LineUnavailableException 當音頻行不可用時拋出
     */
    public Shield(Spaceship spaceship) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.spaceship = spaceship;
        this.x = spaceship.getX();
        this.y = spaceship.getY();

        shieldImage = ImageIO.read(new File("res/shield01.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    /**
     * 獲取護盾的寬度
     * @return 護盾的寬度
     */
    public double getWidth() {
        return width;
    }

    /**
     * 獲取護盾的高度
     * @return 護盾的高度
     */
    public double getHeight() {
        return height;
    }

    /**
     * 獲取護盾的 x 座標
     * @return 護盾的 x 座標
     */
    public double getX() {
        return x;
    }

    /**
     * 獲取護盾的 y 座標
     * @return 護盾的 y 座標
     */
    public double getY() {
        return y;
    }

    /**
     * 獲取護盾的渲染層級
     * @return 渲染層級
     */
    public int getLayer() {
        return layer;
    }

    /**
     * 更新護盾的位置和檢測碰撞
     * @throws IOException 當更新過程中發生 I/O 錯誤時拋出
     * @throws UnsupportedAudioFileException 當音頻文件格式不受支持時拋出
     * @throws LineUnavailableException 當音頻行不可用時拋出
     */
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // 更新護盾的位置，使其跟隨飛船
        this.x = spaceship.getX();
        this.y = spaceship.getY();

        // 檢測與小行星和敵方子彈的碰撞
        Updateable collidingObjectAsteroid = isColliding(this, "asteroid");
        Updateable collidingObjectEnemyBullet = isColliding(this, "enemybullet");

        // 如果護盾的耐久度為 0，移除護盾
        if (objects.Text.Shield <= 0) {
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);
            Sound.playSound("res/Sound/shieldbreak.wav");
            core.emo.shieldalive = false;
        }

        // 處理與小行星的碰撞
        if (collidingObjectAsteroid != null) {
            Updater.removeUpdateableObject(collidingObjectAsteroid);
            Renderer.removeRenderableObject(collidingObjectAsteroid.getRenderable());
            new Explosion(x, y, 0.3);
            Sound.playSound("res/Sound/enemyexplosion.wav");
            objects.Text.Shield -= 10;
        }

        // 處理與敵方子彈的碰撞
        if (collidingObjectEnemyBullet != null) {
            Updater.removeUpdateableObject(collidingObjectEnemyBullet);
            Renderer.removeRenderableObject(collidingObjectEnemyBullet.getRenderable());
            new Explosion(x, y, 0.3);
            Sound.playSound("res/Sound/enemyexplosion.wav");
            objects.Text.Shield -= 5;
        }
    }

    /**
     * 獲取護盾的圖像
     * @return 護盾的圖像
     */
    @Override
    public BufferedImage getBufferedImage() {
        return shieldImage;
    }

    /**
     * 獲取護盾的 ID
     * @return 護盾的 ID，這裡返回 "shield"
     */
    @Override
    public String getID() {
        return "shield";
    }

    /**
     * 獲取護盾的 Renderable 對象
     * @return 護盾的 Renderable 對象
     */
    @Override
    public Renderable getRenderable() {
        return this;
    }

    /**
     * 確定是否繪製碰撞框
     * @return false，表示不繪製碰撞框
     */
    @Override
    public boolean drawCollisionBox() {
        return false;
    }
}
