package com.cf360.popupwindow;

import java.util.ArrayList;

import com.cf360.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 菜单控件头部，封装了下拉动画，动态生成头部按钮个数
 *
 * @author
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

    private ToggleButton selectedButton;
    private ArrayList<String> mTextArray = new ArrayList<String>();
    private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
    private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();
    private Context mContext;
    private final int SMALL = 0;
    private int displayWidth;
    private int displayHeight;
    private PopupWindow popupWindow;
    private int selectPosition;

    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 根据选择的位置设置tabitem显示的值
     */
    public void setTitle(String valueText, int position) {
        if (position < mToggleButton.size()) {
            mToggleButton.get(position).setText(valueText);
        }
    }

    public void setTitle(String title) {

    }

    /**
     * 根据选择的位置获取tabitem显示的值
     */
    public String getTitle(int position) {
        if (position < mToggleButton.size() && mToggleButton.get(position).getText() != null) {
            return mToggleButton.get(position).getText().toString();
        }
        return "";
    }

    /**
     * 设置tabitem的个数和初始值
     *
     * @param textArray ListView中item对应的text值的集合..
     * @param viewArray 当前Layout中需要加入的View..
     */
    public void setValue(ArrayList<String> textArray, ArrayList<View> viewArray) {
        if (mContext == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mTextArray = textArray;
        for (int i = 0; i < viewArray.size(); i++) {
            //这里就添加了一个View..
            final RelativeLayout r = new RelativeLayout(mContext);
            int maxHeight = (int) (displayHeight * 0.7); //定义布局的高度..
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, maxHeight);
            r.addView(viewArray.get(i), rl);  //在布局中添加View并指定参数
            mViewArray.add(r);
            r.setTag(SMALL);

            //定义最上方的按钮,并在布局中添加这个按钮。并设置按钮的text
            ToggleButton tButton = (ToggleButton) inflater.inflate(R.layout.toggle_button, this, false);
            addView(tButton);
            View line = new TextView(mContext);
            line.setBackgroundResource(R.drawable.choosebar_line1);
            if (i < viewArray.size() - 1) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                addView(line, lp);
            }
            mToggleButton.add(tButton);
            tButton.setTag(i);
            tButton.setText(mTextArray.get(i));

            //用于实现当PopWindow显示时.再次点击收回PopWindow
            r.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPressBack();
                }
            });

            r.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
            tButton.setOnClickListener(new OnClickListener() {  //当按钮被点击后需要触发的监听
                @Override
                public void onClick(View view) {
                    // initPopupWindow();
                    ToggleButton tButton = (ToggleButton) view;

                    /** 如果当前点击的按钮与上次的点击不同.则设置当前的按钮处于点击状态 */
                    if (selectedButton != null && selectedButton != tButton) {
                        selectedButton.setChecked(false);
                    }
                    selectedButton = tButton;
                    selectPosition = (Integer) selectedButton.getTag();
                    /** 按钮被点击后,需要触发对应的监听事件.*/
                    startAnimation();
                    if (mOnButtonClickListener != null && tButton.isChecked()) {
                        mOnButtonClickListener.onClick(selectPosition);
                    }
                }
            });
        }
    }

    private void startAnimation() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(mViewArray.get(selectPosition), displayWidth, displayHeight);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }

        if (selectedButton.isChecked()) {
            if (!popupWindow.isShowing()) {
                showPopup(selectPosition);
            } else {
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                hideView();
            }
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                hideView();
            }
        }
    }

    private void showPopup(int position) {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.show();
        }
        if (popupWindow.getContentView() != mViewArray.get(position)) {
            popupWindow.setContentView(mViewArray.get(position));
        }
        popupWindow.showAsDropDown(this, 0, 0);
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean onPressBack() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            if (selectedButton != null) {
                selectedButton.setChecked(false);
            }
            return true;
        } else {
            return false;
        }

    }

    private void hideView() {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.hide();
        }
    }

    private void init(Context context) {
        mContext = context;
        displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void onDismiss() {
        showPopup(selectPosition);
        popupWindow.setOnDismissListener(null);
    }

    private OnButtonClickListener mOnButtonClickListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnButtonClickListener {
        public void onClick(int selectPosition);
    }

}
