package com.ypf.kuaicha.myView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.ypf.kuaicha.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/2.
 */
public class CustomTextView extends TextView {

    Paint paint;
    Rect bound;
    String titletext;
    int titlecolor;
    int titlesize;
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public CharSequence getText() {
        return titletext;
    }


    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,
                R.styleable.CustomTextView,defStyleAttr,0);
        int n=typedArray.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=typedArray.getIndex(i);
            switch(attr){
                case R.styleable.CustomTextView_titletext:
                    titletext=typedArray.getString(attr);
                    break;
                case R.styleable.CustomTextView_titlecolor:
                    titlecolor=typedArray.getInt(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_titlesize:
                    titlesize=typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        paint=new Paint();
        paint.setTextSize(titlesize);
        bound=new Rect();
        paint.getTextBounds(titletext, 0, titletext.length(), bound);
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titletext = getRandom();
                titlecolor=getRandomColor();
                postInvalidate();
            }
        });
    }

    private String getRandom() {
        Random random=new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }
        return sb.toString();
    }

    private int getRandomColor() {

        Random random=new Random();
        int r = random.nextInt(256);
        int g= random.nextInt(256);
        int b = random.nextInt(256);
        int mColor = Color.rgb(r, g, b);
        return mColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(titlecolor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(titletext,getWidth()/2-bound.width()/2,getHeight()/2+bound.height()/2,paint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmode= MeasureSpec.getMode(widthMeasureSpec);
        int widthsize= MeasureSpec.getSize(widthMeasureSpec);
        int heightmode= MeasureSpec.getMode(heightMeasureSpec);
        int heightsize= MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthmode== MeasureSpec.EXACTLY){
            width=widthsize;
        }else{
            paint.setTextSize(titlesize);
            paint.getTextBounds(titletext,0,titletext.length(),bound);
            float textwidth=bound.width();
            int deris= (int) (textwidth+getPaddingLeft()+getPaddingRight());
            width=deris;
        }

        if (heightmode== MeasureSpec.EXACTLY){
            height=heightsize;
        }else{
            paint.setTextSize(titlesize);
            paint.getTextBounds(titletext,0,titletext.length(),bound);
            float textheight=bound.height();
            int deris= (int) (textheight+getPaddingTop()+getPaddingBottom());
            height=deris;
        }

        setMeasuredDimension(width,height);
    }
}
