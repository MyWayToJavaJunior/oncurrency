package me.vgv.oncurrency.provider.cbr;

import me.vgv.oncurrency.Currency;
import me.vgv.oncurrency.CurrencyPair;
import me.vgv.oncurrency.provider.CurrencyRatesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class CBRCurrencyRatesProvider implements CurrencyRatesProvider {

	private static final Logger log = LoggerFactory.getLogger(CBRCurrencyRatesProvider.class);

	private static final String CBR_XML_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

	private static final JAXBContext JAXB_CONTEXT;

	static {
		try {
			JAXB_CONTEXT = JAXBContext.newInstance(CBRCurrencyList.class);
		} catch (JAXBException e) {
			throw new RuntimeException("Can't create JAXBContext", e);
		}
	}

	protected String getXml() {
		try (InputStream inputStream = new URL(CBR_XML_URL).openStream()) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int readBytes;
			byte[] buffer = new byte[4096];
			while ((readBytes = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, readBytes);
			}
			return byteArrayOutputStream.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CBRCurrencyList parseCurrencyList(String xml) {
		try {
			Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
			return (CBRCurrencyList) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Nonnull
	@Override
	public Set<CurrencyPair> getCurrencyPairs() {
		String xml = getXml();
		CBRCurrencyList currencyList = parseCurrencyList(xml);

		// CBR returns currency rates based on RUB
		me.vgv.oncurrency.Currency baseCurrency = Currency.RUB;

		Set<CurrencyPair> result = new HashSet<>();
		for (CBRCurrency cbrCurrency : currencyList.getCurrencyList()) {
			Currency currency = Currency.findByCode(cbrCurrency.getCharCode());
			if (currency == null) {
				log.debug("Currency with code '" + cbrCurrency.getCharCode() + "' not found in Currency enum");
				continue;
			}

			double rate = cbrCurrency.getValue();
			double nominal = cbrCurrency.getNominal();

			CurrencyPair currencyPair = new CurrencyPair(baseCurrency, nominal, currency, rate);
			result.add(currencyPair);
		}

		return result;
	}
}
