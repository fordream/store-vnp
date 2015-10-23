package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideLeftListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListView;
import com.icts.itel.MainActivity;
import com.icts.itel.R;
import com.icts.utils.Constant;
import com.icts.utils.DownloadImage;
import com.icts.utils.DownloadImageTask;

public class BlogListSetting {
	private Context mContext;
	private MainActivity mainActivity;
	private LayoutInflater layoutInflater;
	private View mView = null;
	private Button btnBack;
	private SwipListView mListView;
	private BlogAdapter mAdapter;
	public ArrayList<BlogObject> arrBlockList = new ArrayList<BlogObject>();
	public final static Integer[] imageOutResIds = new Integer[] {
        R.drawable.list_outer_tab_1, R.drawable.list_outer_tab_2, R.drawable.list_outer_tab_3,
        R.drawable.list_outer_tab_4, R.drawable.list_outer_tab_5, R.drawable.list_outer_tab_6};
	public final static Integer[] imageInnerResIds = new Integer[] {
        R.drawable.list_inner_tab_1, R.drawable.list_inner_tab_2, R.drawable.list_inner_tab_3,
        R.drawable.list_inner_tab_4, R.drawable.list_inner_tab_5, R.drawable.list_inner_tab_6};
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				System.out.println("size:  " + arrBlockList.size());
				mAdapter = new BlogAdapter(mContext,
						R.layout.layout_blocklist_item, R.id.tv3, arrBlockList);
				mAdapter.setOnItemSlideLeftListener(new OnItemSlideLeftListener() {

					@Override
					public void onItemSlideLeft(View v) {
						// TODO Auto-generated method stub

					}

				});
				mAdapter.setOnItemSlideRightListener(new OnItemSlideRightListener() {

					@Override
					public void onItemSlideRight(View v) {
						// TODO Auto-generated method stub

					}

				});
				mListView.setAdapter(mAdapter);
				mListView.setClipChildren(false);
				mListView.setClipToPadding(false);
				mListView.setDividerHeight(0);
				mAdapter.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {

					@Override
					public void onItemSlideChanged(View v, int direction) {
						mAdapter.setBusy(true);
						// TODO Auto-generated method stub
						if (direction == SwipAdapter.LEFT) {
							ListAdapter adap = mListView.getAdapter();
							int count = adap.getCount();
							BlogObject obj = null;
							for (int i = 0; i < count - 1; i++) {
								obj = (BlogObject) adap.getItem(i);
							}
							ProfileOther profile = new ProfileOther(mContext,
									mainActivity, obj);
							mainActivity.changeViewLL(profile.getmView());
						} else if (direction == SwipAdapter.RIGHT) {
							PrivateManagement setting = new PrivateManagement(
									mContext, mainActivity);
							mainActivity.changeViewLL(setting.getmView());
						}
					}

				});
			}
		};
	};

	public BlogListSetting(Context context, MainActivity mActivity) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mainActivity = mActivity;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.layout_blocklist, null);
		initViewComponent();
		// getBlogList(ItelApplication.user_id, ItelApplication.uuid);
		getBlogList(ItelApplication.user_id, ItelApplication.uuid);
	}

	void initViewComponent() {
		btnBack = (Button) mView.findViewById(R.id.btnBack);
		mListView = (SwipListView) mView.findViewById(R.id.listView1);
		btnBack.setOnClickListener(onClickComponent);
	}

	OnClickListener onClickComponent = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnBack:
				PrivateManagement pri = new PrivateManagement(mContext,
						mainActivity);
				mainActivity.changeViewLL(pri.getmView());
				break;
			default:
				break;
			}
		}
	};

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	private void getBlogList(final int user_id, final String uuid) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject jObject = Constant
						.getJSONfromURL(Constant.BLOCK_LIST + user_id
								+ Constant.uuid + uuid);
				System.out.println("url: " + Constant.BLOCK_LIST + user_id
						+ Constant.uuid + uuid);
				try {
					JSONObject blockList = jObject.getJSONObject("block_list");
					Iterator keys = blockList.keys();
					BlogObject blockObj;
					while (keys.hasNext()) {
						blockObj = new BlogObject();
						String currentDynamicKey = (String) keys.next();
						JSONObject currentDynamicValue = blockList
								.getJSONObject(currentDynamicKey);
						blockObj.setFriend_id(currentDynamicValue.getInt("id"));
						blockObj.setNick(currentDynamicValue.getString("nick"));
						blockObj.setAvatar(currentDynamicValue
								.getString("avatar"));
						arrBlockList.add(blockObj);
					}
					mHandler.sendEmptyMessage(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	class BlogAdapter extends SwipAdapter<BlogObject> {

		private ArrayList<BlogObject> arrObj;

		public BlogAdapter(Context context, List<BlogObject> objects) {
			super(context, R.layout.layout_blocklist_item, objects);
		}

		public BlogAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<BlogObject> objects) {
			super(context, resource, textViewResourceId);
			mContext = context;
			this.arrObj = objects;
		}

		@Override
		public BlogObject getItem(int position) {
			return this.arrObj.get(position);
		}

		@Override
		public int getCount() {
			return this.arrObj.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			BlogObject blogObj = getItem(position);
			RowWrapper wrapper;
			if (v == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.layout_blocklist_item, null);
				wrapper = new RowWrapper();
				wrapper.timeStamp = (ImageView) convertView
						.findViewById(R.id.imgTimestamp);
				wrapper.rlDelete = (RelativeLayout)convertView.findViewById(R.id.rlDelete);
				wrapper.llItem = (LinearLayout)convertView.findViewById(R.id.llItem);
				wrapper.tvName = (TextView) convertView.findViewById(R.id.tv3);
				convertView.setTag(wrapper);
				v = convertView;
				handleEvent(v);
			} else {
				wrapper = (RowWrapper) v.getTag();
				if (wrapper == null) {
					wrapper = new RowWrapper();
					wrapper.timeStamp = (ImageView) v
							.findViewById(R.id.imgTimestamp);
					wrapper.rlDelete = (RelativeLayout)v.findViewById(R.id.rlDelete);
					wrapper.llItem = (LinearLayout)v.findViewById(R.id.llItem);
					wrapper.tvName = (TextView) v.findViewById(R.id.tv3);
					v.setTag(wrapper);
					handleEvent(v);
				}

			}
	    	wrapper.rlDelete.setBackgroundResource(imageOutResIds[position%6]);
	    	wrapper.llItem.setBackgroundResource(imageInnerResIds[position%6]);
			wrapper.tvName.setText(blogObj.getNick());
			/*DownloadImage down = new DownloadImage(wrapper.timeStamp, 60, 60,
					mContext);
			down.execute(blogObj.getAvatar());*/
			if (blogObj.getAvatar()!=null&!blogObj.getAvatar().trim().equalsIgnoreCase("")){
				/*DownloadImageTask task = new DownloadImageTask(wrapper.timeStamp, 60, 60,
																mContext, blogObj.getAvatar());
				ItelApplication.submitOther(task);*/
				DownloadImage down = new DownloadImage(wrapper.timeStamp, 60, 60,
						mContext);
				down.execute(blogObj.getAvatar());
			}
			return v;
		}
	}

	class RowWrapper {
		private ImageView timeStamp;
		private TextView tvName;
		private RelativeLayout rlDelete;
		private LinearLayout llItem;
	}

	class BlogObject {
		private int friend_id;
		private String nick;
		private String avatar;

		public int getFriend_id() {
			return friend_id;
		}

		public void setFriend_id(int friend_id) {
			this.friend_id = friend_id;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

	}
}
