package ru.rsreu.afonin0617.initializer;

import ru.rsreu.afonin0617.model.Enterprise;
import ru.rsreu.afonin0617.model.OwnershipForm;

public final class EnterprisesInitializer {

	private EnterprisesInitializer() {
	}

	public static Enterprise[] createInitialEnterprises() {

		return new Enterprise[] { new Enterprise("TechnoSoft", "Moscow", OwnershipForm.AO),
				new Enterprise("TechnoSoft", "Moscow", OwnershipForm.AO),
				new Enterprise("BuildPro", "Kazan", OwnershipForm.PAO),
				new Enterprise("AlphaTrade", "Kazan", OwnershipForm.OOO),
				new Enterprise("NeoMarket", "Ryazan", OwnershipForm.PAO),
				new Enterprise("NeoMarket", "Ryazan", OwnershipForm.PAO),
				new Enterprise("GreenFarm", "Kazan", OwnershipForm.OOO) };
	}
}