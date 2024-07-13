package com.pack.tax.service;

import com.pack.tax.dto.TaxCalculationRequest;
import com.pack.tax.dto.TaxCalculationResponse;

public interface TaxSlabService {

	TaxCalculationResponse calculateTax(TaxCalculationRequest request);
}
