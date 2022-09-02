package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Cart;
import com.shop.shopservice.entity.Delivery;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.ProductList;
import com.shop.shopservice.service.ICartService;
import com.shop.shopservice.service.IDeliveryService;
import com.shop.shopservice.service.IProductListService;
import com.shop.shopservice.serviceimpl.ProductListServiceImpl;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
	@Autowired
	private IDeliveryService deliveryService;

	@Autowired
	ICartService cartService;

	@Autowired
	private ILookUpType lookUpType;

	@Autowired
	private ILookUp lookup;

	@Autowired
	private IProductListService productListService;

	private final Logger log = LoggerFactory.getLogger(EmployeeAddressController.class);

	@GetMapping("getall")
	public ResponseEntity<List<Delivery>> getAllDelivery() {
		List<Delivery> deliveryList = deliveryService.getAllDelivery();
		return new ResponseEntity<List<Delivery>>(deliveryList, HttpStatus.OK);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<Delivery> getById(@PathVariable("id") int id) {
		Delivery delivery = deliveryService.getById(id);
		return new ResponseEntity<Delivery>(delivery, HttpStatus.OK);
	}

	@GetMapping("get/total/{shopId}/{mobileNo}")
	public ResponseEntity<Delivery> getTotalAmount(@PathVariable("shopId") String shopId,
			@PathVariable("mobileNo") String mobileNo) {
		Delivery delivery = deliveryService.getTotal(shopId, mobileNo);
		List<Cart> cartList = cartService.getCartByDeliveryBoyNumber(mobileNo, shopId);
		List<Cart> cartList1 = new ArrayList<Cart>();
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "CASH");
		int received = lookUp.getLookUpId();
		int delivered = lookUp1.getLookUpId();
		int successful = lookUp2.getLookUpId();
		int cash = lookUp3.getLookUpId();
		int totalAmount = 0;
		for (int i = 0; i < cartList.size(); i++) {
			int orderStatus = cartList.get(i).getOrderStatus();
			int transactionType = cartList.get(i).getTransactionType();
			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
				cartList1.add(cartList.get(i));	
				if(transactionType == cash) {	
					int payableAmount = cartList1.get(i).getPayableAmount();
					totalAmount = totalAmount + payableAmount;
				}			

			}
		}
		delivery.setTotalAmount(totalAmount);
		deliveryService.updateDelivery(delivery);
		return new ResponseEntity<Delivery>(delivery, HttpStatus.OK);
	}
	
