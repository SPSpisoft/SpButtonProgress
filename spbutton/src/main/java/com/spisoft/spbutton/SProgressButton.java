package com.spisoft.spbutton;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class SProgressButton extends RelativeLayout {
    private View rootView;
    private RelativeLayout mIT;
    private ImageView mIcon;
    private View mIconE;
    private TextView mText;
    private CircularProgressView mProgress;
    private Drawable mIconNormal , mIconProgress , mIconSuccess , mIconFail ;
    private String mTextNormal = "Sign in", mTextProgress = "Progress", mTextSuccess = "Success", mTextFail = "Fail";
    private String mDescNormal = "", mDescProgress = "", mDescSuccess = "", mDescFail = "";
    private int mColorTextDefault = R.color.colorText, mColorNormal = R.color.colorNormal, mColorProgress = R.color.colorProgress,
            mColorSuccess = R.color.colorSuccess, mColorFail = R.color.colorFail;
    private View mViewBase;
    private boolean mColorSet = false, mColorDescSet = false;
    private int mModeStyle = 0;
    private Animation animSel, animation_rotate , animation_left_to_right , animation_right_to_left, animation_up_to_down, animation_down_to_up;
    private int CurrentMode;
    private boolean mBackOnFail = false;
    private long mMiliDelay = 400;
    private boolean mSetFailColor = false;
    private Context mContext;

    public SProgressButton(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public SProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public SProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    //-------------------------------------------------------

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        rootView = inflate(context, R.layout.sp_btn_base_view, this);

        mContext = context;
        mViewBase = rootView.findViewById(R.id.viewBase);
        mIT = rootView.findViewById(R.id.vIT);
        mIcon = rootView.findViewById(R.id.vIcon);
        mIconE = rootView.findViewById(R.id.vIconE);
        mText = rootView.findViewById(R.id.vText);
        mProgress = rootView.findViewById(R.id.vProgress);

        animation_rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely);
        animation_left_to_right = AnimationUtils.loadAnimation(context, R.anim.ltr_indefinitely);
        animation_right_to_left = AnimationUtils.loadAnimation(context, R.anim.rtl_indefinitely);
        animation_up_to_down = AnimationUtils.loadAnimation(context, R.anim.utd_indefinitely);
        animation_down_to_up = AnimationUtils.loadAnimation(context, R.anim.dtu_indefinitely);

//        mIconNormal = context.getResources().getDrawable(R.drawable.ic_account_circle_deep_orange_a700_24dp);
//        mIconProgress = context.getResources().getDrawable(R.drawable.ic_autorenew_yellow_a400_24dp);
//        mIconSuccess = context.getResources().getDrawable(R.drawable.ic_check_circle_green_a700_24dp);
//        mIconFail = context.getResources().getDrawable(R.drawable.ic_report_red_a700_24dp);

        if(attrs != null){
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SProgressButton, 0, 0);

            mColorSet = typedArray.getBoolean(R.styleable.SProgressButton_ColorBackSet, false);
            mColorDescSet = typedArray.getBoolean(R.styleable.SProgressButton_ColorDescSet, false);
            mProgress.setIndeterminate(typedArray.getBoolean(R.styleable.SProgressButton_Indeterminate, true));

            int atModeAnimation = typedArray.getInt(R.styleable.SProgressButton_AnimMode, 0);
            switch (atModeAnimation) {
                case 0:
                    animSel = animation_rotate;
                    break;
                case 1:
                    animSel = animation_left_to_right;
                    break;
                case 2:
                    animSel = animation_right_to_left;
                    break;
                case 3:
                    animSel = animation_up_to_down;
                    break;
                case 4:
                    animSel = animation_down_to_up;
                    break;
            }
            animSel.setDuration(typedArray.getInt(R.styleable.SProgressButton_AnimDuration, 1500));

            int atModeIconPosition = typedArray.getInt(R.styleable.SProgressButton_ModeIconPosition, 0);
            switch (atModeIconPosition){
                case 0:
                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LOCALE);
//                    mIT.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 1:
                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_RTL);
