package org.com.cnc.rosemont.database.table;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.database.table.CommonTable;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.items.ItemSearch;

import android.util.Log;

public class RosemontTable extends CommonTable {
	public static final String CATEGORY_NAME = "cat_name";
	public static final String TRADE_NAME = "trade_name";
	public static final String MAIN_INDICATIONS = "main_indications";
	public static final String ID_PRODUCT = "product_id";
	public static final String TITLE = "title";
	public static final String NHS_RETAIL_PRICE = "NHS_retail_price";
	public static final String GENERIC_NAME = "generic_name";
	public static final String PRODUCT_TYPE = "product_type";
	public static final String VISCOSITY = "viscosity";
	public static final String MODE = "mode_of_action";
	public static final String PATIENT_INFO = "patient_info";
	public static final String PUBLISHED = "published";
	public static final String STRENGTH = "strength";
	private List<String> lText = new ArrayList<String>();
	private List<String> txtShow = new ArrayList<String>();

	public RosemontTable() {
		setTableName("rosemonts");
		addColoumName("id");
		addColoumName(GENERIC_NAME);
		addColoumName(MAIN_INDICATIONS);
		addColoumName("certificate_of_analysis");
		addColoumName("product_name");
		addColoumName("product_name_2");
		addColoumName("bnf_cat_id");
		addColoumName(PRODUCT_TYPE);
		addColoumName(MODE);
		addColoumName("published");
		addColoumName("variant");
		addColoumName("product_image");
		addColoumName("datecreate");
		addColoumName("dateupdate");
		addColoumName("tariff_price");

		addColoumName("datechange");

		// category name
		addColoumName(CATEGORY_NAME);
		addColoumName(TITLE);
		addColoumName("metadescription");
		addColoumName("metakeywords");
		// product ID
		addColoumName(ID_PRODUCT);
		addColoumName("ordering");
		addColoumName(TRADE_NAME);
		// Strength
		addColoumName("strength");
		addColoumName("pack_size");
		// color
		addColoumName("colour");
		addColoumName("favour");
		addColoumName("consistency");
		addColoumName("storage_conditions");
		addColoumName("sucrose_free");
		addColoumName("lactose_free");
		addColoumName("diabetic_free");
		addColoumName("gluten_free");
		addColoumName(VISCOSITY);
		addColoumName("alcohon_concentration");
		addColoumName("sodium_level");
		addColoumName("glucose_free");
		addColoumName("sorbitol_free");
		addColoumName("fructose_free");
		addColoumName("aspartame_free");
		addColoumName("tartrazine_free");
		addColoumName("arachis_oil_free");
		addColoumName("sesame_oil_free");
		addColoumName("suitable_for_kosher");
		addColoumName("suitable_for_halal");
		addColoumName("suitable_for_vegan");
		addColoumName("PIP_code");
		addColoumName("EAN_code");
		addColoumName("shelf_life");
		// retail price
		addColoumName(NHS_RETAIL_PRICE);

		addColoumName(PATIENT_INFO);
		addColoumName("patient_info_large");
		addColoumName("patient_info_audio");
		addColoumName("summary_of_product");
		addColoumName("notes");

		// text view
		addButtonShow();

		addTextShow();
	}

	private void addButtonShow() {
		lText.add("Sucrose_free");
		lText.add("lactose_free");
		lText.add("diabetic_free");
		lText.add("gluten_free");
		lText.add("glucose_free");
		lText.add("sorbitol_free");
		lText.add("fructose_free");
		lText.add("aspartame_free");
		lText.add("tartrazine_free");
		lText.add("arachis_oil_free");
		lText.add("sesame_oil_free");
		lText.add("suitable_for_kosher");
		lText.add("suitable_for_halal");
		lText.add("suitable_for_vegan");

	}

