package com.veda.hrentgelt.dlstools.util.swing.styles;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.util.Collections;

public class CustomDarkLaf extends FlatDarkLaf {
	private static final long serialVersionUID = -2248290849162553442L;

	public void updateAccentColor(Color c) {
		DarkStyle.setGlobalExtraDefaults(Collections.singletonMap("@accentColor",
				String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()) + "88"));
	}
}