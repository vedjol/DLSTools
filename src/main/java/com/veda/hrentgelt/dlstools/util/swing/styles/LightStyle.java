package com.veda.hrentgelt.dlstools.util.swing.styles;

import java.io.Serial;

public class LightStyle extends CustomLightLaf {
	@Serial
	private static final long serialVersionUID = -4308556311053509040L;

	public LightStyle() {
		super();
		registerCustomDefaultsSource("style");
	}

	@Override
	public String getName() {
		return "sehr hell";
	}
	
	@Override
	public String toString() {
		return getName();
	}
}