package eu.michaeln.mibandbroadcastreceiver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.actions.VibratorAction;
import eu.michaeln.mibandbroadcastreceiver.adapters.ButtonActionListAdapter;

public class MainActivity extends AppCompatActivity {
    private ButtonActionRepository _repository;
    private ListView _buttonActionList;
    private FloatingActionButton _addButtonActionFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _repository = new ButtonActionRepository(this);
        _buttonActionList = (ListView)findViewById(R.id.buttonActionList);
        _addButtonActionFAB = (FloatingActionButton)findViewById(R.id.addButtonActionFAB);

        final List<ButtonAction> actions = _repository.getActions();
        final ButtonActionListAdapter adapter = new ButtonActionListAdapter(this, actions);

        _buttonActionList.setAdapter(adapter);

        _addButtonActionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonAction action = new VibratorAction();

                adapter.add(action);
            }
        });
    }
}