//                    mIT.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 2:
                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LTR);
//                    mIT.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case 3:
                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_LTR);
//                    mIT.setOrientation(LinearLayout.VERTICAL);
                    break;
                case 4:
                    mViewBase.setLayoutDirection(LAYOUT_DIRECTION_RTL);
//                    mIT.setOrientation(LinearLayout.VERTICAL);
                    break;
            }

            mModeStyle = typedArray.getInt(R.styleable.SProgressButton_ModeStyle, 0);

            Drawable atBackground = typedArray.getDrawable(R.styleable.SProgressButton_SrcBackground);
            if(atBackground != null) mViewBase.setBackground(atBackground);

            Drawable atIcon = typedArray.getDrawable(R.styleable.SProgressButton_IconNormal);
            if(atIcon != null) {
                mIcon.setImageDrawable(atIcon);
                mIconNormal = atIcon;
                mIconProgress = typedArray.getDrawable(R.styleable.SProgressButton_IconProgress);
                mIconSuccess = typedArray.getDrawable(R.styleable.SProgressButton_IconSuccess);
                mIconFail = typedArray.getDrawable(R.styleable.SProgressButton_IconFail);
            }
            int atIconSize = (int) typedArray.getDimension(R.styleable.SProgressButton_IconSize, 50);
            ViewGroup.LayoutParams params = mIcon.getLayoutParams();
            params.height = atIconSize;
            params.width = atIconSize;
            mIcon.setLayoutParams(params);
            ViewGroup.LayoutParams paramsE = mIconE.getLayoutParams();
            paramsE.height = atIconSize;
            paramsE.width = atIconSize;
            mIconE.setLayoutParams(paramsE);

            int atIconPadding = (int) typedArray.getDimension(R.styleable.SProgressButton_IconPadding, 0);
            mIcon.setPadding(atIconPadding, atIconPadding, atIconPadding, atIconPadding);

            int atTextPadding = typedArray.getInt(R.styleable.SProgressButton_TextPadding, 10);
            mText.setPadding(atTextPadding, atTextPadding, atTextPadding, atTextPadding);

            String atTextNormal = typedArray.getString(R.styleable.SProgressButton_TextNormal);
            if(atTextNormal != null) mTextNormal = atTextNormal;
            String atTextProgress = typedArray.getString(R.styleable.SProgressButton_TextProgress);
            if(atTextProgress != null) mTextProgress = atTextProgress;
            String atTextSuccess = typedArray.getString(R.styleable.SProgressButton_TextSuccess);
            if(atTextSuccess != null) mTextSuccess = atTextSuccess;
            String atTextFail = typedArray.getString(R.styleable.SProgressButton_TextFail);
            if(atTextFail != null) mTextFail = atTextFail;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mColorTextDefault = typedArray.getColor(R.styleable.SProgressButton_ColorText, context.getColor(mColorTextDefault));
                mColorNormal = typedArray.getColor(R.styleable.SProgressButton_ColorNormal, context.getColor(mColorNormal));
                mColorProgress = typedArray.getColor(R.styleable.SProgressButton_ColorProgress, context.getColor(mColorProgress));
                mColorSuccess = typedArray.getColor(R.styleable.SProgressButton_ColorSuccess, context.getColor(mColorSuccess));
                mColorFail = typedArray.getColor(R.styleable.SProgressButton_ColorFail, context.getColor(mColorFail));
            }

        }

        setProgress(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMinimumHeight(this.getHeight());
        this.setMinimumWidth(this.getWidth());
    }

    public SProgressButton setText(String text){
        mTextNormal = text;
        mText.setText(text);
        return this;
    }

    public SProgressButton setTextDesc(String text){
        mText.setText(text);
//        mText.setTextColor();
        return this;
    }

    public SProgressButton autoBackOnFail(boolean back, long delayMilis, boolean setFailColor){
        mBackOnFail = back;
        mMiliDelay = delayMilis;
        mSetFailColor = setFailColor;
        return this;
    }

    public SProgressButton setNormal(String text, String textDesc, Drawable drawable, int color){
        if(text != null) mTextNormal = text;
        if(textDesc != null) mDescNormal = textDesc;
        if(drawable != null) mIconNormal = drawable;
        if(color > 0) mColorNormal = color;

        mText.setText(mTextNormal);
        mIcon.setImageDrawable(mIconNormal);

        return this;
    }

    public SProgressButton setProgress(String text, String textDesc, Drawable drawable, int color){
        if(text != null) mTextProgress = text;
        if(textDesc != null) mDescProgress = textDesc;
        if(drawable != null) mIconProgress = drawable;
        if(color > 0) mColorProgress = color;
        return this;
    }

    public SProgressButton setSuccess(String text, String textDesc, Drawable drawable, int color){
        if(text != null) mTextSuccess = text;
        if(textDesc != null) mDescSuccess = textDesc;
        if(drawable != null) mIconSuccess = drawable;
        if(color > 0) mColorSuccess = color;
        return this;
    }

    public SProgressButton setFail(String text, String textDesc, Drawable drawable, int color){
        if(text != null) mTextFail = text;
        if(textDesc != null) mDescFail = textDesc;
        if(drawable != null) mIconFail = drawable;
        if(color > 0) mColorFail = color;
        return this;
    }

    public SProgressButton setTextPending(String text){
        mTextProgress = text;
        return this;
    }

    public SProgressButton setAnimateDuration(int duration){
        animSel.setDuration(duration);
        return this;
    }

    public SProgressButton setIconPadding(int padding){
        mIcon.setPadding(padding, padding, padding, padding);
        return this;
    }

    public int getCurrentMode(){
        return CurrentMode;
    }

