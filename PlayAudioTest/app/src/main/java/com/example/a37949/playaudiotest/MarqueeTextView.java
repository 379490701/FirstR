package com.example.a37949.playaudiotest;

import android.content.Context;
import android.util.AttributeSet;

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    //返回是否处在选中的状态
    //而只有选中的才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }
}
