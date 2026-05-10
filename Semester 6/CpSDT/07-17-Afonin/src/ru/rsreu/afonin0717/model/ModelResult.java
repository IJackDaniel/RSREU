package ru.rsreu.afonin0717.model;

import java.util.List;
import java.util.Set;

public class ModelResult {

	private final List<Enterprise> sortedDefault;
	private final List<Enterprise> sortedByCityAndForm;
	private final Set<String> uniqueCities;
	private final List<Enterprise> filteredList;
	private final Enterprise found;
	private final Enterprise notFound;

	public ModelResult(List<Enterprise> sortedDefault, List<Enterprise> sortedByCityAndForm, Set<String> uniqueCities,
			List<Enterprise> filteredList, Enterprise found, Enterprise notFound) {

		this.sortedDefault = sortedDefault;
		this.sortedByCityAndForm = sortedByCityAndForm;
		this.uniqueCities = uniqueCities;
		this.filteredList = filteredList;
		this.found = found;
		this.notFound = notFound;
	}

	public List<Enterprise> getSortedDefault() {
		return sortedDefault;
	}

	public List<Enterprise> getSortedByCityAndForm() {
		return sortedByCityAndForm;
	}

	public Set<String> getUniqueCities() {
		return uniqueCities;
	}

	public List<Enterprise> getFilteredList() {
		return filteredList;
	}

	public Enterprise getFound() {
		return found;
	}

	public Enterprise getNotFound() {
		return notFound;
	}
}