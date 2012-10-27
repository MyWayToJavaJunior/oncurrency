package me.vgv.oncurrency;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class CurrencyRate {

	private final Currency currency;
	private final double rate;

	public CurrencyRate(Currency currency, double rate) {
		Preconditions.checkNotNull(currency, "currency is null");

		this.currency = currency;
		this.rate = rate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public double getRate() {
		return rate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CurrencyRate that = (CurrencyRate) o;

		if (Double.compare(that.rate, rate) != 0) return false;
		if (currency != that.currency) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = currency.hashCode();
		temp = rate != +0.0d ? Double.doubleToLongBits(rate) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}

