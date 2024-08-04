package objects;

import java.io.IOException;

import core.Timer;
import render.Renderable;
import update.Updateable;
import update.Updater;

/**
 * AsteroidSpawner 類別
 * 此類別負責生成小行星，並實現了 Updateable 接口。
 */
public class AsteroidSpawner implements Updateable {
    // 用於計時的小行星生成計時器
    Timer timer = new Timer(500);
    
    /**
     * 構造函數
     * 初始化小行星生成器並將其添加到更新列表中。
     */
    public AsteroidSpawner() {
        Updater.addUpdateableObject(this);
    }
    
    /**
     * 更新方法
     * 檢查計時器是否響鈴，如果響鈴則生成新的小行星，並重置計時器。
     * @throws IOException 當更新過程中出現 I/O 錯誤時拋出
     */
    @Override
    public void update() throws IOException {
        if (timer.isRinging()) {
            new Asteroid();
            timer.resetTimer();
        }
    }

    /**
     * 獲取 ID 方法
     * @return 返回 null，這裡沒有特定的 ID 需要返回
     */
    @Override
    public String getID() {
        return null;
    }

    /**
     * 獲取 Renderable 方法
     * @return 返回 null，因為此類別不涉及渲染
     */
    @Override
    public Renderable getRenderable() {
        return null;
    }
}
