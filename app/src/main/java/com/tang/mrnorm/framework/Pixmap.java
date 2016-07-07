package com.tang.mrnorm.framework;

import com.tang.mrnorm.framework.Graphics.PixmapFormat;
/**
 * Created by tang on 4/14/16.
 */
public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}
