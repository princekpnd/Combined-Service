package com.shop.shopservice.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.shop.shopservice.Idao.IDeliveryDAO;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.Delivery;

@Repository
@Transactional
public class DeliveryDAOImpl implements IDeliveryDAO {
	
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Delivery> getAllDelivery() {
		List<Delivery> deliveryList = entityManager.createNamedQuery("Delivery.findAll", Delivery.class)
				.getResultList();
		return deliveryList;
	}

	@Override
	public Delivery getById(int id) {
		return this.entityManager.find(Delivery.class, id);
	}

	@Override
	public boolean deliveryExits(String shopId, String mobileNo) {
		Delivery deliveryList = entityManager.createNamedQuery("Delivery.exitsDelivery", Delivery.class)
				.setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getResultList().stream().findFirst().orElse(null);
		return null != deliveryList ?Boolean.TRUE:Boolean.FALSE;
	}

	@Override
	public void addDelivery(Delivery delivery) {
		entityManager.persist(delivery);
		
	}

	@Override
	public void updateDelivery(Delivery delivery) {
		entityManager.merge(delivery);
		
	}

	@Override
	public Delivery getByShopIdAndMobileNumber(String shopId, String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery.getByShopIdAndMobileNumber",Delivery.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return delivery;
	}

	@Override
	public Delivery getTotal(String shopId, String mobileNo) {
		Delivery delivery = entityManager.createNamedQuery("Delivery.findTotalAmount",Delivery.class).setParameter("shopId", shopId).setParameter("mobileNo", mobileNo).getSingleResult();
		return delivery;
	}
}
