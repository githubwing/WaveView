package com.wingsofts.waveview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 波浪view
 * Created by Administrator on 2016/1/4.
 */
public class WaveView extends View {


    private int mMode;
    //圆角模式
    public final int MODE_CIRCLE = -1;

    //三角模式
    public final int MODE_TRIANGLE = -2;

    private int mWidth;
    private int mHeight;

    private float mRectWidth;
    private float mRectHeight;

    //波浪的总个数
    private int mWaveCount;

    //三角形的宽度
    private float mWaveWidth;
    //三角形的高度或
    private float mWaveHeight;

    private float mRadius;

    private Context mContext;
    private int mColor;
    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.WaveView,defStyleAttr,0);
        mWaveCount = a.getInt(R.styleable.WaveView_waveCount,10);
        mWaveWidth = a.getInt(R.styleable.WaveView_waveWidth,20);
        mMode = a.getInteger(R.styleable.WaveView_mode,-2);
        mColor = a.getColor(R.styleable.WaveView_android_color,Color.parseColor("#2C97DE"));


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mRectWidth = (float) (mWidth * 0.8);
        }else if(widthMode == MeasureSpec.AT_MOST){
            mWidth = PxUtils.dpToPx(300,mContext);

            mRectWidth = (float) (mWidth * 0.8);

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            mRectHeight = (float) (mHeight * 0.8);

        }else if(heightMode == MeasureSpec.AT_MOST){

            mHeight = PxUtils.dpToPx(200,mContext);

            mRectHeight = (float) (mHeight * 0.8);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(mColor);

        //计算每个三角形的高
        mWaveHeight = mRectHeight / mWaveCount;

        //绘制矩形

        //计算padding
        float padding = ((mWidth - mRectWidth) / 2);
        canvas.drawRect(padding, padding, mRectWidth + padding, mRectHeight + padding, p);


        //如果是三角模式
        if(mMode == MODE_TRIANGLE) {
            //绘制右边的波浪
            float startX = padding + mRectWidth;
            float startY = padding;
            Path path = new Path();
            path.moveTo(startX, startY);
            for (int i = 0; i < mWaveCount; i++) {
                path.lineTo(startX + mWaveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
                path.lineTo(startX, startY + mWaveHeight * (i + 1));

            }
            path.close();
            canvas.drawPath(path, p);

            //绘制左边的波浪
            startX = padding;
            startY = padding;
            path.moveTo(startX, startY);
            for (int i = 0; i < mWaveCount; i++) {
                path.lineTo(startX - mWaveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
                path.lineTo(startX, startY + mWaveHeight * (i + 1));
            }

            path.close();
            canvas.drawPath(path, p);
        }else {
            mRadius = mRectHeight / mWaveCount;
            //绘制右边波浪
            float startX = padding + mRectWidth;
            float startY = padding;
            for(int i = 0;i<mWaveCount/2;i++) {

                canvas.drawCircle(startX, startY + i * mRadius*2 + mRadius, mRadius, p);
            }

            //绘制坐标波浪
            startX = padding;
            startY = padding;
            for(int i = 0;i<mWaveCount/2;i++) {

                canvas.drawCircle(startX, startY + i * mRadius*2 + mRadius, mRadius, p);
            }


        }
            super.onDraw(canvas);
        }

    }

