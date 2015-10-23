package vn.com.vega.music.view.custom;

import vn.com.vega.chacha.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class SegmentedRadioGroup extends RadioGroup {

	public SegmentedRadioGroup(Context context) {
		super(context);
	}

	public SegmentedRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		changeButtonsImages();
	}

	private void changeButtonsImages() {
		int count = super.getChildCount();
		if (count > 1) {
			super.getChildAt(0).setBackgroundResource(
					R.drawable.selector_subtab_left);
			for (int i = 1; i < count - 1; i++) {
				super.getChildAt(i).setBackgroundResource(
						R.drawable.selector_subtab_center);
			}
			super.getChildAt(count - 1).setBackgroundResource(
					R.drawable.selector_subtab_right);
		}
	}
}
