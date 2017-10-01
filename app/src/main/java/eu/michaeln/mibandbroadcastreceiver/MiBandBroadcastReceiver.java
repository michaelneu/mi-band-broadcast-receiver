package eu.michaeln.mibandbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import eu.michaeln.mibandbroadcastreceiver.actions.VibratorAction;

public class MiBandBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ButtonAction action = new VibratorAction();

        action.Invoke(context);
    }
}
