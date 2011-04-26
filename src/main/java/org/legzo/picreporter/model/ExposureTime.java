package org.legzo.picreporter.model;

public class ExposureTime extends Parameter implements Comparable<Parameter> {
	public ExposureTime(float f) {
		super(f);
	}

	@Override
	public String toString() {
		return "1/" + (int) value + "s";
	}
}
