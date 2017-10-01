package eu.michaeln.mibandbroadcastreceiver;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ButtonActionRepository {
    private static final String ACTIONS_SETTING_NAME = "actions";
    private final SharedPreferences _preferences;

    public ButtonActionRepository(Context context) {
        _preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    private List<ButtonAction> deserializeActionsFromString(String serialized) {
        try {
            final byte[] bytes = Base64.decode(serialized, Base64.DEFAULT);
            final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            final List<ButtonAction> actions = (List<ButtonAction>)ois.readObject();

            ois.close();

            return actions;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ButtonAction> getActions() {
        final String serialized = _preferences.getString(ACTIONS_SETTING_NAME, "");

        return deserializeActionsFromString(serialized);
    }

    private String serializeActionsToString(List<ButtonAction> actions) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream( baos );

            oos.writeObject(actions);
            oos.close();

            final byte[] bytes = baos.toByteArray();

            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            return "";
        }
    }

    public void saveActions(List<ButtonAction> actions) {
        final SharedPreferences.Editor editor = _preferences.edit();
        final String serialized = serializeActionsToString(actions);

        editor.putString(ACTIONS_SETTING_NAME, serialized);
        editor.apply();
    }
}
