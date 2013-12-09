package me.jko.discogs;

import java.util.ArrayList;

import me.jko.discogs.models.CollectionRelease;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

/*
 * https://github.com/survivingwithandroid/Surviving-with-android/blob/master/EndlessAdapter/src/com/survivingwithandroid/endlessadapter/EndlessListView.java
 */

public class CollectionListView extends ListView implements OnScrollListener {
	
    private View footer;
    private boolean isLoading;
    private CollectionListListener listener;
    private CollectionListAdapter adapter;
	

    public CollectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);        
        this.setOnScrollListener(this);
    }
    
    public CollectionListView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	this.setOnScrollListener(this);
    }
    
    public CollectionListView(Context context) {
    	super(context);
    	this.setOnScrollListener(this);
    }
    
    public void setListener(CollectionListListener listener) {
    	this.listener = listener;
    }
    
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null) {
        	 return;
        }
           
    
	    if (getAdapter().getCount() == 0) {
	    	return;
	    }
	    
	    if (view == null || firstVisibleItem == 0 || visibleItemCount == 0 || totalItemCount == 0) {
	    	
	    	return;
	    }
	            
	    
	    int l = visibleItemCount + firstVisibleItem;
	    if (l >= totalItemCount && !isLoading) {
	            // It is time to add new data. We call the listener
	            this.addFooterView(footer);
	            isLoading = true;
	            listener.loadData();
	    }
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// don't need this
	}
	
	public void setLoadingView(int resId) {
		LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footer = (View) inflater.inflate(resId,  null);
		this.addFooterView(footer);
	}
	
    public void setAdapter(CollectionListAdapter adapter) {                
        super.setAdapter(adapter);
        this.adapter = adapter;
        this.removeFooterView(footer);
    }
	
    public void addNewData(ArrayList<CollectionRelease> data) {
        
        this.removeFooterView(footer);
        
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }
	
    public CollectionListListener setListener() {
        return listener;
    }
    
    public static interface CollectionListListener {
        public void loadData() ;
    }
}
