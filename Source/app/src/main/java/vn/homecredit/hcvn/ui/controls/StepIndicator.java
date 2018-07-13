/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/11/18 11:27 AM, by hien.nguyenm
 */

package vn.homecredit.hcvn.ui.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import vn.homecredit.hcvn.R;

public class StepIndicator extends View {

    private int currentStep = 1;
    private int numberOfStep = 5;
    private int textSize = 15;
    private float lineWidth = 2;
    private final Context currentContext;

    private int leftPadding;
    private int rightPadding;

    /* Using example
            <vn.homecredit.hcvn.ui.controls.StepIndicator
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingLeft="30dp"
                android:paddingRight="30dp"
                app:currentStep="3"
                app:lineWidth="5"
                app:numOfSteps="7"
                app:textSize="13dp" />
    */

    //TODO: Will implement clickable stuff for steps
    public StepIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentContext = context;

        try {
            TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StepIndicator, 0, 0);
            setCurrentStep(attributes.getInteger(R.styleable.StepIndicator_currentStep, 1));
            setNumberOfStep(attributes.getInteger(R.styleable.StepIndicator_numOfSteps, 5));
            setTextSize(attributes.getDimensionPixelOffset(R.styleable.StepIndicator_textSize, 15));
            setLineWidth(attributes.getFloat(R.styleable.StepIndicator_lineWidth, 2));

            int [] defaultAttributeSet = new int [] {android.R.attr.paddingLeft, android.R.attr.paddingRight};

            TypedArray defaultAttrs = context.obtainStyledAttributes(attrs, defaultAttributeSet);

            leftPadding = defaultAttrs.getDimensionPixelOffset(0, 40);
            rightPadding = defaultAttrs.getDimensionPixelOffset(1, 40);

            attributes.recycle();
            defaultAttrs.recycle();

        } catch(Exception ex){}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = getWidth();
        int desiredHeight = textSize * 2 + 20;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // parent_match
            width = Math.min(desiredWidth, widthSize);
        } else {
            // wrap_content
            width = desiredWidth;
        }

        // Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // parent_match
            height = Math.min(desiredHeight, heightSize);
        } else {
            // wrap_content
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int drawWidth = canvas.getWidth() - (leftPadding + rightPadding);
        int baseY = getHeight() / 2;
        int baseX = leftPadding;
        int segment = drawWidth / (numberOfStep -1);

        // Draw lines
        for (int i = 1; i < numberOfStep; i++) {
            int lastStepX = baseX + segment * (i - 1);
            int stepX = baseX + segment * (i - 0);
            int lineColor;
            if (i < currentStep) {
                lineColor = currentContext.getResources().getColor(R.color.primary_red);
            }else{
                lineColor = currentContext.getResources().getColor(R.color.primary_grey);
            }
            drawLine(canvas,lastStepX,stepX,baseY,lineColor);
        }

        // Draw steps
        for (int i = 1; i <= numberOfStep; i++) {
            int stepX = baseX + segment * (i - 1);
            int backGroundColor, textColor, strokeColor;

            if (i <= currentStep) {
                backGroundColor = currentContext.getResources().getColor(R.color.primary_red);
                strokeColor = backGroundColor;
                textColor = Color.WHITE;
            } else {
                backGroundColor = Color.WHITE;
                textColor = currentContext.getResources().getColor(R.color.primary_grey);
                strokeColor = textColor;
            }

            drawStep(canvas, stepX, baseY, String.valueOf(i), backGroundColor, textColor, strokeColor);
        }
    }

    public void drawLine(Canvas canvas, float x, float x2, float y, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);

        canvas.drawLine(x,y,x2,y, paint);
    }

    public void drawStep(Canvas canvas, float x, float y, String text, int backGroundColor, int textColor, int strokeColor) {
        Paint textPaint = new Paint();
        Paint circlePaint = new Paint();
        Paint circleStrokePaint = new Paint();

        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Rect bounds = new Rect();
        // Support maximize step is 99
        textPaint.getTextBounds("00", 0, 2, bounds);
        textPaint.setTypeface(Typeface.create("Arial", Typeface.NORMAL));

        circlePaint.setColor(backGroundColor);
        circlePaint.setAntiAlias(true);
        circleStrokePaint.setStyle(Paint.Style.STROKE);
        circleStrokePaint.setAntiAlias(true);
        circleStrokePaint.setColor(strokeColor);
        circleStrokePaint.setStrokeWidth(lineWidth);

        canvas.drawCircle(x, y , bounds.width() - 7, circlePaint);
        canvas.drawCircle(x, y , bounds.width() - 7, circleStrokePaint);
        canvas.drawText(text, x, y + textSize/3 , textPaint);
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
        invalidate();
    }

    public int getNumberOfStep() {
        return numberOfStep;
    }

    public void setNumberOfStep(int numberOfStep) {
        this.numberOfStep = numberOfStep;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}