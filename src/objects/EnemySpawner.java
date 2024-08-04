package objects;

import java.io.IOException;

import core.Timer;
import render.Renderable;
import update.Updateable;
import update.Updater;

/**
 * EnemySpawner 類
 * 負責生成敵人和小行星的更新邏輯。
 */
public class EnemySpawner implements Updateable {
    private Timer enemySpawnTimer = new Timer(5000); // 設置敵人生成間隔的計時器
    private Timer asteroidSpawnTimer = new Timer(1000); // 設置小行星生成間隔的計時器

    /**
     * 構造函數
     * 將 EnemySpawner 添加到更新列表中。
     */
    public EnemySpawner() {
        Updater.addUpdateableObject(this);
    }

    /**
     * 更新方法
     * 根據計時器的狀態生成敵人或小行星。
     */
    @Override
    public void update() throws IOException {
        if (enemySpawnTimer.isRinging()) {
            // 根據計分生成不同等級的敵人
            if (objects.Text.Count >= 50) {
                new Enemy(2); // 生成高等級敵人
            }else if(objects.Text.Count >= 100) {
            	new Enemy(3);
            }else if(objects.Text.Count >= 200) {
            	new Enemy(4);
            }
            else {
                new Enemy(1); // 生成低等級敵人
            }
            enemySpawnTimer.resetTimer();
        } 

        if (asteroidSpawnTimer.isRinging()) {
            // 當計分大於等於50時生成小行星
            if (objects.Text.Count >= 50) {
                new Asteroid();
            }
            asteroidSpawnTimer.resetTimer();
        }
    }

    /**
     * 獲取對象的 ID
     * @return 空值，因為此類沒有使用 ID
     */
    @Override
    public String getID() {
        return null;
    }

    /**
     * 獲取渲染對象
     * @return 空值，因為此類不渲染任何對象
     */
    @Override
    public Renderable getRenderable() {
        return null;
    }
}
