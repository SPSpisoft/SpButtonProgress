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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class SProgressButton extends RelativeLayout {
    private View rootView;
    private RelativeLayout mIT;
    private ImageView mIcon, vIconInfo;
    private View mIconE;
    private TextView mText;
    private CircularProgressView mProgress;
    private Drawable mIconNormal , mIconProgress , mIconSuccess , mIconFail , mIconInfo ;
    private String mTextNormal = "Sign in", mTextProgress = "Progress", mTextSuccess = "Success", mTextFail = "Fail", mTextInfo = "Info";
    private String mDescNormal = "", mDescProgress = "", mDescSuccess = "", mDescFail = "", mDescInfo = "";
    private int mColorTextDefault = R.color.colorText, mColorNormal = R.color.colorNormal, mColorProgress = R.color.colorProgress,
            mColorSuccess = R.color.colorSuccess, mColorFail = R.color.colorFail, mColorInfo = R.color.colorFail;
    private View mViewBase;
    private boolean mColorSet = false, mColorDescSet = false;
    private int mModeStyle = 0;
    private Animation animSel, animation_blink, animation_rotate, animation_rotate_cb , animation_left_to_right , animation_right_to_left, animation_up_to_down, animation_down_to_up;
    private int CurrentMode;
    private boolean mBackOnFail = false, mBackOnInfo = false;
    private long mMiliDelay = 400, mMiliDelayInfo = 600;
    private boolean mSetFailColor = false, mSetInfoColor = false;
    private Context mContext;
    private OnInfoClickListener mInfoClickListener;
    private boolean mInfoKeyShowOnStable;

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
    private void initView(final Context context, AttributeSet attrs, int defStyleAttr) {
        rootView = inflate(context, R.layout.sp_btn_base_view, this);

        mContext = context;
        mViewBase = rootView.findViewById(R.id.viewBase);
        mIT = rootView.findViewById(R.id.vIT);
        mIcon = rootView.findViewById(R.id.vIcon);
        vIconInfo = rootView.findViewById(R.id.vIconInfo);
        mIconE = rootView.findViewById(R.id.vIconE);
        mText = rootView.findViewById(R.id.vText);
        mProgress = rootView.findViewById(R.id.vProgress);

        animation_rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely);
        animation_rotate_cb = AnimationUtils.loadAnimation(context, R.anim.rotate_top_to_button);
        animation_blink = AnimationUtils.loadAnimation(context, R.anim.blink);
        animation_left_to_right = AnimationUtils.loadAnimation(context, R.anim.ltr_indefinitely);
        animation_right_to_left = AnimationUtils.loadAnimation(context, R.anim.rtl_indefinitely);
        animation_up_to_down = AnimationUtils.loadAnimation(context, R.anim.utd_indefinitely);
        animation_down_to_up = AnimationUtils.loadAnimation(context, R.anim.dtu_indefinitely);

        vIconInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInfoClickListener != null) mInfoClickListener.onEvent();
            }
        });
