/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p/>
 * All Rights Reserved
 * <p/>
 * \file ScaleUtil.java
 * \brief
 * This file contains utilities that are needed for scaling bitmaps
 * <p/>
 * <p/>
 * Created on 27.04.2016
 *
 * @Author Milan Maric
 * <p/>
 * \notes
 * <p/>
 * <p/>
 * \history
 * <p/>
 **********************************************************************/
package net.etfbl.prs.prs112212_z2;


public class ScaleUtil {

    /**
     *This method is used to get scale factor of bitmaps
     * @param width with of destination bitmap
     * @param height height of destination bitmap
     * @param resWidth width of source bitmap
     * @param resHeight height of destination bitmap
     * @return
     */
    public static float factor(int width, int height, int resWidth, int resHeight) {
        float widthFactor = (float) resWidth / width;
        float heightFactor = (float) resHeight / height;
        return Math.min(widthFactor, heightFactor);
    }

    /**
     * This method is used for finding point position in destination, when  we know the source point rate.
     * @param numerator destination bitmap size
     * @param denominator original bitmap size
     * @param value position in px that should be scaled
     * @return
     */
    public static int scale(int numerator, int denominator, int value) {
        float factor = (float) numerator / denominator;
        return (int) (factor * value);
    }
}
