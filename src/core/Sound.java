package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Sound 管理類別
 * 此類別用於管理遊戲中的音效和背景音樂的播放。
 */
public class Sound {
    // 用於播放背景音樂的 Clip
    private static Clip backgroundClip;
    // 緩存已載入的音效 Clip
    private static Map<String, Clip> clipCache = new HashMap<>();

    /**
     * 播放指定的音效。
     * 如果音效未載入，則將其載入並緩存。
     * @param soundFileName 音效檔案的路徑
     * @throws IOException 當音效檔案無法讀取時拋出
     * @throws UnsupportedAudioFileException 當音效檔案格式不支持時拋出
     * @throws LineUnavailableException 當音頻行無法取得時拋出
     */
    public static void playSound(String soundFileName)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // 如果音效未緩存，則載入並緩存
        if (!clipCache.containsKey(soundFileName)) {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFileName));

            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            clip.open(decodedAudioStream);
            clipCache.put(soundFileName, clip);
        }

        // 從緩存中取得 Clip 並播放
        Clip clip = clipCache.get(soundFileName);
        clip.setFramePosition(0);
        clip.start();

        // 創建新執行緒來等待音效播放完畢
        new Thread(() -> {
            try {
                while (!clip.isRunning())
                    Thread.sleep(10);
                while (clip.isRunning())
                    Thread.sleep(10);
                clip.drain();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 播放指定的背景音樂，並設置為循環播放。
     * @param filePath 背景音樂檔案的路徑
     * @throws UnsupportedAudioFileException 當音效檔案格式不支持時拋出
     * @throws IOException 當音效檔案無法讀取時拋出
     * @throws LineUnavailableException 當音頻行無法取得時拋出
     */
    public static void playLoop(String filePath)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stopBackground(); // 停止當前播放的背景音樂
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        backgroundClip = AudioSystem.getClip();
        backgroundClip.open(audioInputStream);
        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // 循環播放
        backgroundClip.start();
    }

    /**
     * 停止背景音樂的播放。
     */
    public static void stopBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    /**
     * 恢復背景音樂的播放。
     */
    public static void resumeBackground() {
        if (backgroundClip != null && !backgroundClip.isRunning()) {
            backgroundClip.start();
        }
    }

    /**
     * 暫停背景音樂的播放。
     */
    public static void pauseBackground() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }
}
