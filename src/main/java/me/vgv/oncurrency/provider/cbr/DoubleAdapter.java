package me.vgv.oncurrency.provider.cbr;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class DoubleAdapter extends XmlAdapter<String, Double> {

	private final ThreadLocal<NumberFormat> NUMBER_FORMATTER = new ThreadLocal<NumberFormat>() {
		@Override
		protected NumberFormat initialValue() {
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
			decimalFormatSymbols.setDecimalSeparator(',');
			return new DecimalFormat("", decimalFormatSymbols);
		}
	};

	@Override
	public Double unmarshal(String value) throws Exception {
		NumberFormat numberFormat = NUMBER_FORMATTER.get();
		return numberFormat.parse(value).doubleValue();
	}

	@Override
	public String marshal(Double value) throws Exception {
		NumberFormat numberFormat = NUMBER_FORMATTER.get();
		return numberFormat.format(value);
	}
}
