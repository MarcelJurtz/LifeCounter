package com.marceljurtz.lifecounter.About;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.marceljurtz.lifecounter.R;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity implements IAboutView {

    IAboutPresenter presenter;
    WebView mainWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mainWebView = (WebView)findViewById(R.id.wvAbout);

        presenter = new AboutPresenter(this, Locale.getDefault().getDisplayLanguage());
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

    @Override
    public void loadAboutPage(String url) {
        mainWebView.loadUrl(url);
    }
}
