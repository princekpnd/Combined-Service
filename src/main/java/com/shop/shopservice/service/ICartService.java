package com.shop.shopservice.service;

import java.util.List;

import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.ProductList;

public interface ICartService {

	List<Cart> getAllCart();
	
	List<Cart> getCartByDeliveryBoyNumber(String dBoyNumber, String shopId);

	public Cart getCart(int cartId);

	List<Cart> getCartByUserId(String userId);
	
	List<Cart> getAllAmount(String shopId);

//	public List<Cart>  getCartForUserByShopId(String shopId);
	public List<Cart> getDeactiveCartByUserIdAndShopId(int userId, String shopId);

	public List<Cart> getCartForOrderByCartId(int cartId);

//	 public boolean updateCart(Cart cart);
	public boolean cartExists(String userId);

//	 public boolean createCart(Cart cart);
	public List<Cart> orderDetails(String shopId, String userId, int orderStatus);
	public List<Cart> orderDetailForUser(String userId, int orderStatus);

	public List<Cart> combinedOrderDetails(String userId, int orderStatus);
	
	public List<Cart> getCartByShopIdId( String shopId);
	
	public List<Cart> getOfflineCart(String shopId, int  orderStatus);

	List<Cart> getCartByUserId(String userId, String shopId, int orderStatus);

	List<Cart> getCartByUserIdAndOrderStatus(String userId, int orderStatus);

	public List<Cart> getCartForUserByShopId(String shopId);

	public Cart getOrderStatus(int cartId, String shopId);
	
	public List<Cart> combinedOrderForAdmin(String shopId, int orderStatus);

	public boolean updateCart(Cart cart);

	public ProductList getProductByProductId(String productId, int cartId);

	public void updateProductList(ProductList plId, float productQuantity);

	public boolean cartExists(String userId, String shopId, int orderStatus);
	
	public boolean offlinecartExists(String shopId, int orderStatus);

	public boolean createCart(Cart cart);
	
	List<Cart> getAllCartForDelivery(String shopId,String dBoyNumber,int orderStatus);
}
