package com.pack.tax;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.pack.tax.Repository.TaxSlabRepository;
import com.pack.tax.dto.TaxCalculationRequest;
import com.pack.tax.dto.TaxCalculationResponse;
import com.pack.tax.entity.TaxSlab;
import com.pack.tax.serviceimpl.TaxSlabServiceImpl;

@SpringBootTest
class TaxManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@InjectMocks
	private TaxSlabServiceImpl taxSlabService;

	@Mock
	private TaxSlabRepository taxSlabRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCalculateTaxOldRegimeTest() {
		// Given
		TaxCalculationRequest request = new TaxCalculationRequest();
		request.setAmount(1500000);
		request.setRegime("old");

		TaxSlab slab1 = new TaxSlab(1L, "old", 0, 250000, 0);
		TaxSlab slab2 = new TaxSlab(2L, "old", 250001, 500000, 5);
		TaxSlab slab3 = new TaxSlab(3L, "old", 500001, 1000000, 20);
		TaxSlab slab4 = new TaxSlab(4L, "old", 1000001, Double.MAX_VALUE, 30);
		List<TaxSlab> oldSlabs = Arrays.asList(slab1, slab2, slab3, slab4);

		when(taxSlabRepository.findByRegimeOrderByIncomeFrom("old")).thenReturn(oldSlabs);

		// When
		TaxCalculationResponse response = taxSlabService.calculateTax(request);

		// Then
		double expectedTaxAmount = 247500.0;
		double expectedCessAmount = 9900.0;
		double expectedTotalTaxAmount = 257400.0;

		assertEquals(expectedTaxAmount, response.getTaxAmount(), 0.01);
		assertEquals(expectedCessAmount, response.getCessAmount(), 0.01);
		assertEquals(expectedTotalTaxAmount, response.getTotalTaxAmount(), 0.01);
	}

	@Test
	public void testCalculateTaxNewRegimeTest() {
		// Given
		TaxCalculationRequest request = new TaxCalculationRequest();
		request.setAmount(1500000);
		request.setRegime("new");

		TaxSlab slab1 = new TaxSlab(1L, "new", 0, 300000, 0);
		TaxSlab slab2 = new TaxSlab(2L, "new", 300001, 600000, 5);
		TaxSlab slab3 = new TaxSlab(3L, "new", 600001, 900000, 10);
		TaxSlab slab4 = new TaxSlab(4L, "new", 900001, 1200000, 15);
		TaxSlab slab5 = new TaxSlab(5L, "new", 1200001, 1500000, 20);
		TaxSlab slab6 = new TaxSlab(6L, "new", 1500001, Double.MAX_VALUE, 30);
		List<TaxSlab> newSlabs = Arrays.asList(slab1, slab2, slab3, slab4, slab5, slab6);

		when(taxSlabRepository.findByRegimeOrderByIncomeFrom("new")).thenReturn(newSlabs);

		// When
		TaxCalculationResponse response = taxSlabService.calculateTax(request);

		// Then
		double expectedTaxAmount = 140000.0;
		double expectedCessAmount = 5600.0;
		double expectedTotalTaxAmount = 145600.0;

		assertEquals(expectedTaxAmount, response.getTaxAmount(), 0.01);
		assertEquals(expectedCessAmount, response.getCessAmount(), 0.01);
		assertEquals(expectedTotalTaxAmount, response.getTotalTaxAmount(), 0.01);
	}

	@ParameterizedTest
	@CsvSource({ 
		"1000000, old, 102500.0, 4100.0, 106600.0", 
		"1500000, old, 247500.0, 9900.0, 257400.0" 
	})
	public void testCalculateTaxOldRegimesTest(double grossAmount, String regime, double expectedTaxAmount,
			double expectedCessAmount, double expectedTotalTaxAmount) {
		// Given
		TaxCalculationRequest request = new TaxCalculationRequest();
		request.setAmount(grossAmount);
		request.setRegime(regime);

		TaxSlab slab1 = new TaxSlab(1L, "new", 0, 300000, 0);
		TaxSlab slab2 = new TaxSlab(2L, "new", 300001, 600000, 5);
		TaxSlab slab3 = new TaxSlab(3L, "new", 600001, 900000, 10);
		TaxSlab slab4 = new TaxSlab(4L, "new", 900001, 1200000, 15);
		TaxSlab slab5 = new TaxSlab(5L, "new", 1200001, 1500000, 20);
		TaxSlab slab6 = new TaxSlab(6L, "new", 1500001, Double.MAX_VALUE, 30);
		List<TaxSlab> newSlabs = Arrays.asList(slab1, slab2, slab3, slab4, slab5, slab6);

		when(taxSlabRepository.findByRegimeOrderByIncomeFrom("new")).thenReturn(newSlabs);

		// When
		TaxCalculationResponse response = taxSlabService.calculateTax(request);

		assertEquals(expectedTaxAmount, response.getTaxAmount(), 0.01);
		assertEquals(expectedCessAmount, response.getCessAmount(), 0.01);
		assertEquals(expectedTotalTaxAmount, response.getTotalTaxAmount(), 0.01);
	}

	@ParameterizedTest
	@CsvSource({ 
		"1000000, new, 52500.0, 2100.0, 54600.0", 
		"1500000, new, 140000.0, 5600.0, 145600.0" 
	})
	public void testCalculateTaxNewRegimesTest(double grossAmount, String regime, double expectedTaxAmount,
			double expectedCessAmount, double expectedTotalTaxAmount) {
		// Given
		TaxCalculationRequest request = new TaxCalculationRequest();
		request.setAmount(grossAmount);
		request.setRegime(regime);

		TaxSlab slab1 = new TaxSlab(1L, "new", 0, 300000, 0);
		TaxSlab slab2 = new TaxSlab(2L, "new", 300001, 600000, 5);
		TaxSlab slab3 = new TaxSlab(3L, "new", 600001, 900000, 10);
		TaxSlab slab4 = new TaxSlab(4L, "new", 900001, 1200000, 15);
		TaxSlab slab5 = new TaxSlab(5L, "new", 1200001, 1500000, 20);
		TaxSlab slab6 = new TaxSlab(6L, "new", 1500001, Double.MAX_VALUE, 30);
		List<TaxSlab> newSlabs = Arrays.asList(slab1, slab2, slab3, slab4, slab5, slab6);

		when(taxSlabRepository.findByRegimeOrderByIncomeFrom(ArgumentMatchers.anyString())).thenReturn(newSlabs);

		// When
		TaxCalculationResponse response = taxSlabService.calculateTax(request);

		assertEquals(expectedTaxAmount, response.getTaxAmount(), 0.01);
		assertEquals(expectedCessAmount, response.getCessAmount(), 0.01);
		assertEquals(expectedTotalTaxAmount, response.getTotalTaxAmount(), 0.01);
	}

}
