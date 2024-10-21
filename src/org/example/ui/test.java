package org.example.ui;

import com.formdev.flatlaf.FlatLightLaf;

public class test
	extends FlatLightLaf
{
	public static final String NAME = "test";

	public static boolean setup() {
		return setup( new test() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, test.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
