package ru.rsreu.afonin0717.model;

import java.util.*;

import ru.rsreu.afonin0717.initializer.EnterprisesInitializer;

public class Model {

	private final List<Enterprise> enterprises;

	public Model() {
		Enterprise[] initialEnterprises = EnterprisesInitializer.createInitialEnterprises();
		this.enterprises = new ArrayList<>(Arrays.asList(initialEnterprises));
	}

	public ModelResult execute() {

		List<Enterprise> enterprisesSortedByName = this.sortByDefaultOrder();
		List<Enterprise> enterprisesSortedByCityAndForm = this.sortByCityAndOwnership();
		Set<String> uniqueRegistrationCities = this.extractUniqueCities();
		List<Enterprise> enterprisesWithoutSpecificForm = this.removeEnterprisesByOwnership(OwnershipForm.OOO);

		Enterprise existingEnterprise = this.findEnterpriseByName("TechnoSoft");
		Enterprise missingEnterprise = this.findEnterpriseByName("Unknown");

		return new ModelResult(enterprisesSortedByName, enterprisesSortedByCityAndForm, uniqueRegistrationCities,
				enterprisesWithoutSpecificForm, existingEnterprise, missingEnterprise);
	}

	private List<Enterprise> sortByDefaultOrder() {
		List<Enterprise> sortedEnterprises = new ArrayList<>(this.enterprises);
		Collections.sort(sortedEnterprises);
		return sortedEnterprises;
	}

	private List<Enterprise> sortByCityAndOwnership() {
		List<Enterprise> sortedEnterprises = new ArrayList<>(this.enterprises);

		sortedEnterprises
				.sort(Comparator.comparing(Enterprise::registrationCity).thenComparing(Enterprise::ownershipForm));

		return sortedEnterprises;
	}

	private Set<String> extractUniqueCities() {
		Set<String> uniqueCities = new HashSet<>();

		for (Enterprise enterprise : this.enterprises) {
			uniqueCities.add(enterprise.registrationCity());
		}

		return uniqueCities;
	}

	private List<Enterprise> removeEnterprisesByOwnership(OwnershipForm ownershipForm) {

		List<Enterprise> filteredEnterprises = new ArrayList<>(this.enterprises);
		Iterator<Enterprise> enterpriseIterator = filteredEnterprises.iterator();

		while (enterpriseIterator.hasNext()) {
			Enterprise currentEnterprise = enterpriseIterator.next();

			if (currentEnterprise.ownershipForm() == ownershipForm) {
				enterpriseIterator.remove();
			}
		}

		return filteredEnterprises;
	}

	private Enterprise findEnterpriseByName(String companyName) {
		for (Enterprise enterprise : this.enterprises) {
			if (enterprise.companyName().equals(companyName)) {
				return enterprise;
			}
		}
		return null;
	}
}