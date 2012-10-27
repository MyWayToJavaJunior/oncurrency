package me.vgv.oncurrency;

import java.util.*;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class Utils {

	public static Set<CurrencyRate> recalculate(Currency baseCurrency, Set<CurrencyPair> currencyPairs) {
		// create copy
		currencyPairs = new HashSet<>(currencyPairs);

		Map<Currency, CurrencyRate> result = new HashMap<>();

		// direct conversions first
		for (Iterator<CurrencyPair> currencyIterator = currencyPairs.iterator(); currencyIterator.hasNext(); ) {
			CurrencyPair currencyPair = currencyIterator.next();
			Currency firstCurrency = currencyPair.getFirstCurrency();
			Currency secondCurrency = currencyPair.getSecondCurrency();

			if (baseCurrency.equals(firstCurrency)) {
				result.put(secondCurrency, new CurrencyRate(secondCurrency, currencyPair.getSecondRate() / currencyPair.getFirstRate()));
				currencyIterator.remove();
			} else if (baseCurrency.equals(secondCurrency)) {
				result.put(firstCurrency, new CurrencyRate(firstCurrency, currencyPair.getFirstRate() / currencyPair.getSecondRate()));
				currencyIterator.remove();
			}
		}

		// indirect conversions
		int lastSize;
		do {
			lastSize = currencyPairs.size();

			for (Iterator<CurrencyPair> currencyIterator = currencyPairs.iterator(); currencyIterator.hasNext(); ) {
				CurrencyPair currencyPair = currencyIterator.next();
				Currency firstCurrency = currencyPair.getFirstCurrency();
				Currency secondCurrency = currencyPair.getSecondCurrency();

				if (result.containsKey(firstCurrency) && !result.containsKey(secondCurrency)) {
					result.put(secondCurrency, new CurrencyRate(secondCurrency, result.get(firstCurrency).getRate() / (currencyPair.getFirstRate() / currencyPair.getSecondRate())));
					currencyIterator.remove();
				} else if (result.containsKey(secondCurrency) && !result.containsKey(firstCurrency)) {
					result.put(firstCurrency, new CurrencyRate(firstCurrency, result.get(secondCurrency).getRate() / (currencyPair.getSecondRate() / currencyPair.getFirstRate())));
					currencyIterator.remove();
				}
			}
		} while (currencyPairs.size() < lastSize);

		return new HashSet<>(result.values());
	}

}
