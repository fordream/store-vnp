package org.com.cnc.rosemont._interface;

import org.com.cnc.rosemont.database.table.RosemontTable;

public interface IActivity {
	public void clearActivity();

	public void gotoAdverse();

	public void gotoProductList();

	public void gotoCatogoryList();

	public void gotoIndicationList();

	public void gotoProductDetail(String id, String idRow);

	public void gotoProductListBy(boolean isCategory, String data);

	public void gotoCalculator(RosemontTable rosemontTable, int index,
			boolean isShowBantBack);

	public void gotoCalculator(boolean isShowBack);

	public void gotoSearch();

	public void gotoSearch(String text);

	public void gotoHome();

	public void onBack1();

	public void gotoCategory_Indication_Detail(int type, String data);

	public void callWeb(final String url, int res_message);

	public void gotoSumary(String url);

	public void gotoProductListCalculator(RosemontTable rosemontTable);

	public void updateCalculator(String id, String idTable, String strength);

	public void gotoIntro();

	public void gotoAbout();

	public void gotoManual();

	public void gotoCrushingMedicines();

	public void gotoCrushingConsumer();

	public void gotoCrushingTheEquality();

	public void gotoCrushingTheHuman();

	public void gotoViewOnlinePDF(String pdfName);

	public void reset();

	public void showKeyBoard();
}
