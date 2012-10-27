package me.vgv.oncurrency;

import me.vgv.oncurrency.provider.CurrencyRatesProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DefaultCurrencyManager implements CurrencyManager {

	// every day
	private static final long UPDATE_INTERVAL_IN_MILLIS = 1000 * 86400;

	private final CurrencyRatesProvider currencyRatesProvider;
	private final Currency baseCurrency;
	private final CurrencyRate defaultCurrencyRate;

	private final Map<Currency, CurrencyRate> currencyRates = new ConcurrentHashMap<>();
	private final AtomicLong lastAccess = new AtomicLong(0);
	private final Lock lock = new ReentrantLock();

	public DefaultCurrencyManager(@Nonnull CurrencyRatesProvider currencyRatesProvider, @Nonnull Currency baseCurrency) {
		this.currencyRatesProvider = currencyRatesProvider;
		this.baseCurrency = baseCurrency;
		this.defaultCurrencyRate = new CurrencyRate(baseCurrency, 1);

		updateRatesIfNeeded();
	}

	private void updateRatesIfNeeded() {
		if (lastAccess.get() + UPDATE_INTERVAL_IN_MILLIS < System.currentTimeMillis()) {
			if (lock.tryLock()) {
				try {
					Set<CurrencyRate> rates = Utils.recalculate(baseCurrency, currencyRatesProvider.getCurrencyPairs());
					for (CurrencyRate currencyRate : rates) {
						currencyRates.put(currencyRate.getCurrency(), currencyRate);
					}
					// update last access time
					lastAccess.set(System.currentTimeMillis());
				} finally {
					lock.unlock();
				}
			}
		}
	}

	@Override
	@Nullable
	public CurrencyRate getRate(@Nonnull Currency currency) {
		if (currency.equals(baseCurrency)) {
			return defaultCurrencyRate;
		}

		updateRatesIfNeeded();

		return currencyRates.get(currency);
	}

	@Nonnull
	@Override
	public Set<CurrencyRate> getRates() {
		updateRatesIfNeeded();

		return new HashSet<>(currencyRates.values());
	}
}
