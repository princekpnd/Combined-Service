package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.shopservice.Idao.IOfflineDAO;
import com.shop.shopservice.Idao.IOfflineProductListDAO;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;

@Transactional
@Repository
public class OfflineProductListServiceImpl implements IOfflineProductListService{
	@Autowired
	IOfflineProductListDAO  offlineProductListDao;

	@Override
	public List<OfflineProductList> getAll() {
		return offlineProductListDao.getAll();
	}

	@Override
	public OfflineProductList getById(int id) {
		return offlineProductListDao.getById(id);
	}

	@Override
	public boolean offlineProductExists( String productName, String shopId, String brandName) {
		return offlineProductListDao.offlineProductExists( productName, shopId, brandName);
	}
	
	@Override
	public boolean offlineProductListExists( String productName, int offlineCartId, String brandName) {
		return offlineProductListDao.offlineProductListExists( productName, offlineCartId, brandName);
	}

	@Override
	public boolean offlineProductCreate(OfflineProductList offlineProductList) {
		if(offlineProductListExists(offlineProductList.getProductName(), offlineProductList.getOfflineCartId(), offlineProductList.getBrandName())) {
			return false;
		}else {
			offlineProductListDao.addOfflineProductList(offlineProductList);
			return true;
		}
		
	}

	@Override
	public boolean updateOfflineProductList(OfflineProductList offlineProductList) {
	offlineProductListDao.updateOfflineProductList(offlineProductList);
	return true;
	}

	@Override
	public List<OfflineProductList> getByShopId(String shopId) {
		return offlineProductListDao.getByShopId(shopId);
	}

	@Override
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId) {
		return offlineProductListDao.getAllProductByCartId(offlineCartId);
	}

	@Override
	public boolean checkExit(int offlineCartId, String productName) {
		return offlineProductListDao.checkExit(offlineCartId, productName);
	}
	
	@Override
	public boolean deleteOfflineProductList(int id) {
	return offlineProductListDao.deleteOfflineProductList(id);
	}

}
