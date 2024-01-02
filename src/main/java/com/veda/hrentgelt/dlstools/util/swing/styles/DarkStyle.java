package com.veda.hrentgelt.dlstools.util.swing.styles;

public class DarkStyle extends CustomDarkLaf {
	private static final long serialVersionUID = -4308556311053509040L;

	public DarkStyle() {
		super();
		registerCustomDefaultsSource("style");
	}

	@Override
	public String getName() {
		return "dunkel";
	}

	@Override
	public String toString() {
		return getName();
	}
}