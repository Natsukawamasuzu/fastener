package com.zstu.natsukawa.fasterner.optionview;

import android.animation.*;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.Nullable;
import com.zstu.natsukawa.fastener.R;

import java.util.ArrayList;
import java.util.List;

public class AbilityLevelView extends View {

    //the radius of the ability view
    private float radius;

    //the X-position of the center point of the view
    private float centerX;

    //the Y-position of the center point of the view
    private float centerY;

    //this paint is used to paint the main framework of this view
    private Paint mainPaint;

    //this paint is used to paint the text of this view
    private Paint textPaint;

    //this paint is used to paint data area
    private Paint dataPaint;

    //this paint is used to paint the line of the data area
    private Paint dataLinePaint;

    //simple description for the edges in the view
    private List<String> dimensionStrings;

    //the values of the dimensions
    private List<Float> dimensionValues;

    //the maximum value of the dimension
    private double dimensionMaxValue;

    //the number of the attributes
    private int dimensionCount;

    //the number of each attribute dimension levels
    private int levelCount;

    private int animationDuration;

    private ArrayList<PointF> endPoints;

    private ArrayList<PointF> currentPoints;


    public AbilityLevelView(Context context) {
        this(context, null);
    }

    public AbilityLevelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbilityLevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiseView(context,attrs, defStyleAttr);
    }

    private void initialiseView(Context context,@Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AbilityLevelView);
        dimensionCount = typedArray.getInt(R.styleable.AbilityLevelView_dimension_count,5);
        animationDuration = typedArray.getInt(R.styleable.AbilityLevelView_set_animation_duration,800);
        radius = typedArray.getDimension(R.styleable.AbilityLevelView_radius,200);
        endPoints = new ArrayList<>(dimensionCount);
        dimensionStrings = new ArrayList<>(dimensionCount);
        dimensionValues = new ArrayList<>(dimensionCount);
        for(int i = 0; i < dimensionCount; i++){
            dimensionStrings.add("status" + i);
            dimensionValues.add(i * 10.0f);
        }
        levelCount = typedArray.getInt(R.styleable.AbilityLevelView_level_count,5);
        dimensionMaxValue = typedArray.getInt(R.styleable.AbilityLevelView_maximum_value,100);
        int frameLineColor = typedArray.getColor(R.styleable.AbilityLevelView_frame_line_color, Color.BLACK);
        float frameLineWidth = typedArray.getDimension(R.styleable.AbilityLevelView_frame_line_width, 1f);
        mainPaint = new Paint();
        mainPaint.setColor(frameLineColor);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(frameLineWidth);
        mainPaint.setStyle(Paint.Style.STROKE);

        int textSize = typedArray.getDimensionPixelSize(R.styleable.AbilityLevelView_android_textSize, 32);
        int textColor = typedArray.getColor(R.styleable.AbilityLevelView_android_textColor, Color.BLACK);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        int dataAreaColor = typedArray.getColor(R.styleable.AbilityLevelView_data_area_color,Color.GRAY);
        int alpha = typedArray.getInt(R.styleable.AbilityLevelView_set_alpha, 128);
        dataPaint = new Paint();
        dataPaint.setColor(dataAreaColor);
        dataPaint.setAntiAlias(true);
        dataPaint.setAlpha(alpha);
        dataPaint.setStyle(Paint.Style.FILL);

        int dataLineColor = typedArray.getColor(R.styleable.AbilityLevelView_ability_line_color, Color.GRAY);
        float dataLineWidth = typedArray.getDimension(R.styleable.AbilityLevelView_ability_line_width, 1.0f);
        dataLinePaint = new Paint();
        dataLinePaint.setColor(dataLineColor);
        dataLinePaint.setStrokeWidth(dataLineWidth);
        dataLinePaint.setAntiAlias(true);
        dataLinePaint.setStyle(Paint.Style.STROKE);

        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int paddingTop = getPaddingTop();
        int paddingStart = getPaddingStart();
        int paddingBottom = getPaddingBottom();
        int paddingEnd = getPaddingEnd();
        centerX = (w+paddingStart-paddingEnd) /2.0f;
        centerY = (h+paddingTop-paddingBottom) /2.0f;
        invalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int paddingTop = getPaddingTop();
        int paddingStart = getPaddingStart();
        int paddingBottom = getPaddingBottom();
        int paddingEnd = getPaddingEnd();
        if(widthMode == MeasureSpec.EXACTLY &&heightMode == MeasureSpec.EXACTLY) {
            width = width + paddingStart + paddingEnd ;
            height = height + paddingTop + paddingBottom;
            radius = Math.min(width - paddingStart - paddingEnd, height - paddingBottom - paddingTop) / 2.5f;
        }else if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int DEFAULT_WIDTH = 600;
            width = DEFAULT_WIDTH + paddingStart + paddingEnd+ (int) (1.2* radius);
            int DEFAULT_HEIGHT = 600;
            height = DEFAULT_HEIGHT + paddingTop + paddingBottom + (int)(1.2* radius);
        }
        setMeasuredDimension(width,height);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        double arcValue = 2 * Math.PI / dimensionCount;
        float levelDistance = radius / levelCount;
        path.moveTo(centerX,centerY);
        for(int i = 0; i < levelCount; i++){
            float currentRadius = (i + 1) * levelDistance;
            float x = centerX;
            float y = centerY - currentRadius;
            path.moveTo(x,y);
            for(int j = 1; j < dimensionCount; j++){
                float x1 = (float) (centerX - currentRadius * Math.sin(arcValue * j));
                float y1 = (float) (centerY - currentRadius * Math.cos(arcValue * j));
                path.lineTo(x1,y1);
            }
            path.close();
        }
        canvas.drawPath(path, mainPaint);

    }

    private void drawLines(Canvas canvas){
        double arcValue = 2 * Math.PI / dimensionCount;
        Path path = new Path();
        for(int i = 0; i < dimensionCount; i++){
            path.moveTo(centerX, centerY);
            float x = (float) (centerX - radius * Math.sin(arcValue * (i + 1)));
            float y = (float) (centerY - radius * Math.cos(arcValue * (i + 1)));
            path.lineTo(x,y);
        }
        canvas.drawPath(path,mainPaint);
    }

    private void drawText(Canvas canvas){
        double arcValue = 2 * Math.PI / dimensionCount;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i =0; i < dimensionCount; i++){
            float x = (float) (centerX - 1.25 * radius * Math.sin(arcValue * i));
            float y = (float) (centerY - 1.25 * radius * Math.cos(arcValue * i));
            canvas.drawText(dimensionStrings.get(i), x, y, textPaint);
            canvas.drawText(dimensionValues.get(i).toString(),x,y+fontHeight,textPaint);
        }
    }

    private void getEndPoints(){
        double arcValue = 2 * Math.PI / dimensionCount;
        double percentage;
        for(int i = 0; i < dimensionCount; i++){
            if(dimensionValues.get(i) == null)
                percentage = 0;
            else
                percentage = dimensionValues.get(i) / dimensionMaxValue;
            float x = (float) (centerX - radius * percentage * Math.sin(arcValue * i));
            float y = (float) (centerY - radius * percentage * Math.cos(arcValue * i));
            endPoints.add(new PointF(x,y));
        }
    }

    private void drawData(Canvas canvas){
        getEndPoints();
        Path path = new Path();
        path.moveTo(currentPoints.get(0).x, currentPoints.get(0).y);
        for (PointF point: currentPoints) {
            path.lineTo(point.x,point.y);
        }
        path.close();
        canvas.drawPath(path,dataLinePaint);
        canvas.drawPath(path,dataPaint);
    }

    @SuppressWarnings("unchecked")
    private void inflatingAnimation(){
        ArrayList<PointF> startPoints = new ArrayList<PointF>(){
            {add(new PointF(centerX, centerY));}
        };
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointFEvaluator(), startPoints,endPoints);
        valueAnimator.addUpdateListener(animation -> {
            currentPoints = (ArrayList<PointF>) animation.getAnimatedValue();
            postInvalidate();
        });
        valueAnimator.setDuration(animationDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        if(currentPoints == null){
            currentPoints = new ArrayList<>(dimensionCount);
            currentPoints.add(0,new PointF(centerX,centerY));
            drawData(canvas);
            inflatingAnimation();
        }else{
            drawData(canvas);
        }
    }


    public List<String> getDimensionStrings() {
        return dimensionStrings;
    }

    public void setDimensionStrings(List<String> dimensionStrings) {
        this.dimensionStrings = dimensionStrings;
    }

    public List<Float> getDimensionValues() {
        return dimensionValues;
    }

    public void setDimensionValues(List<Float> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public double getDimensionMaxValue() {
        return dimensionMaxValue;
    }

    public void setDimensionMaxValue(double dimensionMaxValue) {
        this.dimensionMaxValue = dimensionMaxValue;
    }

    public int getDimensionCount() {
        return dimensionCount;
    }

    public void setDimensionCount(int dimensionCount) {
        this.dimensionCount = dimensionCount;
    }

    public int getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }
}
