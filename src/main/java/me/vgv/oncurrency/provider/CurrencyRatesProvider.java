package me.vgv.oncurrency.provider;

import me.vgv.oncurrency.CurrencyPair;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface CurrencyRatesProvider {

	@Nonnull
	public Set<CurrencyPair> getCurrencyPairs();

}
