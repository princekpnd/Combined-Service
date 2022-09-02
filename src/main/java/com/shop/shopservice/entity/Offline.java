package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name ="OFFLINE")
@NamedQueries({
	@NamedQuery(name = "Offline.getAll",
               query = "SELECT ofl FROM Offline ofl"),
	@NamedQuery(name = "Offline.findByBillId",
	           query= "SELECT ofl FROM Offline ofl WHERE ofl.billingId= :billingId"),
	@NamedQuery(name = "Offline.getByShopId",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and isActive is TRUE"),
	@NamedQuery(name ="Offline.getByUserName",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.userName = :userName and ofl.shopId = :shopId"),
	@NamedQuery(name ="Offline.offlineExist",
	           query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.productName = :productName"),
	@NamedQuery(name ="Offline.findByShopIdAndId",
               query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.billingId = :billingId"),
	@NamedQuery(name= "Offline.checkExist",
                query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.isActive = :isActive"),
	@NamedQuery(name = "Offline.findByShopIdAndMobileNo",
	            query = "SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.mobileNo = :mobileNo"),
	@NamedQuery(name = "Offline.cheackDeactive",
                query = "SELECT ofl FROM Offline ofl WHERE ofl.billingId = :billingId"),
	@NamedQuery(name = "offline.findAllByShopId",
	            query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId"),
	@NamedQuery(name= "Offline.getDeactiveByShopId",
                query="SELECT ofl FROM Offline ofl WHERE ofl.shopId = :shopId and ofl.isActive is FALSE"),
	})
public class Offline implements Serializable{
	
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "ID", nullable = false)
	private int billingId;


	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name ="MOBILE_NUMBER", nullable = false)
	private String mobileNo;
	
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	
	@Column(name = "STOCK", nullable = false)
	private int stock;
	
	@Column(name = "PRODUCT_QUANTITY", nullable = false)
	private int productQuantity;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String shopName;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date createdOn;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name ="IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "SELLING_PRICE", nullable = false)
	private float sellingPrice;
	
	@Column(name = "ADMIN_ID", nullable = false)
	private int adminId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "USER_ID", nullable = false)
	private int userId;
	
	@Column(name = "BILLING_DATE", nullable = false)
	private  Date billingDate;
	
//	@Column(name = "BILLING_ID", nullable = false)
//	private  int billingId;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private  int productId;
	
	@Column(name = "OFFER_ACTIVE_END", nullable = false)
	private  boolean  offerActiveEnd;
	
	@Column(name = "OLD_PRICE", nullable = false)
	private  float oldPrice;
	
	@Column(name = "TOTAL_PRICE", nullable = false)
	private  float totalPrice;
	
	@Column(name = "DISCOUNT", nullable = false)
	private  float discount;
	
	@Column(name = "OFFER_PERCENT", nullable = false)
	private  int offerPercent;
	
	@Column(name = "GST_PERCENT", nullable = false)
	private  int gstPercent;
	
	@Column(name = "BRAND_NAME", nullable = false)
	private  String brandName;
	
	@Column(name="PAYABLE_AMOUNT", nullable = false)
	private int payableAmount;
	
	@Transient
	private List<OfflineProductList> offlineProductList;
	
	public Offline() {
		super();
	}
	
//	public Offline(String shopId, String productName) {
//		this.shopId = shopId;
//		this.productName = productName;
//	}
	
	public Offline(String shopId,  String userName) {
		this.shopId = shopId;
		this.userName = userName;
	}

	/**
	 * @return the billingId
	 */
	public int getBillingId() {
		return billingId;
	}

	/**
	 * @param billingId the billingId to set
	 */
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * @return the productQuantity
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * @param productQuantity the productQuantity to set
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the gstAmount
	 */
	public float getGstAmount() {
		return gstAmount;
	}

	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(float gstAmount) {
		this.gstAmount = gstAmount;
	}

	/**
	 * @return the sellingPrice
	 */
	public float getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the adminId
	 */
	public int getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the shopId
	 */
	public String getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the billingDate
	 */
	public Date getBillingDate() {
		return billingDate;
	}

	/**
	 * @param billingDate the billingDate to set
	 */
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the offerActiveEnd
	 */
	public boolean isOfferActiveEnd() {
		return offerActiveEnd;
	}

	/**
	 * @param offerActiveEnd the offerActiveEnd to set
	 */
	public void setOfferActiveEnd(boolean offerActiveEnd) {
		this.offerActiveEnd = offerActiveEnd;
	}

	/**
	 * @return the oldPrice
	 */
	public float getOldPrice() {
		return oldPrice;
	}

	/**
	 * @param oldPrice the oldPrice to set
	 */
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}

	/**
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	/**
	 * @return the offerPercent
	 */
	public int getOfferPercent() {
		return offerPercent;
	}

	/**
	 * @param offerPercent the offerPercent to set
	 */
	public void setOfferPercent(int offerPercent) {
		this.offerPercent = offerPercent;
	}

	/**
	 * @return the gstPercent
	 */
	public int getGstPercent() {
		return gstPercent;
	}

	/**
	 * @param gstPercent the gstPercent to set
	 */
	public void setGstPercent(int gstPercent) {
		this.gstPercent = gstPercent;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the offlineProductList
	 */
	public List<OfflineProductList> getOfflineProductList() {
		return offlineProductList;
	}

	/**
	 * @param offlineProductList the offlineProductList to set
	 */
	public void setOfflineProductList(List<OfflineProductList> offlineProductList) {
		this.offlineProductList = offlineProductList;
	}


	/**
	 * @return the payableAmount
	 */
	public int getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @param payableAmount the payableAmount to set
	 */
	public void setPayableAmount(int payableAmount) {
		this.payableAmount = payableAmount;
	}

}
