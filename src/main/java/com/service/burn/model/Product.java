package com.service.burn.model;

import com.service.burn.Configuration;

/**
* <h2>Product class represents a product which will be passed to oven for burning.</h2>
* 
* <h2>Product class has 3 properties-</h2>
* <b>productId:</b> an auto incremented id starting from 1 unique for each product.</br>
* <b>productName:</b> a name for the product.<br>
* <b>ovenParameter:</b> couple of parameters which clarify the oven settings required to burn the product. (e.g. temperature, time).
* <br><br>
* @author Ashfaqul Haque
* 
*/
public class Product {
	
	/**
	 * Id of the product.
	 */
	private int productId;
	
	/**
	 * Name of the product.
	 */
	private String productName;
	
	/**
	 * Oven Settings to burn the product.
	 */
	private OvenParameter ovenParameter;
	
	/**
	 * oven tag with which the product was burned
	 */
	private String burnedBy;
	
	
	/**
	 * <h2>Constructor to initialize a product with given product name and oven parameter to burn the product.</h2><br>
	 * Product id is auto incremented and will be set automatically.
	 * @param productName name of the product.
	 * @param ovenParameter oven settings to burn the product.
	 */
	public Product(String productName, OvenParameter ovenParameter) {
		this.productName = productName;
		this.productId = ++Configuration.productIdCounter; //Increase counter by 1 to get unique auto incremented product id.
		this.ovenParameter = ovenParameter;
	}
	
	/**
	 * <h2>Constructor to initialize a product with given product name.</h2><br>
	 * Oven settings will be default.<br>
	 * Product Id is auto incremented and will be set automatically.
	 * @param productName name of the product.
	 */
	public Product(String productName) {
		this(productName, new OvenParameter());
	}
	
	/**
	 * <p>Get product id of current product.</p>
	 * @return product id.
	 */
	public int getProductId() {
		return this.productId;
	}
	
	/**
	 * <p>Get product name of current product.</p>
	 * @return product name.
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * <p>Get oven parameter to burn the product.</p>
	 * @return oven parameter.
	 */
	public OvenParameter getOvenParameter() {
		return this.ovenParameter;
	}
	
	/**
	 * Set consumerTag which represents which oven burned the product
	 * @param consumerTag
	 */
	public void setBurnedBy(String consumerTag) {
		this.burnedBy = consumerTag;
	}
	
	/**
	 * get consumer tag of oven
	 * @return consumer tag
	 */
	public String getBurnedBy() {
		return this.burnedBy;
	}
	
	@Override
	public String toString() {
		return "Product ID: " + this.productId + " |  Name: " + this.productName;
	}
}
