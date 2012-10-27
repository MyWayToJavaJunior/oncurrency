package me.vgv.oncurrency.provider.cbr;

import me.vgv.oncurrency.CurrencyPair;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class CBRCurrencyRatesProviderTest {

	@Test(groups = "integration")
	public void testGetCurrencyPairs() throws Exception {
		CBRCurrencyRatesProvider provider = new CBRCurrencyRatesProvider();
		Set<CurrencyPair> currencyPairList = provider.getCurrencyPairs();
		Assert.assertTrue(currencyPairList.size() > 0);
	}

}
