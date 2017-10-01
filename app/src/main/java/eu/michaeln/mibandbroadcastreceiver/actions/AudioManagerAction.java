package eu.michaeln.mibandbroadcastreceiver.actions;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;

public class AudioManagerAction implements ButtonAction {
    @Override
    public void Invoke(Context context) {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        long timestamp = SystemClock.uptimeMillis();

        KeyEvent downEvent = new KeyEvent(timestamp, timestamp, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        audioManager.dispatchMediaKeyEvent(downEvent);
    }
}
