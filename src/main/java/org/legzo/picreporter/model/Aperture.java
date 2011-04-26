package org.legzo.picreporter.model;

public class Aperture extends Parameter implements Comparable<Parameter> {

	public Aperture(float f) {
		super(f);
	}

	@Override
	public String toString() {
		return "f" + value;
	}
}
