package com.shop.shopservice.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.ICategoryDAO;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.service.ICategoryService;
@Repository
@Transactional
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	ICategoryDAO categoryDao;
	
	@Override
	public List<Category> getAllCategory() {
		return categoryDao.getAllCategory();
		
	}
	@Override
	public List<Category> getCategoryForUser(){
		return categoryDao.getCategoryForUser();
		
	}

	@Override
	public List<Category> getCategoryForUserByShopId(String shopId) {
		return categoryDao.getCategoryForUserByShopId(shopId);
	}

	@Override
	public boolean categoryExists(String name, String shopId) {
		return categoryDao.categoryExists(name, shopId);
	}
	
	public boolean createCategory(Category category) {

		if (categoryExists(category. getName(), category.getShopId())) {
			return false;
		} else {

			categoryDao.addCategory(category);
			category = null;
			return true;
		}
	}

	@Override
	public Category getCategory(int id) {
		return categoryDao.getCategoryById(id) ;
	}

	@Override
	public boolean updateCategory(Category category) {
		categoryDao.updateCategory(category);
		return true;
		
	}
	@Override
	public List<Category> getCategoryByShopId(String shopId) {
	return	categoryDao.getCategoryByShopId(shopId);
	
	}
	@Override
	public List<Category> getAllDeactiveCategoryByShopId(String shopId) {	
	return categoryDao.getAllDeactiveCategoryByShopId(shopId) ;
	}
	@Override
	public List<Category> getCategoryByShopIdAndId(String shopId, int id) {
		return  categoryDao.getCategoryByShopIdAndId(shopId,id);
	}
	@Override
	public boolean deleteCategory(int id) {
		return categoryDao.deleteCategory(id);
	}


}
