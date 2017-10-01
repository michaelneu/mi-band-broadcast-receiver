package eu.michaeln.mibandbroadcastreceiver.actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;
import eu.michaeln.mibandbroadcastreceiver.activities.LogDetailsActivity;

public class LogAction extends ButtonAction {
    private static LogAction _currentInstance;

    public static LogAction getCurrentInstance() {
        return _currentInstance;
    }

    private final ArrayList<String> _log;

    public LogAction() {
        _log = new ArrayList<>();
    }

    public List<String> getLog() {
        return new ArrayList<>(_log);
    }

    public void clear() {
        _log.clear();
    }

    @Override
    public String getName() {
        return "Log Action";
    }

    @Override
    public String getDescription() {
        return "Logs whenever the button was pressed";
    }

    @Override
    public void showDetails(Context context) {
        _currentInstance = this;

        final Intent logDetailsIntent = new Intent(context, LogDetailsActivity.class);

        ((Activity)context).startActivityForResult(logDetailsIntent, 0);
    }

    @Override
    public void invoke(Context context) {
        final DateFormat format = SimpleDateFormat.getDateTimeInstance();
        final Date time = Calendar.getInstance().getTime();
        final String logEntry = format.format(time);

        _log.add(logEntry);
    }
}
