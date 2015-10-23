package com.icts.shortfilmfestival.entity;

public class PhotosEntity {
	private String imgSmall;
	private String imgMedium;
	private String imgBig;
	private int commentCount;
	private String listComment;
	private String caption;
	private String type;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImgSmall() {
		return imgSmall;
	}
	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}
	public String getImgMedium() {
		return imgMedium;
	}
	public void setImgMedium(String imgMedium) {
		this.imgMedium = imgMedium;
	}
	public String getImgBig() {
		return imgBig;
	}
	public void setImgBig(String imgBig) {
		this.imgBig = imgBig;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getListComment() {
		return listComment;
	}
	public void setListComment(String listComment) {
		this.listComment = listComment;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
