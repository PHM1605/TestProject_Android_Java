package top.mintek.testproject_android_java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class SplashScreen  extends View {
    private Bitmap titleG;
    private int scrW;
    private int scrH;
    private Bitmap playBtnUp;
    private Bitmap playBtnDn;
    private boolean playBtnPressed;
    private Context ctx;

    public SplashScreen(Context context) {
        super(context);
        titleG = BitmapFactory.decodeResource(getResources(), R.drawable.splash_graphic);
        playBtnUp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_up);
        playBtnDn = BitmapFactory.decodeResource(getResources(), R.drawable.btn_down);
        ctx = context;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int titleGLeftPos = (scrW - titleG.getWidth()) / 2;
        canvas.drawBitmap(titleG, titleGLeftPos, 100, null);
        int playBtnLeftPos = (scrW - playBtnUp.getWidth()) / 2;
        if (playBtnPressed) {
            canvas.drawBitmap(playBtnDn, playBtnLeftPos, (int)(scrH*0.5), null);
        } else {
            canvas.drawBitmap(playBtnUp, playBtnLeftPos, (int)(scrH*0.5), null);
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrW = w;
        scrH = h;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int evtAction = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();
        switch (evtAction) {
            case MotionEvent.ACTION_DOWN:
                int btnLeft = (scrW - playBtnUp.getWidth()) / 2;
                int btnRight = btnLeft + playBtnUp.getWidth();
                int btnTop = (int) (scrH * 0.5);
                int btnBottom = btnTop + playBtnUp.getHeight();
                boolean withinBtnBounds = X > btnLeft && X < btnRight && Y > btnTop && Y < btnBottom;
                if (withinBtnBounds) {
                    playBtnPressed = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(playBtnPressed) {
                    // launch main screen
                    Intent gameIntent = new Intent(ctx, CrazyEight.class);
                    ctx.startActivity(gameIntent);
                }
                playBtnPressed = false;
                break;
        }
        invalidate();
        return true;
    }
}
