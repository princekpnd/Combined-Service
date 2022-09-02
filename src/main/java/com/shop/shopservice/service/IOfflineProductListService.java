package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.OfflineProductList;

public interface IOfflineProductListService {
	List<OfflineProductList> getAll();
	
	public OfflineProductList getById(int id);
	
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId);
	
	public boolean checkExit(int offlineCartId,String productName);
	
	public List<OfflineProductList> getByShopId(String shopId);
	
	public boolean offlineProductExists( String productName, String shopId, String brandName);
	
	public boolean offlineProductListExists( String productName, int offlineCartId, String brandName);
	
	public boolean offlineProductCreate(OfflineProductList offlineProductList);
	
	public boolean updateOfflineProductList(OfflineProductList offlineProductList);
	
	public boolean deleteOfflineProductList(int id);
	
	

}
