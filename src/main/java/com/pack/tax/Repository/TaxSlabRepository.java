package com.pack.tax.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pack.tax.entity.TaxSlab;

public interface TaxSlabRepository extends JpaRepository<TaxSlab, Long>{

	List<TaxSlab> findByRegimeOrderByIncomeFrom(String regime);
}
