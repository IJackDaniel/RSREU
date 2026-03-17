package com.prutzkow.unnaturalcode.p01caranddriverlicense;

public enum LicenseType {
	ADULT("Adult", "Drive mode enabled!") {
		@Override
		public boolean canDrive() {
			return true;
		}
	},
	TEEN_FULL("Teen full", "Drive mode enabled!") {
		@Override
		public boolean canDrive() {
			return true;
		}
	},
	TEEN_LEARNER("Teen learner", "You cannot drive without a licensed adult's supervision.") {
		@Override
		public boolean canDrive() {
			return false;
		}
	},
	INVALID("Invalid", "You don't have a valid driver's license.") {
		@Override
		public boolean canDrive() {
			return false;
		}
	};
	
	private final String humanType;
	private final String driveModeMessage;

	LicenseType(String humanType, String driveModeMessage) {
		this.humanType = humanType;
		this.driveModeMessage = driveModeMessage;
	}

	public abstract boolean canDrive();
	
	public String getHumanType() {
		return this.humanType;
	}
	
	public String getDriveModeMessage() {
		return this.driveModeMessage;
	}
}
