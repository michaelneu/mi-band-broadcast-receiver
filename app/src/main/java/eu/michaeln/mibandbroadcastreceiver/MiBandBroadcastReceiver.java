package eu.michaeln.mibandbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class MiBandBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final ButtonActionRepository repository = new ButtonActionRepository(context);
        final List<ButtonAction> actions = repository.getActions();

        for (ButtonAction action : actions) {
            if (action.isEnabled()) {
                action.invoke(context);
            }
        }
    }
}
