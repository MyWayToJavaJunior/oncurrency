package me.vgv.oncurrency.provider.openexchange;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import me.vgv.oncurrency.Currency;
import me.vgv.oncurrency.CurrencyPair;
import me.vgv.oncurrency.provider.CurrencyRatesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class OpenExchangeRatesProvider implements CurrencyRatesProvider {

	private static final Logger log = LoggerFactory.getLogger(OpenExchangeRatesProvider.class);

	private static final String ENDPOINT = "http://openexchangerates.org/api/latest.json";

	private final String appId;

	public OpenExchangeRatesProvider(String appId) {
		Preconditions.checkArgument(appId != null && !appId.isEmpty(), "appId is null or empty");
		this.appId = appId;
	}

	private String getJson() throws IOException {
		try (InputStream inputStream = new URL(ENDPOINT + "?app_id=" + appId).openStream()) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int readBytes;
			byte[] buffer = new byte[4096];
			while ((readBytes = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, readBytes);
			}
			return byteArrayOutputStream.toString("UTF-8");
		}
	}

	@Nonnull
	@Override
	public Set<CurrencyPair> getCurrencyPairs() {
		Set<CurrencyPair> currencyPairs = new HashSet<>();

		try {
			Gson gson = new Gson();
			JsonResponse jsonResponse = gson.fromJson(getJson(), JsonResponse.class);

			Currency baseCurrency = Currency.findByCode(jsonResponse.getBase());
			if (baseCurrency == null) {
				log.debug("Base currency with code '" + jsonResponse.getBase() + "' not found in Currency enum => can't calculate other currencies");
				throw new RuntimeException();
			}

			for (Map.Entry<String, Double> entry : jsonResponse.getRates().entrySet()) {
				Currency otherCurrency = Currency.findByCode(entry.getKey());
				if (otherCurrency == null) {
					log.debug("Currency with code '" + entry.getKey() + "' not found in Currency enum");
					continue;
				}

				CurrencyPair currencyPair = new CurrencyPair(baseCurrency, 1.0, otherCurrency, entry.getValue());
				currencyPairs.add(currencyPair);
			}
		} catch (Exception e) {
			log.error("Can't fetch currency rates", e);
		}

		return currencyPairs;
	}
}
