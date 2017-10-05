package com.marceljurtz.lifecounter.Dicing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.R;

public class DicingActivity extends AppCompatActivity implements IDicingView {

    private IDicingPresenter presenter;
    private TextView lblDicing;
    private RelativeLayout rlDicing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicing);

        presenter = new DicingPresenter(this);
        presenter.onCreate();

        lblDicing = (TextView)findViewById(R.id.lblDicing);
        rlDicing = (RelativeLayout)findViewById(R.id.rlDicing);
        rlDicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onScreenTap();
            }
        });
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

    @Override
    public void setDicingText(String text) {
        lblDicing.setText(text);
    }
}
