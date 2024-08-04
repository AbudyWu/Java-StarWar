package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Input 管理類別 此類別用於管理鍵盤和滑鼠輸入。
 */
public class Input implements KeyListener {

	// 定義常量對應按鍵
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int SPACE = 4;
	public static final int ENTER = 5;
	public static final int ESCAPE = 6;
	public static final int RESTART = 7;
	public static final int QUIT = 8;

	// 存儲按鍵狀態的數組
	public static boolean[] keys = new boolean[9];
	/**
	 * 當鍵盤按鍵被按下時調用
	 * 
	 * @param e KeyEvent 事件
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keys[LEFT] = true;
			break;
		case KeyEvent.VK_RIGHT:
			keys[RIGHT] = true;
			break;
		case KeyEvent.VK_UP:
			keys[UP] = true;
			break;
		case KeyEvent.VK_DOWN:
			keys[DOWN] = true;
			break;
		case KeyEvent.VK_SPACE:
			keys[SPACE] = true;
			break;
		case KeyEvent.VK_ENTER:
			keys[ENTER] = true;
			break;
		case KeyEvent.VK_ESCAPE:
			keys[ESCAPE] = true;
			break;
		case KeyEvent.VK_R:
			keys[RESTART] = true;
			break;
		case KeyEvent.VK_Q:
			keys[QUIT] = true;
			break;
		}
	}

	/**
	 * 當鍵盤按鍵被釋放時調用
	 * 
	 * @param e KeyEvent 事件
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			keys[LEFT] = false;
			break;
		case KeyEvent.VK_RIGHT:
			keys[RIGHT] = false;
			break;
		case KeyEvent.VK_UP:
			keys[UP] = false;
			break;
		case KeyEvent.VK_DOWN:
			keys[DOWN] = false;
			break;
		case KeyEvent.VK_SPACE:
			keys[SPACE] = false;
			break;
		case KeyEvent.VK_ENTER:
			keys[ENTER] = false;
			break;
		case KeyEvent.VK_ESCAPE:
			keys[ESCAPE] = false;
			break;
		case KeyEvent.VK_R:
			keys[RESTART] = false;
			break;
		case KeyEvent.VK_Q:
			keys[QUIT] = false;
			break;
		}
	}

	/**
	 * 當鍵盤按鍵被按下並釋放時調用（不使用）
	 * 
	 * @param e KeyEvent 事件
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// 不需要實現
	}

}
