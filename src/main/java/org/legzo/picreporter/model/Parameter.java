package org.legzo.picreporter.model;

public class Parameter implements Comparable<Parameter> {

	protected float value;

	public Parameter(float value) {
		this.value = value;
	}

	public Parameter() {
	}

	public int compareTo(Parameter p) {
		if (p.getClass() != this.getClass()) {
			throw new RuntimeException("Cannot compare " + p.getClass() + " and " + this.getClass());
		}
		return Float.compare(value, p.value);
	}
}
