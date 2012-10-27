package me.vgv.oncurrency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface CurrencyManager {

	@Nullable
	public CurrencyRate getRate(@Nonnull Currency currency);

	@Nonnull
	public List<CurrencyRate> getRates();

}
