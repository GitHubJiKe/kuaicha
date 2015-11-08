package com.ypf.kuaicha.myMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.ypf.kuaicha.R;

/**
 * Created by Administrator on 2015/10/29.
 */
public class MyMenu extends HorizontalScrollView {

    private ViewGroup mMenu;
    private LinearLayout sWapper;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    // dp
    private int mMenuRightPadding = 50;

    private boolean once;

    private boolean isOpen;
    /**
     *
    未使用自定义属性时，调用
     */
    public MyMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyMenu(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        // 获取我们定义的属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50, context
                                            .getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth=metrics.widthPixels;
    }

    public MyMenu(Context context)
    {
        this(context, null);
    }
    /**
     *设置子view的宽和高
     * 还有自己的宽和高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!once)
        {
            sWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) sWapper.getChildAt(0);
            mContent = (ViewGroup) sWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
                    - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                // 隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2)
                {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else
                {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    public void closeMenu()
    {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth; // 1 ~ 0

        /**
         * 区别1：内容区域1.0~0.7 缩放的效果 scale : 1.0~0.0 0.7 + 0.3 * scale
         *
         * 区别2：菜单的偏移量需要修改
         *
         * 区别3：菜单的显示时有缩放以及透明度变化 缩放：0.7 ~1.0 1.0 - scale * 0.3 透明度 0.6 ~ 1.0
         * 0.6+ 0.4 * (1- scale) ;
         *
         */
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

        // 调用属性动画，设置TranslationX
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, leftAlpha);
        // 设置content的缩放的中心点
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);

    }
}
