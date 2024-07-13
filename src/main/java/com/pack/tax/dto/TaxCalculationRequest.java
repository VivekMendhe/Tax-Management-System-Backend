package com.pack.tax.dto;

public class TaxCalculationRequest {

	private double amount;
	private String regime;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

}
