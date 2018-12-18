package com.marceljurtz.lifecounter.views.Intro;

import android.app.Activity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.AppDetails;
import com.marceljurtz.lifecounter.models.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int red = new com.marceljurtz.lifecounter.models.Color(MagicColorEnum.RED, getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).getIntValue();
        int green = new com.marceljurtz.lifecounter.models.Color(MagicColorEnum.GREEN, getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).getIntValue();
        int blue = new com.marceljurtz.lifecounter.models.Color(MagicColorEnum.BLUE, getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).getIntValue();
        int white = new com.marceljurtz.lifecounter.models.Color(MagicColorEnum.WHITE, getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).getIntValue();
        int black = new com.marceljurtz.lifecounter.models.Color(MagicColorEnum.BLACK, getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).getIntValue();

        if(AppDetails.IS_PRO_MODE) {

            // Slide 1 - Welcome
            SliderPage pg1 = new SliderPage();
            pg1.setTitle(getResources().getString(R.string.intro_1_header));
            pg1.setDescription(getResources().getString(R.string.intro_1_description));
            pg1.setImageDrawable(R.drawable.icon_small);
            pg1.setBgColor(blue);

            // Slide 2 - Navigation
            SliderPage pg2 = new SliderPage();
            pg2.setTitle(getResources().getString(R.string.intro_2_header));
            pg2.setDescription(getResources().getString(R.string.intro_2_description));
            pg2.setImageDrawable(R.drawable.intro_navdrawer);
            pg2.setBgColor(red);

            // Slide 3 - Settings
            SliderPage pg3 = new SliderPage();
            pg3.setTitle(getResources().getString(R.string.intro_3_header));
            pg3.setDescription(getResources().getString(R.string.intro_3_description));
            pg3.setImageDrawable(R.drawable.ic_settings_black_128dp);
            pg3.setBgColor(blue);

            // Slide 4 - Game
            SliderPage pg4 = new SliderPage();
            pg4.setTitle(getResources().getString(R.string.intro_4_header));
            pg4.setDescription(getResources().getString(R.string.intro_4_description));
            pg4.setImageDrawable(R.drawable.intro_game);
            pg4.setBgColor(red);

            // Slide 5 - Energy Saving Mode
            SliderPage pg5 = new SliderPage();
            pg5.setTitle(getResources().getString(R.string.intro_5_header));
            pg5.setDescription(getResources().getString(R.string.intro_5_description));
            pg5.setImageDrawable(R.drawable.ic_power_black_128dp);
            pg5.setBgColor(blue);

            // Slide 6 - Dicing
            SliderPage pg6 = new SliderPage();
            pg6.setTitle(getResources().getString(R.string.intro_6_header));
            pg6.setDescription(getResources().getString(R.string.intro_6_description));
            pg6.setImageDrawable(R.drawable.ic_casino_black_128dp);
            pg6.setBgColor(red);

            // Slide 7 - Counter
            SliderPage pg7 = new SliderPage();
            pg7.setTitle(getResources().getString(R.string.intro_7_header));
            pg7.setDescription(getResources().getString(R.string.intro_7_description));
            pg7.setImageDrawable(R.drawable.intro_counter);
            pg7.setBgColor(blue);

            // Slide 8 - About
            SliderPage pg8 = new SliderPage();
            pg8.setTitle(getResources().getString(R.string.intro_8_header));
            pg8.setDescription(getResources().getString(R.string.intro_8_description));
            pg8.setImageDrawable(R.drawable.ic_check_black_128dp);
            pg8.setBgColor(red);

            addSlide(AppIntroFragment.newInstance(pg1));
            addSlide(AppIntroFragment.newInstance(pg2));
            addSlide(AppIntroFragment.newInstance(pg3));
            addSlide(AppIntroFragment.newInstance(pg4));
            addSlide(AppIntroFragment.newInstance(pg5));
            addSlide(AppIntroFragment.newInstance(pg6));
            addSlide(AppIntroFragment.newInstance(pg7));
            addSlide(AppIntroFragment.newInstance(pg8));

        } else {

            // Slide 1 - Welcome
            SliderPage pg1 = new SliderPage();
            pg1.setTitle(getResources().getString(R.string.lite_intro_1_header));
            pg1.setDescription(getResources().getString(R.string.lite_intro_1_description));
            pg1.setImageDrawable(R.drawable.icon_small);
            pg1.setBgColor(blue);

            // Slide 2 - Navigation
            SliderPage pg2 = new SliderPage();
            pg2.setTitle(getResources().getString(R.string.intro_2_header));
            pg2.setDescription(getResources().getString(R.string.intro_2_description));
            pg2.setImageDrawable(R.drawable.intro_navdrawer);
            pg2.setBgColor(red);

            // Slide 3 - Settings
            SliderPage pg3 = new SliderPage();
            pg3.setTitle(getResources().getString(R.string.intro_3_header));
            pg3.setDescription(getResources().getString(R.string.intro_3_description));
            pg3.setImageDrawable(R.drawable.ic_settings_black_128dp);
            pg3.setBgColor(blue);

            // Slide 4 - Game
            SliderPage pg4 = new SliderPage();
            pg4.setTitle(getResources().getString(R.string.lite_intro_4_header));
            pg4.setDescription(getResources().getString(R.string.lite_intro_4_description));
            pg4.setImageDrawable(R.drawable.intro_game);
            pg4.setBgColor(red);

            // Slide 5 - Energy Saving Mode
            SliderPage pg5 = new SliderPage();
            pg5.setTitle(getResources().getString(R.string.intro_5_header));
            pg5.setDescription(getResources().getString(R.string.intro_5_description));
            pg5.setImageDrawable(R.drawable.ic_power_black_128dp);
            pg5.setBgColor(blue);

            // Slide 6 - Dicing
            SliderPage pg6 = new SliderPage();
            pg6.setTitle(getResources().getString(R.string.intro_6_header));
            pg6.setDescription(getResources().getString(R.string.intro_6_description));
            pg6.setImageDrawable(R.drawable.ic_casino_black_128dp);
            pg6.setBgColor(red);

            // Slide 7 - Pro
            SliderPage pg7 = new SliderPage();
            pg7.setTitle(getResources().getString(R.string.intro_pro_header));
            pg7.setDescription(getResources().getString(R.string.intro_pro_description));
            pg7.setImageDrawable(R.drawable.ic_cake_black_128dp);
            pg7.setBgColor(blue);

            // Slide 8 - About
            SliderPage pg8 = new SliderPage();
            pg8.setTitle(getResources().getString(R.string.intro_8_header));
            pg8.setDescription(getResources().getString(R.string.intro_8_description));
            pg8.setImageDrawable(R.drawable.ic_check_black_128dp);
            pg8.setBgColor(red);

            addSlide(AppIntroFragment.newInstance(pg1));
            addSlide(AppIntroFragment.newInstance(pg2));
            addSlide(AppIntroFragment.newInstance(pg3));
            addSlide(AppIntroFragment.newInstance(pg4));
            addSlide(AppIntroFragment.newInstance(pg5));
            addSlide(AppIntroFragment.newInstance(pg6));
            addSlide(AppIntroFragment.newInstance(pg7));
            addSlide(AppIntroFragment.newInstance(pg8));
        }

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.

        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.

        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
