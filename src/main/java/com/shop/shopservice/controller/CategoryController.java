package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ICategoryDAO;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Admin;
import com.shop.shopservice.entity.Category;
import com.shop.shopservice.service.IAdminService;
import com.shop.shopservice.service.ICategoryService;
@RestController
@RequestMapping("/api/category")


public class CategoryController {
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	ICategoryDAO categoryDao;
	
	
	@Autowired
	private IAdminService adminservice ;
	
	@GetMapping("getallcategory")
	public ResponseEntity<List<Category>> getAllcategory() {
		List<Category> categoryList = categoryService.getAllCategory();
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@GetMapping("getdeactivecategorybyshopid/{shopId}")
	public ResponseEntity<List<Category>> getAllDeactiveCategoryByShopId(@PathVariable ("shopId")String shopId){
		List<Category> categoryList = categoryService.getAllDeactiveCategoryByShopId(shopId);
		return new ResponseEntity<List<Category>>(categoryList,HttpStatus.OK);
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<Category> getById(@PathVariable("id") int id){
		Category category = categoryService.getCategory(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	@GetMapping("getcategorybyshopidandcategoryid/{shopId}/{id}")
	public ResponseEntity<List<Category>> getCategoryByShopIdAndId(@PathVariable("shopId") String shopId,@PathVariable("id")int id){
		List<Category> categoryList = categoryService.getCategoryByShopIdAndId(shopId,id);
		return new ResponseEntity<List<Category>>(categoryList,HttpStatus.OK);
		
	}
	
	@GetMapping("getcategoryforuserbyshopid/{shopId}")
	public ResponseEntity<List<Category>>  getAllCategoryByShopId(@PathVariable ("shopId") String shopId){
		Admin admin = adminservice.getAdminByShopId( shopId);
		List<Category> categoryList =null;
		if(admin != null &&  admin.isActive() ==Boolean. TRUE  && admin.isDeleted()==Boolean.FALSE) {
			categoryList = categoryService.getCategoryForUserByShopId(shopId);
		}
		return new ResponseEntity<List<Category>>(categoryList,HttpStatus.OK);
	}
	
	@GetMapping("getcategoryforuser")
	public ResponseEntity<List<Category>> getCategoryForUser(){
		List<Category> categoryList =categoryService.getCategoryForUser();
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@GetMapping("getcategorybyshopid/{shopId}")
	public ResponseEntity<List<Category>> getCategoryByShopId(@PathVariable ("shopId") String shopId){
		Admin admin = adminservice.getAdminByShopId(shopId);
		List<Category>categoryList =null;
		if(admin != null && admin.isActive() ==Boolean.TRUE && admin.isDeleted()==Boolean.FALSE ) {
		 categoryList =categoryService.getCategoryByShopId(shopId);
		}
		return new ResponseEntity<List<Category>>(categoryList,HttpStatus.OK);
	}
	
	@GetMapping("search/index")
	public ResponseEntity<String> indexAll() {
		categoryDao.indexCategory();
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("search/{keyword}")
	public ResponseEntity<List<Category>> searchAllCategoryBykeyword(@PathVariable("keyword") String keyword) {
		List<Category> result = new ArrayList<Category>();
		result = categoryDao.searchCategory(keyword);
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}
	
	@SuppressWarnings({ })
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createCasul(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		Category category = new Category(json.get(ServiceConstants.SHOP_ID),(json.get(ServiceConstants.NAME)));
		
		category.setName( json.get(ServiceConstants.NAME));
		category.setTitle(json.get(ServiceConstants.TITLE));
		category.setShopId( json.get(ServiceConstants.SHOP_ID));
		category.setActive(Boolean.TRUE);
		category.setDeleted(Boolean.FALSE);
		
		if(null != json.get(ServiceConstants.AVATAR)) {
			category.setAvatar(json.get(ServiceConstants.AVATAR));
		}
		
		
		response.put("NAME", json.get(ServiceConstants.NAME));
		if (categoryService.categoryExists(category.getName(), category.getShopId())) {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Category already exist with given shopId");
		} else {
			boolean result = categoryService.createCategory(category);
			response.put("status", Strings.EMPTY + result);
			response.put("description",
					"Category created successfully with given shopId, go through your inbox to activate");
		}
		return ResponseEntity.ok().body(response);
}
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateCategory(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.NAME));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.ID) && null != categoryService.getCategory(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Category category = categoryService.getCategory(Integer.parseInt(json.get(ServiceConstants.ID)));
			
			if(null != json.get(ServiceConstants.ID)) {
				int id = Integer.parseInt(json.get(ServiceConstants.ID));
				System.out.println(id);
				category.setId(id);
			}
			
			if(null != json.get(ServiceConstants.NAME)) {
				String name = json.get(ServiceConstants.NAME);
				System.out.println(name);
				category.setName(name);
			}
			if(null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId =json.get(ServiceConstants.SHOP_ID);
				System.out.println(shopId);
				category.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.TITLE)) {
				String title = json.get(ServiceConstants.TITLE);
				category.setTitle(title);
			}
			if(null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR);
				System.out.println(avatar);
				category.setAvatar(avatar);
			}
			
			if(null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = (Boolean.parseBoolean(json.get(ServiceConstants.IS_ACTIVE)));
				System.out.println(isActive);
				category. setActive(isActive);
			}
			
			if(null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = (Boolean.parseBoolean(json.get(ServiceConstants.IS_DELETED)));
				System.out.println(isDeleted);
				category. setDeleted(isDeleted);
						}
			
			
			categoryService.updateCategory(category);
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Category updated");
				}
		else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description",
					"Category doesn't exist with given Id ");
		}
	
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Map<String, String>> deleteCategory(@PathVariable("id") int id) {
		Map<String, String> response = new HashMap<String, String>();
		Category category = categoryService.getCategory(id);
		
		if(null != category) {
			boolean result = categoryService.deleteCategory(id);
			if(result) {
				response.put("status", Boolean.TRUE.toString());
				response.put("description","Category delete with given id:" +id);
			}
			}else {
				response.put("status",Boolean.FALSE.toString());
				response.put("description", "Category does not exist with given category Id");
			
		}
		return ResponseEntity.ok().body(response);
	}

}
		
			

