package eu.michaeln.mibandbroadcastreceiver.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.ButtonAction;
import eu.michaeln.mibandbroadcastreceiver.ButtonActionRepository;
import eu.michaeln.mibandbroadcastreceiver.R;

public class ButtonActionListAdapter extends ArrayAdapter<ButtonAction> {
    private final List<ButtonAction> _actions;
    private final ButtonActionRepository _repository;

    public ButtonActionListAdapter(@NonNull Context context, List<ButtonAction> actions) {
        super(context, R.layout.list_item_buttonaction, actions);

        _actions = actions;
        _repository = new ButtonActionRepository(context.getApplicationContext());
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        _repository.saveActions(_actions);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_buttonaction, null);
        }

        final ButtonAction action = getItem(position);

        if (action != null) {
            final CheckBox buttonActionEnabled = (CheckBox)convertView.findViewById(R.id.buttonActionEnabled);
            final TextView buttonActionName = (TextView)convertView.findViewById(R.id.buttonActionName),
                           buttonActionDescription = (TextView)convertView.findViewById(R.id.buttonActionDescription),
                           deleteButtonAction = (TextView)convertView.findViewById(R.id.deleteButtonAction);

            buttonActionEnabled.setChecked(action.isEnabled());
            buttonActionEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    action.setEnabled(b);
                    notifyDataSetChanged();
                }
            });

            buttonActionName.setText(action.getName());
            buttonActionDescription.setText(action.getDescription());

            deleteButtonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("Do you really want to delete " + action.getName() + "?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    _actions.remove(action);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        return convertView;
    }
}
