package eu.michaeln.mibandbroadcastreceiver.actions;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;
import eu.michaeln.mibandbroadcastreceiver.ConfigurationDoneEvent;

public class NotificationAction extends ButtonAction {
    private static int NOTIFICATION_ID = 0;

    private int _id;
    private String _text;

    public NotificationAction() {
        _id = ++NOTIFICATION_ID;
        _text = "";
    }

    @Override
    public String getName() {
        return "Notification Action";
    }

    @Override
    public String getDescription() {
        return "Displays '" + _text + "' in a notification";
    }

    @Override
    public void configureAction(Context context, final ConfigurationDoneEvent configurationDone) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText input = new EditText(context);

        final FrameLayout container = new FrameLayout(context);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMarginStart(50);
        params.setMarginEnd(50);
        input.setLayoutParams(params);

        container.addView(input);

        builder.setTitle("Text to display")
                .setView(container)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _text = input.getText()
                                    .toString();

                        configurationDone.call();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void invoke(Context context) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentTitle("Mi Band Broadcast Receiver")
                .setContentText(_text)
                .setSmallIcon(android.R.drawable.presence_online);

        final NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(_id, builder.build());
    }
}
