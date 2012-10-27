package me.vgv.oncurrency;

import me.vgv.oncurrency.provider.CurrencyRatesProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DefaultCurrencyManager implements CurrencyManager {

	private final CurrencyRatesProvider currencyRatesProvider;
	private final Currency baseCurrency;
	private final CurrencyRate defaultCurrencyRate;

	public DefaultCurrencyManager(@Nonnull CurrencyRatesProvider currencyRatesProvider, @Nonnull Currency baseCurrency) {
		this.currencyRatesProvider = currencyRatesProvider;
		this.baseCurrency = baseCurrency;
		this.defaultCurrencyRate = new CurrencyRate(baseCurrency, 1);
	}

	@Override
	@Nullable
	public CurrencyRate getRate(@Nonnull Currency currency) {
		if (currency.equals(baseCurrency)) {
			return defaultCurrencyRate;
		}

		return null;

	}

	@Nonnull
	@Override
	public List<CurrencyRate> getRates() {
		return null;
	}
}
