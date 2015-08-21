package com.tdb.mip.filter;

import com.tdb.mip.util.GraphicsUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

// Original code : copyright

/*
 * $Id: ColorTintFilter.java,v 1.1 2007/01/15 16:12:02 gfx Exp $
 *
 * Dual-licensed under LGPL (Sun and Romain Guy) and BSD (Romain Guy).
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * Copyright (c) 2006 Romain Guy <romain.guy@mac.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

// https://code.google.com/p/filthy-rich-clients/source/browse/trunk/filthyRichClients-16/src16.StaticEffects.Blur/org/progx/artemis/image/ColorTintFilter.java?r=2
public class Tint implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tint.class);

    private final Color tint;

    private final float mixValue;

    /**
     * @param tint
     * @param mixValue 0f..1f
     */
    public Tint(Color tint, float mixValue) {
        this.tint = tint;
        this.mixValue = mixValue;
    }

    @Override
    public BufferedImage applyTo(BufferedImage src) {
        LOGGER.debug("tint " + tint + " mixValue=" + mixValue);

        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dst = createCompatibleDestImage(src, null);

        int[] pixels = new int[width * height];
        GraphicsUtilities.getPixels(src, 0, 0, width, height, pixels);
        mixColor(pixels);
        GraphicsUtilities.setPixels(dst, 0, 0, width, height, pixels);

        return dst;
    }

    private void mixColor(int[] pixels) {
        // we don't want to alter the alpha channel
        // int mix_a = tint.getAlpha();
        int mix_r = tint.getRed();
        int mix_g = tint.getGreen();
        int mix_b = tint.getBlue();

        for (int i = 0; i < pixels.length; i++) {
            int argb = pixels[i];

            int a = (argb >> 24) & 0xFF;
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = (argb) & 0xFF;

            // a = (int) (a * (1.0f - mixValue) + mix_a * mixValue);
            r = (int) (r * (1.0f - mixValue) + mix_r * mixValue);
            g = (int) (g * (1.0f - mixValue) + mix_g * mixValue);
            b = (int) (b * (1.0f - mixValue) + mix_b * mixValue);

            pixels[i] = a << 24 | r << 16 | g << 8 | b;
        }
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }

        return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
                destCM.isAlphaPremultiplied(), null);
    }

    @Override
    public Filter copy() {
        return new Tint(tint, mixValue);
    }
}
