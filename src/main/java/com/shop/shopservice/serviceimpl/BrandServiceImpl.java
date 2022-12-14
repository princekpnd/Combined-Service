package com.shop.shopservice.serviceimpl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IBrandDAO;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.service.IBrandService;
@Repository
@Transactional
public class BrandServiceImpl implements IBrandService{
	
	@Autowired
	IBrandDAO brandDao;
	
	@Override
	public List<Brand> getAllBrand() {
		return brandDao.getAllBrand();
	}
	
	@Override
	public List<Brand> getBrandForUser() {
		return brandDao.getBrandForUser();
	}

	@Override
	public List<Brand> getBrandByShopId(String shopId) {
		return  brandDao.getBrandByShopId(shopId);
	}
	
	@Override
	public List<Brand> getBrandForUserByShopId(String shopId) {
		return  brandDao.getBrandForUserByShopId(shopId);
	}
	
	@Override
	public boolean brandExists(String name, String shopId, int category) {
		return brandDao.brandExists(name, shopId, category);
	}
	
	public boolean createBrand(Brand brand) {

		if (brandExists(brand.getName(), brand.getShopId(), brand.getCategory())) {
			return false;
		} else {

			brandDao.addBrand(brand);
			brand = null;
			return true;
		}
	}
	
	@Override
	public Brand getBrand(int category) {
		return brandDao.getBrandByCategory(category);
	}
	
	@Override
	public boolean updateBrand(Brand brand) {
		brandDao.updateBrand(brand);
		return true;
	}

	@Override
	public List<Brand> getAllDeactiveBrandByShopId(int shopId) {
		return brandDao.getAllDeactiveBrandByShopId(shopId);
	}

	@Override
	public List<Brand> getBrandByShopIdAndId(String shopId, int id) {
		return brandDao.getBrandByShopIdAndId(shopId,id) ;
	}

	@Override
	public Brand getBrandById(int id) {
		return brandDao.getBrandById(id);
	}

	@Override
	public boolean deleteBrand(int id) {
		return brandDao.deleteBrandById(id);
	}

	@Override
	public List<Brand> getAllBrandByCategory(int category) {
		return brandDao.getAllBrandByCategory(category);
	}

	

	@Override
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive) {
		return brandDao.getAllActiveBrandByCategory(category,isActive);
	}

}