//    public int getCurrentProgress(){
//        return mProgress.getProgress();
//    }

    public SProgressButton setProgress(int pMode){
        CurrentMode = pMode;
        int syncColor = Color.WHITE;
        switch (pMode){
            case 0: //normal
                syncColor = mColorNormal;
                mIcon.clearAnimation();
                mText.setText(mTextNormal);
                mIcon.setImageDrawable(mIconNormal);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                break;
            default: //pending
                syncColor = mColorProgress;
                mIcon.startAnimation(animSel);
                mIcon.setImageDrawable(mIconProgress);
                mText.setText(mTextProgress);
                mProgress.setProgress(pMode);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                mProgress.setVisibility(VISIBLE);
                break;
            case 100: //success
                syncColor = mColorSuccess;
                mIcon.clearAnimation();
                mIcon.setImageDrawable(mIconSuccess);
                mText.setText(mTextSuccess);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                mProgress.setVisibility(GONE);
                break;
            case -1: //fail
                syncColor = mColorFail;
                mIcon.clearAnimation();
                mIcon.setImageDrawable(mIconFail);
                mText.setText(mTextFail);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);

                if(mBackOnFail) {
                    mProgress.setVisibility(VISIBLE);
                    mProgress.setIndeterminate(false);
                    mProgress.setMaxProgress(5);
                    mProgress.setProgress(1);
//                    final int DM = 400;
                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setProgress(2);
                            mText.setText(mDescFail);
                            if(mSetFailColor) mText.setTextColor(getResources().getColor(R.color.colorFail));
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setProgress(3);
                                    handler1.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgress.setProgress(4);
                                            handler1.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mProgress.setProgress(5);
                                                    handler1.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mProgress.setIndeterminate(true);
                                                            mText.setTextColor(getResources().getColor(R.color.colorText));
                                                            setProgress(0);
                                                        }
                                                    }, mMiliDelay);
                                                }
                                            }, mMiliDelay);
                                        }
                                    }, mMiliDelay);
                                }
                            }, mMiliDelay);
                        }
                    }, mMiliDelay);
                }

                break;
        }

        if(mColorSet) {
            GradientDrawable backgroundGradient = (GradientDrawable) mViewBase.getBackground();
            backgroundGradient.setColor(syncColor);
        }

        return this;
    }

}