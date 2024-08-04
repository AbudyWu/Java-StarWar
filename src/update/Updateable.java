package update;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import render.Renderable;

/**
 * 可更新物件介面
 * @author B11007143
 * @version final
 */
public interface Updateable {
	/**
	 * 使承接這個介面的class能夠更新自身狀態
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException;

	/**
	 * @return 回傳自身物件ID
	 */
	public String getID();

	/**
	 * @return 將物件加入到可渲染
	 */
	public Renderable getRenderable();

	/**
	 * 碰撞判斷
	 * @param thisObject
	 * @param otherObjectID
	 * @return 碰撞到的物件
	 */
	public default Updateable isColliding(Renderable thisObject, String otherObjectID) {
		ArrayList<Updateable> objects = Updater.getUpdateableObject();
		for (Updateable object : objects) {
			if (object.getID() == otherObjectID) {
				if (thisObject.getX() < object.getRenderable().getX() + object.getRenderable().getWidth()
						&& thisObject.getX() + thisObject.getWidth() > object.getRenderable().getX()) {
					if (thisObject.getY() < object.getRenderable().getY() + object.getRenderable().getHeight()
							&& thisObject.getY() + thisObject.getHeight() > object.getRenderable().getY()) {
						return object;
					}
				}
			}
		}
		return null;
	}
}
