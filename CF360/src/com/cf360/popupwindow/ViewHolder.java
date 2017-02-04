package com.cf360.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewHolder {
	private final SparseArray<View> mViews;  
	private int mPosition;  
	private View mConvertView;  

	private ViewHolder(Context context, ViewGroup parent, int layoutId,  
			int position)  
	{  
		this.mPosition = position;  
		this.mViews = new SparseArray<View>();  
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,  
				false);  
		mConvertView.setTag(this);  
	}  

	public static ViewHolder get(Context context, View convertView,  
			ViewGroup parent, int layoutId, int position)  
	{  
		if (convertView == null)  
		{  
			return new ViewHolder(context, parent, layoutId, position);  
		}  
		return (ViewHolder) convertView.getTag();  
	}  

	public View getConvertView()  
	{  
		return mConvertView;  
	}  
	public <T extends View> T getView(int viewId)  
	{  
		View view = mViews.get(viewId);  
		if (view == null)  
		{  
			view = mConvertView.findViewById(viewId);  
			mViews.put(viewId, view);  
		}  
		return (T) view;  
	}  

	public ViewHolder setText(int viewId, String text)  
	{  
		TextView view = getView(viewId);  
		view.setText(text);  
		return this;  
	}  
	public ViewHolder setProgress(int viewId, String text)  
	{  
		ProgressBar view = getView(viewId);  
		view.setProgress(new Integer(text));
		return this;  
	}  

	public ViewHolder setImageResource(int viewId, int drawableId)  
	{  
		ImageView view = getView(viewId);  
		view.setImageResource(drawableId);  

		return this;  
	}  

	public ViewHolder setImageBitmap(int viewId, Bitmap bm)  
	{  
		ImageView view = getView(viewId);  
		view.setImageBitmap(bm);  
		return this;  
	}  

	public ViewHolder setImageByUrl(int viewId, String url)  
	{  
		ImageLoader.getInstance().displayImage(url,(ImageView)getView(viewId));
		return this;  
	}  

	public ViewHolder setVisibility(int viewId ,Boolean v){
		RelativeLayout view = getView(viewId);
		if(v){
			view.setVisibility(View.VISIBLE);
		}else{
			view.setVisibility(View.GONE);	
		}
		return this;  
	}
	

	public int getPosition()  
	{  
		return mPosition;  
	}  
}
