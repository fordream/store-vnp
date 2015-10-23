package org.com.cnc.common.maispreco.response;

import java.util.ArrayList;
import java.util.List;

import com.cnc.maispreco.soap.data.Product;

public class ProductResponse extends Response {
	private List<Product> lProducts = new ArrayList<Product>();
	public List<Product> getlProducts() {
		return lProducts;
	}

	public void setlProducts(List<Product> lProducts) {
		this.lProducts = lProducts;
	}
}