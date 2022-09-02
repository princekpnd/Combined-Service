package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.shop.shopservice.Idao.IBrandDAO;
import com.shop.shopservice.entity.Brand;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.service.IProductService;

@Repository
@Transactional

public class BrandDAOImpl implements IBrandDAO {

	@Autowired
	private IProductService productService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Brand> getAllBrand() {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findAll", Brand.class).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandForUser() {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findAllForUser", Brand.class).getResultList();
		return brandList;
	}

//	@Override
//	public List<Brand> getBrandByShopId(String shopId) {
//		List<Brand> brandList= entityManager.createNamedQuery("Brand.findByShopId",Brand.class).setParameter("shopId", shopId).getResultList();
//		return  brandList;
//	}

	@Override
	public List<Brand> getBrandByShopId(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findByShopId", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getAllDeactiveBrandByShopId(int shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findDeactiveBrand", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandForUserByShopId(String shopId) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findForUserByShopId", Brand.class)
				.setParameter("shopId", shopId).getResultList();
		return brandList;
	}

	@Override
	public List<Brand> getBrandByShopIdAndId(String shopId, int id) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findBrandByShopIdAndId", Brand.class)
				.setParameter("shopId", shopId).setParameter("id", id).getResultList();
		return brandList;
	}

	@Override
	public boolean brandExists(String name, String shopId, int category) {
		Brand brand = entityManager.createNamedQuery("Brand.findByNameShopId", Brand.class).setParameter("name", name)
				.setParameter("shopId", shopId).setParameter("category", category).getResultList().stream().findFirst()
				.orElse(null);
		;
		return null != brand ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addBrand(Brand brand) {
		entityManager.persist(brand);

	}

	@Override
	public Brand getBrandByCategory(int category) {
		return this.entityManager.find(Brand.class, category);
	}

	@Override
	public void updateBrand(Brand brand) {
		entityManager.merge(brand);

	}

	@Override
	public Brand getBrandById(int id) {
		return this.entityManager.find(Brand.class, id);

	}

	@Override
	public boolean deleteBrandById(int id) {

		List<Product> productList = productService.getProductByBrand(id);
		if (productList.size() > 0) {
			for (int i = 0; i < productList.size(); i++) {
				int productId = productList.get(i).getProductId();
				productService.deleteProduct(productId);
			}
			Query query = entityManager.createQuery("delete Brand where id = " + id);
			query.executeUpdate();
			entityManager.flush();
		} else {
			Query query = entityManager.createQuery("delete Brand where id = " + id);
			query.executeUpdate();
			entityManager.flush();
		}

		return true;
	}

	@Override
	public List<Brand> getAllBrandByCategory(int category) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findByCategory", Brand.class)
				.setParameter("category", category).getResultList();
		return brandList;
	}

	public void indexBrand() {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("An error occurred trying to build the serach index: " + e.toString());
		}
	}

	@Override
	public List<Brand> searchBrand(String keyword) {
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Brand.class)
				.get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "name").matching(keyword)
				.createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Brand.class);

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Brand> results = (List<Brand>) jpaQuery.getResultList();

		return results;
	}

	@Override
	public List<Brand> getAllActiveBrandByCategory(int category, boolean isActive) {
		List<Brand> brandList = entityManager.createNamedQuery("Brand.findActiveCategory", Brand.class)
				.setParameter("category", category).setParameter("isActive", isActive).getResultList();
		return brandList;
	}

}
