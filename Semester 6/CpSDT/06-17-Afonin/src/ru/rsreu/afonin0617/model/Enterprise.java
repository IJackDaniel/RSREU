package ru.rsreu.afonin0617.model;

public record Enterprise(String companyName, String registrationCity, OwnershipForm ownershipForm) {

	@Override
	public String toString() {
		return this.companyName + ";" + this.registrationCity + ";" + this.ownershipForm;
	}
}