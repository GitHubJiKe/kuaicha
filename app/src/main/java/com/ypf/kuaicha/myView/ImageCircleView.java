package com.ypf.kuaicha.myView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/10/30.
 */
public class ImageCircleView extends ImageView {
//
//        Path path;
//        public PaintFlagsDrawFilter mPaintFlagsDrawFilter;
        Paint paint;

        public ImageCircleView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.init();
        }

        public ImageCircleView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.init();
        }

        public ImageCircleView(Context context) {
            super(context);
            this.init();
        }

        public void init() {
            this.paint = new Paint();
        }

        protected void onDraw(Canvas cns) {
            Drawable drawable = this.getDrawable();
            if(drawable != null) {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                Bitmap b = this.circleDraw(bitmap);
                Rect rect1 = new Rect(0, 0, b.getWidth(), b.getHeight());
                Rect rect2 = new Rect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
                this.paint.reset();
                cns.drawBitmap(b, rect1, rect2, this.paint);
            } else {
                super.onDraw(cns);
            }

        }

        private Bitmap circleDraw(Bitmap bitmap) {
//            boolean r = false;
            int width = this.getWidth();
            int height = this.getHeight();
            int r1;
            if(width > height) {
                r1 = height;
            } else {
                r1 = width;
            }

            Bitmap output = Bitmap.createBitmap(r1, r1, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Rect rect = new Rect(0, 0, r1, r1);
            this.paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            this.paint.setColor(-1);
            canvas.drawCircle((float)(r1 / 2), (float)(r1 / 2), (float)(r1 / 2), this.paint);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            bitmap = Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(), true);
            canvas.drawBitmap(bitmap, rect, rect, this.paint);
            return output;

    }
}
