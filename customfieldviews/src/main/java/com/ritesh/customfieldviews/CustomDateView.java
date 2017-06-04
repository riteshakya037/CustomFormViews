package com.ritesh.customfieldviews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritesh.customfieldviews.validators.ValidityBase;
import com.ritesh.customfieldviews.validators.ValidityListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class CustomDateView extends LinearLayout implements ValidityBase {
    private final Context mContext;
    @BindView(R2.id.custom_date_view_validity_icon)
    public ImageView validityIcon;
    @BindView(R2.id.custom_date_view_text_hint_layout)
    protected ViewGroup txtHintLayout;
    @BindView(R2.id.custom_date_view_text_hint)
    protected TextView txtHint;
    @BindView(R2.id.custom_date_view_text_main)
    protected TextView dateText;
    private String mTitle;
    private SelectionListener mListener;
    private ValidityListener mValidityListener;
    private boolean mValidity;

    public CustomDateView(Context context) {
        super(context);
        this.mContext = context;
        init(context, "", "");
    }

    public CustomDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.custom_view);
        init(context, typedArray.getString(R.styleable.custom_view_hint), typedArray.getString(R.styleable.custom_view_title));
        typedArray.recycle();

    }

    @Optional
    @OnClick(R2.id.custom_date_view_text_main)
    protected void onClick() {
        final Calendar calendar = new GregorianCalendar();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                txtHintLayout.setVisibility(VISIBLE);
                calendar.set(i, i1, i2);
                SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                dateText.setText(format1.format(calendar.getTime()));
                if (mListener != null) {
                    mValidity = mListener.getDateValidity(CustomDateView.this, calendar);
                    validityIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), mValidity ? R.drawable.ic_check_green : R.drawable.ic_error));
                }
                if (mValidityListener != null)
                    mValidityListener.checkValidity();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (mListener != null) {
            if (mListener.setMaxDate(this) != 0)
                datePickerDialog.getDatePicker().setMaxDate(mListener.setMaxDate(this));
            if (mListener.setMinDate(this) != 0)
                datePickerDialog.getDatePicker().setMinDate(mListener.setMinDate(this));
        }
        datePickerDialog.setTitle(mTitle);
        datePickerDialog.show();
    }

    private void init(Context context, String hint, String title) {
        mTitle = title;
        View rootView = inflate(context, R.layout.custom_date_view, this);
        ButterKnife.bind(this, rootView);

        if (txtHint != null)
            txtHint.setText(hint);
        if (txtHintLayout != null)
            txtHintLayout.setVisibility(GONE);
        if (dateText != null) {
            dateText.setHint(hint);
        }
    }

    public void addSelectionListener(SelectionListener listener, ValidityListener validityListener) {
        mListener = listener;
        mValidityListener = validityListener;
    }

    @SuppressWarnings("ConstantConditions")
    public void setValue(String dateText) {
        if (!TextUtils.isEmpty(dateText)) {
            txtHintLayout.setVisibility(VISIBLE);
            if (mListener != null) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                try {
                    date = formatter.parse(dateText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                mValidity = mListener.getDateValidity(this, calendar);
                SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                this.dateText.setText(format1.format(calendar.getTime()));
                validityIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), mValidity ? R.drawable.ic_check_green : R.drawable.ic_error));
            }
        }
    }

    public String getText() {
        return dateText.getText().toString();
    }

    @Override
    public boolean getValidity() {
        return mValidity;
    }

    public interface SelectionListener {
        boolean getDateValidity(CustomDateView view, Calendar date);

        long setMaxDate(CustomDateView view);

        long setMinDate(CustomDateView view);
    }
}
