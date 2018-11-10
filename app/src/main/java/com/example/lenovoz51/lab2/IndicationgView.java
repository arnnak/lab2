package com.example.lenovoz51.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lenovo Z51 on 2018-11-09.
 */

public class IndicationgView extends View {

    public static final int NOTEEECUTED = 0;
    public static final int success = 1;
    public static final int failed = 2;
    public static final int executing = 3;

    int state = NOTEEECUTED;

    public IndicationgView(Context context) {
        super(context);
    }

    public IndicationgView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public IndicationgView(Context context, AttributeSet attrs, int defStyeAttr){
        super(context,attrs,defStyeAttr);
    }

    public int getState(){return state;};
    public void setState(int state){this.state = state;}

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint;
        switch(state){
            case success:
                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(20f);
                canvas.drawLine(0,0,width/2,height,paint);
                canvas.drawLine(width/2,height,width,height/2,paint);
                break;
            case failed:
                paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(20f);
                canvas.drawLine(0,0,width,height,paint);
                canvas.drawLine(0,height,width,0,paint);
                break;
            case executing:
                paint = new Paint();
                paint.setColor(Color.YELLOW);
                paint.setStrokeWidth(20f);
                canvas.drawLine(0,height,width/2,0,paint);
                canvas.drawLine(width/2,0,width,height,paint);
                canvas.drawLine(0,height,width,height,paint);
                break;
            default:
                break;
        }
    }

}
