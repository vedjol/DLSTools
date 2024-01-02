package com.veda.hrentgelt.dlstools.util.swing.styles;

public class LightGrayStyle extends CustomLightLaf {
    private static final long serialVersionUID = -4308556311053509040L;

    public LightGrayStyle() {
        super();
        registerCustomDefaultsSource("style");
    }

    @Override
    public String getName() {
        return "eher hell";
    }

    @Override
    public String toString() {
        return getName();
    }
}
