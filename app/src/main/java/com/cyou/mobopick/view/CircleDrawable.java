package com.cyou.mobopick.view;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by chengfei on 14/10/31.
 */
public class CircleDrawable extends ShapeDrawable {

    CircleDrawable() {
        Shape shape = new OvalShape();
        setShape(shape);
//        setColorFilter();
    }

}
