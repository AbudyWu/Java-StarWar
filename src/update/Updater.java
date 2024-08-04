package update;

import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.emo.GameState;

/**
 * 更新所有變動物件的當前位置
 * @author B11007143
 * @version final
 */
public class Updater {
	private static ArrayList<Updateable> updateableObjects = new ArrayList<Updateable>();
	private static ArrayList<Updateable> addUpdateableObjects = new ArrayList<Updateable>();
	private static ArrayList<Updateable> removeUpdateableObjects = new ArrayList<Updateable>();
	
	public static void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		GameState currentState = core.emo.getCurrentState();
		if(currentState == GameState.START_MENU || currentState == GameState.GAME_STOP) {
			return;
		}else if(currentState == GameState.IN_GAME || currentState == GameState.GAME_OVER){
			for (Updateable object: updateableObjects) {
				object.update();
			}
			updateableObjects.removeAll(removeUpdateableObjects);
			updateableObjects.addAll(addUpdateableObjects);
			
			addUpdateableObjects.clear();
			removeUpdateableObjects.clear();
		}	
	}
	
	/**
	 * @param object 將該可更新物件加到更新暫存
	 */
	public static void addUpdateableObject(Updateable object) {
		addUpdateableObjects.add(object);
	}
	/**
	 * @param object 將該可更新物件加到刪除暫存
	 */
	public static void removeUpdateableObject(Updateable object) {
		removeUpdateableObjects.add(object);
	}
	/**
	 * @return 訪問目前更新暫存陣列
	 */
	public static ArrayList<Updateable> getUpdateableObject() {
		return updateableObjects;
	}
}
