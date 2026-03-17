package com.prutzkow.unnaturalcode.p01caranddriverlicense;

public enum LicenseType {
	ADULT("Drive mode enabled!") {
		@Override
		public boolean canDrive() {
			return true;
		}
	},
	TEEN_FULL("Drive mode enabled!") {
		@Override
		public boolean canDrive() {
			return true;
		}
	},
	TEEN_LEARNER("You cannot drive without a licensed adult's supervision.") {
		@Override
		public boolean canDrive() {
			return false;
		}
	},
	INVALID("You don't have a valid driver's license.") {
		@Override
		public boolean canDrive() {
			return false;
		}
	};

	private final String driveModeMessage;

	LicenseType(String driveModeMessage) {
		this.driveModeMessage = driveModeMessage;
	}

	public abstract boolean canDrive();

	public String getDriveModeMessage() {
		return this.driveModeMessage;
	}
}
