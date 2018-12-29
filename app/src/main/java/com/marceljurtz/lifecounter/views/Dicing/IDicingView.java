package com.marceljurtz.lifecounter.views.Dicing;

import com.marceljurtz.lifecounter.contracts.base.IView;
import com.marceljurtz.lifecounter.models.Color;

public interface IDicingView extends IView {
    void setDicingText(String text);
    void loadActivity(Class c);
    void setBackgroundColor(Color color);
}
