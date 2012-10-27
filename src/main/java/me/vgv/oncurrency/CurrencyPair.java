package me.vgv.oncurrency;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class CurrencyPair {

	private final Currency firstCurrency;
	private final double firstRate;

	private final Currency secondCurrency;
	private final double secondRate;

	public CurrencyPair(@Nonnull Currency firstCurrency, double firstRate, @Nonnull Currency secondCurrency, double secondRate) {
		Preconditions.checkNotNull(firstCurrency, "firstCurrency is null");
		Preconditions.checkNotNull(secondCurrency, "secondCurrency is null");

		this.firstCurrency = firstCurrency;
		this.firstRate = firstRate;
		this.secondCurrency = secondCurrency;
		this.secondRate = secondRate;
	}

	@Nonnull
	public Currency getFirstCurrency() {
		return firstCurrency;
	}

	public double getFirstRate() {
		return firstRate;
	}

	@Nonnull
	public Currency getSecondCurrency() {
		return secondCurrency;
	}

	public double getSecondRate() {
		return secondRate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CurrencyPair that = (CurrencyPair) o;

		if (Double.compare(that.firstRate, firstRate) != 0) return false;
		if (Double.compare(that.secondRate, secondRate) != 0) return false;
		if (firstCurrency != that.firstCurrency) return false;
		if (secondCurrency != that.secondCurrency) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = firstCurrency.hashCode();
		temp = firstRate != +0.0d ? Double.doubleToLongBits(firstRate) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + secondCurrency.hashCode();
		temp = secondRate != +0.0d ? Double.doubleToLongBits(secondRate) : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "CurrencyPair{" +
				"firstCurrency=" + firstCurrency +
				", firstRate=" + firstRate +
				", secondCurrency=" + secondCurrency +
				", secondRate=" + secondRate +
				'}';
	}
}
