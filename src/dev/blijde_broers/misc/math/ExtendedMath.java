package dev.blijde_broers.misc.math;

public class ExtendedMath {

	public static float random(float min, float max) {
		float out = (float) Math.random();
		out = min + (out * (max - min));
		return out;
	}
	
	public static double random(double max) {
		return Math.random() * max;
	}
	
}