	private void addTextShow() {
		txtShow.add("Sucrose_free");
		txtShow.add("lactose_free");
		txtShow.add("diabetic_free");
		txtShow.add("gluten_free");
		txtShow.add("glucose_free");
		txtShow.add("sorbitol_free");
		txtShow.add("fructose_free");
		txtShow.add("aspartame_free");
		txtShow.add("tartrazine_free");
		txtShow.add("arachis_oil_free");
		txtShow.add("sesame_oil_free");
		txtShow.add("suitable_for_kosher");
		txtShow.add("suitable_for_halal");
		txtShow.add("suitable_for_vegan");
		txtShow.add("pack_size");
		txtShow.add("colour");
		txtShow.add("favour");
		txtShow.add("PIP_code");
		txtShow.add("EAN_code");
		txtShow.add(NHS_RETAIL_PRICE);
		txtShow.add(PATIENT_INFO);
		txtShow.add("Sucrose_free");
		txtShow.add("lactose_free");
		txtShow.add("diabetic_free");
		txtShow.add("gluten_free");
		txtShow.add(VISCOSITY);
		txtShow.add("alcohon_concentration");
		txtShow.add("sodium_level");
		txtShow.add("glucose_free");
		// txtShow.add("product_image");
		txtShow.add("sorbitol_free");
		txtShow.add("fructose_free");
		txtShow.add("aspartame_free");
		txtShow.add("tartrazine_free");
		txtShow.add("arachis_oil_free");
		txtShow.add("sesame_oil_free");
		txtShow.add("suitable_for_kosher");
		txtShow.add("suitable_for_halal");
		txtShow.add("suitable_for_vegan");
		txtShow.add(PATIENT_INFO);
	}

	public String getId(int row) {
		int index = getIndexColumns(ID_PRODUCT);
		return getRow(row).get(index);
	}

	public String getGenericName(int row) {
		int index = getIndexColumns(GENERIC_NAME);
		return getRow(row).get(index);
	}

	public String getContentDetail(int row) {
		String retail = getValue(row, NHS_RETAIL_PRICE);
		String tariff = getValue(row, "tariff_price");
		float margin = 0;
		margin = CommonApp.per(retail, tariff);
		if (margin == 0) {
			return "Retail Price : £" + retail;
		} else {
			return "Retail Price : £" + retail + " Tariff Price : £" + tariff
					+ " Margin : " + margin + "%";
		}

	}

	public String getCategoryName(int row) {
		int index = getIndexColumns(CATEGORY_NAME);
		return getRow(row).get(index);
	}

	public String getIndicationName(int row) {
		int index = getIndexColumns(MAIN_INDICATIONS);

		return getRow(row).get(index);
	}

	public List<ItemSearch> getCategoryList(String data) {
		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		for (int i = 0; i < sizeOfRow(); i++) {
			List<String> row = getRow(i);
			int index = getIndexColumns(RosemontTable.CATEGORY_NAME);
			if (!data.equals(row.get(index))) {
				continue;
			}

			ItemSearch item = new ItemSearch();

			item.setStrength(getValue(i, "strength"));
			item.setIdTable(getIdTable(i));
			int index_id = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.ID_PRODUCT);
			item.setId(row.get(index_id));

			int title = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.GENERIC_NAME);
			item.setTxtHeader(row.get(title));

			// add
			item.setId(getId(i));
			item.setIdTable(getIdTable(i));
			// item.setTxtHeader(getGenericName(i) + " " + getStreng(i));
			item.setTxtHeader(getGenericName(i));
			item.setTxtStrength("Strength - " + getStreng(i));
			item.setTxtContent(getContentDetail(i));
			lItemSearchs.add(item);

			String product_type = getValue(i, PRODUCT_TYPE);
			item.setSpec("0".equals(product_type));

			boolean check = false;
			for (int j = 0; j < lItemSearchs.size(); j++) {
				ItemSearch itemSearch = lItemSearchs.get(j);
				if (item.getId().equals(itemSearch.getId())) {
					check = true;
					break;
				}
			}

