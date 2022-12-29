/*
 * Dùng để tạo lưu sản phẩm đã được đặt hàng, gọi lên từ CSDL (bảng tổng hợp)
 */
package model;

import java.sql.Date;

public class ProductOrders {
	private int orderId;
	private int productId;
	private int amountProduct;
	private float priceProduct;
	private float priceProductSum;
	private String productName;
	private String producImgSource;
	private Date orderDate;
	
	
	public ProductOrders(int orderId, int productId, int amountProduct, float priceProduct, float priceProductSum,
			String productName, String producImgSource, Date orderDate) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.amountProduct = amountProduct;
		this.priceProduct = priceProduct;
		this.priceProductSum = priceProductSum;
		this.productName = productName;
		this.producImgSource = producImgSource;
		this.orderDate = orderDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getAmountProduct() {
		return amountProduct;
	}

	public void setAmountProduct(int amountProduct) {
		this.amountProduct = amountProduct;
	}

	public float getPriceProduct() {
		return priceProduct;
	}

	public void setPriceProduct(float priceProduct) {
		this.priceProduct = priceProduct;
	}

	public float getPriceProductSum() {
		return priceProductSum;
	}

	public void setPriceProductSum(float priceProductSum) {
		this.priceProductSum = priceProductSum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProducImgSource() {
		return producImgSource;
	}

	public void setProducImgSource(String producImgSource) {
		this.producImgSource = producImgSource;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	
}
