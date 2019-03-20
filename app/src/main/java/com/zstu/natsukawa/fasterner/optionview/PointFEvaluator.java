package com.zstu.natsukawa.fasterner.optionview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import java.util.ArrayList;

public class PointFEvaluator implements TypeEvaluator<ArrayList<PointF>> {

    @Override
    public ArrayList<PointF> evaluate(float fraction, ArrayList<PointF> startValue, ArrayList<PointF> endValue) {
        ArrayList<PointF> points = new ArrayList<>();
        int size = endValue.size();
        for(int i = 0; i < size; i++){
            PointF startPoint = startValue.get(0);
            PointF endPoint = endValue.get(i);
            float x = startPoint.x + fraction * (endPoint.x - startPoint.x);
            float y = startPoint.y + fraction * (endPoint.y - startPoint.y);
            points.add(new PointF(x,y));
        }
        return points;
    }
}
