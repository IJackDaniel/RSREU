package ru.rsreu.afonin0517.model.agency;

import java.util.Arrays;

import ru.rsreu.afonin0517.model.voucher.AbstractVoucher;
import ru.rsreu.afonin0517.model.voucher.NullVoucher;

public class TravelAgency {

	private final AbstractVoucher[] vouchers;

	public TravelAgency(AbstractVoucher[] vouchers) {
		this.vouchers = vouchers;
	}

	public AbstractVoucher[] getVouchers() {
		return this.vouchers;
	}

	public void sortByValue() {
		Arrays.sort(this.vouchers);
	}

	public AbstractVoucher findByTitle(String title) {
		return Arrays.stream(this.vouchers).filter(voucher -> voucher.getTitle().equals(title)).findFirst()
				.orElse(new NullVoucher());
	}

	public double calculateTotalPrice() {
		return Arrays.stream(this.vouchers).mapToDouble(AbstractVoucher::getPrice).sum();
	}

	public int calculateTotalDays() {
		return Arrays.stream(this.vouchers).mapToInt(AbstractVoucher::getDays).sum();
	}
}