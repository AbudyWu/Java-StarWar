package core;

import update.*;
import render.*;

import java.io.IOException;

/**
 * 計時器類別 此類別用於設置和管理計時器，並且可以與遊戲的更新循環集成。
 */
public class Timer implements Updateable {
	// 計時器的時間（毫秒）
	int setMillisTime = 0;
	// 計時器的初始時間（毫秒）
	int beginningMillisTime = setMillisTime;

	/**
	 * 構造函數 初始化計時器並將其添加到可更新對象列表中。
	 * 
	 * @param setMillisTime 設置的計時時間（毫秒）
	 */
	public Timer(int setMillisTime) {
		this.beginningMillisTime = setMillisTime;
		Updater.addUpdateableObject(this);
	}

	/**
	 * 更新計時器時間 減去自上次更新以來的時間增量。
	 * 
	 * @throws IOException 當更新過程中出現 I/O 錯誤時拋出
	 */
	@Override
	public void update() throws IOException {
		setMillisTime -= FPS.getDeltaTime() * 1000;
	}

	/**
	 * 判斷計時器是否響鈴
	 * 
	 * @return 如果計時器時間已到，返回 true；否則返回 false
	 */
	public boolean isRinging() {
		return setMillisTime <= 0;
	}

	/**
	 * 重置計時器到初始時間
	 */
	public void resetTimer() {
		setMillisTime = beginningMillisTime;
	}

	/**
	 * 獲取計時器的 ID
	 * 
	 * @return 返回計時器的 ID，此處為 null
	 */
	@Override
	public String getID() {
		return null;
	}

	/**
	 * 獲取計時器的可渲染對象
	 * 
	 * @return 返回計時器的可渲染對象，此處為 null
	 */
	@Override
	public Renderable getRenderable() {
		return null;
	}
}
