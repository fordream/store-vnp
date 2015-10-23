/***
  Copyright (c) 2008-2009 CommonsWare, LLC
  Portions (c) 2009 Google, Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.endless;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ListAdapter;
import java.util.concurrent.atomic.AtomicBoolean;
import com.commonsware.cwac.adapter.AdapterWrapper;

abstract public class EndlessAdapter extends AdapterWrapper {
	abstract protected boolean cacheInBackground() throws Exception;

	abstract protected void appendCachedData();

	private View pendingView = null;
	private AtomicBoolean keepOnAppending = new AtomicBoolean(true);
	private Context context;
	private int pendingResource = -1;

	public EndlessAdapter(ListAdapter wrapped) {
		super(wrapped);
	}

	public EndlessAdapter(Context context, ListAdapter wrapped,
			int pendingResource) {
		super(wrapped);
		this.context = context;
		this.pendingResource = pendingResource;
	}

	@Override
	public int getCount() {
		if (keepOnAppending.get()) {
			return (super.getCount() + 1); // one more for "pending"
		}

		return (super.getCount());
	}

	public int getItemViewType(int position) {
		if (position == getWrappedAdapter().getCount()) {
			return (IGNORE_ITEM_VIEW_TYPE);
		}

		return (super.getItemViewType(position));
	}

	public int getViewTypeCount() {
		return (super.getViewTypeCount() + 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == super.getCount() && keepOnAppending.get()) {
			if (pendingView == null) {
				pendingView = getPendingView(parent);

				new AppendTask().execute();
			}

			return (pendingView);
		}

		return (super.getView(position, convertView, parent));
	}

	protected boolean onException(View pendingView, Exception e) {
		Log.e("EndlessAdapter", "Exception in cacheInBackground()", e);

		return (false);
	}

	protected View getPendingView(ViewGroup parent) {
		if (context != null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(pendingResource, parent, false);
		}

		throw new RuntimeException(
				"You must either override getPendingView() or supply a pending View resource via the constructor");
	}

	protected Context getContext() {
		return (context);
	}

	class AppendTask extends AsyncTask<Void, Void, Exception> {
		@Override
		protected Exception doInBackground(Void... params) {
			Exception result = null;

			try {
				keepOnAppending.set(cacheInBackground());
			} catch (Exception e) {
				result = e;
			}

			return (result);
		}

		@Override
		protected void onPostExecute(Exception e) {
			if (e == null) {
				appendCachedData();
			} else {
				keepOnAppending.set(onException(pendingView, e));
			}

			pendingView = null;
			notifyDataSetChanged();
		}
	}

}