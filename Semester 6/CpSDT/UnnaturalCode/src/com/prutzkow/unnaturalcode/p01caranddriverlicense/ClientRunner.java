package com.prutzkow.unnaturalcode.p01caranddriverlicense;

/**
 * Source: Beckwith M. (2024) JVM Performance Engineering. Inside OpenJDK and
 * the HotSpot Java Virtual Machine // Addison-Wesley
 * 
 * Description (p. 10-11): In this example, the Car class requires a
 * DriverLicense to enable drive mode. The driver’s license can be for an adult,
 * a teen driver with a learner’s permit, or a teen driver with a full license.
 * The enableDriveMode() method checks the driver’s license using the isAdult(),
 * isTeenDriver(), and isLearner() methods, and prints the appropriate message
 * to the console.
 * 
 * Why is the package class code unnatural?
 */

public class ClientRunner {
	public static void main(String[] args) {
		DriverLicense driverLicense = new DriverLicense(false, true, false);
		Car myCar = new Car(driverLicense);
		myCar.enableDriveMode();
	}
}
