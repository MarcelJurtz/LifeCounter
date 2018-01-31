package com.marceljurtz.lifecounter.Dicing;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void loadGameActivity();
    void loadSettingsActivity();
    void loadAboutActivity();
    void loadCounterManagerActivity();
    void setBackgroundColor(Color color);
}
