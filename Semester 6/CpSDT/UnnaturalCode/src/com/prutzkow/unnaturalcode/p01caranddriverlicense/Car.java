package com.prutzkow.unnaturalcode.p01caranddriverlicense;

class Car {
	private final DriverLicense driverLicense;
	private boolean isReady = false;
	
	public Car(DriverLicense driverLicense) {
		this.driverLicense = driverLicense;
	}
	
	public void enableDriveMode() {
		this.isReady = this.driverLicense.canDrive();
	}
	
	private boolean isCarReady() {
		return this.isReady;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Human type: " + this.driverLicense.getHumanType());
		builder.append("\nType of license: " + this.driverLicense.getDriveModeMessage());
		builder.append("\nCar condition: ");
		if (this.isCarReady()) {
			builder.append("Car is ready to drive!");
		} else {
			builder.append("Car is not ready to drive!");
		}
		builder.append("\n\n");
		return builder.toString();
	}
}