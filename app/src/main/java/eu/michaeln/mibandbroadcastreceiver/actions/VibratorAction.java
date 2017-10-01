package eu.michaeln.mibandbroadcastreceiver.actions;

import android.content.Context;
import android.os.Vibrator;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;

public class VibratorAction implements ButtonAction {
    @Override
    public void Invoke(Context context) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] vibes = { 0, 100, 100, 100, 100, 100 };
        vibrator.vibrate(vibes, -1);
    }
}
