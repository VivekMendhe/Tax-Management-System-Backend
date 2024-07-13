package com.pack.tax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tax_slabs")
public class TaxSlab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String regime;
	@Column(name = "income_from")
	private double incomeFrom;
	
	@Column(name = "income_to")
	private double incomeTo;
	
	@Column(name = "tax_rate")
	private double taxRate;

	public TaxSlab() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaxSlab(Long id, String regime, double incomeFrom, double incomeTo, double taxRate) {
		super();
		this.id = id;
		this.regime = regime;
		this.incomeFrom = incomeFrom;
		this.incomeTo = incomeTo;
		this.taxRate = taxRate;
	}

	public TaxSlab(String regime, double incomeFrom, double incomeTo, double taxRate) {
		super();
		this.regime = regime;
		this.incomeFrom = incomeFrom;
		this.incomeTo = incomeTo;
		this.taxRate = taxRate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public double getIncomeFrom() {
		return incomeFrom;
	}

	public void setIncomeFrom(double incomeFrom) {
		this.incomeFrom = incomeFrom;
	}

	public double getIncomeTo() {
		return incomeTo;
	}

	public void setIncomeTo(double incomeTo) {
		this.incomeTo = incomeTo;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

}
