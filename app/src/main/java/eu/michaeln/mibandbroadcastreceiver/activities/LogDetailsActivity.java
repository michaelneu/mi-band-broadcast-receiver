package eu.michaeln.mibandbroadcastreceiver.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import eu.michaeln.mibandbroadcastreceiver.R;
import eu.michaeln.mibandbroadcastreceiver.actions.LogAction;

public class LogDetailsActivity extends AppCompatActivity {
    private ListView _logList;
    private Toolbar _toolbar;
    private ArrayAdapter<String> _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        _logList = (ListView)findViewById(R.id.logList);

        final LogAction currentAction = LogAction.getCurrentInstance();

        _adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentAction.getLog());
        _logList.setAdapter(_adapter);

        _toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Log");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearLog:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Really clear the log?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogAction.getCurrentInstance().clear();
                                _adapter.clear();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
