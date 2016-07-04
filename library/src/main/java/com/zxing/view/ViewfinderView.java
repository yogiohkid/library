/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.joker.jokerlibrary.R;
import com.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View
{
    private Context mContext;
    /**
     * 刷新界面的时间
     */
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    private int angularWidth;//四个绿色边角对应的宽度
    private int angularLength;//四个绿色边角对应的长度
    private int boxColor;//四个角以及扫描线的颜色
    private int scanLineWidth;//扫描框中的中间线的宽度
    private int scanLineSpacing;//扫描框中的中间线的与扫描框左右的间隙
    private int scanLineSpeed;//扫描线移动速度
    private int textSize;//提示文字大小
    private int textColor;//提示文字颜色
    private String text = "请将二维码放入框内，即可自动扫描";//提示文字
    private int text_spacing;//字体距离扫描框下面的距离
    private int maskColor;//遮盖层颜色
    private int resultColor;//返回层颜色
    private int resultPointColor;//返回点颜色
    boolean isFirst;

    private Paint paint; //画笔对象的应用
    private int slideTop;//中间滑动线的最顶端位置
    private int slideBottom;//中间滑动线的最底端位置

    /**
     * 将扫描的二维码拍下来，这里没有这个功能，暂时不考虑
     */
    private Bitmap resultBitmap;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    public ViewfinderView(Context context)
    {
        super(context);
        mContext = context;
        paint = new Paint();
        possibleResultPoints = new HashSet<>(5);
    }

    public ViewfinderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        paint = new Paint();
        possibleResultPoints = new HashSet<>(5);

        setCustomAttributes(attrs);
    }

    public ViewfinderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs,defStyle);
        mContext = context;
        paint = new Paint();
        possibleResultPoints = new HashSet<>(5);

        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs)
    {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.viewfinderview);
        angularWidth = a.getDimensionPixelSize(R.styleable.viewfinderview_angular_width,3);
        angularLength = a.getDimensionPixelSize(R.styleable.viewfinderview_angular_length,20);
        boxColor = a.getColor(R.styleable.viewfinderview_box_color,Color.WHITE);
        scanLineWidth = a.getDimensionPixelSize(R.styleable.viewfinderview_scan_line_width,3);
        scanLineSpacing = a.getDimensionPixelSize(R.styleable.viewfinderview_scan_line_spacing,3);
        scanLineSpeed = a.getDimensionPixelSize(R.styleable.viewfinderview_scan_line_speed,2);
        textSize = a.getDimensionPixelSize(R.styleable.viewfinderview_text_size,16);
        textColor = a.getColor(R.styleable.viewfinderview_text_color,Color.WHITE);
        text = (String) a.getText(R.styleable.viewfinderview_text);
        text_spacing = a.getDimensionPixelSize(R.styleable.viewfinderview_text_spacing,30);
        maskColor = a.getColor(R.styleable.viewfinderview_mask_color,0x60000000);
        resultColor = a.getColor(R.styleable.viewfinderview_result_color,0xb0000000);
        resultPointColor = a.getColor(R.styleable.viewfinderview_result_point_color,0x0ffff00);

        if (text == null)
        {
            text = "请将二维码放入框内，即可自动扫描";
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
        Rect frame = CameraManager.get().getFramingRect();
        if (frame == null)
        {
            return;
        }

        //初始化中间线滑动的最上边和最下边
        if (!isFirst)
        {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        //获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);

        //画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);


        if (resultBitmap != null)
        {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else
        {

            //画扫描框边上的角，总共8个部分
            paint.setColor(boxColor);
            canvas.drawRect(frame.left, frame.top, frame.left + angularLength,
                    frame.top + angularWidth, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + angularWidth, frame.top
                    + angularLength, paint);
            canvas.drawRect(frame.right - angularLength, frame.top, frame.right,
                    frame.top + angularWidth, paint);
            canvas.drawRect(frame.right - angularWidth, frame.top, frame.right, frame.top
                    + angularLength, paint);
            canvas.drawRect(frame.left, frame.bottom - angularWidth, frame.left
                    + angularLength, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - angularLength,
                    frame.left + angularWidth, frame.bottom, paint);
            canvas.drawRect(frame.right - angularLength, frame.bottom - angularWidth,
                    frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - angularWidth, frame.bottom - angularLength,
                    frame.right, frame.bottom, paint);


            //绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += scanLineSpeed;
            if (slideTop >= frame.bottom)
            {
                slideTop = frame.top;
            }
            canvas.drawRect(frame.left + scanLineSpacing, slideTop - scanLineWidth / 2, frame.right - scanLineSpacing, slideTop + scanLineWidth / 2, paint);


            //画扫描框下面的字
            paint.setColor(Color.WHITE);
            paint.setTextSize(textSize);
            paint.setAlpha(0x40);
            paint.setTypeface(Typeface.create("System", Typeface.BOLD));
            float textLength = paint.measureText(text);
            canvas.drawText(text, (width - textLength) / 2, (float) (frame.bottom + (float) text_spacing), paint);


            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty())
            {
                lastPossibleResultPoints = null;
            } else
            {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible)
                {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null)
            {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast)
                {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }


            //只刷新扫描框的内容，其他地方不刷新
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
    }

    public void drawViewfinder()
    {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode)
    {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point)
    {
        possibleResultPoints.add(point);
    }

}
