package ru.rsreu.afonin0617.view.converter;

import ru.rsreu.afonin0617.model.Enterprise;

public final class EnterpriseToTableConverter {

	private EnterpriseToTableConverter() {
	}

	public static String[][] convertEnterprisesToTableRows(Enterprise[] enterprises) {

		String[][] table = new String[enterprises.length][EnterpriseTableColumn.getColumnsCount()];

		for (int index = 0; index < enterprises.length; index++) {

			Enterprise enterprise = enterprises[index];

			table[index][EnterpriseTableColumn.COMPANY_NAME.getIndex()] = enterprise.companyName();

			table[index][EnterpriseTableColumn.REGISTRATION_CITY.getIndex()] = enterprise.registrationCity();

			table[index][EnterpriseTableColumn.OWNERSHIP_FORM.getIndex()] = enterprise.ownershipForm().name();
		}

		return table;
	}
}