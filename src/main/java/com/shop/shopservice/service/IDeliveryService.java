package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Delivery;

public interface IDeliveryService {

	List<Delivery> getAllDelivery();
	
	public Delivery getById(int id);
	
	public Delivery getTotal(String shopId,String mobileNo);
	
	public Delivery getByShopIdAndMobileNumber(String shopId, String mobileNo);
	
    public boolean deliveryExits(String shopId, String mobileNo);
    
    public boolean deliveryCreate(Delivery delivery);
    
    public boolean updateDelivery(Delivery delivery);
}
