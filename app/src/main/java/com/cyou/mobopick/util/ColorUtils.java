package com.cyou.mobopick.util;

import android.graphics.Color;

/**
 * Created by chengfei on 14-10-10.
 */
public class ColorUtils {

    public static int mixColor(int color1, int color2, float color1Compent) {

        int red1 = Color.red(color1);
        int green1 = Color.green(color1);
        int blue1 = Color.blue(color1);


        int red2 = Color.red(color2);
        int green2 = Color.green(color2);
        int blue2 = Color.blue(color2);

        //r1*(1-a2)+(r2*a2)=r3

        int redMixed = (int) (red1 * color1Compent + red2 * (1 - color1Compent));
        int greenMixed = (int) (green1 * color1Compent + green2 * (1 - color1Compent));
        int blueMixed = (int) (blue1 * color1Compent + blue2 * (1 - color1Compent));

        return Color.rgb(redMixed, greenMixed, blueMixed);
    }
}
