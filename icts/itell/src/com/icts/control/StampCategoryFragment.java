package com.icts.control;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StampCategoryFragment extends ListFragment {
	OnStampCategorySelectedListener mCallback;
	public interface OnStampCategorySelectedListener{
		public abstract void onStampCategorySelected(int position);
	}
	
	private  ArrayList<String> arrUrl;
	private int resourceID;
	
	public StampCategoryFragment() {
		super();
	}
	
	public StampCategoryFragment(ArrayList<String> arr, int resourceID){
		super();
		this.arrUrl = arr;
		this.resourceID = resourceID;
	}
	
	public StampCategoryFragment(ArrayList<String> arr, int resourceID, int resourceTvId){
		super();
		this.arrUrl = arr;
		this.resourceID = resourceID;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(), resourceID, -1));
	}
	
	@Override
	public void onStart() {
		super.onStart();
		 //if (getFragmentManager().findFragmentById(R.id.category_stamp_fragment) != null) {
	    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	     //}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		 // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnStampCategorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStampCategorySelectedListener");
        }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		mCallback.onStampCategorySelected(position);
		getListView().setItemChecked(position, true);
	}
}
