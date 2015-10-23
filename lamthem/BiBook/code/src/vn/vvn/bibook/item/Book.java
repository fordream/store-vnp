package vn.vvn.bibook.item;

import java.util.ArrayList;

public class Book {
	// "id":"84",
	// "title":"Các Loài �?ộng vật 3",
	// "description":"Các con vật ngộ nghĩnh!",
	// "cover_url":"http://ibook.vvn.vn/binary_data/books/84/cover.jpeg",
	// "number_comments":null,
	// "number_likes":null,
	// "categories":"Ngoại ngữ,",
	// "created_timestamp":1332322706,
	// "autoslide":1,
	// "category_ids":"2,",
	// "zip_folder_name":"Cac_Loai_Dong_vat_3_2011-12-15_13_34_19"
	public int idCategory;

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public boolean isShowDel = false;

	public boolean isShowDel() {
		return isShowDel;
	}

	public void setShowDel(boolean isShowDel) {
		this.isShowDel = isShowDel;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public int getNumber_comments() {
		return number_comments;
	}

	public void setNumber_comments(int number_comments) {
		this.number_comments = number_comments;
	}

	public int getNumber_likes() {
		return number_likes;
	}

	public void setNumber_likes(int number_likes) {
		this.number_likes = number_likes;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
		String[] cats = this.categories.split(",");
		listCategory = new ArrayList<String>();
		for (int i = 0; i < cats.length; i++) {
			listCategory.add(cats[i]);
		}
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
		String[] cats = this.categoryIds.split(",");
		listCategoryId = new ArrayList<Integer>();
		for (int i = 0; i < cats.length; i++) {
			try {
				listCategoryId.add(Integer.valueOf(cats[i]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public String getZipFolderName() {
		return zipFolderName;
	}

	public void setZipFolderName(String zipFolderName) {
		this.zipFolderName = zipFolderName;
	}

	private int bookId;
	private String title;
	private String description;
	private String coverUrl;
	private int number_comments;
	private int number_likes;
	private String categories;
	private String categoryIds;
	private String zipFolderName;
	private boolean isAutoSlide;

	private ArrayList<Integer> listCategoryId;
	private ArrayList<String> listCategory;

	public ArrayList<Integer> getListCategoryId() {
		return listCategoryId;
	}

	public ArrayList<String> getListCategory() {
		return listCategory;
	}

	public boolean isAutoSlide() {
		return isAutoSlide;
	}

	public void setAutoSlide(boolean isAutoSlide) {
		this.isAutoSlide = isAutoSlide;
	}

}
