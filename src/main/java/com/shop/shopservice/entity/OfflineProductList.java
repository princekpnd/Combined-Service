package com.shop.shopservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name ="OFFLINE_PRODUCT")

@NamedQueries({
	@NamedQuery(name = "OfflineProductList.getAll",
               query = "SELECT ofp FROM OfflineProductList ofp"),
	@NamedQuery(name = "OfflineProductList.existProductList",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.productName =:productName and ofp.shopId =:shopId and ofp.brandName =:brandName"),
	@NamedQuery(name = "OfflineProductList.existProduct",
    query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.productName =:productName and ofp.offlineCartId =:offlineCartId and ofp.brandName =:brandName"),
	@NamedQuery(name ="OfflineProductList.findByShopId",
	           query ="SELECT ofp FROM OfflineProductList ofp WHERE ofp.shopId = :shopId"),
	@NamedQuery(name ="OfflineProductList.findByCartId",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.offlineCartId = :offlineCartId"),
	@NamedQuery(name = "OfflineProductList.checkexistProductList",
	           query = "SELECT ofp FROM OfflineProductList ofp WHERE ofp.offlineCartId = :offlineCartId and ofp.productName = :productName")
})
public class OfflineProductList implements Serializable{
	private static final long serialVersionUID = 1385794955661915701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	

	@Column(name = "BRAND_NAME", nullable = false)
	private String brandName;
	
	@Column(name = "QUANTITY", nullable = false)
	private float quantity;
	
	@Column(name = "PRICE", nullable = false)
	private float price;
	
	@Column(name = "OLD_PRICE", nullable = false)
	private float oldPrice;
	
	@Column(name = "TOTAL_PRICE", nullable = false)
	private float totalPrice;
	
	@Column(name = "DISCOUNT", nullable = false)
	private float discount;
	
	@Column(name = "OFFER_PERCENT", nullable = false)
	private int offerPercent;
	
	@Column(name = "GST_PERCENT", nullable = false)
	private int gstPercent;
	
	@Column(name = "GST_AMOUNT", nullable = false)
	private float gstAmount;
	
	@Column(name = "OFFLINE_CART_ID", nullable = false)
	private int offlineCartId;
	
	@Column(name = "SHOP_ID", nullable = false)
	private String shopId;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private boolean isActive;
	
	@Column(name = "IS_DELETED", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "CREATED_ON", nullable = false)
	private Date  createdOn;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;
	
	@Column(name = "SHOP_NAME", nullable = false)
	private String  shopName;
	
	@Column(name = "MEASUREMENT", nullable = false)
	private String measurement;
	
	@Column(name = "BATCH_NUMBER", nullable = false)
	private String batchNumber;
	
	@Column(name = "DATE_OF_EXPIRE", nullable = false)
	private Date dateOfExpire;
	
	@Column(name = "STOCK_ACTIVE_IND", nullable = false)
	private  boolean  stockActiveInd;
	
	@Column(name = "STOCK", nullable = false)
	private  int  stock;

	
	public OfflineProductList(String shopId, String productName, String brandName) {
		this.shopId = shopId;
		this.productName = productName;
		this.brandName= brandName;
	}
	
	public OfflineProductList() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
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
	 * @return the offlineCartId
	 */
	public int getOfflineCartId() {
		return offlineCartId;
	}

	/**
	 * @param offlineCartId the offlineCartId to set
	 */
	public void setOfflineCartId(int offlineCartId) {
		this.offlineCartId = offlineCartId;
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the measurement
	 */
	public String getMeasurement() {
		return measurement;
	}

	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the dateOfExpire
	 */
	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	/**
	 * @param dateOfExpire the dateOfExpire to set
	 */
	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

	/**
	 * @return the stockActiveInd
	 */
	public boolean isStockActiveInd() {
		return stockActiveInd;
	}

	/**
	 * @param stockActiveInd the stockActiveInd to set
	 */
	public void setStockActiveInd(boolean stockActiveInd) {
		this.stockActiveInd = stockActiveInd;
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


	}
