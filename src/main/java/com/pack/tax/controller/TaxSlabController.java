package com.pack.tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pack.tax.dto.TaxCalculationRequest;
import com.pack.tax.dto.TaxCalculationResponse;
import com.pack.tax.service.TaxSlabService;

@RestController
@RequestMapping("/api/taxslab")
@CrossOrigin("*")
public class TaxSlabController {

	@Autowired
	private TaxSlabService taxSlabService;

	@PostMapping("/calculate")
	public TaxCalculationResponse calculateTax(@RequestBody TaxCalculationRequest request) {
		return taxSlabService.calculateTax(request);
	}
	
}
