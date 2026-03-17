package com.prutzkow.unnaturalcode.p01caranddriverlicense;

class DriverLicense {
	private final LicenseType licenseType;
	
	public DriverLicense(LicenseType licenseType) {
		this.licenseType = licenseType;
	}
	
	public boolean canDrive() {
		return this.licenseType.canDrive();
	}
	
	public String getHumanType() {
		return this.licenseType.getHumanType();
	}
	
	public String getDriveModeMessage() {
		return this.licenseType.getDriveModeMessage();
	}
}
