package com.shop.shopservice.Idao;

import java.util.List;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.entity.Product;

public interface ICategoryDAO {

	List<Category> getAllCategory();

	List<Category> getCategoryForUser();

	public List<Category> getCategoryForUserByShopId(String shopId);

	public List<Category> getCategoryByShopId(String shopId);

	public List<Category> getAllDeactiveCategoryByShopId(String shopId);

	public List<Category> getCategoryByShopIdAndId(String shopId, int id);

	boolean categoryExists(String name, String shopId);

	void addCategory(Category category);

	Category getCategoryById(int id);

	void updateCategory(Category category);

	boolean deleteCategory(int id);

	public List<Category> searchCategory(String keyword);

	public void indexCategory();

}
