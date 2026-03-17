package com.prutzkow.unnaturalcode.p01caranddriverlicense;

public class ClientRunner {
	public static void main(String[] args) {
		String result = testDriverLicenses(LicenseType.ADULT, LicenseType.TEEN_FULL, LicenseType.TEEN_LEARNER, LicenseType.INVALID);
		System.out.println(result);
	}
	
	private static String testDriverLicenses(LicenseType... licenseTypes) {
		StringBuilder builder = new StringBuilder();
		for (LicenseType license : licenseTypes) {
			DriverLicense driverLicense = new DriverLicense(license);
			Car myCar = new Car(driverLicense);
			
			myCar.enableDriveMode();
			
			builder.append(myCar.toString());
		}
		return builder.toString();
	}
}
