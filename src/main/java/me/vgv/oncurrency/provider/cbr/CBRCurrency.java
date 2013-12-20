package me.vgv.oncurrency.provider.cbr;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class CBRCurrency {

	//private String id;
	//private String numCode;
	private String charCode;
	private Double nominal;
	//private String name;
	private Double value;

	/*
	@XmlAttribute(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "NumCode")
	public String getNumCode() {
		return numCode;
	}

	public void setNumCode(String numCode) {
		this.numCode = numCode;
	} */

	@XmlElement(name = "CharCode")
	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	@XmlElement(name = "Nominal")
	public Double getNominal() {
		return nominal;
	}

	public void setNominal(Double nominal) {
		this.nominal = nominal;
	}

	/*
	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	*/

	@XmlElement(name = "Value")
	@XmlJavaTypeAdapter(DoubleAdapter.class)
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}

