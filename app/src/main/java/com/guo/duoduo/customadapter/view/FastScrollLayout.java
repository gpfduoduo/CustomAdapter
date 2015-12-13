package com.guo.duoduo.customadapter.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guo.duoduo.customadapter.R;


/**
 * Created by 郭攀峰 on 2015/12/13.
 */
public class FastScrollLayout extends RelativeLayout
{
    private static final String tag = FastScrollLayout.class.getSimpleName();

    private OnChangeFastScrollPlaceListener onChangeFastScrollPlaceListener;
    private TextView mDragView;
    private float currentY = 0;
    private float savedY = 0;
    private float downY = 0;

    private int barHeight = 0;
    private int barWidth = 0;
    private int viewHeight = 0;

    public FastScrollLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setCurrentPlace(float place)
    {
        Log.d(tag, "set current place = " + place);
        currentY = (viewHeight - barHeight) * place;
        savedY = currentY;
        mDragView.setTranslationY(currentY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        viewHeight = getHeight();
        barHeight = mDragView.getHeight();
        barWidth = mDragView.getWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (!isViewHit(mDragView, (int) event.getX(), (int) event.getY()))
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_UP :
                    savedY = currentY;
                    if (onChangeFastScrollPlaceListener != null)
                    {
                        onChangeFastScrollPlaceListener.onState(false);
                    }
                    break;
            }
            return super.onTouchEvent(event);
        }

        float eventY = event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN :
                currentY = savedY;
                downY = eventY;
                Log.d(tag, "down:" + currentY);
                break;

            case MotionEvent.ACTION_MOVE :
                if ((int) (savedY + eventY - downY) >= 0
                    && (int) (savedY + eventY - downY) <= (viewHeight - barHeight))
                {
                    currentY = savedY + eventY - downY;

                    float percent = currentY / (viewHeight - barHeight);

                    Log.d(tag, "percent:" + percent);
                    if (onChangeFastScrollPlaceListener != null)
                    {
                        onChangeFastScrollPlaceListener.onTouchingLetterChanged(percent);
                        onChangeFastScrollPlaceListener.onState(true);
                    }
                }
                Log.d(tag, "move:" + currentY);
                break;

            case MotionEvent.ACTION_UP :
                savedY = currentY;
                Log.d(tag, "up:" + currentY);
                if (onChangeFastScrollPlaceListener != null)
                {
                    onChangeFastScrollPlaceListener.onState(false);
                }
                break;
        }
        mDragView.setTranslationY(currentY);
        return true;
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mDragView = (TextView) findViewById(R.id.drag_view);
    }

    private boolean isViewHit(View view, int x, int y)
    {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);

        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;

        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth()
            && screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    public void setOnChangeFastScrollPlace(
            OnChangeFastScrollPlaceListener onChangeListener)
    {
        this.onChangeFastScrollPlaceListener = onChangeListener;
    }

    public interface OnChangeFastScrollPlaceListener
    {
        public void onTouchingLetterChanged(float percent);

        public void onState(boolean isMoving);
    }
}