			if (!check) {
				lItemSearchs.add(item);
			}
		}
		return lItemSearchs;
	}

	public List<ItemSearch> getIndicationsList(String data) {
		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		for (int i = 0; i < sizeOfRow(); i++) {
			List<String> row = getRow(i);
			int index = getIndexColumns(RosemontTable.MAIN_INDICATIONS);
			if (!row.get(index).contains(data)) {
				continue;
			}

			ItemSearch item = new ItemSearch();
			item.setIdTable(getIdTable(i));
			int index_id = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.ID_PRODUCT);
			item.setId(row.get(index_id));

			int title = CommonApp.ROSEMONT
					.getIndexColumns(RosemontTable.GENERIC_NAME);
			item.setTxtHeader(row.get(title));
			item.setStrength(getValue(i, "strength"));
			// add
			item.setId(getId(i));
			item.setIdTable(getIdTable(i));
			// item.setTxtHeader(getGenericName(i) + " " + getStreng(i));
			item.setTxtHeader(getGenericName(i));
			item.setTxtStrength("Strength - " + getStreng(i));
			item.setTxtContent(getContentDetail(i));

			// TODO
			String product_type = getValue(i, PRODUCT_TYPE);
			item.setSpec("0".equals(product_type));
			Log.i("SPEC", item.isSpec() + "");
			// lItemSearchs.add(item);
			lItemSearchs.add(item);

			// boolean check = false;
			// for (int j = 0; j < lItemSearchs.size(); j++) {
			// ItemSearch itemSearch = lItemSearchs.get(j);
			// if (item.getId().equals(itemSearch.getId())) {
			// check = true;
			// break;
			// }
			// }
			//
			// if (!check) {
			// lItemSearchs.add(item);
			// }
		}
		return lItemSearchs;
	}

	public List<ItemSearch> search(String strSearch, boolean isAll) {
		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		if (!isAll && "".equals(strSearch.trim())) {
			return lItemSearchs;
		}
		String strHoa = strSearch.toUpperCase();
		for (int i = 0; i < sizeOfRow(); i++) {
			if (isAll
					|| Common.contains(strSearch, getGenericName(i)
							.toUpperCase())
					|| Common.contains(strSearch, getIndicationName(i)
							.toUpperCase())
					|| Common.contains(strSearch, getCategoryName(i)
							.toUpperCase())
					|| Common.contains(strHoa, getGenericName(i).toUpperCase())
					|| Common.contains(strHoa, getIndicationName(i)
							.toUpperCase())
					|| Common
							.contains(strHoa, getCategoryName(i).toUpperCase())) {
				ItemSearch item = new ItemSearch();
				item.setId(getId(i));
				item.setIdTable(getIdTable(i));
				// String text = getGenericName(i) + " " + getStreng(i);
				// item.setTxtHeader(text);
				item.setTxtHeader(getGenericName(i));
				item.setTxtStrength("Strength - " + getStreng(i));
				item.setTxtContent(getContentDetail(i));

				item.setStrength(getValue(i, "strength"));
				String product_type = getValue(i, PRODUCT_TYPE);
				item.setSpec("0".equals(product_type));

				lItemSearchs.add(item);
			}
		}

		return lItemSearchs;
	}

	public String getIdTable(int row) {
		int index = getIndexColumns("id");
		return getRow(row).get(index);
	}

	public String getStreng(int row) {
		int index = getIndexColumns("strength");
		return getRow(row).get(index);
	}

	public String getNameFile(int row) {
		try {
			int index = getIndexColumns("certificate_of_analysis");
			return getRow(row).get(index);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean contains(String string) {
		for (int i = 0; i < lText.size(); i++) {
			if (lText.get(i).equals(string)) {
				return true;
			}
		}
		return false;
	}

	public List<ItemSearch> getDetailList(int index) {
		List<String> lHeader = new ArrayList<String>();
		lHeader.addAll(getHeaderList());
		List<String> row = new ArrayList<String>();
		row.addAll(getRow(index));
		String product_type = getValue(index, PRODUCT_TYPE);

		// special
		if ("0".equals(product_type)) {
			lHeader.remove(PATIENT_INFO);
		}

		List<ItemSearch> lItemSearchs = new ArrayList<ItemSearch>();
		for (int i = 0; i < lHeader.size(); i++) {

			if (txtShow.contains(lHeader.get(i))) {

				ItemSearch item = new ItemSearch();
				item.setTxtHeader(lHeader.get(i).replace("_", " "));
				item.setChecked(contains(lHeader.get(i)));

				if (NHS_RETAIL_PRICE.equals(lHeader.get(i))) {
					item.setTxtContent("£" + row.get(i));
				} else {
					item.setTxtContent(row.get(i));
				}

				lItemSearchs.add(item);
			}
		}

		return lItemSearchs;
	}

	public RosemontTable createRosemontable(String id) {
		RosemontTable rosemontTable = new RosemontTable();
		for (int i = 0; i < sizeOfRow(); i++) {
			if (id != null && id.equals(getValue(i, ID_PRODUCT))) {
				rosemontTable.addRow(getRow(i));
			}

		}
		return rosemontTable;
	}

	public int getIndex(String id_row) {
		for (int i = 0; i < sizeOfRow(); i++) {
			if (id_row != null && id_row.equals(getValue(i, "id"))) {
				return i;
			}
		}
		return 0;
	}

	public void addAll(RosemontTable table) {
		for (int i = 0; i < table.sizeOfRow(); i++) {
			addRow(table.getRow(i));
		}
	}

	public boolean isExits(String idRow) {
		for (int i = 0; i < sizeOfRow(); i++) {
			if (idRow.equals(getValue(i, "id"))) {
				return true;
			}
		}

		return false;
	}

	public RosemontTable searchByName(String name) {

		RosemontTable table = new RosemontTable();
		for (int i = 0; i < sizeOfRow(); i++) {
			if (name.equals(getValue(i, GENERIC_NAME))) {
				table.addRow(getRow(i));
			}
		}
		return table;
	}
}