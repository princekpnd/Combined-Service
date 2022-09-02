package com.shop.shopservice.service;

import java.util.List;
import com.shop.shopservice.entity.Category;

public interface ICategoryService {

	List<Category> getAllCategory();

	List<Category> getCategoryForUser();

	public List<Category> getCategoryForUserByShopId(String shopId);

	public List<Category> getAllDeactiveCategoryByShopId(String shopId);

	public List<Category> getCategoryByShopIdAndId(String shopId, int id);

	public List<Category> getCategoryByShopId(String shopId);

	public boolean categoryExists(String name, String shopId);

	public boolean createCategory(Category category);

	public Category getCategory(int id);

	public boolean updateCategory(Category category);

	public boolean deleteCategory(int id);

}
