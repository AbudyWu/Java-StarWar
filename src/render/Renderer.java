package render;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import core.Window;
import core.emo.GameState;

/**
 * 渲染控制
 * @author B11007143
 * @version final
 */
public class Renderer extends JPanel {
	private static ArrayList<Renderable> renderableObjects = new ArrayList<Renderable>();
	private static ArrayList<Renderable> addRenderableObjects = new ArrayList<Renderable>();
	private static ArrayList<Renderable> removeRenderableObjects = new ArrayList<Renderable>();

	/**
	 *主要繪圖區，負責不同遊戲狀態的繪圖
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		GameState currentState = core.emo.getCurrentState();

		synchronized (renderableObjects) {
			if (currentState == GameState.START_MENU) {
				displayStartMenu(g2d);
			} else if (currentState == GameState.IN_GAME || currentState == GameState.GAME_STOP) {

				for (Renderable object : renderableObjects) {
					object.drawSprite(g2d);
					g.setColor(Color.PINK);
					g.setFont(new Font("標楷體", Font.BOLD | Font.ITALIC, 30));
					g.drawString("分數: " + objects.Text.Count, 350, 30);
				}
				drawHealthBar(g2d, 10, 10, 200, 20, objects.Text.HP, objects.Text.Shield);
				if (currentState == GameState.GAME_STOP) {
					displayPauseOverlay(g2d);
				}

			} else if (currentState == GameState.GAME_OVER) {
				for (Renderable object : renderableObjects) {
					object.drawSprite(g2d);
					g.setColor(Color.PINK);
					g.setFont(new Font("標楷體", Font.BOLD | Font.ITALIC, 30));
					g.drawString("分數: " + objects.Text.Count, 350, 30);
					displayGameOver(g2d);
				}
			}
			processRenderLists();
		}

	}

	/**
	 * 渲染開始畫面
	 * @param g 
	 */
	public void displayStartMenu(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		String title = "Space Invader";
		int titleWidth = g.getFontMetrics().stringWidth(title);
		g.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 2 - 50);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		String title2 = "Press ENTER to start";
		int titleWidth2 = g.getFontMetrics().stringWidth(title);
		g.drawString(title2, ((getWidth() - titleWidth) / 2) + 45, getHeight() / 2 + 50);

	}

	/**
	 * 渲染暫停畫面
	 * @param g 
	 */
	private void displayPauseOverlay(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 150)); // 半透明黑色
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		String title = "Game Paused";
		int titleWidth = g.getFontMetrics().stringWidth(title);
		g.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 2 - 50);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		String title2 = "Press ESC to resume";
		int titleWidth2 = g.getFontMetrics().stringWidth(title2);
		g.drawString(title2, (getWidth() - titleWidth2) / 2, getHeight() / 2 + 50);
		String title3 = "Press R to restart";
		int titleWidth3 = g.getFontMetrics().stringWidth(title3);
		g.drawString(title3, (getWidth() - titleWidth3) / 2, getHeight() / 2 + 74);
		String title4 = "Press Q to Exit";
		int titleWidth4 = g.getFontMetrics().stringWidth(title4);
		g.drawString(title4, (getWidth() - titleWidth4) / 2, getHeight() / 2 + 98);
	}

	/**
	 * 渲染遊戲結束畫面
	 * @param g
	 */
	private void displayGameOver(Graphics2D g) {
		g.setColor(new Color(0, 0, 0, 150)); // 半透明黑色
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		String title = "Game Over";
		int titleWidth = g.getFontMetrics().stringWidth(title);
		g.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 2 - 50);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		String score = "Score: " + objects.Text.Count;
		int scoreWidth = g.getFontMetrics().stringWidth(score);
		g.drawString(score, (getWidth() - scoreWidth) / 2, getHeight() / 2);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		String restartMsg = "Press R to Restart or Q to Quit";
		int restartWidth = g.getFontMetrics().stringWidth(restartMsg);
		g.drawString(restartMsg, (getWidth() - restartWidth) / 2, getHeight() / 2 + 50);
	}

	/**
	 * @param g
	 * @param x 血條 x位置
	 * @param y 血條 y位置
	 * @param width 血條 寬度
	 * @param height 血條 長度
	 * @param hp 血量值
	 * @param shield 護盾值
	 */
	private void drawHealthBar(Graphics2D g, int x, int y, int width, int height, int hp, int shield) {
		// 设置血量条的背景颜色
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.RED);
		int currentWidth = (int) (width * (hp / 100.0)); // 假设最大HP为100
		g.fillRect(x, y, currentWidth, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("標楷體", Font.BOLD, 15));
		g.drawString("HP: " + hp, x + 5, y + 15);

		if (shield > 0) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y + 20, width, height);
			g.setColor(Color.BLUE);
			int currentWidth1 = (int) ((width) * (shield / 100.0));
			g.fillRect(x, y + 20, currentWidth1, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y + 20, width, height);
			g.setColor(Color.WHITE);
			g.setFont(new Font("標楷體", Font.BOLD, 15));
			g.drawString("Shield: " + shield, x + 5, y + 35);
		}
	}

	/**
	 * 清除暫存區，並把當前暫存加到渲染陣列並依圖層做排序
	 */
	private void processRenderLists() {
		renderableObjects.removeAll(removeRenderableObjects);
		if (!addRenderableObjects.isEmpty()) {
			renderableObjects.addAll(addRenderableObjects);
			Collections.sort(renderableObjects);
		}
		addRenderableObjects.clear();
		removeRenderableObjects.clear();
	}

	public static void clearRenderableObjects() {
		renderableObjects.clear();
	}

	public static void addRenderableObject(Renderable object) {
		addRenderableObjects.add(object);
	}

	public static void removeRenderableObject(Renderable object) {
		removeRenderableObjects.add(object);
	}

	/**
	 *固定視窗大小
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) Window.getwinWidth(), (int) Window.getwinHeight());
	}

}
