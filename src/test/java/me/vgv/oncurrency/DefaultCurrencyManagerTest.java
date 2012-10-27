package me.vgv.oncurrency;

import me.vgv.oncurrency.provider.CurrencyRatesProvider;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DefaultCurrencyManagerTest {

	@Test(groups = "unit")
	public void testGetRate() throws Exception {
		Set<CurrencyPair> currencyPairs = new HashSet<>();
		currencyPairs.add(new CurrencyPair(Currency.USD, 10, Currency.RUB, 1));
		currencyPairs.add(new CurrencyPair(Currency.USD, 100, Currency.GBP, 1));
		currencyPairs.add(new CurrencyPair(Currency.USD, 1000, Currency.JPY, 1));
		CurrencyRatesProvider currencyRatesProvider = Mockito.mock(CurrencyRatesProvider.class);
		Mockito.when(currencyRatesProvider.getCurrencyPairs()).thenReturn(currencyPairs);

		DefaultCurrencyManager defaultCurrencyManager = new DefaultCurrencyManager(currencyRatesProvider, Currency.RUB);
		Assert.assertEquals(defaultCurrencyManager.getRate(Currency.USD).getRate(), 10.0, 0.00001);
		Assert.assertEquals(defaultCurrencyManager.getRate(Currency.GBP).getRate(), 0.1, 0.00001);
		Assert.assertEquals(defaultCurrencyManager.getRate(Currency.JPY).getRate(), 0.01, 0.00001);

		Mockito.verify(currencyRatesProvider).getCurrencyPairs();
	}

	@Test(groups = "unit")
	public void testGetRates() throws Exception {
		Set<CurrencyPair> currencyPairs = new HashSet<>();
		currencyPairs.add(new CurrencyPair(Currency.USD, 10, Currency.RUB, 1));
		currencyPairs.add(new CurrencyPair(Currency.USD, 100, Currency.GBP, 1));
		currencyPairs.add(new CurrencyPair(Currency.USD, 1000, Currency.JPY, 1));
		CurrencyRatesProvider currencyRatesProvider = Mockito.mock(CurrencyRatesProvider.class);
		Mockito.when(currencyRatesProvider.getCurrencyPairs()).thenReturn(currencyPairs);

		DefaultCurrencyManager defaultCurrencyManager = new DefaultCurrencyManager(currencyRatesProvider, Currency.RUB);
		Set<CurrencyRate> currencyRates = defaultCurrencyManager.getRates();

		Assert.assertEquals(currencyRates.size(), 3);
		Assert.assertTrue(currencyRates.contains(new CurrencyRate(Currency.USD, 10.0)));
		Assert.assertTrue(currencyRates.contains(new CurrencyRate(Currency.GBP, 0.1)));
		Assert.assertTrue(currencyRates.contains(new CurrencyRate(Currency.JPY, 0.01)));

		Mockito.verify(currencyRatesProvider).getCurrencyPairs();

	}
}
