package ru.rsreu.afonin0717.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {

	private final List<Enterprise> enterprises;
	private final Map<String, Enterprise> enterprisesByCompanyName;

	public Model(Enterprise[] enterprises) {

		this.enterprises = new ArrayList<>(Arrays.asList(enterprises));

		this.enterprisesByCompanyName = this.createEnterprisesByCompanyName();
	}

	public ModelResult execute() {

		List<Enterprise> enterprisesSortedByName = this.sortByDefaultOrder();

		List<Enterprise> enterprisesSortedByCityAndOwnership = this.sortByCityAndOwnership();

		Set<String> uniqueRegistrationCities = this.extractUniqueRegistrationCities();

		List<Enterprise> enterprisesWithoutLimitedLiability = this.removeByOwnershipForm(OwnershipForm.OOO);

		Enterprise existingEnterprise = this.findEnterpriseByCompanyName("TechnoSoft");

		Enterprise missingEnterprise = this.findEnterpriseByCompanyName("UnknownCompany");

		return new ModelResult(enterprisesSortedByName, enterprisesSortedByCityAndOwnership, uniqueRegistrationCities,
				enterprisesWithoutLimitedLiability, existingEnterprise, missingEnterprise);
	}

	private Map<String, Enterprise> createEnterprisesByCompanyName() {

		Map<String, Enterprise> enterprisesByName = new HashMap<>();

		for (Enterprise enterprise : this.enterprises) {
			enterprisesByName.put(enterprise.companyName(), enterprise);
		}

		return enterprisesByName;
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

	private Set<String> extractUniqueRegistrationCities() {

		Set<String> uniqueCities = new HashSet<>();

		for (Enterprise enterprise : this.enterprises) {
			uniqueCities.add(enterprise.registrationCity());
		}

		return uniqueCities;
	}

	private List<Enterprise> removeByOwnershipForm(OwnershipForm ownershipForm) {

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

	private Enterprise findEnterpriseByCompanyName(String companyName) {

		if (this.enterprisesByCompanyName.containsKey(companyName)) {

			return this.enterprisesByCompanyName.get(companyName);
		}

		return null;
	}
}