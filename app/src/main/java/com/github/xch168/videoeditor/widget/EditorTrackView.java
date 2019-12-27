package com.github.xch168.videoeditor.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import com.github.xch168.videoeditor.R;
import com.github.xch168.videoeditor.entity.TrackEditEntry;
import com.github.xch168.videoeditor.util.SizeUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EditorTrackView extends FrameLayout {

    private final int COLOR_EDITOR_THEME = getResources().getColor(R.color.colorAccent);

    private Context mContext;

    private ImageView mLeftThumb;
    private ImageView mRightThumb;
    private ImageView mCursorThumb;

    private EditorMediaTrackView mMediaTrackView;

    private Paint mBgPaint;
    private Paint mBorderPaint;
    private TextPaint mTextPaint;

    private int mScreenWidth;

    private float mFontHeight;
    private int G = -1;
    private int J;
    private int M;
    private int H = 10;
    private float E;
    private int mCurrentTrackIndex = -1;


    private GestureDetector mGestureDetector;
    private Scroller mScroller;

    private Rect mBounds;

    private EditorMediaTrackView.OnMediaTrackTouchListener mOnMediaTrackTouchListener;
    private OnEditorTrackStateChangedListener mOnEditorTrackStateChangedListener;

    private List<TrackEditEntry> mTrackEditEntryList = new ArrayList<>();

    public EditorTrackView(@NonNull Context context) {
        this(context, null);
    }

    public EditorTrackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorTrackView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        initView();
    }

    private void initView() {
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        J = SizeUtil.dp2px(mContext, 1);
        M = mScreenWidth / 6;

        setWillNotDraw(false);

        initPaint();
        initGestureDetector();
        initScrollListener();
        initUIComponent();
        addUIComponent();

        mBounds = new Rect();
    }

    private void initPaint() {
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(SizeUtil.dp2px(mContext, 2));

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(SizeUtil.sp2px(mContext, 10));
        mTextPaint.setColor(getResources().getColor(R.color.white));
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mFontHeight = fontMetrics.bottom - fontMetrics.top;
        E = SizeUtil.dp2px(mContext, 4) / 2;
        H = SizeUtil.dp2px(mContext, H);
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    private void initScrollListener() {
        mScroller = new Scroller(mContext, new LinearInterpolator());
        mOnMediaTrackTouchListener = new EditorMediaTrackView.OnMediaTrackTouchListener() {

        };
    }

    private void initUIComponent() {
        mLeftThumb = new ImageView(mContext);
        mLeftThumb.setImageResource(R.drawable.ic_progress_left);
        mLeftThumb.setScaleType(ImageView.ScaleType.FIT_END);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(SizeUtil.dp2px(mContext, 24), SizeUtil.dp2px(mContext, 40));
        layoutParams.topMargin = SizeUtil.dp2px(mContext, 5);
        mLeftThumb.setLayoutParams(layoutParams);

        mRightThumb = new ImageView(mContext);
        mRightThumb.setImageResource(R.drawable.ic_progress_right);
        mRightThumb.setScaleType(ImageView.ScaleType.FIT_START);
        layoutParams = new FrameLayout.LayoutParams(SizeUtil.dp2px(mContext, 24), SizeUtil.dp2px(mContext, 40));
        mRightThumb.setLayoutParams(layoutParams);

        mCursorThumb = new ImageView(mContext);
        mCursorThumb.setBackgroundResource(R.drawable.shape_video_progress_cursor);
        mCursorThumb.setX(mScreenWidth / 2);
        mCursorThumb.setTag(Integer.valueOf(mScreenWidth / 2));
        layoutParams = new FrameLayout.LayoutParams(SizeUtil.dp2px(mContext, 2), ViewGroup.LayoutParams.MATCH_PARENT);
        mCursorThumb.setLayoutParams(layoutParams);

        mMediaTrackView = new EditorMediaTrackView(mContext);
        layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, SizeUtil.dp2px(mContext, 44));
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.bottomMargin = SizeUtil.dp2px(mContext, 5);
        mMediaTrackView.setLayoutParams(layoutParams);
        mMediaTrackView.setDrawFakeDivider(true);
        mMediaTrackView.setOnMediaTrackTouchListener(mOnMediaTrackTouchListener);
    }

    private void addUIComponent() {
        removeAllViews();
        addView(mMediaTrackView);
        addView(mLeftThumb);
        addView(mRightThumb);
        addView(mCursorThumb);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipRect(getLeft(), getTop(), getRight(), getBottom());
        int index = 0;
        if (index < mTrackEditEntryList.size()) {
            TrackEditEntry entry = mTrackEditEntryList.get(index);
            Rect rect = entry.getRect();
            int left = rect.left;
            int right = rect.right;
            setBounds(mBounds, left, rect.top, right, rect.bottom);
            drawBackground(canvas, mBounds);

            rect = mBounds;
            for (boolean bool = true;; bool = false) {
                drawBorder(canvas, rect, bool);
                index++;
                if (index == mCurrentTrackIndex) {
                    break;
                }
            }
        }

    }

    private void setBounds(Rect rect, int left, int top, int right, int bottom) {
        rect.left = left;
        rect.top = top;
        rect.right = right;
        rect.bottom = bottom;
    }

    private void drawBackground(Canvas canvas, Rect bounds) {
        canvas.drawRect(bounds, mBgPaint);
    }

    private void drawBorder(Canvas canvas, Rect rect, boolean bool) {
        if (G == 3 && bool) {
            mBorderPaint.setColor(COLOR_EDITOR_THEME);
        } else {
            mBorderPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawRect(rect.left + J, rect.top + J, rect.right - J, rect.bottom - J, mBorderPaint);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnEditorTrackStateChangedListener(OnEditorTrackStateChangedListener listener) {
        mOnEditorTrackStateChangedListener = listener;
    }

    public static abstract interface OnEditorTrackStateChangedListener {

    }
}
