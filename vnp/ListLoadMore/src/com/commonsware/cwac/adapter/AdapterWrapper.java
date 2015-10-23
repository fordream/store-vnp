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

package com.commonsware.cwac.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class AdapterWrapper extends BaseAdapter {
	private ListAdapter wrapped = null;

	public AdapterWrapper(ListAdapter wrapped) {
		super();

		this.wrapped = wrapped;

		wrapped.registerDataSetObserver(new DataSetObserver() {
			public void onChanged() {
				notifyDataSetChanged();
			}

			public void onInvalidated() {
				notifyDataSetInvalidated();
			}
		});
	}

	@Override
	public Object getItem(int position) {
		return (wrapped.getItem(position));
	}

	@Override
	public int getCount() {
		return (wrapped.getCount());
	}

	@Override
	public int getViewTypeCount() {
		return (wrapped.getViewTypeCount());
	}

	public int getItemViewType(int position) {
		return (wrapped.getItemViewType(position));
	}

	@Override
	public boolean areAllItemsEnabled() {
		return (wrapped.areAllItemsEnabled());
	}

	@Override
	public boolean isEnabled(int position) {
		return (wrapped.isEnabled(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return (wrapped.getView(position, convertView, parent));
	}

	@Override
	public long getItemId(int position) {
		return (wrapped.getItemId(position));
	}

	protected ListAdapter getWrappedAdapter() {
		return (wrapped);
	}
}