//	@GetMapping("get/total/{shopId}/{mobileNo}")
//	public ResponseEntity<Delivery> getTotalAmount(@PathVariable("shopId") String shopId,
//			@PathVariable("mobileNo") String mobileNo) {
//		Delivery delivery = deliveryService.getTotal(shopId, mobileNo);
//		List<Cart> cartList = cartService.getCartByDeliveryBoyNumber(mobileNo, shopId);
//		List<Cart> cartList1 = new ArrayList<Cart>();
//		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
//		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
//		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
//		LookUp lookUp3 = lookup.findLookUpIdByName("MILAAN", "CASH");
//		int received = lookUp.getLookUpId();
//		int delivered = lookUp1.getLookUpId();
//		int successful = lookUp2.getLookUpId();
//		int cash = lookUp3.getLookUpId();
//		int totalAmount = 0;
//		for (int i = 0; i < cartList.size(); i++) {
//			int orderStatus = cartList.get(i).getOrderStatus();
//			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
//				cartList1.add(cartList.get(i));		
//			int cartId = cartList1.get(i).getCartId();
//			Cart cart = cartService.getCart(cartId);
//			int transactionType = cart.getTransactionType();
//			if(transactionType == cash) {
//				int payableAmount = cartList1.get(i).getPayableAmount();
//				totalAmount = totalAmount + payableAmount;
//			}				
//			}
//		}
//		delivery.setTotalAmount(totalAmount);
//		deliveryService.updateDelivery(delivery);
//		return new ResponseEntity<Delivery>(delivery, HttpStatus.OK);
//	}


	@GetMapping("getby/shopid/mobilenumber/{shopId}/{mobileNo}")
	public ResponseEntity<Delivery> getByShopIdAndMobileNumber(@PathVariable("shopId") String shopId,
			@PathVariable("mobileNo") String mobileNo) {
		Delivery delivery = null;
		delivery = deliveryService.getByShopIdAndMobileNumber(shopId, mobileNo);
		List<Cart> cartList = cartService.getCartByDeliveryBoyNumber(mobileNo, shopId);
		List<Cart> cartList1 = new ArrayList<Cart>();
		LookUp lookUp = lookup.findLookUpIdByName("MILAAN", "RECEIVED");
		LookUp lookUp1 = lookup.findLookUpIdByName("MILAAN", "DELIVERED");
		LookUp lookUp2 = lookup.findLookUpIdByName("MILAAN", "SUCCESSFUL");
		int received = lookUp.getLookUpId();
		int delivered = lookUp1.getLookUpId();
		int successful = lookUp2.getLookUpId();
		for (int i = 0; i < cartList.size(); i++) {
			int orderStatus = cartList.get(i).getOrderStatus();
			int cartId = cartList.get(i).getCartId();
			List<ProductList> productList = productListService.getByshopIdCartId(shopId, cartId);
			if (orderStatus == received || orderStatus == delivered || orderStatus == successful) {
				cartList1.add(cartList.get(i));
				cartList1.get(i).setProductList(productList);
			}
			delivery.setCartList(cartList1);
		}

		return new ResponseEntity<Delivery>(delivery, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createDelivery(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.CITY));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.MOBILE_NUMBER) && null != json.get(ServiceConstants.SHOP_ID)) {
			String mobileNumber = json.get(ServiceConstants.MOBILE_NUMBER), shopId = json.get(ServiceConstants.SHOP_ID);
			Delivery delivery = new Delivery(mobileNumber, shopId);

			delivery.setMobileNo(mobileNumber);
			delivery.setFristName(json.get(ServiceConstants.F_NAME));
			delivery.setGender(Integer.parseInt(json.get(ServiceConstants.GENDER)));
			delivery.setLastName(json.get(ServiceConstants.L_NAME));
			delivery.setState(json.get(ServiceConstants.STATE));
			delivery.setUserType(Integer.parseInt(json.get(ServiceConstants.USER_TYPE)));
			delivery.setShopId(shopId);
			delivery.setShopName(json.get(ServiceConstants.SHOP_NAME));
			delivery.setActive(Boolean.TRUE);
			delivery.setCreatedOn(new Date());
			delivery.setDeleted(Boolean.FALSE);

			if (null != json.get(ServiceConstants.AVATAR)) {
				delivery.setAvatar(json.get(ServiceConstants.AVATAR));
			}
			if (null != json.get(ServiceConstants.CART_ID)) {
				delivery.setCartId(Integer.parseInt(json.get(ServiceConstants.CART_ID)));
			}
			if (null != json.get(ServiceConstants.CITY)) {
				delivery.setCity(json.get(ServiceConstants.CITY));
			}
			if (null != json.get(ServiceConstants.COUNTRY)) {
				delivery.setCountry(json.get(ServiceConstants.COUNTRY));
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				delivery.setDistrict(json.get(ServiceConstants.DISTRICT));
			}
			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				delivery.setEmailId(json.get(ServiceConstants.EMAIL_ID));
			}
			if (null != json.get(ServiceConstants.PAN_NUM)) {
				delivery.setPanNumber(json.get(ServiceConstants.PAN_NUM));
			}
			if (null != json.get(ServiceConstants.PIN_CODE)) {
				delivery.setPinCode(Integer.parseInt(json.get(ServiceConstants.PIN_CODE)));
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				delivery.setPoliceStation(json.get(ServiceConstants.POLICE_STATION));
			}
			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				delivery.setPostOffice(json.get(ServiceConstants.POST_OFFICE));
			}
			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				delivery.setAdharNumber(json.get(ServiceConstants.ADHAR_NUM));
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				delivery.setTotalAmount(Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT)));
			}

			if (null != json.get(ServiceConstants.PAID)) {
				delivery.setPaid(Integer.parseInt(json.get(ServiceConstants.PAID)));
			}
			if (null != json.get(ServiceConstants.DUES)) {
				delivery.setDues(Integer.parseInt(json.get(ServiceConstants.DUES)));
			}

			response.put("Status", json.get(ServiceConstants.SHOP_ID));
			if (deliveryService.deliveryExits(delivery.getShopId(), delivery.getMobileNo())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("descreption", "Delivery boy already exist with givenId");
			} else {
				boolean result = deliveryService.deliveryCreate(delivery);
				response.put("status", Boolean.TRUE.toString());
				response.put("deliveryBoyId", String.valueOf(delivery.getId()));
				response.put("descerption", "delivery information created sussfully");
			}
		}

		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({})
	@PutMapping("/update")
	ResponseEntity<Map<String, String>> updateDelivery(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.CITY));
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.ID)
				&& null != deliveryService.getById(Integer.parseInt(json.get(ServiceConstants.ID)))) {
			Delivery delivery = deliveryService.getById(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				delivery.setShopId(shopId);
			}

			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				delivery.setShopName(shopName);
			}
			if (null != json.get(ServiceConstants.ADHAR_NUM)) {
				String adharNumber = json.get(ServiceConstants.ADHAR_NUM);
				delivery.setAdharNumber(adharNumber);
			}
			if (null != json.get(ServiceConstants.AVATAR)) {
				String avatar = json.get(ServiceConstants.AVATAR);
				delivery.setAvatar(avatar);
			}
			if (null != json.get(ServiceConstants.CART_ID)) {
				int cartId = Integer.parseInt(json.get(ServiceConstants.CART_ID));
				delivery.setCartId(cartId);
			}
			if (null != json.get(ServiceConstants.CITY)) {
				String city = json.get(ServiceConstants.CITY);
				delivery.setCity(city);
			}

			if (null != json.get(ServiceConstants.COUNTRY)) {
				String country = json.get(ServiceConstants.COUNTRY);
				delivery.setCountry(country);
			}

			if (null != json.get(ServiceConstants.DISTRICT)) {
				String district = json.get(ServiceConstants.DISTRICT);
				delivery.setDistrict(district);
			}
			if (null != json.get(ServiceConstants.EMAIL_ID)) {
				String emailId = json.get(ServiceConstants.EMAIL_ID);
				delivery.setEmailId(emailId);
			}
			if (null != json.get(ServiceConstants.F_NAME)) {
				String fname = json.get(ServiceConstants.F_NAME);
				delivery.setFristName(fname);
			}

			if (null != json.get(ServiceConstants.L_NAME)) {
				String lastName = json.get(ServiceConstants.L_NAME);
				delivery.setLastName(lastName);
			}
			if (null != json.get(ServiceConstants.GENDER)) {
				int gender = Integer.parseInt(json.get(ServiceConstants.GENDER));
				delivery.setGender(gender);
			}
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNumber = json.get(ServiceConstants.MOBILE_NUMBER);
				delivery.setMobileNo(mobileNumber);
			}
			if (null != json.get(ServiceConstants.PAN_NUM)) {
				String panNumber = json.get(ServiceConstants.PAN_NUM);
				delivery.setPanNumber(panNumber);
			}

			if (null != json.get(ServiceConstants.PIN_CODE)) {
				int pinCode = Integer.parseInt(json.get(ServiceConstants.PIN_CODE));
				delivery.setPinCode(pinCode);
			}
			if (null != json.get(ServiceConstants.POLICE_STATION)) {
				String policeStation = json.get(ServiceConstants.POLICE_STATION);
				delivery.setPoliceStation(policeStation);
			}

			if (null != json.get(ServiceConstants.POST_OFFICE)) {
				String postOffice = json.get(ServiceConstants.POST_OFFICE);
				delivery.setPostOffice(postOffice);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				delivery.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				delivery.setShopName(shopName);
			}
			if (null != json.get(ServiceConstants.STATE)) {
				String state = json.get(ServiceConstants.STATE);
				delivery.setState(state);
			}
			if (null != json.get(ServiceConstants.USER_TYPE)) {
				int userType = Integer.parseInt(json.get(ServiceConstants.USER_TYPE));
				delivery.setUserType(userType);
			}

			if (null != json.get(ServiceConstants.TOTAL_AMOUNT)) {
				int totalAmount = Integer.parseInt(json.get(ServiceConstants.TOTAL_AMOUNT));
				delivery.setTotalAmount(totalAmount);
			}

			deliveryService.updateDelivery(delivery);
			response.put("status", Boolean.TRUE.toString());
			response.put("Descreption", "Update delivery boy information");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Descreption", "not updated");
		}
		return ResponseEntity.ok().body(response);
	}
}
