package com.ritesh.customformviews;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ritesh.customfieldviews.CustomDateView;
import com.ritesh.customfieldviews.CustomSpinnerView;
import com.ritesh.customfieldviews.CustomTextView;
import com.ritesh.customfieldviews.models.BaseSpinner;
import com.ritesh.customfieldviews.validators.OutputListener;
import com.ritesh.customfieldviews.validators.ServerListener;
import com.ritesh.customfieldviews.validators.ValidityClassBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ritesh on 0029, May 29, 2017.
 */

class MainPresenterImpl extends ValidityClassBase implements CustomTextView.FocusListener, CustomSpinnerView.SpinnerListener<BaseSpinner>, CustomDateView.SelectionListener, OutputListener, MainPresenter, CustomTextView.ServerValidator {
    private final MainPresenterView mView;
    private final MainPresenterMapper mMapper;

    MainPresenterImpl(MainPresenterView mView, MainPresenterMapper mMapper) {
        super();
        this.mView = mView;
        this.mMapper = mMapper;
    }

    @Override
    protected CustomTextView.FocusListener getFocusListener() {
        return this;
    }


    @Override
    protected CustomSpinnerView.SpinnerListener<BaseSpinner> getSpinnerListener() {
        return this;
    }

    @Override
    protected CustomDateView.SelectionListener getSelectionListener() {
        return this;
    }

    @Override
    protected CustomTextView.ServerValidator getServerValidator() {
        return this;
    }


    @Override
    public boolean checkValidity(CustomTextView view, String text) {
        switch (view.getId()) {
            case R.id.activity_main_text_email:
                Pattern VALID_EMAIL_ADDRESS_REGEX =
                        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
                return matcher.matches();
            case R.id.activity_main_text_async_check:
                if (text.length() >= 5) {
                    view.checkValidityWithServer();
                }
                return view.getValidity();
        }
        return false;
    }

    @Override
    public void getData(CustomSpinnerView view, BaseSpinner baseSpinner) {
        switch (view.getId()) {
            case R.id.activity_main_programmatically_load:
                break;
        }
    }

    @Override
    public boolean setDate(CustomDateView view, Calendar date) {
        switch (view.getId()) {
            case R.id.activity_main_date_of_birth:
                Calendar today = Calendar.getInstance();
                today.add(Calendar.YEAR, -18);
                return today.after(date);
        }
        return false;
    }

    @Override
    public long getMaxDate(CustomDateView view) {
        switch (view.getId()) {
            case R.id.activity_main_date_of_birth:
                Calendar today = Calendar.getInstance();
                today.add(Calendar.YEAR, -18);
                return today.getTime().getTime();
        }
        return 0;
    }

    @Override
    public void finalValidity(boolean allValid) {
        mView.setNextButton(allValid);
    }

    @Override
    public void validateFromServer(View view, final String text, final ServerListener listener) {
        switch (view.getId()) {
            case R.id.activity_main_text_async_check:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.serverTaskComplete(text.equals("Hello"));
                    }
                }, 2000);
                break;
        }
    }

    @Override
    public CustomTextView.Properties getProperties(CustomTextView view) {
        switch (view.getId()) {
            case R.id.activity_main_text_async_check:
                return new CustomTextView.Properties("Not entered \"Hello\"",
                        "Type in \"Hello\"");
        }
        return null;
    }

    @Override
    public void setData() {
        ((CustomSpinnerView) getView(R.id.activity_main_programmatically_load)).setData(new ArrayList<>(new ArrayList<BaseSpinner>() {{
            add(new BaseSpinner("Yes"));
            add(new BaseSpinner("No"));
        }}));
    }
}
