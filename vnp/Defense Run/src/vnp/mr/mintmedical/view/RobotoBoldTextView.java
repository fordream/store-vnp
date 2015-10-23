package vnp.mr.mintmedical.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.vnp.core.common.CommonAndroid;
//vnp.mr.mintmedical.view.RobotoBoldTextView
public class RobotoBoldTextView extends TextView {

	public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RobotoBoldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RobotoBoldTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
		try {
			//setTypeface(Typeface.SANS_SERIF);
			CommonAndroid.FONT.setTypefaceFromAsset(this, "RobotoSlab-Bold.ttf");
			//MintUtils.setTypeface(this);
		} catch (Exception exception) {
		}
	}
}
