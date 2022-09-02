package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.Offline;
import com.shop.shopservice.entity.OfflineProductList;
import com.shop.shopservice.entity.Product;
import com.shop.shopservice.service.IOfflineProductListService;
import com.shop.shopservice.service.IOfflineService;
import com.shop.shopservice.service.IProductService;

@RestController
@RequestMapping("/api/offline")
public class OfflineController {
	private final Logger log = LoggerFactory.getLogger(OfflineController.class);
	@Autowired
	private IOfflineService offlineService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IOfflineProductListService offlineProductListService;

	@GetMapping("getall")
	public ResponseEntity<List<Offline>> getAllOffline() {
		List<Offline> offlineList = offlineService.getAllOffline();
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/bybillid/{billingId}")
	public ResponseEntity<Offline> getByBillId(@PathVariable("billingId") int billingId) {
		List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
		Offline offline = offlineService.getByBillId(billingId);
		offline.setOfflineProductList(offlineProductList);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("get/byshopid/{shopId}")
	public ResponseEntity<Offline> getAllBillByShopId(@PathVariable("shopId") String shopId) {
		Offline offline = offlineService.getAllBillByShopId(shopId);
		if (null != offline) {
			int billingId = offline.getBillingId();
			List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(billingId);
			offline.setOfflineProductList(offlineProductList);
		}
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}
	
	@GetMapping("get/alldeactive/byshopId/{shopId}")
	public ResponseEntity<List<Offline>> getDeactiveByShopId(@PathVariable("shopId") String shopId) {
		List<Offline> offlineList = offlineService.getDeactiveByShopId(shopId);
		if(null != offlineList && offlineList.size() > 0) {
			for(int i= 0 ; i<offlineList.size() ; i++) {
				int offlineCartId = offlineList.get(i).getBillingId();
				Offline offline = offlineList.get(i);
				List<OfflineProductList> offlineProductList = offlineProductListService.getAllProductByCartId(offlineCartId);
				offline.setOfflineProductList(offlineProductList);
			}
		}
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("getbyshopid/{shopId}")
	public ResponseEntity<List<Offline>> getByShopId(@PathVariable("shopId") String shopId) {
		List<Offline> offlineList = offlineService.getByShopId(shopId);
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/bydecactivebyid/{billingId}")
	public ResponseEntity<Offline> checkDecative(@PathVariable("billingId") int billingId) {
		Offline offline = offlineService.checkDeactive(billingId);
		offline.setActive(Boolean.FALSE);
		offlineService.updateOffline(offline);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("get/shopid/billingid/{shopId}/{billingId}")
	public ResponseEntity<Offline> getByShopIdAndBillId(@PathVariable("shopId") String shopId,
			@PathVariable("billingId") int billingId) {
		Offline offline = offlineService.getByShopIdAndBillId(shopId, billingId);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@GetMapping("getbill/byshopid/username/{shopId}/{userName}")
	public ResponseEntity<List<Offline>> getAllBillByUserName(@PathVariable("shopId") String shopId,
			@PathVariable("userName") String userName) {
		List<Offline> offlineList = offlineService.getAllBillByUserName(shopId, userName);
		return new ResponseEntity<List<Offline>>(offlineList, HttpStatus.OK);
	}

	@GetMapping("get/byshopid/mobileno/{shopId}/{mobileNo}")
	public ResponseEntity<Offline> getByShopIdAndMobileNo(@PathVariable("shopId") String shopId,
			@PathVariable("mobileNo") String mobileNo) {
		Offline offline = offlineService.getByShopIdAndMobileNo(shopId, mobileNo);
		return new ResponseEntity<Offline>(offline, HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.BILLING_ID)) {
			Offline offline = new Offline(json.get(ServiceConstants.SHOP_ID), json.get(ServiceConstants.PRODUCT_NAME));

			int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID));
			int productQuantity = Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY));
			String productName = json.get(ServiceConstants.PRODUCT_NAME);

			List<Product> productList = productService.getProductByName(productName);
			Product product = productList.get(0);
			int sellingPrice = ((int) product.getSellingPrice());
			int gstAmount1 = (int) product.getGstAmount();
			int offerPercent = product.getOfferPercent();

			int quantity = product.getStock() - productQuantity;

			int gstAmount = productQuantity * gstAmount1;
			int totalPrice = sellingPrice * productQuantity;
			int paidAmount = totalPrice + gstAmount;

			if (Boolean.parseBoolean(json.get(ServiceConstants.OFFER_ACTIVE_IND))) {
				int offerPrice = (totalPrice * offerPercent) / 100;
				int amount = totalPrice - offerPrice;
				int paidOfferAmount = amount + gstAmount;

				offline.setSellingPrice(paidOfferAmount);
			} else {
				offline.setSellingPrice(paidAmount);
			}

			offline.setActive(Boolean.TRUE);
			offline.setAdminId(Integer.parseInt(json.get(ServiceConstants.ADMIN_ID)));
			offline.setBillingDate(new Date());
//			offline.setBillingId(Integer.parseInt(json.get(ServiceConstants.BILLING_ID)));
			offline.setCreatedOn(new Date());
			offline.setDeleted(Boolean.FALSE);
			offline.setGstAmount(gstAmount);
			offline.setMobileNo(json.get(ServiceConstants.MOBILE_NUMBER));
			offline.setProductName(productName);
			offline.setSellingPrice(paidAmount);
			offline.setProductId(productId);
			offline.setShopId(json.get(ServiceConstants.SHOP_ID));
			offline.setShopName(json.get(ServiceConstants.SHOP_NAME));
			offline.setProductQuantity(Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY)));

			offline.setUserId(Integer.parseInt(json.get(ServiceConstants.USER_ID)));
			offline.setUserName(json.get(ServiceConstants.USER_NAME));

			offline.setStock(quantity);

			response.put("BILLING_ID", json.get(ServiceConstants.BILLING_ID));
			if (offlineService.offlineExists(offline.getShopId(), offline.getProductName())) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "offline already exist with given billId");
			} else {
				boolean result = offlineService.offlineCreate(offline);
				if (product.getStock() >= productQuantity) {
					product.setStock(product.getStock() - productQuantity);
					productService.updateProduct(product);

				} else {
					response.put("Status", Boolean.FALSE.toString());
					response.put("description", "Not Available this product");
				}

				response.put("status", Strings.EMPTY + result);
				response.put("description", "Bill created successfully with given billingId");

			}
		}
		return ResponseEntity.ok().body(response);

	}

	@SuppressWarnings({})
	@PostMapping("/create/main")
	ResponseEntity<Map<String, String>> createOffline(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.PRODUCT_NAME)
				&& null != json.get(ServiceConstants.BRAND_NAME) && null != json.get(ServiceConstants.SHOP_NAME)
				&& null != json.get(ServiceConstants.QUANTITY) && null != json.get(ServiceConstants.TOTAL_PRICE)
				&& null != json.get(ServiceConstants.PRICE) && null != json.get(ServiceConstants.OFFLINE_CART_ID)
				&& null != json.get(ServiceConstants.OLD_PRICE)
				&& null != json.get(ServiceConstants.STOCK_ACTIVE_IND)) {
			String shopId = json.get(ServiceConstants.SHOP_ID), productName = json.get(ServiceConstants.PRODUCT_NAME),
					brandName = json.get(ServiceConstants.BRAND_NAME), shopName = json.get(ServiceConstants.SHOP_NAME),
					productId = null, measurement = null, batchNumber = null;
			boolean stockActiveInd = Boolean.parseBoolean(json.get(ServiceConstants.STOCK_ACTIVE_IND));
			Product product = null;

			float totalAmount = Float.parseFloat(json.get(ServiceConstants.TOTAL_PRICE)),
					sellingPrice = Float.parseFloat(json.get(ServiceConstants.PRICE)),
					productQuantity = Float.parseFloat(json.get(ServiceConstants.QUANTITY)), discount = 0,
					oldPrice = Float.parseFloat(json.get(ServiceConstants.OLD_PRICE)), gstAmount = 0;
			int offlineCartId = Integer.parseInt(json.get(ServiceConstants.OFFLINE_CART_ID)), gstPercent = 0,
					offerPercent = 0;

			Offline offline = null;
			boolean offlinecreate = false;
			
			

			OfflineProductList offlineProductList = null;

			offline = offlineService.getAllBillByShopId(shopId);
			if (null != offline) {
				offlineProductList = new OfflineProductList(shopId, productName, brandName);

				offlineProductList.setBrandName(brandName);
				offlineProductList.setOfflineCartId(offlineCartId);
				offlineProductList.setPrice(sellingPrice);
				offlineProductList.setProductName(productName);
				offlineProductList.setQuantity(productQuantity);
				offlineProductList.setShopId(shopId);
				offlineProductList.setTotalPrice(totalAmount);
				offlineProductList.setShopName(shopName);
				offlineProductList.setActive(Boolean.TRUE);
				offlineProductList.setCreatedOn(new Date());
				offlineProductList.setDeleted(Boolean.FALSE);
				offlineProductList.setStockActiveInd(stockActiveInd);
				offlineProductList.setOldPrice(oldPrice);
				

				if (null != json.get(ServiceConstants.PRODUCT_ID)) {
					productId = json.get(ServiceConstants.PRODUCT_ID);
					offlineProductList.setProductId((productId));
				}
				if (null != json.get(ServiceConstants.DISCOUNT)) {
					discount = Float.parseFloat(json.get(ServiceConstants.DISCOUNT));
					offlineProductList.setDiscount(discount);
				}

				if (null != json.get(ServiceConstants.GST_PERCENT)) {
					gstPercent = Integer.parseInt((json.get(ServiceConstants.GST_PERCENT)));
					offlineProductList.setGstPercent(gstPercent);
				}

				if (null != json.get(ServiceConstants.GST_AMOUNT)) {
					gstAmount = Float.parseFloat(json.get(ServiceConstants.GST_AMOUNT));
					offlineProductList.setGstAmount(gstAmount);
				}

				if (null != json.get(ServiceConstants.OFFER_PERCENT)) {
					offerPercent = Integer.parseInt(json.get(ServiceConstants.OFFER_PERCENT));
					offlineProductList.setOfferPercent(offerPercent);
				}

				if (null != json.get(ServiceConstants.MEASUREMENT)) {
					measurement = json.get(ServiceConstants.MEASUREMENT);
					offlineProductList.setMeasurement(measurement);
				}

				if (null != json.get(ServiceConstants.DATE_OF_EXPIRE)) {
					Date dateOfExpire = null;
					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dateOfExpire = formater.parse(json.get(ServiceConstants.DATE_OF_EXPIRE));
						offlineProductList.setDateOfExpire(dateOfExpire);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (null != json.get(ServiceConstants.BATCH_NUMBER)) {
					batchNumber = json.get(ServiceConstants.BATCH_NUMBER);
					offlineProductList.setBatchNumber(batchNumber);
				}

				float offlineGstAmount = offline.getGstAmount() + gstAmount,
						offlineDiscount = offline.getDiscount() + discount,
						offlinePrice = offline.getSellingPrice() + sellingPrice,
						offlineOldPrice = offline.getOldPrice() + oldPrice,
						offlineTotalAmount = offline.getTotalPrice() + totalAmount;
				int payableAmount = (int) Math.ceil(offlineTotalAmount);

				offline.setGstAmount(offlineGstAmount);
				offline.setDiscount(offlineDiscount);
				offline.setSellingPrice(offlinePrice);
				offline.setTotalPrice(offlineTotalAmount);
				offline.setPayableAmount(payableAmount);
				offline.setOldPrice(offlineOldPrice);

				

				boolean result1 = offlineService.updateOffline(offline);
				boolean exists = offlineProductListService.checkExit(offlineCartId, productName);
				if (exists) {
					response.put("status", Boolean.FALSE.toString());
					response.put("description", "Product allready exists with given product name.");
					response.put("cause", "product_exists");
				} else {
					boolean result = offlineProductListService.offlineProductCreate(offlineProductList);
					if (result) {
						if (result1) {
							response.put("status", Strings.EMPTY + result1);
							response.put("description", "ProductList updated successfully with given Shop Id");
							if (stockActiveInd) {
								int productId1 = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID)), stock = 0;
								product = productService.getProduct(productId1);
								if (product.getStock() >= (int) productQuantity) {
									stock = product.getStock();
									product.setStock(stock - (int) productQuantity);
									offlineProductList.setStock(product.getStock());
									boolean update = productService.updateProduct(product);
								}

								/*------------------NEED TO ADD ELSE PART----------------*/
							}
							
						} else {
							response.put("status", Boolean.FALSE.toString());
							response.put("description", "Cart has not been updated.");
							response.put("cause", "cart_update_faield");
						}
					} else {
						response.put("status", Boolean.FALSE.toString());
						response.put("description", "Cart has not been updated.");
						response.put("cause", "product_create_faield");
					}
				}

			} else {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Cart does not exist with given cart ID.");
				response.put("cause", "cart_not_found");
			}
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Product creation failed due to less data.");
			response.put("cause", "parameter_faield");
		}

		return ResponseEntity.ok().body(response);

	}

	@PostMapping("/create2")
	ResponseEntity<Map<String, String>> createOfflineByShopId(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SHOP_ID));
		Map<String, String> response = new HashMap<String, String>();
		if (null != json.get(ServiceConstants.SHOP_ID) && null != json.get(ServiceConstants.USER_NAME)) {
			String shopId = json.get(ServiceConstants.SHOP_ID), mobileNo = null,
					userName = json.get(ServiceConstants.USER_NAME);
			Offline offline = new Offline(json.get(ServiceConstants.SHOP_ID), json.get(ServiceConstants.USER_NAME));
			offline.setShopId(shopId);
			offline.setUserName(userName);
			offline.setActive(Boolean.TRUE);
			offline.setDeleted(Boolean.FALSE);
			offline.setCreatedOn(new Date());
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				offline.setMobileNo(mobileNo);
			}
			response.put("shopId", json.get(ServiceConstants.SHOP_ID));
			if (offlineService.offlineCheckExists(offline.getShopId(), true)) {
				response.put("status", Boolean.FALSE.toString());
				response.put("description", "Offline cart allready exists with given shop ID.");
				response.put("cause", "cart_exists");
			} else {
				offlineService.offlineCreate2(offline);
				response.put("status", Boolean.TRUE.toString());
				response.put("billingId", String.valueOf(offline.getBillingId()));
				response.put("description", "Offline cart created");
			}

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Cart creation failed due to less data.");
			response.put("cause", "parameter_faield");
		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/update")
	ResponseEntity<Map<String, String>> UpdateOffline(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to update user: {}", json.get(ServiceConstants.ID));
		Map<String, String> response = new HashMap<String, String>();

		if (null != json.get(ServiceConstants.ID)) {
			Offline offline = offlineService.getByBillId(Integer.parseInt(json.get(ServiceConstants.ID)));

			if (null != json.get(ServiceConstants.USER_NAME)) {
				String userName = json.get(ServiceConstants.USER_NAME);
				offline.setUserName(userName);
			}
			if (null != json.get(ServiceConstants.USER_ID)) {
				int userId = Integer.parseInt(json.get(ServiceConstants.USER_ID));
				offline.setUserId(userId);
			}
			if (null != json.get(ServiceConstants.IS_ACTIVE)) {
				boolean isActive = Boolean.TRUE;
				offline.setActive(isActive);
			}
			if (null != json.get(ServiceConstants.ADMIN_ID)) {
				int adminId = Integer.parseInt(json.get(ServiceConstants.ADMIN_ID));
				offline.setAdminId(adminId);
			}

			if (null != json.get(ServiceConstants.CREATED_ON)) {
				Date createdOn = new Date();
				offline.setCreatedOn(createdOn);
			}
			if (null != json.get(ServiceConstants.IS_DELETED)) {
				boolean isDeleted = Boolean.FALSE;
				offline.setDeleted(isDeleted);
			}

			if (null != json.get(ServiceConstants.GST_AMOUNT)) {
				int gstAmount = Integer.parseInt(json.get(ServiceConstants.GST_AMOUNT));
				offline.setGstAmount(gstAmount);
			}
			if (null != json.get(ServiceConstants.MOBILE_NUMBER)) {
				String mobileNo = json.get(ServiceConstants.MOBILE_NUMBER);
				offline.setMobileNo(mobileNo);
			}
			if (null != json.get(ServiceConstants.PRODUCT_ID)) {
				int productId = Integer.parseInt(json.get(ServiceConstants.PRODUCT_ID));
				offline.setProductId(productId);
			}
			if (null != json.get(ServiceConstants.PRODUCT_NAME)) {
				String productName = json.get(ServiceConstants.PRODUCT_NAME);
				offline.setProductName(productName);
			}
			if (null != json.get(ServiceConstants.PRODUCT_QUANTITY)) {
				int productQuantity = Integer.parseInt(json.get(ServiceConstants.PRODUCT_QUANTITY));
				offline.setProductQuantity(productQuantity);
			}
			if (null != json.get(ServiceConstants.SELLING_PRICE)) {
				int sellingPrice = Integer.parseInt(json.get(ServiceConstants.SELLING_PRICE));
				offline.setSellingPrice(sellingPrice);
			}
			if (null != json.get(ServiceConstants.SHOP_ID)) {
				String shopId = json.get(ServiceConstants.SHOP_ID);
				offline.setShopId(shopId);
			}
			if (null != json.get(ServiceConstants.SHOP_NAME)) {
				String shopName = json.get(ServiceConstants.SHOP_NAME);
				offline.setShopName(shopName);
			}
			if (null != json.get(ServiceConstants.STOCK)) {
				int stock = Integer.parseInt(json.get(ServiceConstants.STOCK));
				offline.setStock(stock);
			}

			offlineService.updateOffline(offline);
			response.put("status", Boolean.TRUE.toString());
			response.put("Description", "Offline bill update");

		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("Description", "Offline bill not update");
		}
		return ResponseEntity.ok().body(response);
	}

}
