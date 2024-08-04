package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * 渲染介面
 * @author B11007143
 * @version final
 */
public interface Renderable extends Comparable<Object> {

	/**
	 * @return 圖層
	 */
	public int getLayer();
	
	/**
	 * @return 物件x位置
	 */
	public double getX();
	
	/**
	 * @return 物件y位置
	 */
	public double getY();

	/**
	 * @return 物件寬度
	 */
	public double getWidth();

	/**
	 * @return 物件高度
	 */
	public double getHeight();

	/**
	 * @return 是否顯示碰撞有效匡
	 */
	public boolean drawCollisionBox();

	/**
	 * @return 回傳物件圖片
	 */
	public BufferedImage getBufferedImage();

	

	/**
	 * @param g 渲染且是否顯示 碰撞有效匡
	 */
	public default void drawSprite(Graphics2D g) {
		g.drawImage(getBufferedImage(), (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);

		if (drawCollisionBox()) {
			g.setColor(Color.GRAY);
			g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		}
	}

	/**
	 * 依途層排序渲染順序
	 */
	public default int compareTo(Object o) {
		Renderable object = (Renderable) o;

		if (getLayer() < object.getLayer()) {
			return -1;
		} else if (getLayer() > object.getLayer()) {
			return 1;
		} else {
			return 0;
		}

	}
	

}
