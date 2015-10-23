package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ViewOnlinePDF extends LinearLayout implements IView {
	// private HeaderView headerView;
	String pdfName;
	//private AnimationSlide animationSlide=new AnimationSlide();
	public ViewOnlinePDF(Context context, String mPdfName) {
		super(context);
		pdfName = mPdfName;
		config(R.layout.view_pdf_online);
	}

	public ViewOnlinePDF(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.view_pdf_online);
	}

	private void config(int resource_layouy) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key19);
		setUrl();

	}

	public void setUrl() {
		WebViewController controller = (WebViewController) findViewById(R.id.webViewController1);

		controller
				.loadUrl("https://docs.google.com/gview?embedded=true&url=http://rosemontdev.pslink.org.uk/components/com_product/media/"
						+ pdfName);

	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
