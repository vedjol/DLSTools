package com.veda.hrentgelt.dlstools.util.swing.styles;

public class SpecialStyle extends CustomDarkLaf {
    private static final long serialVersionUID = -4308556311053509040L;

    public SpecialStyle() {
        super();
        registerCustomDefaultsSource("style");
    }

    @Override
    public String getName() {
        return "Semi-dark";
    }

    @Override
    public String toString() {
        return getName();
    }
}
