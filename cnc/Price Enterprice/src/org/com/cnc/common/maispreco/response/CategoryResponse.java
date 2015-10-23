package org.com.cnc.common.maispreco.response;

import java.util.ArrayList;
import java.util.List;

import com.cnc.maispreco.soap.data.Category;

public class CategoryResponse extends Response {
	private List<Category> lProducts = new ArrayList<Category>();

	public List<Category> getlProducts() {
		return lProducts;
	}

	public void setlProducts(List<Category> lProducts) {
		this.lProducts = lProducts;
	}


}