package com.marceljurtz.lifecounter.Dicing;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void loadActivity(Class c);
    void setBackgroundColor(Color color);
}
