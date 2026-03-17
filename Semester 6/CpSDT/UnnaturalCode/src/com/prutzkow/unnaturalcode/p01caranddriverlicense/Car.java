package com.prutzkow.unnaturalcode.p01caranddriverlicense;

class Car {
	private DriverLicense driverLicense;
	public Car(DriverLicense driverLicense) {
		this.driverLicense = driverLicense;
	}
	public void enableDriveMode() {
		if (driverLicense.isAdult()) {
			System.out.println("Drive mode enabled!");
		} else if (driverLicense.isTeenDriver()) {
			if (driverLicense.isLearner()) {
				System.out.println(
						"You cannot drive without a licensed adult's supervision.");
			} else {
				System.out.println("Drive mode enabled!");
			}
		} else {

			System.out.println("You don't have a valid driver's license.");
		}
	}
}