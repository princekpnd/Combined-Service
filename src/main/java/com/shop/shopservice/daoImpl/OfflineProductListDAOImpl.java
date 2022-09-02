package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IOfflineProductListDAO;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.OfflineProductList;

@Repository
@Transactional
public class OfflineProductListDAOImpl implements IOfflineProductListDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OfflineProductList> getAll() {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.getAll", OfflineProductList.class).getResultList();
		return offlineProductList;
	}

	@Override
	public OfflineProductList getById(int id) {
		return this.entityManager.find(OfflineProductList.class, id);
	}

	@Override
	public boolean offlineProductExists(String productName, String shopId, String brandName) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.existProductList", OfflineProductList.class)
				.setParameter("productName", productName).setParameter("shopId", shopId)
				.setParameter("brandName", brandName).getResultList().stream().findFirst().orElse(null);

		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public boolean offlineProductListExists(String productName, int offlineCartId, String brandName) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.existProduct", OfflineProductList.class)
				.setParameter("productName", productName).setParameter("offlineCartId", offlineCartId)
				.setParameter("brandName", brandName).getResultList().stream().findFirst().orElse(null);

		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public void addOfflineProductList(OfflineProductList offlineProductList) {
		entityManager.persist(offlineProductList);

	}

	@Override
	public void updateOfflineProductList(OfflineProductList offlineProductList) {
		entityManager.merge(offlineProductList);

	}

	@Override
	public List<OfflineProductList> getByShopId(String shopId) {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.findByShopId", OfflineProductList.class)
				.setParameter("shopId", shopId).getResultList();
		return offlineProductList;
	}

	@Override
	public List<OfflineProductList> getAllProductByCartId(int offlineCartId) {
		List<OfflineProductList> offlineProductList = entityManager
				.createNamedQuery("OfflineProductList.findByCartId", OfflineProductList.class)
				.setParameter("offlineCartId", offlineCartId).getResultList();
		return offlineProductList;
	}

	@Override
	public boolean checkExit(int offlineCartId, String productName) {
		OfflineProductList offlineProduct = entityManager
				.createNamedQuery("OfflineProductList.checkexistProductList", OfflineProductList.class)
				.setParameter("offlineCartId", offlineCartId).setParameter("productName", productName).getResultList()
				.stream().findFirst().orElse(null);
		return null != offlineProduct ? Boolean.TRUE : Boolean.FALSE;
	}
	
	@Override
	public boolean deleteOfflineProductList(int id) {
		Query query = entityManager.createQuery("delete OfflineProductList where id = " + id);			
		query.executeUpdate();
		entityManager.flush();
		return true;
	}

}
