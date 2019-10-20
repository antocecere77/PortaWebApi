package com.portal.webapp.service;

import com.portal.webapp.entity.Barcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.webapp.repository.BarcodeRepository;

@Service
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService {

	@Autowired
	BarcodeRepository barcodeRepository;
	
	@Override
	public Barcode getByBarcode(String barcode) {
		return barcodeRepository.findByBarcode(barcode);		
	}

}
