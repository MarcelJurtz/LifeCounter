package com.marceljurtz.lifecounter.views.Intro

import android.app.Activity
import android.os.Bundle

import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import com.marceljurtz.lifecounter.R
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.AppDetails
import com.marceljurtz.lifecounter.models.PreferenceManager
import androidx.fragment.app.Fragment

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val red = com.marceljurtz.lifecounter.models.Color(MagicColorEnum.RED, applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).intValue
        val blue = com.marceljurtz.lifecounter.models.Color(MagicColorEnum.BLUE, applicationContext.getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE)).intValue

        // Slide 1 - Welcome
        val pg1 = SliderPage()
        pg1.title = resources.getString(R.string.intro_1_header)
        pg1.description = resources.getString(R.string.intro_1_description)
        pg1.imageDrawable = R.drawable.icon_small
        pg1.bgColor = blue

        // Slide 2 - Navigation
        val pg2 = SliderPage()
        pg2.title = resources.getString(R.string.intro_2_header)
        pg2.description = resources.getString(R.string.intro_2_description)
        pg2.imageDrawable = R.drawable.intro_navdrawer
        pg2.bgColor = red

        // Slide 3 - Settings
        val pg3 = SliderPage()
        pg3.title = resources.getString(R.string.intro_3_header)
        pg3.description = resources.getString(R.string.intro_3_description)
        pg3.imageDrawable = R.drawable.ic_settings_black_128dp
        pg3.bgColor = blue

        // Slide 4 - Game
        val pg4 = SliderPage()
        pg4.title = resources.getString(R.string.intro_4_header)
        pg4.description = resources.getString(R.string.intro_4_description)
        pg4.imageDrawable = R.drawable.intro_game
        pg4.bgColor = red

        // Slide 5 - Energy Saving Mode
        val pg5 = SliderPage()
        pg5.title = resources.getString(R.string.intro_5_header)
        pg5.description = resources.getString(R.string.intro_5_description)
        pg5.imageDrawable = R.drawable.ic_power_black_128dp
        pg5.bgColor = blue

        // Slide 6 - Dicing
        val pg6 = SliderPage()
        pg6.title = resources.getString(R.string.intro_6_header)
        pg6.description = resources.getString(R.string.intro_6_description)
        pg6.imageDrawable = R.drawable.ic_casino_black_128dp
        pg6.bgColor = red

        // Slide 7 - Counter
        val pg7 = SliderPage()
        pg7.title = resources.getString(R.string.intro_7_header)
        pg7.description = resources.getString(R.string.intro_7_description)
        pg7.imageDrawable = R.drawable.intro_counter
        pg7.bgColor = blue

        // Slide 8 - About
        val pg8 = SliderPage()
        pg8.title = resources.getString(R.string.intro_8_header)
        pg8.description = resources.getString(R.string.intro_8_description)
        pg8.imageDrawable = R.drawable.ic_check_black_128dp
        pg8.bgColor = red

        addSlide(AppIntroFragment.newInstance(pg1))
        addSlide(AppIntroFragment.newInstance(pg2))
        addSlide(AppIntroFragment.newInstance(pg3))
        addSlide(AppIntroFragment.newInstance(pg4))
        addSlide(AppIntroFragment.newInstance(pg5))
        addSlide(AppIntroFragment.newInstance(pg6))
        addSlide(AppIntroFragment.newInstance(pg7))
        addSlide(AppIntroFragment.newInstance(pg8))

        // Hide Skip/Done button.
        showSkipButton(true)
        isProgressButtonEnabled = true
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Do something when users tap on Skip button.

        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Do something when users tap on Done button.

        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.
    }
}
