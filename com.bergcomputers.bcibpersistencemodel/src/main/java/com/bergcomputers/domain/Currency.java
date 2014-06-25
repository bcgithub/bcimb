package com.bergcomputers.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CURRENCY")
@XmlRootElement
@NamedQuery(name=Currency.findAll,query="SELECT c from Currency c")
public class Currency extends BaseEntity implements Serializable, ICurrency{
	public final static String findAll = "com.bergcomputers.currency.findAll";

	@NotNull
	private String symbol;

	@NotNull
	private Double exchangerate;

	@Override
	public String toString() {
		return "Currency [symbol=" + symbol + ", exchangerate=" + exchangerate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((exchangerate == null) ? 0 : exchangerate.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (exchangerate == null) {
			if (other.exchangerate != null)
				return false;
		} else if (!exchangerate.equals(other.exchangerate))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}
}
