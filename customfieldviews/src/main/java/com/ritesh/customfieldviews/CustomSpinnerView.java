package com.ritesh.customfieldviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ritesh.customfieldviews.adaptors.NothingSelectedSpinnerAdapter;
import com.ritesh.customfieldviews.models.BaseSpinner;
import com.ritesh.customfieldviews.validators.ValidityBase;
import com.ritesh.customfieldviews.validators.ValidityListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Optional;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess")
public class CustomSpinnerView extends LinearLayout implements ValidityBase {
    @BindView(R2.id.custom_spinner_view_text_hint_layout)
    protected ViewGroup txtHintLayout;
    @BindView(R2.id.custom_spinner_view_text_hint)
    protected TextView txtHint;

    @Nullable
    @BindView(R2.id.custom_spinner_view_spinner_main)
    Spinner mSpinner;
    private Context mContext;
    private String mHint;
    private List<BaseSpinner> mData = new ArrayList<>();
    private SpinnerListener<BaseSpinner> mListener;
    private ValidityListener mValidityListener;
    private boolean mValidity;

    public CustomSpinnerView(Context context) {
        super(context);
        init(context, "", new CharSequence[]{});
    }

    public CustomSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.custom_view);
        init(context, typedArray.getString(R.styleable.custom_view_hint), typedArray.getTextArray(R.styleable.custom_view_android_entries));
        typedArray.recycle();

    }

    @Optional
    @OnClick(R2.id.custom_spinner_view_root_view)
    protected void onClickDrop() {
        if (mSpinner != null) {
            mSpinner.performClick();
        }
    }

    @Optional
    @OnItemSelected(R2.id.custom_spinner_view_spinner_main)
    protected void onItemSelected(int position) {
        if (position > 0 && txtHintLayout != null) {
            mValidity = true;
            txtHintLayout.setVisibility(VISIBLE);
            if (mListener != null)
                mListener.getSpinnerValidity(CustomSpinnerView.this, mData.get(position - 1));
            if (mValidityListener != null)
                mValidityListener.checkValidity();
        }
    }

    private void init(Context context, String hint, CharSequence[] textArray) {
        mContext = context;
        mHint = hint;

        View rootView = inflate(context, R.layout.custom_spinner_view, this);
        ButterKnife.bind(this, rootView);

        ArrayAdapter<BaseSpinner> adapter;
        if (textArray != null && textArray.length > 0) {
            for (CharSequence sequence : textArray) {
                mData.add(new BaseSpinner(sequence.toString(), sequence.toString().equals("0") ? "None" : sequence.toString()));
            }
            adapter = new ArrayAdapter<>(mContext, R.layout.spinner_row_selected, mData);
            adapter.setDropDownViewResource(R.layout.spinner_row_dropdown);
            if (mSpinner != null) {
                mSpinner.setAdapter(
                        new NothingSelectedSpinnerAdapter(
                                adapter,
                                R.layout.spinner_row_nothing_selected,
                                context, hint));
            }
        }
        if (txtHint != null)
            txtHint.setText(hint);
        if (txtHintLayout != null)
            txtHintLayout.setVisibility(GONE);
    }

    @SuppressWarnings("ConstantConditions")
    public void setData(List<BaseSpinner> data) {
        mData = data;
        ArrayAdapter<BaseSpinner> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_row_selected, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.spinner_row_nothing_selected,
                        mContext, mHint));
    }

    @SuppressWarnings("ConstantConditions")
    public void setSelection(String id) {
        if (!TextUtils.isEmpty(id) && mData.contains(new BaseSpinner(id, ""))) {
            int pos = mData.indexOf(new BaseSpinner(id, ""));
            mSpinner.setSelection(pos + 1);
            onItemSelected(pos + 1);
        }
    }

    public void addListener(SpinnerListener<BaseSpinner> listener, ValidityListener validityListener) {
        this.mListener = listener;
        mValidityListener = validityListener;
    }

    @SuppressWarnings("ConstantConditions")
    public BaseSpinner getValue() {
        return mSpinner.getSelectedItem() == null ? new BaseSpinner("", "") : (BaseSpinner) (mSpinner.getSelectedItem());
    }

    @Override
    public boolean getValidity() {
        return mValidity;
    }

    public interface SpinnerListener<T extends BaseSpinner> {
        void getSpinnerValidity(CustomSpinnerView view, T baseSpinner);
    }
}
