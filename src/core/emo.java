package core;

//import java.awt.Component;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import objects.Background;

import objects.EnemySpawner;
import objects.Spaceship;

import render.Renderer;
import update.Updater;

/**
 * 主程式
 * 
 * @author B11007143
 * @version final
 */
public class emo {

	/**
	 * 遊戲的狀態
	 */
	public enum GameState {
		START_MENU, IN_GAME, GAME_STOP, GAME_OVER
	}

	public static GameState currentState = GameState.START_MENU;

	public static boolean spaceshipIsHit = false;
	public static boolean shieldalive = true;

	private static long lastEscPressTime = 0;
	private static final long ESC_PRESS_INTERVAL = 200;

	/**
	 * 主程式邏輯區
	 * 
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

		Window window = new Window("Game", Window.getwinWidth(), Window.getwinHeight());

		Renderer renderer = new Renderer();

		Updater updater = new Updater();

		window.addKeyListener(new Input());
		window.add(renderer);
		window.packWindow();
		window.setVisible(true);

		new Spaceship((Window.getwinWidth() / 2) - (Spaceship.width), Window.getwinHeight() - 150);
		new Background(-Window.getwinHeight());
		new EnemySpawner();

		FPS.calcBeginTime();

		while (true) {
			long currentTime = System.currentTimeMillis();

			if (Input.keys[Input.ENTER] && currentState == GameState.START_MENU) {
				setCurrentState(GameState.IN_GAME);
				Sound.playLoop("res/Sound/background.wav");
			} else if (Input.keys[Input.ESCAPE] && currentTime - lastEscPressTime > ESC_PRESS_INTERVAL) {
				lastEscPressTime = currentTime;
				if (getCurrentState() == GameState.IN_GAME) {
					setCurrentState(GameState.GAME_STOP);
					Sound.stopBackground();
				} else if (getCurrentState() == GameState.GAME_STOP) {
					setCurrentState(GameState.IN_GAME);
					Sound.resumeBackground();
				}
			}
			if (spaceshipIsHit) {
				setCurrentState(GameState.GAME_OVER);
			}
			if (Input.keys[Input.RESTART] & getCurrentState() == GameState.GAME_STOP) {
				resetGame();
			}else if (Input.keys[Input.QUIT] & getCurrentState() == GameState.GAME_STOP) {
				exitGame();
			}
			if (getCurrentState() == GameState.GAME_OVER) {
				Sound.pauseBackground();
				if (Input.keys[Input.RESTART]) {
					resetGame();
				} else if (Input.keys[Input.QUIT]) {
					exitGame();
				}
			}
			updater.update();
			renderer.repaint();
			FPS.calcDeltaTime();
		}

	}

	/**
	 * 將遊戲結束狀態切換成開始狀態並重置參數
	 * 
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public static void resetGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		Updater.getUpdateableObject().clear();
		Renderer.clearRenderableObjects();

		new Spaceship((Window.getwinWidth() / 2) - (Spaceship.width), Window.getwinHeight() - 150);
		new Background(-Window.getwinHeight());
		new EnemySpawner();
		objects.Text.Count = 0;
		objects.Text.HP = 100;
		objects.Text.Shield = 100;

		setCurrentState(GameState.START_MENU);
		spaceshipIsHit = false;
		shieldalive = true;

	}

	/**
	 * @return 當前遊戲的狀態
	 */
	public static GameState getCurrentState() {
		return currentState;
	}

	/**
	 * 設定遊戲狀態
	 * 
	 * @param state
	 */
	public static void setCurrentState(GameState state) {
		currentState = state;
	}

	/**
	 * 將程式關閉
	 */
	public static void exitGame() {
		System.exit(0);
	}
}