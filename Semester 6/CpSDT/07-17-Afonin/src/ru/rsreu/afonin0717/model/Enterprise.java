package ru.rsreu.afonin0717.model;

public record Enterprise(String companyName, String registrationCity, OwnershipForm ownershipForm)
		implements Comparable<Enterprise> {

	@Override
	public String toString() {
		return this.companyName + ";" + this.registrationCity + ";" + this.ownershipForm;
	}

	@Override
	public int compareTo(Enterprise other) {
		return this.companyName.compareTo(other.companyName);
	}
}