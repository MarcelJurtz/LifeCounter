package com.marceljurtz.lifecounter.Counter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.marceljurtz.lifecounter.Helper.Counter;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.R;

import java.util.ArrayList;

public class CounterActivity extends AppCompatActivity {

    private CounterPresenter presenter;

    // TODO
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO
        players = new ArrayList<>();
        players.add(new Player(PlayerID.ONE));
        players.add(new Player(PlayerID.TWO));
        players.add(new Player(PlayerID.THREE));
        players.add(new Player(PlayerID.FOUR));

        final ArrayAdapter<Player> adapter =
                new ArrayAdapter<Player>(getApplicationContext(), R.layout.spinner_item, players);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(CounterActivity.this);
                dialog.setContentView(R.layout.dialog_countermanager_new);
                //dialog.setTitle("Title...");

                final EditText txtCardDescription = (EditText)findViewById(R.id.txtCardDescription);
                final EditText txtATK = (EditText)dialog.findViewById(R.id.txtCardAtk);
                final EditText txtDEF = (EditText)dialog.findViewById(R.id.txtCardDef);
                Spinner spPlayers = (Spinner)dialog.findViewById(R.id.spUserSelection);
                spPlayers.setAdapter(adapter);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Counter counter = new Counter(txtCardDescription.getText().toString(),
                                    Integer.parseInt(txtATK.getText().toString()),
                                    Integer.parseInt(txtDEF.getText().toString()));
                        } catch(Exception ex) {
                            Snackbar.make(findViewById(android.R.id.content), R.string.dialog_countermanager_error_invalid_entry, Snackbar.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        presenter = new CounterPresenter();
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }
}
