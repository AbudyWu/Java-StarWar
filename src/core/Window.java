package core;

import javax.swing.JFrame;

/**
 * Window 類別
 * 此類別用於創建和管理遊戲窗口。
 */
public class Window extends JFrame {
    // 窗口的寬度
    private static double winWidth = 500;
    // 窗口的高度
    private static double winHeight = 600;
    // 窗口的名稱
    private static String winName;
    // 計數器變量
    public static int Count;

    /**
     * 構造函數
     * 初始化窗口屬性。
     * @param winName 窗口名稱
     * @param winWidth 窗口寬度
     * @param winHeight 窗口高度
     */
    public Window(String winName, double winWidth, double winHeight) {
        super(winName);
        
        Window.winWidth = winWidth;
        Window.winHeight = winHeight;

        setWindowAttributes();
    }
    
    /**
     * 設置窗口大小、不可調整大小並將其居中顯示。
     */
    public void packWindow() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * 設置窗口屬性
     * 設置窗口的默認關閉操作。
     */
    private void setWindowAttributes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 獲取窗口寬度
     * @return 窗口寬度
     */
    public static double getwinWidth() {
        return winWidth;
    }

    /**
     * 獲取窗口高度
     * @return 窗口高度
     */
    public static double getwinHeight() {
        return winHeight;
    }

    /**
     * 獲取窗口名稱
     * @return 窗口名稱
     */
    public static String winName() {
        return winName;
    }
}
