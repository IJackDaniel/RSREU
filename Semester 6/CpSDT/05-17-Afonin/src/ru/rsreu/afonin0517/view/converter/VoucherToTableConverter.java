package ru.rsreu.afonin0517.view.converter;

import ru.rsreu.afonin0517.model.voucher.AbstractVoucher;

public final class VoucherToTableConverter {

	private VoucherToTableConverter() {
	}

	public static String[][] convertVouchersToTableRows(AbstractVoucher[] vouchers) {

		String[][] table = new String[vouchers.length][VoucherTableColumn.getColumnsCount()];

		for (int i = 0; i < vouchers.length; i++) {
			AbstractVoucher voucher = vouchers[i];

			table[i][VoucherTableColumn.TYPE.getIndex()] = voucher.getClass().getSimpleName();

			table[i][VoucherTableColumn.TITLE.getIndex()] = voucher.getTitle();

			table[i][VoucherTableColumn.DAYS.getIndex()] = String.valueOf(voucher.getDays());

			table[i][VoucherTableColumn.PRICE.getIndex()] = String.format("%.2f", voucher.getPrice());

			table[i][VoucherTableColumn.TRANSPORT.getIndex()] = voucher.getTransport().type();

			table[i][VoucherTableColumn.MEAL.getIndex()] = voucher.getMealPlan().type();

			table[i][VoucherTableColumn.VALUE.getIndex()] = String.format("%.2f", voucher.calculateValue());
		}

		return table;
	}
}