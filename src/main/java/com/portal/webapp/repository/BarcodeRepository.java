package com.portal.webapp.repository;

import com.portal.webapp.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeRepository extends JpaRepository<Barcode, String> {
	
	Barcode findByBarcode(String barcode);

}
