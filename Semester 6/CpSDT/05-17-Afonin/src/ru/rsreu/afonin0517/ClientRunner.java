package ru.rsreu.afonin0517;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0517.initializer.VoucherInitializer;
import ru.rsreu.afonin0517.model.agency.TravelAgency;
import ru.rsreu.afonin0517.model.voucher.AbstractVoucher;
import ru.rsreu.afonin0517.view.converter.VoucherToTableConverter;
import ru.rsreu.afonin0517.view.table.SmartTableDrawer;

public class ClientRunner {

	private static final String REQUIRED_TITLE = "Sunny Beach";
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {
	}

	public static void main(String[] args) {

		StringBuilder output = new StringBuilder();

		AbstractVoucher[] vouchers = VoucherInitializer.initializeVouchers();
		TravelAgency agency = new TravelAgency(vouchers);

		agency.sortByValue();

		String[] headers = new String[] { ClientRunner.RESOURCER.getString("message.tableColumn.type"),
				ClientRunner.RESOURCER.getString("message.tableColumn.title"),
				ClientRunner.RESOURCER.getString("message.tableColumn.days"),
				ClientRunner.RESOURCER.getString("message.tableColumn.price"),
				ClientRunner.RESOURCER.getString("message.tableColumn.transport"),
				ClientRunner.RESOURCER.getString("message.tableColumn.meal"),
				ClientRunner.RESOURCER.getString("message.tableColumn.value") };

		String[][] data = VoucherToTableConverter.convertVouchersToTableRows(agency.getVouchers());

		output.append(ClientRunner.RESOURCER.getString("message.output.header"));
		output.append(SmartTableDrawer.drawTable(headers, data));

		output.append(ClientRunner.RESOURCER.getString("message.output.totalPrice"))
				.append(agency.calculateTotalPrice());

		output.append(ClientRunner.RESOURCER.getString("message.output.totalDays")).append(agency.calculateTotalDays());

		AbstractVoucher found = agency.findByTitle(ClientRunner.REQUIRED_TITLE);

		output.append(ClientRunner.RESOURCER.getString("message.output.searchResult")).append(found.getTitle());

		System.out.println(output);
	}
}