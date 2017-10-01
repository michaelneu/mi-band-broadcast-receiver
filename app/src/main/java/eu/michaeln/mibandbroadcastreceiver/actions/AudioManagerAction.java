package eu.michaeln.mibandbroadcastreceiver.actions;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;

public class AudioManagerAction extends ButtonAction {
    @Override
    public String getName() {
        return "Audio Manager Action";
    }

    @Override
    public String getDescription() {
        return "Starts the next audio track";
    }

    @Override
    public void invoke(Context context) {
        final AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        final long timestamp = SystemClock.uptimeMillis();
        final KeyEvent downEvent = new KeyEvent(timestamp, timestamp, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT, 0);

        audioManager.dispatchMediaKeyEvent(downEvent);
    }
}
