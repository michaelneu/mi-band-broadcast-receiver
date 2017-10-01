package eu.michaeln.mibandbroadcastreceiver;

import java.util.ArrayList;
import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.actions.AudioManagerAction;
import eu.michaeln.mibandbroadcastreceiver.actions.VibratorAction;

public class ButtonActionRepository {
    public List<ButtonAction> getActions() {
        List<ButtonAction> actions = new ArrayList<>();

        actions.add(new VibratorAction());
        actions.add(new AudioManagerAction());

        return actions;
    }

    public void saveActions(List<ButtonAction> actions) {

    }
}
