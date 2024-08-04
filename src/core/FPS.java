package core;

import java.time.Duration;
import java.time.Instant;

/**
 * 遊戲張數管理
 * 管理遊戲的每秒幀數 (FPS) 和時間增量 (delta time)。
 * 使用 java.time 包中的類來進行時間的測量和計算
 * @author B11007143
 * @version final
 */
public class FPS {
	// 上一次計算的時間間隔
    private static Duration fpsDeltaTime = Duration.ZERO;
    // 上一次計算時間
    private static Duration lastTime = Duration.ZERO;
    // 開始時間，用於計算幀間隔
    private static Instant beginTime = Instant.now();
    // 當前的時間增量，以毫秒為單位
    private static double deltaTime = fpsDeltaTime.toMillis() - lastTime.toMillis();

    /**
     * 計算並設置開始時間。
     * 將 beginTime 設置為當前時間，並重置 fpsDeltaTime。
     */
    public static void calcBeginTime() {
        beginTime = Instant.now();
        fpsDeltaTime = Duration.ZERO;
    }

    /**
     * 計算並更新時間增量 (delta time)。
     * 使用當前時間和開始時間之間的差來更新 fpsDeltaTime。
     * 並更新 deltaTime 和 lastTime。
     */
    public static void calcDeltaTime() {
        fpsDeltaTime = Duration.between(beginTime, Instant.now());
        deltaTime = (double) fpsDeltaTime.toMillis() - lastTime.toMillis();
        lastTime = fpsDeltaTime;
    }

    /**
     * 獲取當前的時間增量 (delta time)。
     * @return 當前時間增量（以秒為單位）。
     */
    public static double getDeltaTime() {
        return deltaTime / 1000;
    }
}
