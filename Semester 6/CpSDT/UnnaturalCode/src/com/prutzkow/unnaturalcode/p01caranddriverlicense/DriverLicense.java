package com.prutzkow.unnaturalcode.p01caranddriverlicense;

class DriverLicense {
	private boolean isTeenDriver;
	private boolean isAdult;
	private boolean isLearner;
	public DriverLicense(boolean isTeenDriver, boolean isAdult,
			boolean isLearner) {
		this.isTeenDriver = isTeenDriver;
		this.isAdult = isAdult;
		this.isLearner = isLearner;
	}
	public boolean isTeenDriver() {
		return isTeenDriver;
	}
	public boolean isAdult() {
		return isAdult;
	}
	public boolean isLearner() {
		return isLearner;
	}
}
