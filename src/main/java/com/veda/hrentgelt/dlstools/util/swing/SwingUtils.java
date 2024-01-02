package com.veda.hrentgelt.dlstools.util.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

public class SwingUtils {
    public static void updateLAF(BasicLookAndFeel bLAF, Color foreground) {
        UIManager.put("ToolTip.foreground", foreground);
        try {
            UIManager.setLookAndFeel(bLAF);
            for (Window windows : Window.getWindows()) {
                updateLAFRecursive(windows);
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void updateLAFRecursive(Window window) {
        for (Window childWindow : window.getOwnedWindows()) {
            updateLAFRecursive(childWindow);
        }
        SwingUtilities.updateComponentTreeUI(window);
    }
}
