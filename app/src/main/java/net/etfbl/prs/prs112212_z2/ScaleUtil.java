package net.etfbl.prs.prs112212_z2;

/**
 * Created by milan on 1.5.2016.
 */
public class ScaleUtil {

    public static float factor(int width, int height, int resWidth, int resHeight) {
        float widthFactor = (float)resWidth/width;
        float heightFactor = (float)resHeight/height;
        return Math.min(widthFactor, heightFactor);
    }

    public static int scale(int numerator, int denominator, int value) {
        float factor = (float)numerator/denominator;
        return (int)(factor*value);
    }
}
