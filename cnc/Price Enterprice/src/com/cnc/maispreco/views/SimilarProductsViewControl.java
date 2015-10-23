package com.cnc.maispreco.views;

import com.cnc.maispreco.soap.data.Product;

import android.content.Context;

public class SimilarProductsViewControl extends CommonHomeViewControl {

	public SimilarProductsViewControl(Context context,Product product) {
		super(context,product);
	}
//	private ListView lVProduct;
//	private ArrayList<Product> alProduct = new ArrayList<Product>();
//	private ArrayList<Product> alProductStore = new ArrayList<Product>();
//	private ArrayAdapter<Product> adapter;
//	private int curent = 0;
//	@SuppressWarnings("unused")
//	private Context context;
//	private LoadMoreView loadMoreView;
//	public void reload(){
//		adapter.notifyDataSetChanged();
//	}
//
//	public SimilarProductsViewControl(Context context) {
//		super(context);
//		this.context = context;
//		LayoutInflater li = (LayoutInflater) this.getContext()
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		li.inflate(R.layout.homeview, this);
//		curent = ConfigurationView.page;
//		lVProduct = (ListView) findViewById(R.id.lVProducts);
//		loadMoreView = new LoadMoreView(context);
//		loadMoreView.setType(LoadMoreView.TYPE_PRODUCT);
//		lVProduct.addFooterView(loadMoreView);
//		loadMoreView.setVisibility(View.GONE);
//		adapter = new ProductApater(context, R.layout.item_product, alProduct);
//
//		lVProduct.setAdapter(adapter);
//		addReload();
//	}
//
//	private void addReload() {
//		MyCount myCount = new MyCount(15000, 1000);
//		myCount.start();
//	}
//
//	public class MyCount extends CountDownTimer {
//
//		public MyCount(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);
//		}
//
//		@Override
//		public void onFinish() {
//			adapter.notifyDataSetChanged();
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {
//			adapter.notifyDataSetChanged();
//		}
//	}
//
//	public void addProduct(String productId) {
//
//		Search search = new Search();
//		search.setToken(Common.tooken);
//		search.setSearchType("6");
//		search.setSearch(productId);
//		List<Product> temp = Product.getLProduct(search, getContext()).getlProducts();
//
//		alProduct.clear();
//
//		for (int i = 0; i < temp.size(); i++) {
//			alProduct.add(temp.get(i));
//
//		}
//	}
//
//	public void addProduct(List<Product> temp) {
//		alProduct.clear();
//		alProductStore.clear();
//		for (int i = 0; i < temp.size(); i++) {
//			alProductStore.add(temp.get(i));
//
//		}
//	}
//
//	public void notifyDataSetChanged() {
//		if (curent != ConfigurationView.page) {
//			alProduct.clear();
//			curent = ConfigurationView.page;
//		}
//
//		int index = alProduct.size();
//		if (index > 1) {
//		//	index--;
//		}
//		// if(index == 0) index = 1;
//		alProduct.clear();
//
//		for (int i = 0; i < index + ConfigurationView.page; i++) {
//			if (i < alProductStore.size()) {
//				alProduct.add(alProductStore.get(i));
//			}
//		}
//
//		if (alProduct.size() < alProductStore.size()) {
//			// Product product = new Product();
//			// product.setId("______");
//			// String loadmore = context.getResources().getString(
//			// R.string.Load_More_Products);
//			// String show = context.getResources().getString(R.string.Showing);
//			// String of = context.getResources().getString(R.string.of);
//			// String total = context.getResources().getString(
//			// R.string.total_products);
//			//
//			// product.setName(loadmore + "\n" + show + " " + alProduct.size()
//			// + " " + of + " " + alProductStore.size() + " " + total);
//			// Bitmap bitmap = BitmapFactory.decodeResource(getContext()
//			// .getResources(), R.drawable.bg1);
//			// Drawable d = new BitmapDrawable(bitmap);
//			//
//			// product.setDrawable(d);
//			// product.setMaxPrice("");
//			// product.setMaxPrice("");
//			// alProduct.add(product);
//			// }
//		loadMoreView.setData(alProduct.size() + "", alProductStore.size() + "");
//		loadMoreView.setVisibility(VISIBLE);
//	}else{
//		loadMoreView.setVisibility(GONE);
//	}
//		adapter.notifyDataSetChanged();
//	}
//
//	public ListView getlVProduct() {
//		return lVProduct;
//	}
//
//	public void setlVProduct(ListView lVProduct) {
//		this.lVProduct = lVProduct;
//	}
//
//	public ArrayList<Product> getAlProduct() {
//		return alProduct;
//	}
//
//	public void setAlProduct(ArrayList<Product> alProduct) {
//		this.alProduct = alProduct;
//	}
//
//	public ArrayAdapter<Product> getAdapter() {
//		return adapter;
//	}
//
//	public void setAdapter(ArrayAdapter<Product> adapter) {
//		this.adapter = adapter;
//	}

}