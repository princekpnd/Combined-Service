package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Measurement;

public interface IMeasurementService {
	List<Measurement> getAll();
	
	public Measurement getById(int id);
	
	public List<Measurement> getMeasurementByShopId(String shopId);
	
	public boolean updateMeasurement(Measurement measurement);
	
	public boolean measurementExists(String name, String shopId);
	
	public boolean createMeasurement(Measurement measurement);

}
