package com.cf360.popupwindow;


import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cf360.R;
import com.cf360.adapter.TextAdapter;

public class ViewLeft extends RelativeLayout implements ViewBaseAction {
    //,OnPositionListener
    private ListView mListView;
    public OnSelectListener mOnSelectListener;
    private Context mContext;
    private int clickPosition;
    //private MyBaseAdapter myBaseAdapter;
    private ArrayList<String> arrayList;
    private TextAdapter adapter;

    public ViewLeft(Context context, ArrayList<String> arrayList) {
        super(context);
        this.arrayList = arrayList;
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ViewLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new TextAdapter(context, arrayList, R.drawable.abcd, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(13);

        adapter.setSelectedPositionNoNotify(0);
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mOnSelectListener != null) {
                    mOnSelectListener.getValue(view, position);
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(View view, int position);
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }


    //状态hui
    public void ReturnState() {
        //	Toast.makeText(mContext,"1", 0).show();
        clickPosition = 0;
        adapter.setSelectedPositionNoNotify(0);
    }

}
