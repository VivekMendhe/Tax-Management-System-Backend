package com.pack.tax.serviceimpl;

import org.springframework.stereotype.Service;

import com.pack.tax.dto.TaxCalculationRequest;
import com.pack.tax.dto.TaxCalculationResponse;
import com.pack.tax.service.TaxSlabService;

@Service
public class TaxSlabServiceImpl implements TaxSlabService {

	private static final double STANDARD_DEDUCTION = 50000;
    private static final double CESS_RATE = 0.04;
    
    @Override
	public TaxCalculationResponse calculateTax(TaxCalculationRequest request) {

		double grossAmount = request.getAmount();
		String regime = request.getRegime();
		double taxableIncome = grossAmount - STANDARD_DEDUCTION;
		double taxAmount = 0;

		if ("old".equalsIgnoreCase(regime)) {
			taxAmount = calculateOldTax(taxableIncome);
		} else if ("new".equalsIgnoreCase(regime)) {
			taxAmount = calculateNewTax(taxableIncome);
		}

		double cessAmount = taxAmount * CESS_RATE;
		double totalTaxAmount = taxAmount + cessAmount;

		if (totalTaxAmount <=50000) {
			totalTaxAmount = 0;
			cessAmount = 0;
		}

		TaxCalculationResponse response = new TaxCalculationResponse();
		response.setTaxAmount(taxAmount);
		response.setCessAmount(cessAmount);
		response.setTotalTaxAmount(totalTaxAmount);

		return response;
	}

    /*@Override
    public TaxCalculationResponse calculateTax(TaxCalculationRequest request) {
        double grossAmount = request.getAmount();
        String regime = request.getRegime();
        double taxAmount = 0;

        // Calculate initial tax amount without any deductions
        if ("old".equalsIgnoreCase(regime)) {
            taxAmount = calculateOldTax(grossAmount);
        } else if ("new".equalsIgnoreCase(regime)) {
            taxAmount = calculateNewTax(grossAmount);
        }

        double totalTaxAmount = taxAmount;

        // Check if total tax amount exceeds 50,000
        if (totalTaxAmount > 50000) {
            // Recalculate taxable income by reducing the standard deduction
            double taxableIncome = grossAmount - STANDARD_DEDUCTION;
            if ("old".equalsIgnoreCase(regime)) {
                taxAmount = calculateOldTax(taxableIncome);
            } else if ("new".equalsIgnoreCase(regime)) {
                taxAmount = calculateNewTax(taxableIncome);
            }
            double cessAmount = taxAmount * CESS_RATE;
            totalTaxAmount = taxAmount + cessAmount;
        } else {
            // Set tax and cess to 0 if total tax amount is less than or equal to 50,000
            totalTaxAmount = 0;
        }

        TaxCalculationResponse response = new TaxCalculationResponse();
        response.setTaxAmount(totalTaxAmount);
        response.setCessAmount(totalTaxAmount * CESS_RATE);
        response.setTotalTaxAmount(totalTaxAmount);

        return response;
    }*/

    private double calculateOldTax(double amount) {
        double tax = 0;
        if (amount <= 250000) {
            tax = 0;
        } else if (amount <= 500000) {
            tax = (amount - 250000) * 0.05;
        } else if (amount <= 1000000) {
            tax = (amount - 500000) * 0.2 + 250000 * 0.05;
        } else {
            tax = (amount - 1000000) * 0.3 + 500000 * 0.2 + 250000 * 0.05;
        }
        return tax;
    }

    private double calculateNewTax(double amount) {
        double tax = 0;
        if (amount <= 300000) {
            tax = 0;
        } else if (amount <= 600000) {
            tax = (amount - 300000) * 0.05;
        } else if (amount <= 900000) {
            tax = 300000 * 0.05 + (amount - 600000) * 0.1;
        } else if (amount <= 1200000) {
            tax = 300000 * 0.05 + 300000 * 0.1 + (amount - 900000) * 0.15;
        } else if (amount <= 1500000) {
            tax = 300000 * 0.05 + 300000 * 0.1 + 300000 * 0.15 + (amount - 1200000) * 0.2;
        } else {
            tax = 300000 * 0.05 + 300000 * 0.1 + 300000 * 0.15 + 300000 * 0.2 + (amount - 1500000) * 0.3;
        }
        return tax;
    }
}
