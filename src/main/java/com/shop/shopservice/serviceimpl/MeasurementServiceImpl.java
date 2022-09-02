package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IMeasurementDAO;
import com.shop.shopservice.entity.Measurement;
import com.shop.shopservice.service.IMeasurementService;

@Transactional
@Repository
public class MeasurementServiceImpl implements IMeasurementService{
	@Autowired
	IMeasurementDAO  measurementDao;

	@Override
	public List<Measurement> getAll() {
		return measurementDao.getAll();
	}

	@Override
	public List<Measurement> getMeasurementByShopId(String shopId) {
		return measurementDao.getMeasurementByShopId(shopId);
	}

	@Override
	public Measurement getById(int id) {
		return measurementDao.getById(id);
	}

	@Override
	public boolean updateMeasurement(Measurement measurement) {
		measurementDao.updateMeasurement(measurement);
		return true;
	}

	@Override
	public boolean measurementExists(String name, String shopId) {
		return measurementDao.measurementExists(name, shopId);
		 
	}

	@Override
	public boolean createMeasurement(Measurement measurement) {
		if(measurementExists(measurement.getName(), measurement.getShopId())) {
			return false;
		}else {
			measurementDao.addMeasurement(measurement);			
			return true;
		}
		
	}

}
