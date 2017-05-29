package com.ritesh.customfieldviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritesh.customfieldviews.commons.StringUtils;
import com.ritesh.customfieldviews.validators.ServerListener;
import com.ritesh.customfieldviews.validators.ValidityBase;
import com.ritesh.customfieldviews.validators.ValidityListener;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Optional;


/**
 * @author Ritesh Shakya
 */
public class CustomTextView extends LinearLayout implements ValidityBase, ServerListener {
    @BindView(R2.id.custom_text_view_text_hint_layout)
    ViewGroup txtHintLayout;
    @BindView(R2.id.custom_text_view_validity_icon)
    ImageView validityIcon;
    @BindView(R2.id.custom_text_view_validity_animation)
    AVLoadingIndicatorView validityAnimation;
    @BindView(R2.id.custom_text_view_text_hint)
    TextView txtHint;
    @BindView(R2.id.custom_text_view_error_view)
    TextView errorView;
    @BindView(R2.id.custom_text_view_text_main)
    EditText inputEditText;

    private FocusListener mListener;
    private ValidityListener mValidityListener;
    private ServerValidator mServerValidator;
    private boolean validity;
    private String text;
    private boolean dontCallback = false;
    private String mHint;
    private boolean checkServerContinuously = false;
    private boolean clearing = false;

    public CustomTextView(Context context) {
        super(context);
        init(context, "", "", InputType.TYPE_CLASS_TEXT, true, false);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.custom_view);
        init(context, typedArray.getString(R.styleable.custom_view_hint), typedArray.getString(R.styleable.custom_view_text), typedArray.getInt(R.styleable.custom_view_android_inputType, 0), typedArray.getBoolean(R.styleable.custom_view_editable, true), typedArray.getBoolean(R.styleable.custom_view_password, false));
        typedArray.recycle();

    }

    @Nullable
    @OnTextChanged(value = R2.id.custom_text_view_text_main, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void nameChanged(CharSequence text) {
        if (mListener != null && !dontCallback && !clearing) {
            validity = mListener.checkValidity(CustomTextView.this, text.toString());
            validityIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), validity ? R.drawable.ic_check_green : R.drawable.ic_error));
        }
        if (mValidityListener != null) {
            mValidityListener.checkValidity();
        }
        if (checkServerContinuously && mServerValidator != null) {
            checkValidityWithServer();
        } else {
            validityIcon.setVisibility(text.length() == 0 ? GONE : VISIBLE);
        }
        clearing = false;
    }

    @Optional
    @SuppressWarnings("ResourceType")
    @OnFocusChange(R2.id.custom_text_view_text_main)
    void inputFocusChanged(boolean hasFocus) {
        boolean condition = hasFocus || !TextUtils.isEmpty(inputEditText.getText());
        txtHintLayout.setVisibility(condition ? VISIBLE : GONE);
        validityIcon.setVisibility(!TextUtils.isEmpty(inputEditText.getText().toString()) && !checkServerContinuously ? VISIBLE : GONE);
        inputEditText.setHint(condition ? "" : txtHint.getText());
        if (!hasFocus) {
            if (StringUtils.isNull(inputEditText.getText().toString())) {
                clearing = true;
                reset();
            }
        }
    }

    public void checkValidityWithServer() {
        mServerValidator.validateFromServer(CustomTextView.this, inputEditText.getText().toString(), this);
        validityIcon.setVisibility(GONE);
        validityAnimation.setVisibility(VISIBLE);
        checkServerContinuously = true;
    }

    private void showValidityResponse() {
        Properties properties = mServerValidator.getProperties(CustomTextView.this);
        if (!validity) {
            errorView.setVisibility(VISIBLE);
            errorView.setTextColor(Color.parseColor("#e03d46"));
            errorView.setText(properties.getErrorMessage());
        } else {
            if (properties.getNonErrorMessage() == null) {
                errorView.setVisibility(GONE);
            } else {
                errorView.setTextColor(Color.parseColor("#8e86a2"));
                errorView.setText(properties.getNonErrorMessage());
            }
        }
        validityIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), validity ? R.drawable.ic_check_green : R.drawable.ic_error));
    }

    private void init(Context context, String hint, String text, int inputType, boolean editable, boolean password) {
        mHint = hint;
        View rootView = getView(context);
        if (!isInEditMode()) {
            ButterKnife.bind(this, rootView);

            if (txtHint != null)
                txtHint.setText(hint);
            if (txtHintLayout != null)
                txtHintLayout.setVisibility(GONE);
            if (inputEditText != null) {
                inputEditText.setHint(hint);
                inputEditText.setEnabled(editable);
                inputEditText.setText(text);
                if (password) {
                    inputEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                    inputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inputEditText.setInputType(inputType);
                }
            }
        }
    }

    View getView(Context context) {
        return inflate(context, R.layout.custom_text_view, this);
    }

    public void addFocusValidator(FocusListener listener, ValidityListener validityListener) {
        addFocusValidator(listener, validityListener, null);
    }

    public void addFocusValidator(FocusListener listener, ValidityListener validityListener, ServerValidator serverValidator) {
        mListener = listener;
        mValidityListener = validityListener;
        mServerValidator = serverValidator;
    }

    public String getText() {
        return inputEditText.getText().toString();
    }

    public void setText(String text) {
        setText(text, false);
    }

    public void setText(String text, boolean dontCallback) {
        this.dontCallback = dontCallback;
        if (!TextUtils.isEmpty(text) || dontCallback) {
            inputEditText.setText(text);
            txtHintLayout.setVisibility(VISIBLE);
            validityIcon.setVisibility(VISIBLE);
        }
        this.dontCallback = false;
    }

    @Override
    public boolean getValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
        validityIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), validity ? R.drawable.ic_check_green : R.drawable.ic_error));
        if (mValidityListener != null)
            mValidityListener.checkValidity();
    }

    private void setPositionLast() {
        inputEditText.setSelection(inputEditText.getText().length());
    }

    private void reset() {
        inputEditText.setText("");
        inputEditText.setHint(mHint);
        txtHintLayout.setVisibility(GONE);
        validityIcon.setVisibility(GONE);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        inputEditText.setEnabled(enabled);
    }

    public void setCheckServerContinuously(boolean checkServerContinuously) {
        this.checkServerContinuously = checkServerContinuously;
    }

    @Override
    public void serverTaskComplete(boolean success) {
        validityIcon.setVisibility(VISIBLE);
        checkServerContinuously = false;
        validityAnimation.setVisibility(GONE);
        validity = success;
//        if (mValidityListener != null) {
//            mValidityListener.checkValidity();
//        }
        showValidityResponse();
    }

    public interface FocusListener {
        boolean checkValidity(CustomTextView view, String text);

    }

    public interface ServerValidator {
        void validateFromServer(View view, String text, ServerListener listener);

        CustomTextView.Properties getProperties(CustomTextView view);
    }

    public static class Properties {
        String errorMessage;
        String nonErrorMessage;

        public Properties(String errorMessage, String nonErrorMessage) {
            this.errorMessage = errorMessage;
            this.nonErrorMessage = nonErrorMessage;
        }


        String getNonErrorMessage() {
            return nonErrorMessage;
        }

        String getErrorMessage() {
            return errorMessage;
        }
    }
}
