package me.vgv.oncurrency;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class UtilsTest {

	private double getRate(Currency currency, Set<CurrencyRate> rates) {
		for (CurrencyRate currencyRate : rates) {
			if (currency.equals(currencyRate.getCurrency())) {
				return currencyRate.getRate();
			}
		}

		throw new AssertionError("Invalid currency " + currency);
	}

	@Test(groups = "unit")
	public void testOnlyDirectRecalculate() throws Exception {
		Set<CurrencyPair> pairs = new HashSet<>();
		pairs.add(new CurrencyPair(Currency.RUB, 100, Currency.CAD, 3.17));
		pairs.add(new CurrencyPair(Currency.RUB, 1000, Currency.GBP, 19.77));
		pairs.add(new CurrencyPair(Currency.JPY, 10, Currency.RUB, 3.94));
		pairs.add(new CurrencyPair(Currency.USD, 1, Currency.RUB, 31.41));

		Set<CurrencyRate> currencyRates = Utils.recalculate(Currency.RUB, pairs);
		Assert.assertEquals(currencyRates.size(), 4);

		Assert.assertEquals(getRate(Currency.CAD, currencyRates), 3.17 / 100, 0.000001);
		Assert.assertEquals(getRate(Currency.GBP, currencyRates), 19.77 / 1000, 0.000001);
		Assert.assertEquals(getRate(Currency.JPY, currencyRates), 10 / 3.94, 0.000001);
		Assert.assertEquals(getRate(Currency.USD, currencyRates), 1 / 31.41, 0.000001);
	}

	@Test(groups = "unit")
	public void testAllRecalculate() throws Exception {
		Set<CurrencyPair> pairs = new HashSet<>();

		pairs.add(new CurrencyPair(Currency.NZD, 10, Currency.AUD, 7.93));
		pairs.add(new CurrencyPair(Currency.AUD, 10, Currency.GBP, 6.43));
		pairs.add(new CurrencyPair(Currency.GBP, 10, Currency.CHF, 15.05));
		pairs.add(new CurrencyPair(Currency.JPY, 1000, Currency.USD, 12.55));
		pairs.add(new CurrencyPair(Currency.USD, 10, Currency.GBP, 6.21));
		pairs.add(new CurrencyPair(Currency.CAD, 10, Currency.USD, 10.01));
		pairs.add(new CurrencyPair(Currency.USD, 1, Currency.RUB, 31.41));

		Set<CurrencyRate> currencyRates = Utils.recalculate(Currency.RUB, pairs);
		Assert.assertEquals(currencyRates.size(), 7);
		Assert.assertEquals(getRate(Currency.USD, currencyRates), 1 / 31.41, 0.000001);
		Assert.assertEquals(getRate(Currency.GBP, currencyRates), 6.21 / (31.41 * 10), 0.000001);
		Assert.assertEquals(getRate(Currency.CAD, currencyRates), 10 / (31.41 * 10.01), 0.000001);
		Assert.assertEquals(getRate(Currency.JPY, currencyRates), 1000 / (31.41 * 12.55), 0.000001);
		Assert.assertEquals(getRate(Currency.CHF, currencyRates), 15.05 / 10 * (6.21 / (31.41 * 10)), 0.000001);
		Assert.assertEquals(getRate(Currency.AUD, currencyRates), 10 / 6.43 * (6.21 / (31.41 * 10)), 0.000001);
		Assert.assertEquals(getRate(Currency.NZD, currencyRates), 10 / 7.93 * (10 / 6.43 * (6.21 / (31.41 * 10))), 0.000001);
	}

}
