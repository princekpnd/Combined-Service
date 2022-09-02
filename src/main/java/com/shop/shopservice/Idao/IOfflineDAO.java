package com.shop.shopservice.Idao;

import java.util.List;

import com.shop.shopservice.entity.Offline;

public interface IOfflineDAO {
	
	List<Offline> getAllOffline();
	
	Offline getByBillId(int billingId);
	
	Offline getAllBillByShopId(String shopId);
	
	List<Offline> getAllBillByUserName(String shopId, String userName);
	
	boolean offlineExists(String shopId, String productName);
	
	void addOffline(Offline offline);
	
	List<Offline> getByShopId(String shopId);
	
	List<Offline> getDeactiveByShopId(String shopId);
	
	Offline checkDeactive(int billingId);
	
	Offline getByShopIdAndMobileNo(String shopId,String mobileNo);
	
	void updateOffline(Offline offline);

	Offline getByShopIdAndBillId(String shopId,int billingId);
	
	boolean offlineCheckExists(String shopId, boolean isActive);
}
