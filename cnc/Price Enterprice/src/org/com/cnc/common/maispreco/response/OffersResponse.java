package org.com.cnc.common.maispreco.response;

import java.util.ArrayList;
import java.util.List;

import com.cnc.maispreco.soap.data.Offers;

public class OffersResponse extends Response {
	private List<Offers> loOffersResponses = new ArrayList<Offers>();

	public List<Offers> getLoOffersResponses() {
		return loOffersResponses;
	}

	public void setLoOffersResponses(List<Offers> loOffersResponses) {
		this.loOffersResponses = loOffersResponses;
	}
}