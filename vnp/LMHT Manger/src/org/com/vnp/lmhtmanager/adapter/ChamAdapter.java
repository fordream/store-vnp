package org.com.vnp.lmhtmanager.adapter;

import java.util.List;

import org.com.vnp.lmhtmanager.R;

import android.content.Context;
import android.widget.ImageView;

import com.vnp.core.base.callback.CallBack;
import com.vnp.core.base.callback.ExeCallBack;
import com.vnp.core.base.view.CustomLinearLayoutView;
import com.vnp.core.v2.BaseAdapter;

public class ChamAdapter extends BaseAdapter {

	public ChamAdapter(Context context, List<Object> lData) {
		super(context, lData);
	}

	@Override
	public boolean isShowHeader(int position) {
		return false;
	}

	@Override
	public CustomLinearLayoutView getView(Context context, Object data) {
		return new ChamView(context);
	}

	private class ChamView extends CustomLinearLayoutView {

		public ChamView(Context context) {
			super(context);
			init(R.layout.itemcham);
		}

		@Override
		public void showHeader(boolean isShowHeader) {

		}

		@Override
		public void setGender() {
			dto.Champion.Champion champion = (dto.Champion.Champion) getData();
			final long id = champion.getId();

			ImageView cham_img = getView(R.id.cham_img);
			// ImageLoaderUtils.getInstance(getContext()).DisplayImage(champion.,
			// imageView);

			CallBack back = new CallBack() {

				@Override
				public void onCallBack(Object object) {
					if (object != null) {
						//TextView cham_View = getView(R.id.cham_name);
					//	Champion champion2 = (Champion) object;
					//	cham_View.setText(champion2.getName());
					}
				}

				@Override
				public Object execute() {
					//try {
						//return MainActivity.RIOT_API.getDataChampion((int) id);
					//} catch (RiotApiException e) {
						return null;
					//}
				}
			};

			new ExeCallBack().executeAsynCallBack(back);
		}
	}
}
