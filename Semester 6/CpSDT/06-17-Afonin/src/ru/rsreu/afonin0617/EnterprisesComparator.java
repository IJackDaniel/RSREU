package ru.rsreu.afonin0617;

import ru.rsreu.afonin0617.model.Enterprise;

public final class EnterprisesComparator {

	private EnterprisesComparator() {
	}

	public static boolean areEnterprisesEqual(Enterprise[] firstArray, Enterprise[] secondArray) {

		if (firstArray.length != secondArray.length) {
			return false;
		}

		return EnterprisesComparator.compareEnterpriseElements(firstArray, secondArray);
	}

	private static boolean compareEnterpriseElements(Enterprise[] firstArray, Enterprise[] secondArray) {

		for (int index = 0; index < firstArray.length; index++) {

			if (!EnterprisesComparator.areEnterpriseRecordsEqual(firstArray[index], secondArray[index])) {
				return false;
			}
		}

		return true;
	}

	private static boolean areEnterpriseRecordsEqual(Enterprise firstEnterprise, Enterprise secondEnterprise) {

		return firstEnterprise.companyName().equals(secondEnterprise.companyName())
				&& firstEnterprise.registrationCity().equals(secondEnterprise.registrationCity())
				&& firstEnterprise.ownershipForm() == secondEnterprise.ownershipForm();
	}
}