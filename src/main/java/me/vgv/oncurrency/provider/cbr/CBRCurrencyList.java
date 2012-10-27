package me.vgv.oncurrency.provider.cbr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@XmlRootElement(name = "ValCurs")
final class CBRCurrencyList {

	private String date;

	private String name;

	private List<CBRCurrency> currencyList;

	@XmlAttribute(name = "Date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "Valute")
	public List<CBRCurrency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CBRCurrency> currencyList) {
		this.currencyList = currencyList;
	}
}
