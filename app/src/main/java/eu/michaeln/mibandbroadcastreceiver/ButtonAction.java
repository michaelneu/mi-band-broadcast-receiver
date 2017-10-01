package eu.michaeln.mibandbroadcastreceiver;

import android.content.Context;

import java.io.Serializable;

public abstract class ButtonAction implements Serializable {
    private boolean _isEnabled = true;

    public boolean isEnabled() {
        return _isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        _isEnabled = isEnabled;
    }

    public abstract String getName();
    public abstract String getDescription();

    public abstract void Invoke(Context context);
}
