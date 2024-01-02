package com.veda.hrentgelt.dlstools.util.swing.styles;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.util.Collections;

public class CustomLightLaf extends FlatLightLaf {
	private static final long serialVersionUID = -7982400039437155726L;

	public void updateAccentColor(Color c) {
		FlatLightLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor",
				String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()) + "88"));
		
	}
}
