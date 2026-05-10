package ru.rsreu.afonin0717.view;

import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0717.model.*;
import ru.rsreu.afonin0717.view.converter.EnterpriseToTableConverter;
import ru.rsreu.afonin0717.view.table.SmartTableDrawer;

public class View {

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private final ModelResult result;
	private final StringBuilder builder = new StringBuilder();

	public View(ModelResult result) {
		this.result = result;
	}

	public void show() {

		this.builder.append(RESOURCER.getString("message.output.sorted.default"));
		this.drawTable(this.result.getSortedDefault());

		this.builder.append(RESOURCER.getString("message.output.sorted.cityOwnership"));
		this.drawTable(this.result.getSortedByCityAndForm());

		this.builder.append(RESOURCER.getString("message.output.unique.cities"));
		this.builder.append(this.result.getUniqueCities()).append("\n");

		this.builder.append(RESOURCER.getString("message.output.filtered"));
		this.drawTable(this.result.getFilteredList());

		this.builder.append(RESOURCER.getString("message.output.search"));

		this.appendSearchResult(this.result.getFound());
		this.appendSearchResult(this.result.getNotFound());
	}

	private void drawTable(List<Enterprise> enterprises) {

		Enterprise[] array = enterprises.toArray(new Enterprise[0]);

		String[] headers = { RESOURCER.getString("table.column.name"), RESOURCER.getString("table.column.city"),
				RESOURCER.getString("table.column.ownership") };

		String[][] table = EnterpriseToTableConverter.convertEnterprisesToTableRows(array);

		this.builder.append(SmartTableDrawer.drawTable(headers, table));
	}

	private void appendSearchResult(Enterprise enterprise) {

		if (enterprise == null) {
			this.builder.append(RESOURCER.getString("message.notFound")).append("\n");
		} else {
			this.builder.append(RESOURCER.getString("message.found")).append(enterprise).append("\n");
		}
	}

	public String toStringType() {
		return this.builder.toString();
	}
}