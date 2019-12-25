package com.spisoft.spbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.ContentLoadingProgressBar;

public class spView extends RelativeLayout {
    private View rootView;
    private LinearLayout mIT;
    private ImageView mIcon;
    private TextView mText;
    private ContentLoadingProgressBar mProgress;
    private Drawable mIconNormal , mIconProgress , mIconSuccess , mIconFail ;
    private String mTextNormal = "Sign in", mTextProgress = "Progress", mTextSuccess = "Success", mTextFail = "Fail";
    private int mColorNormal = R.color.colorNormal, mColorProgress = R.color.colorProgress,
            mColorSuccess = R.color.colorSuccess, mColorFail = R.color.colorFail;
    private View mViewBase;
    private boolean mColorSet = false;
    private int mModeStyle = 0;

    public spView(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public spView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public spView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public spView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //-------------------------------------------------------

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        rootView = inflate(context, R.layout.sp_btn_base_view, this);

        mViewBase = rootView.findViewById(R.id.viewBase);
        mIT = rootView.findViewById(R.id.vIT);
        mIcon = rootView.findViewById(R.id.vIcon);
        mText = rootView.findViewById(R.id.vText);
        mProgress = rootView.findViewById(R.id.vProgress);

//        mIconNormal = context.getResources().getDrawable(R.drawable.ic_account_circle_deep_orange_a700_24dp);
//        mIconProgress = context.getResources().getDrawable(R.drawable.ic_autorenew_yellow_a400_24dp);
//        mIconSuccess = context.getResources().getDrawable(R.drawable.ic_check_circle_green_a700_24dp);
//        mIconFail = context.getResources().getDrawable(R.drawable.ic_report_red_a700_24dp);

        if(attrs != null){
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.spView, 0, 0);

            mColorSet = typedArray.getBoolean(R.styleable.spView_ColorSet, false);

            int atModeIconPosition = typedArray.getInt(R.styleable.spView_ModeIconPosition, 0);
            switch (atModeIconPosition){
                case 0:
                    mIT.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                    mIT.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 1:
                    mIT.setLayoutDirection(LAYOUT_DIRECTION_RTL);
                    mIT.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 2:
                    mIT.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                    mIT.setOrientation(LinearLayout.VERTICAL);
                    break;
                case 3:
                    mIT.setLayoutDirection(LAYOUT_DIRECTION_RTL);
                    mIT.setOrientation(LinearLayout.VERTICAL);
                    break;
            }

            mModeStyle = typedArray.getInt(R.styleable.spView_ModeStyle, 0);

            Drawable atBackground = typedArray.getDrawable(R.styleable.spView_SrcBackground);
            if(atBackground != null) mViewBase.setBackground(atBackground);

            Drawable atIcon = typedArray.getDrawable(R.styleable.spView_IconNormal);
            if(atIcon != null) {
                mIcon.setImageDrawable(atIcon);
                mIconNormal = atIcon;
                mIconProgress = typedArray.getDrawable(R.styleable.spView_IconProgress);
                mIconSuccess = typedArray.getDrawable(R.styleable.spView_IconSuccess);
                mIconFail = typedArray.getDrawable(R.styleable.spView_IconFail);

//                int atIconSize = typedArray.getInt(R.styleable.spView_IconSize, 80);
                int atIconSize = (int) typedArray.getDimension(R.styleable.spView_IconSize, 50);
//                iIcon.getLayoutParams().width = (int) typedArray.getDimension(R.styleable.SpaSwitch_SwitchSize, R.dimen.sps_lpr_sz_30);
                ViewGroup.LayoutParams params = mIcon.getLayoutParams();
                params.height = atIconSize;
                params.width = atIconSize;
                mIcon.setLayoutParams(params);

                int atIconPadding = typedArray.getInt(R.styleable.spView_IconPadding, 10);
                mIcon.setPadding(atIconPadding, atIconPadding, atIconPadding, atIconPadding);
            }

            String atTextNormal = typedArray.getString(R.styleable.spView_TextNormal);
            if(atTextNormal != null) mTextNormal = atTextNormal;
            String atTextProgress = typedArray.getString(R.styleable.spView_TextProgress);
            if(atTextProgress != null) mTextProgress = atTextProgress;
            String atTextSuccess = typedArray.getString(R.styleable.spView_TextSuccess);
            if(atTextSuccess != null) mTextSuccess = atTextSuccess;
            String atTextFail = typedArray.getString(R.styleable.spView_TextFail);
            if(atTextFail != null) mTextFail = atTextFail;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mColorNormal = typedArray.getColor(R.styleable.spView_ColorNormal, context.getColor(mColorNormal));
                mColorProgress = typedArray.getColor(R.styleable.spView_ColorProgress, context.getColor(mColorProgress));
                mColorSuccess = typedArray.getColor(R.styleable.spView_ColorSuccess, context.getColor(mColorSuccess));
                mColorFail = typedArray.getColor(R.styleable.spView_ColorFail, context.getColor(mColorFail));
            }

        }

//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initClick(v);
//            }
//        });
        setProgress(context,0);
    }

//    private void initClick(View v) {
//        setMode(1);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMinimumHeight(this.getHeight());
        this.setMinimumWidth(this.getWidth());
    }

    public spView setProgress(Context mContext, int pMode){
        int syncColor = Color.WHITE;
        switch (pMode){
            case 0: //normal
                syncColor = mColorNormal;
                mText.setText(mTextNormal);
                mIcon.setImageDrawable(mIconNormal);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                break;
            case 1: //pending
                syncColor = mColorProgress;
                mIcon.setImageDrawable(mIconProgress);
                mText.setText(mTextProgress);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                mProgress.setVisibility(VISIBLE);
                break;
            case 2: //success
                syncColor = mColorSuccess;
                mIcon.setImageDrawable(mIconSuccess);
                mText.setText(mTextSuccess);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                mProgress.setVisibility(GONE);
                break;
            case -1: //fail
                syncColor = mColorFail;
                mIcon.setImageDrawable(mIconFail);
                mText.setText(mTextFail);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                break;
        }

        if(mColorSet) {
            GradientDrawable backgroundGradient = (GradientDrawable) mViewBase.getBackground();
            backgroundGradient.setColor(syncColor);
        }

        return this;
    }
}