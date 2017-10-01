package eu.michaeln.mibandbroadcastreceiver.actions;

import android.content.Context;
import android.os.Vibrator;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;

public class VibratorAction extends ButtonAction {
    @Override
    public String getName() {
        return "Vibrator Action";
    }

    @Override
    public String getDescription() {
        return "Vibrates the phone to signal the button was pressed";
    }

    @Override
    public void invoke(Context context) {
        final Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        final long[] vibes = { 0, 100, 100, 100, 100, 100 };

        vibrator.vibrate(vibes, -1);
    }
}
