package eu.michaeln.mibandbroadcastreceiver.actions;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;
import eu.michaeln.mibandbroadcastreceiver.ConfigurationDoneEvent;

public class HttpRequestAction extends ButtonAction {
    private String _url;

    @Override
    public String getName() {
        return "HTTP Request Action";
    }

    @Override
    public String getDescription() {
        return "Gets " + _url;
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

        builder.setTitle("URL to call")
                .setView(container)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _url = input.getText()
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
        final int id = (int)(new Date().getTime() * Math.random() % Integer.MAX_VALUE);

        final NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle("Request Action")
                .setSmallIcon(android.R.drawable.presence_online);

        final RequestQueue queue = Volley.newRequestQueue(context);
        final int[] statusArrayWorkaround = new int[1];

        final StringRequest request = new StringRequest(Request.Method.GET, _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String message = "Request finished (" + statusArrayWorkaround[0] + ")\n" + _url;

                builder.setStyle(new Notification.BigTextStyle().bigText(message));
                manager.notify(id, builder.build());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final String message = "Error while getting\n" + _url;

                builder.setStyle(new Notification.BigTextStyle().bigText(message));
                manager.notify(id, builder.build());
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusArrayWorkaround[0] = response.statusCode;

                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }
}