//        mIconNormal = context.getResources().getDrawable(R.drawable.ic_account_circle_deep_orange_a700_24dp);
//        mIconProgress = context.getResources().getDrawable(R.drawable.ic_autorenew_yellow_a400_24dp);
//        mIconSuccess = context.getResources().getDrawable(R.drawable.ic_check_circle_green_a700_24dp);
//        mIconFail = context.getResources().getDrawable(R.drawable.ic_report_red_a700_24dp);
        mText.setSelected(true);

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
                mIconInfo = typedArray.getDrawable(R.styleable.SProgressButton_IconInfo);
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

    public SProgressButton setInfoKeyShow(boolean infoKeyShow, Drawable changeIcon){
        if(infoKeyShow) {
            mProgress.setVisibility(GONE);
            vIconInfo.setVisibility(VISIBLE);
        }
        else vIconInfo.setVisibility(GONE);
        if(changeIcon != null) vIconInfo.setImageDrawable(changeIcon);
        return this;
    }

    public SProgressButton setInfoKeyShowOnStable(boolean infoKeyShowOnStable, Drawable changeIcon){
        mInfoKeyShowOnStable = infoKeyShowOnStable;
        if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
        if(changeIcon != null) vIconInfo.setImageDrawable(changeIcon);
        return this;
    }

    public interface OnInfoClickListener {
        void onEvent();
    }

    public void setOnInfoClickListener(OnInfoClickListener eventListener) {
        mInfoClickListener = eventListener;
    }

    public SProgressButton autoBackOnFail(boolean back, long delayMilis, boolean setFailColor){
        mBackOnFail = back;
        mMiliDelay = delayMilis;
        mSetFailColor = setFailColor;
        return this;
    }

    public SProgressButton autoBackOnInfo(boolean back, long delayMilisInfo, boolean setInfoColor){
        mBackOnInfo = back;
        mMiliDelayInfo = delayMilisInfo;
        mSetInfoColor = setInfoColor;
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

    public SProgressButton setInfo(String text, String textDesc, Drawable drawable, int color){
        if(text != null) mTextInfo = text;
        if(textDesc != null) mDescInfo = textDesc;
        if(drawable != null) mIconInfo = drawable;
        if(color > 0) mColorInfo = color;
        return this;
    }

    public SProgressButton setTextDesc(String text, boolean OnSet){
        if(OnSet) mText.setText(text);
        mDescFail = text;
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
                mText.setTextColor(getResources().getColor(R.color.colorText));
                vIconInfo.setImageResource(R.drawable.ic_action_info);
                vIconInfo.clearAnimation();

                syncColor = mColorNormal;
                mIcon.clearAnimation();
                mText.setText(mTextNormal);
                mIcon.setImageDrawable(mIconNormal);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                if(mInfoKeyShowOnStable)
                    vIconInfo.setVisibility(VISIBLE);
                break;
            default: //pending
                mText.setTextColor(getResources().getColor(R.color.colorText));
                vIconInfo.setImageResource(R.drawable.ic_action_info);
                vIconInfo.clearAnimation();

                syncColor = mColorProgress;
                mIcon.startAnimation(animSel);
                mIcon.setImageDrawable(mIconProgress);
                mText.setText(mTextProgress);
                mProgress.setProgress(pMode);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                vIconInfo.setVisibility(GONE);
                mProgress.setVisibility(VISIBLE);
                break;
            case 100: //success
                mText.setTextColor(getResources().getColor(R.color.colorText));
                vIconInfo.setImageResource(R.drawable.ic_action_info);
                vIconInfo.clearAnimation();

                syncColor = mColorSuccess;
                mIcon.clearAnimation();
                mIcon.setImageDrawable(mIconSuccess);
                mText.setText(mTextSuccess);
                if(mModeStyle > 0) mIT.setVisibility(GONE);
                mProgress.setVisibility(GONE);
                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);
                break;
            case -1: //fail
                syncColor = mColorFail;
                mIcon.clearAnimation();
                mIcon.setImageDrawable(mIconFail);
                mText.setText(mTextFail);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);

                if(mBackOnFail) {
                    vIconInfo.setVisibility(VISIBLE);
                    mProgress.setVisibility(GONE);

                    animation_rotate_cb.setDuration(mMiliDelay);
                    vIconInfo.startAnimation(animation_rotate_cb);
                    animation_rotate_cb.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            mText.setText(mDescFail);
                            vIconInfo.setImageResource(R.drawable.ic_action_error);
                            if(mSetFailColor) mText.setTextColor(getResources().getColor(R.color.colorFail));
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            setProgress(0);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
                break;
            case 101: //info
                syncColor = mColorProgress;
                mIcon.clearAnimation();
                mText.setText(mTextInfo);
                mIT.setVisibility(VISIBLE);
                mProgress.setVisibility(GONE);
                if(mInfoKeyShowOnStable) vIconInfo.setVisibility(VISIBLE);

                if(mBackOnInfo) {
                    vIconInfo.setVisibility(VISIBLE);
                    mProgress.setVisibility(GONE);

                    animation_blink.setDuration(mMiliDelayInfo);
                    vIconInfo.startAnimation(animation_blink);
                    animation_blink.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            mText.setText(mDescInfo);
                            vIconInfo.setImageResource(R.drawable.ic_action_info);
                            if(mSetInfoColor) mText.setTextColor(getResources().getColor(R.color.colorInfo));
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            CurrentMode = 0;
                            mText.setText(mTextNormal);
                            mText.setTextColor(getResources().getColor(R.color.colorText));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

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