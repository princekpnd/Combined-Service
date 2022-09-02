package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Offline;

public interface IOfflineService {

	List<Offline> getAllOffline();

	public Offline getByBillId(int billingId);

	public Offline getAllBillByShopId(String shopId);
	
	public List<Offline> getDeactiveByShopId(String shopId);
	
	public List<Offline> getByShopId(String shopId);
	
	public Offline checkDeactive(int billingId);

	public Offline getByShopIdAndBillId(String shopId, int billingId);

	public List<Offline> getAllBillByUserName(String shopId, String userName);
	
	public Offline getByShopIdAndMobileNo(String shopId,String mobileNo);

	public boolean offlineExists(String shopId, String productName);

	public boolean offlineCreate(Offline offline);

	public boolean updateOffline(Offline offline);

	public boolean offlineCheckExists(String shopId, boolean isActive);

	public boolean offlineCreate2(Offline offline);

}
