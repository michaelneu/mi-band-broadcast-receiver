package eu.michaeln.mibandbroadcastreceiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.adapters.ButtonActionListAdapter;

public class MainActivity extends AppCompatActivity {
    private final ButtonActionRepository _repository;
    private ListView _buttonActionList;

    public MainActivity() {
        _repository = new ButtonActionRepository();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _buttonActionList = (ListView)findViewById(R.id.buttonActionList);

        final List<ButtonAction> actions = _repository.getActions();
        final ButtonActionListAdapter adapter = new ButtonActionListAdapter(this, actions);

        _buttonActionList.setAdapter(adapter);
    }
}
