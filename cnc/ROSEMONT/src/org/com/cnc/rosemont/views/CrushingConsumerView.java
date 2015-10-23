package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CrushingConsumerView extends LinearLayout implements IView{
	// private HeaderView headerView;
	//public AnimationSlide animationSlide=new AnimationSlide();
	public CrushingConsumerView(Context context) {
		super(context);
		config(R.layout.crushing_consumer);
	}

	public CrushingConsumerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.crushing_consumer);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key15);
		setUrl();

	}

	public void setUrl() {
		WebViewController controller = (WebViewController) findViewById(R.id.webViewController1);
		// String path = CommonApp.PATH + "consumer.pdf";
		// CommonApp.startPDF(path, getContext());
		// controller.loadUrl("http://www.legislation.gov.uk/ukpga/1987/43/pdfs/ukpga_19870043_en.pdf");
		
		controller.loadUrl("https://docs.google.com/gview?embedded=true&url=http://www.legislation.gov.uk/ukpga/1987/43/pdfs/ukpga_19870043_en.pdf");
		
		//controller.loadUrl("https://docs.google.com/gview?embedded=true&url=http://rosemontdev.pslink.org.uk/components/com_product/media/100_1269954016_Certificate_Of_Analysis.pdf");

	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
