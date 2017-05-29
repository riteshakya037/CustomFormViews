package com.ritesh.customformviews;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ritesh.customfieldviews.CustomDateView;
import com.ritesh.customfieldviews.CustomSpinnerView;
import com.ritesh.customfieldviews.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenterView, MainPresenterMapper {


    @BindView(R.id.activity_main_text_email)
    CustomTextView emailView;
    @BindView(R.id.activity_main_text_async_check)
    CustomTextView textAscync;
    @BindView(R.id.activity_main_number_of_dependents)
    CustomSpinnerView noOdDependents;
    @BindView(R.id.activity_main_programmatically_load)
    CustomSpinnerView programmaticallyLoad;
    @BindView(R.id.activity_main_date_of_birth)
    CustomDateView dateOfBirth;
    @BindView(R.id.activity_main_text_validity_button)
    Button validityButton;
    private MainPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenterImpl(this, this);
        mPresenter.addValidators(mPresenter, emailView, textAscync, noOdDependents, programmaticallyLoad, dateOfBirth);
        mPresenter.setData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setNextButton(boolean allValid) {
        validityButton.setVisibility(allValid ? View.VISIBLE : View.GONE);
    }
}
