package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Brand;

public interface IBrandDAO {

	List<Brand> getAllBrand();

	List<Brand> getBrandForUser();

	List<Brand> getBrandByShopId(String shopId);

	List<Brand> getBrandForUserByShopId(String shopId);

	public List<Brand> getAllDeactiveBrandByShopId(int shopId);

	public List<Brand> getBrandByShopIdAndId(String shopId, int id);

	public List<Brand> getAllBrandByCategory(int category);
	
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive);

	boolean brandExists(String name, String shopId, int category);

	void addBrand(Brand brand);

	Brand getBrandByCategory(int category);

	void updateBrand(Brand brand);

	Brand getBrandById(int id);

	boolean deleteBrandById(int id);

	public void indexBrand();

	public List<Brand> searchBrand(String keyword);

}
