package com.vn.icts.wendy.model;


public class Item {
	private String id;
	private String name;
	private String comment;
	private String address;
	private String web;
	private String price;
	private String urlImage;
	private String phone;
	
	private String published_datetime;
	private String create_datetime;
	
	/**
	 * @return the published_datetime
	 */
	public String getPublished_datetime() {
		return published_datetime;
	}


	/**
	 * @param published_datetime the published_datetime to set
	 */
	public void setPublished_datetime(String published_datetime) {
		this.published_datetime = published_datetime;
	}


	/**
	 * @return the create_datetime
	 */
	public String getCreate_datetime() {
		return create_datetime;
	}


	/**
	 * @param create_datetime the create_datetime to set
	 */
	public void setCreate_datetime(String create_datetime) {
		this.create_datetime = create_datetime;
	}


	public Item() {
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the urlImage
	 */
	public String getUrlImage() {
		return urlImage;
	}

	/**
	 * @param urlImage
	 *            the urlImage to set
	 */
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the web
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * @param web
	 *            the web to set
	 */
	public void setWeb(String web) {
		this.web = web;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
}