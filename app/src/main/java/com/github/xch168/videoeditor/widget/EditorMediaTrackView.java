package com.github.xch168.videoeditor.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EditorMediaTrackView extends View {

    private OnMediaTrackTouchListener mOnMediaTrackTouchListener;

    public EditorMediaTrackView(Context context) {
        this(context, null);
    }

    public EditorMediaTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorMediaTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawFakeDivider(boolean fakeDivider) {

    }

    public void setOnMediaTrackTouchListener(OnMediaTrackTouchListener listener) {
        mOnMediaTrackTouchListener = listener;
    }

    public interface OnMediaTrackTouchListener {

    }
}
