package objects;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.FPS;
import core.Sound;
import core.Window;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

/**
 * Enemy 類別
 * 此類別用於表示敵人對象，並實現了 Updateable 和 Renderable 接口。
 */
public class Enemy implements Updateable, Renderable {
    // 敵人的寬度和高度
    private double width;
    private double height;
    // 敵人的 X 和 Y 坐標
    private double x;
    private double y;
    // 敵人的速度
    private double speed = 100;
    
    // 移動方向
    private int directionX = 0;
    private int directionY = 0;
    // 上次發射子彈的時間
    private long lastShotTime = 0;
    // 發射子彈的時間間隔（毫秒）
    private long shotInterval = 2000;
    // 上次改變方向的時間
    private long lastDirectionChangeTime = 0;
    // 改變方向的時間間隔（毫秒）
    private long directionChangeInterval = 1000;  
    // 隨機數生成器
    private Random rand = new Random();
    
    // 敵人的等級
    public int grade;
    
    // 渲染層級
    private final int layer = 2;
    // 敵人圖像
    private static BufferedImage enemyImage;

    /**
     * 構造函數
     * 初始化敵人對象並將其添加到渲染和更新列表中。
     * @param a 敵人的等級
     * @throws IOException 當讀取圖像失敗時拋出
     */
    public Enemy(int a) throws IOException {
        int dimensions = 50; 
        grade = a;
        
        width = dimensions;
        height = dimensions;

        // 設置敵人初始位置
        int posX = rand.nextInt((int) Window.getwinWidth() - (int) getWidth() + 1);
        this.x = posX;
        this.y = 0; 

        // 加載敵人圖像
        enemyImage = ImageIO.read(new File("res/enemyship.png"));

        // 將敵人對象添加到渲染和更新列表中
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
        return enemyImage;
    }

    /**
     * 更新敵人的狀態
     * @throws IOException 當更新過程中出現 I/O 錯誤時拋出
     * @throws UnsupportedAudioFileException 當音頻文件格式不支持時拋出
     * @throws LineUnavailableException 當音頻線路不可用時拋出
     */
    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        long currentTime = System.currentTimeMillis();
        
        // 隨機改變移動方向
        if (currentTime - lastDirectionChangeTime >= (directionChangeInterval / grade)) {
            directionX = rand.nextInt(3) - 1; // -1, 0, 或 1
            directionY = rand.nextInt(3) - 1; // -1, 0, 或 1
            lastDirectionChangeTime = currentTime;
        }
        
        // 更新速度以產生更隨機的運動效果
        double randomSpeedX = grade * 0.5 * speed * (0.5 + rand.nextDouble()); // 隨機速度，範圍在 0.5 * speed 到 1.5 * speed 之間
        double randomSpeedY = grade * 0.5 * speed * (0.5 + rand.nextDouble());
       
        // 移動敵人
        x += directionX * randomSpeedX * FPS.getDeltaTime();
        y += directionY * randomSpeedY * FPS.getDeltaTime();

        // 確保敵人不會移出屏幕
        if (x < 0) {
            x = 0;
        } else if (x > Window.getwinWidth() - width) {
            x = Window.getwinWidth() - width;
        }
        if (y < 0) {
            y = 0;
        } else if (y > (Window.getwinHeight() / 2) - height) {
            y = (Window.getwinHeight() / 2) - height;
        }

        // 發射子彈
        if (currentTime - lastShotTime >= (shotInterval / grade)) {
            new EnemyBullet(x + (getWidth() / 2), y + getHeight(), grade);
            lastShotTime = currentTime;
        }

        // 檢測子彈碰撞
        Updateable collidingObject = isColliding(this, "bullet");
        if (collidingObject != null) {
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdateableObject(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());
            
            new Explosion(x, y, 0.5);
            Sound.playSound("res/Sound/enemyexplosion.wav");
            
            double dropChance = 0.2; // 20% 掉落機率
            if (Math.random() < dropChance) {
                HealthItem healthItem = new HealthItem(x, y);
                Updater.addUpdateableObject(healthItem);
                Renderer.addRenderableObject(healthItem);
            }
            
            objects.Text.Count += 10;
        }
    }

    @Override
    public String getID() {
        return "enemy";
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
