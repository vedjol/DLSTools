package com.veda.hrentgelt.dlstools.util.swing;

import com.formdev.flatlaf.FlatLaf;

import java.awt.*;
import java.util.Random;

public class ColorUtils {

    /**
     * creates a randomized Color for the "random color button"
     *
     * @return a randomized Color
     */
    public static Color getRandomColor(FlatLaf currentLAF) {
        if (currentLAF.isDark()) {
            return getRandomColor(1f, 1f);
        } else {
            return getRandomColor(0f, 0.4f);
        }
    }

    private static Color getRandomColor(float minBrightness, float maxBrightness) {
        if (maxBrightness < minBrightness) {
            float f = maxBrightness;
            maxBrightness = minBrightness;
            minBrightness = f;
        }
        Random random = new Random();
        float h = random.nextFloat();
        float s = 1f;
        float b = minBrightness + ((1f - maxBrightness) * random.nextFloat());
        return Color.getHSBColor(h, s, b);
    }

    public static boolean isFittingColor(FlatLaf laf, Color c) {
        double darkness = 1 - (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()) / 255;
        if (darkness < 0.5) {
            return laf.isDark(); // It's a light color
        } else {
            return !laf.isDark(); // It's a dark color
        }
    }

    public static Color getFittingColor(FlatLaf laf) {
        if (laf.isDark()) {
            return getRandomColor(.75f, 1f);
        } else return getRandomColor(0f, .4f);
    }
}
