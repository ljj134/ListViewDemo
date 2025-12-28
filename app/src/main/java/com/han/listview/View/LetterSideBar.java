package com.han.listview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class LetterSideBar extends View {

    private String[] letters = {};
    private Paint paint;
    private int choose = -1; // 当前选中的索引
    private OnLetterChangeListener listener;

    public LetterSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(24);
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    // 👇 新增方法：动态设置侧边栏字母
    public void setLetters(String[] letters) {
        this.letters = letters != null ? letters : new String[0];
        invalidate(); // 重绘
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (letters.length == 0) return;

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / letters.length;

        for (int i = 0; i < letters.length; i++) {
            // 高亮当前选中字母
            if (i == choose) {
                paint.setColor(Color.BLUE);
                paint.setFakeBoldText(true);
            } else {
                paint.setColor(Color.GRAY);
                paint.setFakeBoldText(false);
            }

            float x = (float) (width / 2.0);
            float y = (float) (singleHeight * i + singleHeight*1.0 - ((float)singleHeight / 2.0) + (float)getTextHeight() / 2);
            canvas.drawText(letters[i], x, y, paint);
        }
    }

    private int getTextHeight() {
        Rect bounds = new Rect();
        paint.getTextBounds("A", 0, 1, bounds);
        return bounds.height();
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (letters.length == 0) return false;

        int oldChoose = choose;
        float y = event.getY();
        int index = (int) (y / getHeight() * letters.length);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (index >= 0 && index < letters.length) {
                    choose = index;
                    if (listener != null) {
                        listener.onLetterChange(letters[choose]);
                    }
                    // 显示悬浮提示（可选）
                    showTips(letters[choose]);
                }
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                choose = -1;
                break;
        }

        // 只有在选择变化时才重绘
        if (oldChoose != choose) {
            invalidate();
        }
        return true; // 必须返回 true 才能持续接收 MOVE/UP 事件
    }

    private void showTips(String letter) {
        Toast.makeText(getContext(), letter, Toast.LENGTH_SHORT).show();
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.listener = listener;
    }
}