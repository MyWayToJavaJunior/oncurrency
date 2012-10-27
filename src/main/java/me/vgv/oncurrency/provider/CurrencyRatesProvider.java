package me.vgv.oncurrency.provider;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface CurrencyRatesProvider {

	@Nonnull
	public List<CurrencyPair> getCurrencyPairs();

}
