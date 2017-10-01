package eu.michaeln.mibandbroadcastreceiver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.actions.AudioManagerAction;
import eu.michaeln.mibandbroadcastreceiver.actions.LogAction;
import eu.michaeln.mibandbroadcastreceiver.actions.NotificationAction;
import eu.michaeln.mibandbroadcastreceiver.actions.VibratorAction;
import eu.michaeln.mibandbroadcastreceiver.adapters.ButtonActionListAdapter;

public class MainActivity extends AppCompatActivity {
    private ButtonActionRepository _repository;
    private List<ButtonAction> _actions;
    private ListView _buttonActionList;
    private FloatingActionButton _addButtonActionFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _repository = new ButtonActionRepository(this);
        _buttonActionList = (ListView)findViewById(R.id.buttonActionList);
        _addButtonActionFAB = (FloatingActionButton)findViewById(R.id.addButtonActionFAB);

        _actions = _repository.getActions();
        final ButtonActionListAdapter adapter = new ButtonActionListAdapter(this, _actions);

        _buttonActionList.setAdapter(adapter);

        _addButtonActionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = MainActivity.this;
                final List<ButtonAction> availableActions = new ArrayList<>();

                availableActions.add(new AudioManagerAction());
                availableActions.add(new LogAction());
                availableActions.add(new NotificationAction());
                availableActions.add(new VibratorAction());

                final List<String> availableActionNames = new ArrayList<>();

                for (ButtonAction action : availableActions) {
                    availableActionNames.add(action.getName());
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final Spinner spinner = new Spinner(context);

                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, availableActionNames));

                final FrameLayout container = new FrameLayout(context);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.setMarginStart(50);
                params.setMarginEnd(50);
                spinner.setLayoutParams(params);

                container.addView(spinner);

                builder.setTitle("Select an action")
                        .setView(container)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String name = spinner.getSelectedItem().toString();
                                final int nameIndex = availableActionNames.indexOf(name);
                                final ButtonAction action = availableActions.get(nameIndex);

                                action.configureAction(context, new ConfigurationDoneEvent() {
                                    @Override
                                    public void call() {
                                        adapter.add(action);
                                    }
                                });
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        _repository.saveActions(_actions);
    }
}
