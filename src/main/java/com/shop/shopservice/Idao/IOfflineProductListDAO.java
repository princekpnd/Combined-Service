package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.OfflineProductList;

public interface IOfflineProductListDAO {
	
	List<OfflineProductList> getAll();
	
	List<OfflineProductList> getAllProductByCartId(int offlineCartId);
	
	boolean checkExit(int offlineCartId,String productName);
	
	OfflineProductList getById(int id);
	
	boolean offlineProductExists(String productName, String shopId, String brandName);
	
	boolean offlineProductListExists(String productName, int offlineCartId, String brandName);
	
	void addOfflineProductList(OfflineProductList offlineProductList);
	
	void updateOfflineProductList(OfflineProductList offlineProductList);
	
	List<OfflineProductList> getByShopId(String shopId);
	
	boolean deleteOfflineProductList(int id);

}
